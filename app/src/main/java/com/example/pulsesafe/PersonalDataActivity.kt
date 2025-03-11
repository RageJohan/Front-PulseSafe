package com.example.pulsesafe

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pulsesafe.api.ApiService
import com.example.pulsesafe.api.RetrofitClient
import com.example.pulsesafe.utils.SessionManager
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonalDataActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        // Configurar la barra de estado
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // Inicializar ApiService y SessionManager
        apiService = RetrofitClient.instance
        sessionManager = SessionManager(this)

        // Configurar botón de retroceso
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // Configurar botón de editar foto
        findViewById<ImageView>(R.id.editProfileButton).setOnClickListener {
            // Aquí iría la lógica para cambiar la foto de perfil
            Toast.makeText(this, "Cambiar foto de perfil", Toast.LENGTH_SHORT).show()
        }

        // Mostrar datos del usuario
        mostrarDatosUsuario()

        // Configurar botón de actualizar
        findViewById<MaterialButton>(R.id.updateButton).setOnClickListener {
            actualizarPerfil()
        }
    }

    private fun mostrarDatosUsuario() {
        val nombre = sessionManager.getUserName()
        val correo = sessionManager.getUserEmail()

        findViewById<EditText>(R.id.nameEditText).setText(nombre)
        findViewById<EditText>(R.id.emailEditText).setText(correo)

        // Aquí puedes agregar lógica para mostrar el número de emergencia si lo tienes almacenado
    }

    private fun actualizarPerfil() {
        val nombre = findViewById<EditText>(R.id.nameEditText).text.toString()
        val numeroEmergencia = findViewById<EditText>(R.id.emergencyNumberEditText).text.toString()
        val correo = findViewById<EditText>(R.id.emailEditText).text.toString()

        val datosActualizados = mapOf(
            "nombre" to nombre,
            "numeroEmergencia" to numeroEmergencia,
            "correo" to correo
        )

        val idUsuario = sessionManager.getUserId()

        apiService.actualizarUsuario(idUsuario, datosActualizados).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Actualizar los datos en SessionManager
                    sessionManager.saveUser(idUsuario, nombre, correo)
                    Toast.makeText(this@PersonalDataActivity, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@PersonalDataActivity, "Error al actualizar el perfil", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@PersonalDataActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}