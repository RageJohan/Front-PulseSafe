package com.example.pulsesafe

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class PersonalDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        // Configurar la barra de estado
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // Configurar botón de retroceso
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // Configurar botón de editar foto
        findViewById<ImageView>(R.id.editProfileButton).setOnClickListener {
            // Aquí iría la lógica para cambiar la foto de perfil
            Toast.makeText(this, "Cambiar foto de perfil", Toast.LENGTH_SHORT).show()
        }

        // Configurar botón de actualizar
        findViewById<MaterialButton>(R.id.updateButton).setOnClickListener {
            // Aquí iría la lógica para actualizar los datos del perfil
            Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}