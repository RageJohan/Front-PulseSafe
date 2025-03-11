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
    private var updateJob: Job? = null

    private val PERMISSIONS = setOf(
        HealthPermission.getReadPermission(HeartRateRecord::class),
        HealthPermission.getWritePermission(HeartRateRecord::class)
    )

    private val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        if (result.all { it.value }) {
            Toast.makeText(this, "Permisos otorgados", Toast.LENGTH_SHORT).show()
            startPeriodicUpdate()
        } else {
            Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pressure)

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        healthClient = HealthConnectClient.getOrCreate(this)


        heartRateTextView = findViewById(R.id.heartRateTextView)


        CoroutineScope(Dispatchers.Main).launch {
            checkPermissionsAndRun(healthClient)
        }


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


    private suspend fun checkPermissionsAndRun(healthConnectClient: HealthConnectClient) {
        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        if (granted.containsAll(PERMISSIONS)) {

            Toast.makeText(this, "Permisos ya otorgados", Toast.LENGTH_SHORT).show()
            startPeriodicUpdate()
        } else {

            println("Solicitando permisos...")
            requestPermissions.launch(PERMISSIONS.toTypedArray())
        }
    }


    private suspend fun readHeartRate() {
        val now = Instant.now()
        val request = ReadRecordsRequest(
            HeartRateRecord::class,
            timeRangeFilter = TimeRangeFilter.between(now.minus(1, ChronoUnit.HOURS), now)
        )

        try {
            val response = healthClient.readRecords(request)
            val latestHeartRate = response.records.lastOrNull()?.samples?.lastOrNull()?.beatsPerMinute

            withContext(Dispatchers.Main) {
                heartRateTextView.text = latestHeartRate?.toString() ?: "No disponible"
            }
        } catch (e: Exception) {

            withContext(Dispatchers.Main) {
                Toast.makeText(this@PressureActivity, "Error al leer la frecuencia cardíaca: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun startPeriodicUpdate() {
        updateJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                readHeartRate()
                delay(2000)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        updateJob?.cancel()
    }
}