package com.example.paseleriamilsabores.model

data class Pedido (
    val idPedido: Long? = null,
    val usuario: Usuario,
    val detalles: List<DetallePedido>,
    val fechaPedido: String? = null,
    val total: Double
    )