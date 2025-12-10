package com.example.paseleriamilsabores.model
import com.google.gson.annotations.SerializedName

data class DetallePedido(
    val idDetalle: Long? = null,
    val producto: Producto,
    val cantidad: Int,
    val subtotal: Double,
    val pedidoId: Long? = null   // <-- nuevo: referencia al pedido padre (opcional)
)
