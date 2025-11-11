package com.example.paseleriamilsabores.navigation

sealed class AppScreens(val route: String) {
    object Home : AppScreens("home")
    object Login : AppScreens("login")
    object Registro : AppScreens("registro")
    object Tortas : AppScreens("tortas")
    object Postres : AppScreens("postres")
    object DetalleProducto : AppScreens("detalle/{productoId}") // Ruta con argumento
    object Carrito : AppScreens("carrito")
    object Checkout : AppScreens("checkout")
    object Contacto : AppScreens("contacto")
    object CompraExitosa : AppScreens("compra_exitosa")
    object CompraFallida : AppScreens("compra_fallida")
}

