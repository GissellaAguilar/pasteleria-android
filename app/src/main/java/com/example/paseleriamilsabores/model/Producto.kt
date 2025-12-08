package com.example.paseleriamilsabores.model

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val img: Int,
    val categoria: ProductoCategoria
)
