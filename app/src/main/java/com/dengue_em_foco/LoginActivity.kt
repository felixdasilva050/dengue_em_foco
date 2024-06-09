package com.dengue_em_foco.com.dengue_em_foco

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.dengue_em_foco.R

class LoginActivity : ComponentActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DatabaseHelper(this)

        val userInput = findViewById<EditText>(R.id.user_input)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val userName = userInput.text.toString()
            if (dbHelper.userExists(userName)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
