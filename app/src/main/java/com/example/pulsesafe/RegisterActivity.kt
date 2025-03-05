package com.example.pulsesafe

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializar vistas
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val confirmPasswordInputLayout = findViewById<TextInputLayout>(R.id.confirmPasswordInputLayout)
        val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.confirmPasswordEditText)
        val termsLinkTextView = findViewById<TextView>(R.id.termsLinkTextView)
        val privacyLinkTextView = findViewById<TextView>(R.id.privacyLinkTextView)
        val registerButton = findViewById<MaterialButton>(R.id.registerButton)
        val loginLinkTextView = findViewById<TextView>(R.id.loginLinkTextView)

        // Configurar botón de retroceso
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Configurar botón de registro
        registerButton.setOnClickListener {
            // Aquí iría la lógica de validación y registro
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (validateInputs(name, email, password, confirmPassword)) {
                // Simulación de registro exitoso
                // En una app real, aquí iría la llamada a tu API o servicio de registro
                // Por ahora, simplemente navegamos a una pantalla principal ficticia
                // Intent intent = new Intent(this, MainActivity.class);
                // startActivity(intent);
                // finish();
            }
        }

        // Configurar enlace a login
        loginLinkTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Configurar enlaces de términos y privacidad
        termsLinkTextView.setOnClickListener {
            // Aquí iría la lógica para mostrar los términos de uso
            // Por ahora, solo mostramos un mensaje
            // Toast.makeText(this, "Términos de uso", Toast.LENGTH_SHORT).show();
        }

        privacyLinkTextView.setOnClickListener {
            // Aquí iría la lógica para mostrar la política de privacidad
            // Por ahora, solo mostramos un mensaje
            // Toast.makeText(this, "Política de privacidad", Toast.LENGTH_SHORT).show();
        }
    }

    private fun validateInputs(name: String, email: String, password: String, confirmPassword: String): Boolean {
        var isValid = true

        // Validar nombre
        if (name.isEmpty()) {
            findViewById<EditText>(R.id.nameEditText).error = "El nombre es obligatorio"
            isValid = false
        }

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

        // Validar confirmación de contraseña
        if (confirmPassword.isEmpty()) {
            findViewById<TextInputLayout>(R.id.confirmPasswordInputLayout).error = "Debe confirmar la contraseña"
            isValid = false
        } else if (confirmPassword != password) {
            findViewById<TextInputLayout>(R.id.confirmPasswordInputLayout).error = "Las contraseñas no coinciden"
            isValid = false
        } else {
            findViewById<TextInputLayout>(R.id.confirmPasswordInputLayout).error = null
        }

        return isValid
    }
}