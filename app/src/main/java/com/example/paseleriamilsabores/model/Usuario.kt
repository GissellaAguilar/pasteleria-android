package com.example.paseleriamilsabores.model

data class Usuario(
    val run: String,
    val nombre: String,
    val apellidos: String,
    val correo: String,
    val password: String,
    val direccion: String,
    val region: String,
    val comuna: String,
    val fechaNac: String, // "YYYY-MM-DD"
    val codigo: String? = null,   // opcional
    val rol: String = "USER"      // igual que backend
)


