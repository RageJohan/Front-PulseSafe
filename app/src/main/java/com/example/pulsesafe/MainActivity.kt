package com.example.pulsesafe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar la barra de estado
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // Configurar botón de ajustes
        findViewById<ImageButton>(R.id.settingsButton).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Configurar tarjetas expandibles
        setupExpandableCard(
            R.id.cardiologiaCard,
            R.id.cardiologiaArrow,
            R.id.cardiologiaDescription
        )

        setupExpandableCard(
            R.id.medicinaCard,
            R.id.medicinaArrow,
            R.id.medicinaDescription
        )

        setupExpandableCard(
            R.id.nutricionCard,
            R.id.nutricionArrow,
            R.id.nutricionDescription
        )

        setupExpandableCard(
            R.id.neumologiaCard,
            R.id.neumologiaArrow,
            R.id.neumologiaDescription
        )

        // Configurar navegación inferior
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

    private fun setupExpandableCard(cardId: Int, arrowId: Int, descriptionId: Int) {
        val card = findViewById<CardView>(cardId)
        val arrow = findViewById<ImageView>(arrowId)
        val description = findViewById<TextView>(descriptionId)

        card.setOnClickListener {
            if (description.visibility == View.VISIBLE) {
                description.visibility = View.GONE
                arrow.rotation = 0f
            } else {
                description.visibility = View.VISIBLE
                arrow.rotation = 180f
            }
        }
    }
}