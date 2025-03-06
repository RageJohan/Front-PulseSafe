package com.example.pulsesafe

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        // Configurar la barra de estado
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // Configurar botón de retroceso
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // Configurar botón de guardar
        findViewById<MaterialButton>(R.id.saveButton).setOnClickListener {
            if (validatePasswords()) {
                // Aquí iría la lógica para cambiar la contraseña
                Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun validatePasswords(): Boolean {
        val currentPassword = findViewById<TextInputEditText>(R.id.currentPasswordEditText).text.toString()
        val newPassword = findViewById<TextInputEditText>(R.id.newPasswordEditText).text.toString()
        val confirmPassword = findViewById<TextInputEditText>(R.id.confirmPasswordEditText).text.toString()

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }

        if (newPassword != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}