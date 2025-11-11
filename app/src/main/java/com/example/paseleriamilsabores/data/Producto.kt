package com.example.paseleriamilsabores.data

import androidx.annotation.DrawableRes

data class Producto(
    val id: String,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val categoria: String,
    @DrawableRes val imagen: Int // @DrawableRes es para imágenes en tu app
)