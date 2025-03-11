package com.example.pulsesafe
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pulsesafe.R
import com.example.pulsesafe.api.RetrofitClient
import com.example.pulsesafe.model.Notificacion
import com.example.pulsesafe.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AlertsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var notificationContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts)

        // Usa SessionManager en lugar de SharedPreferences manual
        val sessionManager = SessionManager(this)
        val idUsuario = sessionManager.getUserId()
        Log.d("AlertsActivity", "ID de usuario obtenido de SessionManager: $idUsuario") // <-- Imprime el ID obtenido


        notificationContainer = findViewById(R.id.notificationContainer)

        if (idUsuario != 0L) {  // 0L es el valor por defecto en getUserId()
            obtenerNotificaciones(idUsuario)
        } else {
            Log.e("AlertsActivity", "ID de usuario no encontrado en SessionManager")
            Toast.makeText(this, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show()
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.navigation_home // Seleccionar "Inicio" por defecto

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Ya estamos en la vista de inicio
                    true
                }
                R.id.navigation_pressure -> {
                    // Navegar a la vista de presión
                    startActivity(Intent(this, PressureActivity::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    // Navegar a la vista de perfil
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.navigation_alerts -> {
                    // Navegar a la vista de alertas
                    startActivity(Intent(this, AlertsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun obtenerTextoFecha(fecha: String): String {
        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaNotificacion = formato.parse(fecha)
        val hoy = Calendar.getInstance()
        val ayer = Calendar.getInstance()
        ayer.add(Calendar.DAY_OF_YEAR, -1)

        return when {
            formato.format(hoy.time) == fecha -> "Hoy"
            formato.format(ayer.time) == fecha -> "Ayer"
            else -> SimpleDateFormat("dd 'de' MMMM", Locale("es")).format(fechaNotificacion)
        }
    }

    private fun obtenerNotificaciones(idUsuario: Long) {
        RetrofitClient.instance.getNotificaciones(idUsuario).enqueue(object : Callback<List<Notificacion>> {
            override fun onResponse(call: Call<List<Notificacion>>, response: Response<List<Notificacion>>) {
                if (response.isSuccessful) {
                    val notificaciones = response.body()
                    if (!notificaciones.isNullOrEmpty()) {
                        mostrarNotificaciones(notificaciones)
                    } else {
                        mostrarMensaje("No hay notificaciones")
                    }
                } else {
                    Log.e("AlertsActivity", "Error en la respuesta del servidor: ${response.code()}")
                    mostrarMensaje("Error al obtener notificaciones")
                }
            }

            override fun onFailure(call: Call<List<Notificacion>>, t: Throwable) {
                Log.e("AlertsActivity", "Error en la conexión con la API", t)
                mostrarMensaje("Error de conexión")
            }
        })
    }
    private fun formatearFecha(timestamp: String): String {
        return timestamp.replace("T", " ").substring(0, 16) // Quita "T" y deja "YYYY-MM-DD HH:mm"
    }

    private fun mostrarNotificaciones(notificaciones: List<Notificacion>) {
        runOnUiThread {
            notificationContainer.visibility = View.VISIBLE
            notificationContainer.removeAllViews()

            // Agrupar por fecha
            val notificacionesAgrupadas = notificaciones.groupBy { it.timestamp.substring(0, 10) }

            for ((fecha, listaNotificaciones) in notificacionesAgrupadas.toSortedMap(compareByDescending { it })) {
                for (notificacion in listaNotificaciones.asReversed()) {

                // Crear un TextView para la fecha
                val fechaTextView = TextView(this).apply {
                    text = obtenerTextoFecha(fecha)
                    textSize = 16f
                    setTextColor(resources.getColor(R.color.colorPrimary))
                    setPadding(16, 8, 16, 8)
                }
                notificationContainer.addView(fechaTextView)

                // Agregar notificaciones de esta fecha
// Agregar notificaciones de esta fecha
                for (notificacion in listaNotificaciones) {
                    val itemView = layoutInflater.inflate(R.layout.item_notification, null)

                    val tvTitulo = itemView.findViewById<TextView>(R.id.notificationTitle)
                    val tvDescripcion = itemView.findViewById<TextView>(R.id.notificationDescription)
                    val tvHora = itemView.findViewById<TextView>(R.id.notificationTime)

                    tvTitulo.text = notificacion.tipo_alerta ?: "Alerta"
                    tvDescripcion.text = notificacion.mensaje ?: "Sin descripción"
                    tvHora.text = notificacion.timestamp ?: "00:00"

                    notificationContainer.addView(itemView)
                }

            } }
        }
    }


    private fun mostrarMensaje(mensaje: String) {
        runOnUiThread {
            notificationContainer.removeAllViews()
            val textView = TextView(this).apply {
                text = mensaje
                textSize = 18f
                setPadding(16, 16, 16, 16)
            }
            notificationContainer.addView(textView)
        }
    }
}
