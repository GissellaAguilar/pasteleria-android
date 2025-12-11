package com.example.paseleriamilsabores.model

data class LoginResponse(
    val id: Long,
    val nombre: String,
    val apellidos: String,
    val correo: String,
    val direccion: String?,
    val region: String?,
    val comuna: String?,
    val run: String?,
    val fechaNac: String?,
    val rol: String    // 👈 IMPORTANTE
)
