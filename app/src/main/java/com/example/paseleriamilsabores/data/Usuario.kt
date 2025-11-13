package com.example.paseleriamilsabores.data

data class Usuario(
    val run: String,
    val nombre: String,
    val apellidos: String,
    val correo: String,
    val direccion: String,
    val region: String,
    val comuna: String,
    val fechaNac: String,
    val edad: Int,
    // Clase anidada para replicar el objeto 'beneficios' de tu JS
    val beneficios: Beneficios
) {
    data class Beneficios(
        // Descuento base por edad o código (ej: 0.5 para 50%)
        val descuento: Double = 0.0,
        // Indica si el descuento es fijo y no se debe mezclar con otros (como el 50%)
        val descuentoFijo: Boolean = false,
        // Si tiene torta gratis (no afecta el carrito, pero es un beneficio)
        val tortaGratisCumple: Boolean = false
    )
}