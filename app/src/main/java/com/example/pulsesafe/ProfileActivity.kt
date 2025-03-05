package com.example.pulsesafe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Configurar la barra de estado
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // Configurar navegación inferior
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.navigation_profile // Seleccionar "Perfil" por defecto

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Navegar a la vista de inicio
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // Cerrar esta actividad para no acumular actividades en la pila
                    true
                }
                R.id.navigation_pressure -> {
                    // Navegar a la vista de presión
                    startActivity(Intent(this, PressureActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_profile -> {
                    // Ya estamos en la vista de perfil
                    true
                }
                R.id.navigation_alerts -> {
                    // Navegar a la vista de alertas
                    startActivity(Intent(this, AlertsActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}