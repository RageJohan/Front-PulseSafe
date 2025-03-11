package com.example.pulsesafe

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pulsesafe.api.RetrofitClient
import com.example.pulsesafe.utils.SessionManager
import com.google.android.material.button.MaterialButton
import com.tecsup.pulsesafe.model.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<MaterialButton>(R.id.loginButton)

        val sessionManager = SessionManager(this) // Instancia de SessionManager

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usuario = Usuario(email = email, password = password)

            RetrofitClient.instance.login(usuario).enqueue(object : Callback<Map<String, Any>> {
                override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                    if (response.isSuccessful && response.body() != null) {
                        val body = response.body()!!
                        val id = (body["idUsuario"] as? Double)?.toLong() ?: 0L

                        val nombre = body["nombre"] as? String ?: "Usuario"


                        sessionManager.saveUser(id, nombre, email)

                        Toast.makeText(this@LoginActivity, "Bienvenido $nombre", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
