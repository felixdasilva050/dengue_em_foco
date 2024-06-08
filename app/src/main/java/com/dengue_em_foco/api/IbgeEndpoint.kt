package com.dengue_em_foco.com.dengue_em_foco.api

import com.dengue_em_foco.com.dengue_em_foco.entities.District
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.GET

interface IbgeEndpoint {

    @GET("v1/localidades/estados/{UF}/distritos")
    fun getAllDistrictByUF(@Path(value="UF", encoded = true) uf:String):Call<List<District>>

    @GET("v1/localidades/distritos/{id}")
    fun getDistrictById(@Path(value="id", encoded = true) id:Long):Call<District>
}