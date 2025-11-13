package com.example.paseleriamilsabores.data

data class ItemCarrito(
    val id: String,
    val nombre: String,
    val precio: Double,
    val cantidad: Int,
    val imagenResId: Int? = null // Opcional, para la imagen
) {
    val subtotal: Double
        get() = precio * cantidad
}