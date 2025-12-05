package com.example.paseleriamilsabores.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Configura la URL base de tu backend (ej. http://10.0.2.2:8080/ para el emulador Android)
    private const val BASE_URL = "http://localhost:8080/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // Nota: Si usas 'suspend' debes añadir la dependencia de coroutines,
            // pero con versiones recientes de Retrofit y Kotlin, suele funcionar sin un adapter explícito.
            .build()
    }

    // Instancia de la única interfaz de servicio
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
