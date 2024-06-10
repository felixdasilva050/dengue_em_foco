package com.dengue_em_foco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.dengue_em_foco.api.DengueService
import com.dengue_em_foco.entities.DengueData
import com.dengue_em_foco.entities.Municipio
import com.dengue_em_foco.service.IbgeService
import com.dengue_em_foco.util.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val uuid = intent.getStringExtra("uuid").toString()
        val name = intent.getStringExtra("name")
        val cases = intent.getStringExtra("cases")
        val city = intent.getStringExtra("city")
        val date = intent.getStringExtra("date")
        val geocode = intent.getStringExtra("geocode").toString().toInt()

        val tvUsername = findViewById<TextView>(R.id.tv_username)
        val tvCasesCount = findViewById<TextView>(R.id.tv_cases_count)
        val tvMunicipioDetails = findViewById<TextView>(R.id.tv_municipio_details)
        val tvSaveDate = findViewById<TextView>(R.id.tv_save_date)

        tvUsername.text = name
        tvCasesCount.text = "Casos: $cases"
        tvMunicipioDetails.text = city
        tvSaveDate.text = "Salvo no dia: $date"

        val buttonDelete = findViewById<Button>(R.id.btn_delete)
        val buttonUpdate = findViewById<Button>(R.id.btn_atualizar_profile)

        buttonDelete.setOnClickListener{
            val dbHelper = DatabaseHelper(this)
            val rowAffected = dbHelper.deleteDengueNoticeByUuid(uuid)
            if(rowAffected != 0){
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }else{
                Toast.makeText(this, "Falha ao tentar excluir", Toast.LENGTH_SHORT).show()
            }
        }

        buttonUpdate.setOnClickListener{
            val dbHelper = DatabaseHelper(this)
            lifecycleScope.launch {
                try {
                    val cases:Int
                    val retrofit = NetworkUtils.getRetrofitInstance("https://info.dengue.mat.br/api/")
                    val dengueService = retrofit.create(DengueService::class.java)
                    val endWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)
                    val year = Calendar.getInstance().get(Calendar.YEAR)
                    val startWeek = endWeek - 5
                    val dengueDatas = dengueService.getDengueData(
                        "dengue", geocode, "json",
                        startWeek, endWeek, year, year)
                    val dengueData: DengueData = dengueDatas.last()
                    withContext(Dispatchers.Main) {cases = dengueData.casos}
                    val success = dbHelper.updateDengueNotice(uuid, cases)
                    if (success) {
                        val mainIntent = Intent(this@ProfileActivity, MainActivity::class.java)
                        startActivity(mainIntent)
                    } else {
                        Toast.makeText(this@ProfileActivity, "Falha ao tentar atualizar", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ProfileActivity, "Erro ao obter dados de dengue: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}