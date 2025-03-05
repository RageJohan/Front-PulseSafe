package com.example.pulsesafe

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }
    }
}