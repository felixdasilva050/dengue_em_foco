package com.dengue_em_foco

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.activity.ComponentActivity


class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val sendRegistrationButton = findViewById<Button>(R.id.registration_button)

        sendRegistrationButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            }
        }
    }
