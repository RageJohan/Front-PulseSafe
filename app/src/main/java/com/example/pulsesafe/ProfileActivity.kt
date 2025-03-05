package com.example.pulsesafe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
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

        // Configurar botón de retroceso
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // Configurar opciones del menú
        findViewById<LinearLayout>(R.id.profileOption).setOnClickListener {
            startActivity(Intent(this, PersonalDataActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.privacyOption).setOnClickListener {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.settingsOption).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.helpOption).setOnClickListener {
            startActivity(Intent(this, HelpActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.logoutOption).setOnClickListener {
            // Implementar lógica de cierre de sesión
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity() // Cierra todas las actividades anteriores
        }

        // Configurar navegación inferior
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.navigation_profile

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
                R.id.navigation_profile -> true
                R.id.navigation_alerts -> {
                    startActivity(Intent(this, AlertsActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}