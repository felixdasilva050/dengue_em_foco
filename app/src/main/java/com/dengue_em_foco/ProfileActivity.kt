package com.dengue_em_foco

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val uuid = intent.getStringExtra("uuid")
        val name = intent.getStringExtra("name")
        val cases = intent.getStringExtra("cases")
        val city = intent.getStringExtra("city")
        val date = intent.getStringExtra("date")

        val tvUsername = findViewById<TextView>(R.id.tv_username)
        val tvCasesCount = findViewById<TextView>(R.id.tv_cases_count)
        val tvMunicipioDetails = findViewById<TextView>(R.id.tv_municipio_details)
        val tvSaveDate = findViewById<TextView>(R.id.tv_save_date)

        tvUsername.text = name
        tvCasesCount.text = "Casos: $cases"
        tvMunicipioDetails.text = city
        tvSaveDate.text = "Salvo no dia: $date"

    }
}