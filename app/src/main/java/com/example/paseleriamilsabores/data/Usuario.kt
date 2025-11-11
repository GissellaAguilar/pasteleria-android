package com.example.paseleriamilsabores.data

data class Usuario(
    val run: String,
    val nombre: String,
    val apellidos: String,
    val correo: String,
    val direccion: String,
    val region: String,
    val comuna: String
    // No guardes el password en la base de datos en texto plano
)