package com.dengue_em_foco.com.dengue_em_foco.service

import com.dengue_em_foco.com.dengue_em_foco.api.IbgeEndpoint
import com.dengue_em_foco.com.dengue_em_foco.util.NetworkUtils
import com.dengue_em_foco.com.dengue_em_foco.entities.District
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class IbgeService {
    companion object{
        private val BASE_URL = "https://servicodados.ibge.gov.br/api/"
        private val ibgeClient = NetworkUtils.getRetrofitInstance(BASE_URL)
        private val ibgeApi = ibgeClient.create(IbgeEndpoint::class.java)
        suspend fun getListDistrictByUF(uf: String): List<District> {
            return withContext(Dispatchers.IO) {
                try {
                    val response: Response<List<District>> = ibgeApi.getAllDistrictByUF(uf).execute()
                    if (response.isSuccessful) {
                        response.body() ?: emptyList()
                    } else {
                        emptyList()
                    }
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }
        fun findDistrictByName(nome: String, districts:List<District>):District? {
            val district = districts.find{it.nome.contains(nome, ignoreCase = true)}
            return district
        }

        suspend fun getDistrictByNameAndUF(name:String, uf:String):District?{
            val district = getListDistrictByUF(uf)
            return findDistrictByName(name, district)
        }
    }
}