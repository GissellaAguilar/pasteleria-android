package com.example.paseleriamilsabores.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paseleriamilsabores.ui.components.BottomNavBar
import com.example.paseleriamilsabores.ui.screens.*
import com.example.paseleriamilsabores.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val cartViewModel: CarritoViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = AppScreens.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            // Principales
            composable(AppScreens.Home.route) { HomeScreen(navController) }

            composable(AppScreens.Producto.route) { ProductosScreen(carritoViewModel = cartViewModel) }

            composable(AppScreens.Carrito.route) {
                CarritoScreen(navController = navController, viewModel = cartViewModel)
            }

            // Checkout: recibe callbacks con el código de orden
            composable(AppScreens.Checkout.route) {
                CheckoutScreen(
                    navController = navController,
                    viewModel = cartViewModel,
                    onCompraExitosa = { codigoOrden ->
                        // viewModel ya guarda datos (usuarioActual, ultimoCarrito, ultimoTotalPagado, ultimoCodigoOrden)
                        // pero nos aseguramos de guardar el codigo si viene por callback:
                        if (codigoOrden != null) cartViewModel.ultimoCodigoOrden = codigoOrden
                        navController.navigate(AppScreens.OrderSuccess.route)
                    },
                    onCompraFallida = { codigoOrden ->
                        if (codigoOrden != null) cartViewModel.ultimoCodigoOrden = codigoOrden
                        navController.navigate(AppScreens.OrderFailure.route)
                    }
                )
            }

            // Order Success
            composable(AppScreens.OrderSuccess.route) {
                cartViewModel.usuarioActual?.let { usuario ->
                    OrderSuccessScreen(
                        navController = navController,
                        usuario = usuario,
                        carrito = cartViewModel.ultimoCarrito ?: emptyList(),
                        totalReal = cartViewModel.ultimoTotalPagado ?: 0.0,
                        codigoOrden = cartViewModel.ultimoCodigoOrden ?: "N/A"
                    )
                }
            }

            // Order Failure
            composable(AppScreens.OrderFailure.route) {
                cartViewModel.usuarioActual?.let { usuario ->
                    OrderFailureScreen(
                        navController = navController,
                        usuario = usuario,
                        carrito = cartViewModel.ultimoCarrito ?: emptyList(),
                        totalReal = cartViewModel.ultimoTotalPagado ?: 0.0,
                        codigoOrden = cartViewModel.ultimoCodigoOrden ?: "N/A"
                    )
                }
            }

            // Otras pantallas
            composable(AppScreens.Contacto.route) { ContactoScreen(navController) }
            composable(AppScreens.Mas.route) { MasScreen(navController) }
            composable(AppScreens.Perfil.route) { PerfilScreen() }
            composable(AppScreens.Registro.route) { RegistroScreen(navController) }
            composable(AppScreens.Login.route) { LoginScreen(navController) }
            composable(AppScreens.Nosotros.route) { NosotrosScreen(navController) }
            composable(AppScreens.Pronto.route) { ProntoScreen(navController) }
        }
    }
}
