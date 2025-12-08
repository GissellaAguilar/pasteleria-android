package com.example.paseleriamilsabores.data

import com.example.paseleriamilsabores.model.Producto

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int = 1
) {
    val subtotal: Double
        get() = producto.precio * cantidad
}