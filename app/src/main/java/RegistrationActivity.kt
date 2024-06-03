package com.dengue_em_foco

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.dengue_em_foco.com.dengue_em_foco.entities.District
import com.dengue_em_foco.com.dengue_em_foco.service.IbgeService
import kotlinx.coroutines.launch

class RegistrationActivity : ComponentActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        dbHelper = DatabaseHelper(this)

        val nameEditText = findViewById<EditText>(R.id.name_input)
        val cityEditText = findViewById<EditText>(R.id.city_input)
        val ufEditText = findViewById<EditText>(R.id.state_input)
        val sendRegistrationButton = findViewById<Button>(R.id.registration_button)

        sendRegistrationButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val city = cityEditText.text.toString()
            val uf = ufEditText.text.toString()

            if (name.isNotEmpty() && city.isNotEmpty() && uf.isNotEmpty()) {
                val success = dbHelper.addUser(name, city, uf)
                if (success) {
                    Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Registration failed! Name might be already taken.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            }
            lifecycleScope.launch {
                val district:District? = IbgeService.getDistrictByNameAndUF(city,uf)
                if(district != null){
                    Toast.makeText(this@RegistrationActivity,"id:${district.id}", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@RegistrationActivity,"Município não encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
