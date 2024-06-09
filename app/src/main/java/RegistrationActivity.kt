package com.dengue_em_foco

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.dengue_em_foco.com.dengue_em_foco.api.DengueService
import com.dengue_em_foco.com.dengue_em_foco.entities.DengueData
import com.dengue_em_foco.com.dengue_em_foco.entities.District
import com.dengue_em_foco.com.dengue_em_foco.entities.Municipio
import com.dengue_em_foco.com.dengue_em_foco.service.IbgeService
import com.dengue_em_foco.com.dengue_em_foco.util.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

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
            var cases:Int
            lifecycleScope.launch {
                val district: Municipio? = IbgeService.getDistrictByNameAndUF(city, uf)
                if (district != null) {
                    val retrofit = NetworkUtils.getRetrofitInstance("https://info.dengue.mat.br/api/")
                    val dengueService = retrofit.create(DengueService::class.java)
                    try {
                        val endWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)
                        val year = Calendar.getInstance().get(Calendar.YEAR)
                        val startWeek = endWeek - 5
                        val dengueDatas = dengueService.getDengueData(
                            "dengue", district.id, "json",
                            startWeek, endWeek, year, year)
                        val dengueData: DengueData = dengueDatas.last()
                        withContext(Dispatchers.Main) {cases = dengueData.casos}
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegistrationActivity, "Erro ao obter dados de dengue: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@RegistrationActivity, "Município não encontrado", Toast.LENGTH_SHORT).show()
                }
            }
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
        }
    }
}
