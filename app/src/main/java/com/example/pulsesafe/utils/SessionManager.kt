package com.example.pulsesafe.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(id: Long, name: String, email: String) {
        if (id <= 0) {
            Log.e("SessionManager", "Error: intentaste guardar un ID inválido ($id)")
            return
        }
        Log.d("SessionManager", "Guardando usuario - ID: $id, Nombre: $name, Email: $email")
        val editor = prefs.edit()
        editor.putLong("USER_ID", id)
        editor.putString("USER_NAME", name)
        editor.putString("USER_EMAIL", email)
        editor.apply()
    }



    fun getUserId(): Long {
        val id = prefs.getLong("USER_ID", 0L)
        Log.d("SessionManager", "Recuperado USER_ID: $id")
        return id
    }


    fun getUserName(): String? {
        return prefs.getString("USER_NAME", "Usuario")
    }

    fun getUserEmail(): String? {
        return prefs.getString("USER_EMAIL", "")
    }

    fun clearSession() {
        Log.d("SessionManager", "clearSession() llamado, borrando datos de sesión") // <-- Mensaje de depuración
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

}
