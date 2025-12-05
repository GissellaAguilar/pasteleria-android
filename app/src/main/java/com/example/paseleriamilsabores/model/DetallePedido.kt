package com.example.paseleriamilsabores.model
import com.google.gson.annotations.SerializedName

class DetallePedido(
    @SerializedName("id")
    val idDetalle: Long? = null,

    // El DetallePedido se relaciona con un Producto y contiene la cantidad.
    // Necesitarás una propiedad para el Producto aquí (o solo su ID si el backend lo maneja así)

    @SerializedName("producto")
    val producto: Producto, // Si envías/recibes el objeto Producto completo

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("subtotal")
    val subtotal: Double

    // Nota: El backend de Spring Boot puede manejar el mapeo de vuelta al objeto Pedido
    // No necesitas incluir la propiedad 'pedido' aquí si usas @JsonManagedReference/@JsonBackReference
)