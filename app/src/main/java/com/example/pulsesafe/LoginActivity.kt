package com.example.pulsesafe

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar vistas
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val forgotPasswordTextView = findViewById<TextView>(R.id.forgotPasswordTextView)
        val loginButton = findViewById<MaterialButton>(R.id.loginButton)
        val registerLinkTextView = findViewById<TextView>(R.id.registerLinkTextView)

        // Configurar botón de retroceso
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Configurar botón de login
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (validateInputs(email, password)) {
                // Redirigir a MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Cierra la actividad de login para que no vuelva atrás
            }
        }

        // Configurar enlace a registro
        registerLinkTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Configurar enlace de olvidé mi contraseña
        forgotPasswordTextView.setOnClickListener {
            // Aquí iría la lógica para recuperar contraseña
            // Por ahora, solo mostramos un mensaje
            // Toast.makeText(this, "Funcionalidad de recuperar contraseña", Toast.LENGTH_SHORT).show();
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        var isValid = true

        // Validar email
        if (email.isEmpty()) {
            findViewById<EditText>(R.id.emailEditText).error = "El correo es obligatorio"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            findViewById<EditText>(R.id.emailEditText).error = "Ingrese un correo válido"
            isValid = false
        }

        // Validar contraseña
        if (password.isEmpty()) {
            findViewById<TextInputLayout>(R.id.passwordInputLayout).error = "La contraseña es obligatoria"
            isValid = false
        } else if (password.length < 6) {
            findViewById<TextInputLayout>(R.id.passwordInputLayout).error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        } else {
            findViewById<TextInputLayout>(R.id.passwordInputLayout).error = null
        }

        return isValid
    }
}