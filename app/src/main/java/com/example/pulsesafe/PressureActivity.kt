package com.example.pulsesafe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.temporal.ChronoUnit

class PressureActivity : AppCompatActivity() {
    private lateinit var healthClient: HealthConnectClient
    private lateinit var heartRateTextView: TextView
    private var updateJob: Job? = null // Job para la actualización periódica

    // Conjunto de permisos necesarios
    private val PERMISSIONS = setOf(
        HealthPermission.getReadPermission(HeartRateRecord::class),
        HealthPermission.getWritePermission(HeartRateRecord::class)
    )

    // Lanzador de permisos
    private val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        if (result.all { it.value }) {
            // Permisos otorgados
            Toast.makeText(this, "Permisos otorgados", Toast.LENGTH_SHORT).show()
            startPeriodicUpdate() // Iniciar la actualización periódica
        } else {
            // Permisos denegados
            Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pressure)

        // Configurar la barra de estado
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // Configurar botón de retroceso
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // Inicializar el cliente de Health Connect
        healthClient = HealthConnectClient.getOrCreate(this)

        // Inicializar TextView para mostrar la frecuencia cardíaca
        heartRateTextView = findViewById(R.id.heartRateTextView)

        // Verificar y solicitar permisos
        CoroutineScope(Dispatchers.Main).launch {
            checkPermissionsAndRun(healthClient)
        }

        // Configurar navegación inferior
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.navigation_pressure

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_pressure -> true
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_alerts -> {
                    startActivity(Intent(this, AlertsActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    // Función para verificar permisos y ejecutar la lógica
    private suspend fun checkPermissionsAndRun(healthConnectClient: HealthConnectClient) {
        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        if (granted.containsAll(PERMISSIONS)) {
            // Permisos ya otorgados
            Toast.makeText(this, "Permisos ya otorgados", Toast.LENGTH_SHORT).show()
            startPeriodicUpdate() // Iniciar la actualización periódica
        } else {
            // Solicitar permisos
            println("Solicitando permisos...")  // Log de depuración
            requestPermissions.launch(PERMISSIONS.toTypedArray())
        }
    }

    // Función para leer la frecuencia cardíaca
    private suspend fun readHeartRate() {
        val now = Instant.now()
        val request = ReadRecordsRequest(
            HeartRateRecord::class,
            timeRangeFilter = TimeRangeFilter.between(now.minus(1, ChronoUnit.HOURS), now)
        )

        try {
            // Leer los registros de frecuencia cardíaca
            val response = healthClient.readRecords(request)
            val latestHeartRate = response.records.lastOrNull()?.samples?.lastOrNull()?.beatsPerMinute

            // Actualizar la UI en el hilo principal
            withContext(Dispatchers.Main) {
                heartRateTextView.text = latestHeartRate?.toString() ?: "No disponible"
            }
        } catch (e: Exception) {
            // Manejar errores
            withContext(Dispatchers.Main) {
                Toast.makeText(this@PressureActivity, "Error al leer la frecuencia cardíaca: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para iniciar la actualización periódica
    private fun startPeriodicUpdate() {
        updateJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                readHeartRate() // Leer la frecuencia cardíaca
                delay(2000) // Esperar 5 segundos antes de la próxima actualización
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Detener la actualización periódica cuando la actividad se destruye
        updateJob?.cancel()
    }
}