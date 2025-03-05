package com.example.pulsesafe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class AlertsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts)

        // Configurar la barra de estado
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // Configurar botón de retroceso
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // Configurar las notificaciones
        setupNotifications()

        // Configurar navegación inferior
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.navigation_alerts

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_pressure -> {
                    startActivity(Intent(this, PressureActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_alerts -> true
                else -> false
            }
        }
    }

    private fun setupNotifications() {
        // Configurar las notificaciones de hoy
        setupNotification(
            R.id.notification1,
            "Tu Presión Arterial Estuvo Dentro De Los Niveles Normales Durante El Día",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "2 Min",
            true
        )

        setupNotification(
            R.id.notification2,
            "Realizaste 3 Medicaciones Con Resultados Estables",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "2 Hr",
            true
        )

        setupNotification(
            R.id.notification3,
            "Frecuencia Cardiaca Estable: 75 BPM",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "3 Hr",
            true
        )

        // Configurar la notificación de ayer
        setupNotification(
            R.id.notification4,
            "Nivel Alto De Presión Arterial",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "1 D",
            false
        )

        // Configurar la notificación del 15 de enero
        setupNotification(
            R.id.notification5,
            "No Se Detectaron Anomalías En Tu Presión Arterial",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "5 D",
            false
        )
    }

    private fun setupNotification(
        viewId: Int,
        title: String,
        description: String,
        time: String,
        isRed: Boolean
    ) {
        val view = findViewById<View>(viewId)
        view.findViewById<TextView>(R.id.notificationTitle).text = title
        view.findViewById<TextView>(R.id.notificationDescription).text = description
        view.findViewById<TextView>(R.id.notificationTime).text = time

        val icon = view.findViewById<ImageView>(R.id.notificationIcon)
        icon.backgroundTintList = ContextCompat.getColorStateList(
            this,
            if (isRed) R.color.colorPrimary else R.color.pressureCardBackground
        )
    }
}