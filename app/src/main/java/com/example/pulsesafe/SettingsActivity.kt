package com.example.pulsesafe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Configurar la barra de estado
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // Configurar botón de retroceso
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // Configurar opción de cambiar contraseña
        findViewById<LinearLayout>(R.id.changePasswordOption).setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        // Configurar opción de eliminar cuenta
        findViewById<LinearLayout>(R.id.deleteAccountOption).setOnClickListener {
            showDeleteAccountDialog()
        }
    }

    private fun showDeleteAccountDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_delete_account, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<View>(R.id.cancelButton).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<View>(R.id.deleteButton).setOnClickListener {
            // Aquí iría la lógica para eliminar la cuenta
            dialog.dismiss()
            // Redirigir al login después de eliminar la cuenta
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        dialog.show()
    }
}