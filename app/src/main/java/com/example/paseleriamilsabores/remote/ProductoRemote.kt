package com.example.paseleriamilsabores.remote

data class ProductoRemote(
    val id: Long,
    val nombre_producto: String,
    val precio: Double,
    val descripcion: String,
    val categoria: String
)
