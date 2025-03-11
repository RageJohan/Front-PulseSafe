package com.example.pulsesafe

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pulsesafe.api.RetrofitClient
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tecsup.pulsesafe.model.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.confirmPasswordEditText)
        val registerButton = findViewById<MaterialButton>(R.id.registerButton)
        val loginLinkTextView = findViewById<TextView>(R.id.loginLinkTextView)

        backButton.setOnClickListener { onBackPressed() }
        loginLinkTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (validateInputs(name, email, password, confirmPassword)) {
                val usuario = Usuario(null, name, email, password)
                registerUser(usuario)
            }
        }
    }

    private fun validateInputs(name: String, email: String, password: String, confirmPassword: String): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            findViewById<EditText>(R.id.nameEditText).error = "El nombre es obligatorio"
            isValid = false
        }
        if (email.isEmpty()) {
            findViewById<EditText>(R.id.emailEditText).error = "El correo es obligatorio"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            findViewById<EditText>(R.id.emailEditText).error = "Ingrese un correo válido"
            isValid = false
        }
        if (password.isEmpty()) {
            findViewById<TextInputLayout>(R.id.passwordInputLayout).error = "La contraseña es obligatoria"
            isValid = false
        } else if (password.length < 6) {
            findViewById<TextInputLayout>(R.id.passwordInputLayout).error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        }
        if (confirmPassword.isEmpty()) {
            findViewById<TextInputLayout>(R.id.confirmPasswordInputLayout).error = "Debe confirmar la contraseña"
            isValid = false
        } else if (confirmPassword != password) {
            findViewById<TextInputLayout>(R.id.confirmPasswordInputLayout).error = "Las contraseñas no coinciden"
            isValid = false
        }

        return isValid
    }

    private fun registerUser(usuario: Usuario) {
        RetrofitClient.instance.register(usuario).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Error al registrar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
