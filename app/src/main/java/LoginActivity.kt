package com.dengue_em_foco

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.activity.ComponentActivity

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val entrarButton = findViewById<Button>(R.id.entrar_button)

        entrarButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
