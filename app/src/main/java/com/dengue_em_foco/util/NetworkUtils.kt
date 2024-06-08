package com.dengue_em_foco.com.dengue_em_foco.util
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class NetworkUtils {
    companion object{
        fun getRetrofitInstance(path:String):Retrofit{
            return Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}