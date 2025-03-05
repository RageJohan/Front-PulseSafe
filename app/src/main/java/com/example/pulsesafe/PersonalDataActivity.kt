package com.example.pulsesafe

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class PersonalDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }
    }
}