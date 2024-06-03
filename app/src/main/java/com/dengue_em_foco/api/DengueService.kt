package com.dengue_em_foco.com.dengue_em_foco.api

import com.dengue_em_foco.com.dengue_em_foco.entities.DengueData
import retrofit2.http.GET
import retrofit2.http.Query

interface DengueService {
    @GET("alertcity")
    suspend fun getDengueData(
        @Query("disease") disease: String,
        @Query("geocode") geocode: Int,
        @Query("format") format: String,
        @Query("ew_start") ewStart: Int,
        @Query("ew_end") ewEnd: Int,
        @Query("ey_start") eyStart: Int,
        @Query("ey_end") eyEnd: Int
    ): List<DengueData>
}
