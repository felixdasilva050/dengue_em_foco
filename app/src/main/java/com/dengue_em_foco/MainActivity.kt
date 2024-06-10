package com.dengue_em_foco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.main_login_button)
        loginButton.setOnClickListener {
            val intent = Intent(this, DengueNoticesActivity::class.java)
            startActivity(intent)
        }

        val registrationButton = findViewById<Button>(R.id.main_register_button)
        registrationButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}
