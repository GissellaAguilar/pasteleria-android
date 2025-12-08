package com.example.paseleriamilsabores.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    // ✔ Este lazy devuelve un Retrofit REAL (no ApiService)
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ✔ Este lazy devuelve el ApiService usando el Retrofit anterior
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
