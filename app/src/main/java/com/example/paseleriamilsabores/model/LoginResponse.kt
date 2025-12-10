package com.example.paseleriamilsabores.model

data class LoginResponse(
    val token: String,
    val rol: String,
    val nombre: String,
    val run: String,
    val apellidos: String,
    val correo: String,
    val direccion: String,
    val region: String,
    val comuna: String,
)