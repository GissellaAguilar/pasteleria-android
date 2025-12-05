package com.example.paseleriamilsabores.navigation

import androidx.compose.material3.*
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paseleriamilsabores.ui.components.BottomNavBar
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paseleriamilsabores.ui.screens.CarritoScreen
import com.example.paseleriamilsabores.ui.screens.ContactoScreen
import com.example.paseleriamilsabores.ui.screens.HomeScreen
import com.example.paseleriamilsabores.ui.screens.MasScreen
import com.example.paseleriamilsabores.ui.screens.PerfilScreen
import com.example.paseleriamilsabores.ui.screens.RegistroScreen
import com.example.paseleriamilsabores.viewmodel.CarritoViewModel
import com.example.paseleriamilsabores.ui.screens.CheckoutScreen
import com.example.paseleriamilsabores.ui.screens.LoginScreen
import com.example.paseleriamilsabores.ui.screens.NosotrosScreen
import com.example.paseleriamilsabores.ui.screens.OrderFailureScreen
import com.example.paseleriamilsabores.ui.screens.OrderSuccessScreen
import com.example.paseleriamilsabores.ui.screens.ProductosScreen
import com.example.paseleriamilsabores.ui.screens.ProntoScreen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
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
            /*
            navController = navController,
            startDestination = AppScreens.Home.route,
            modifier = Modifier.padding(innerPadding) ,

            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {it},
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },

            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                )
            }


            */
        ) {
            composable(AppScreens.Home.route) { HomeScreen(navController) }
            composable(AppScreens.Registro.route) { /* RegistroScreen(navController) */ }
            composable(AppScreens.Producto.route) { ProductosScreen(carritoViewModel = cartViewModel)}
            composable(AppScreens.Carrito.route) { CarritoScreen(navController = navController, viewModel = cartViewModel)}
            composable(AppScreens.Checkout.route) { CheckoutScreen(
                navController = navController,
                viewModel = cartViewModel,
                onCompraExitosa = {
                    navController.navigate(AppScreens.OrderSuccess.route)
            },
                onCompraFallida = {
                    navController.navigate(AppScreens.OrderFailure.route)
                }) }

            // ✔️ ORDER SUCCESS SCREEN
            composable(AppScreens.OrderSuccess.route) {

                OrderSuccessScreen(
                    navController = navController,
                    usuario = cartViewModel.usuarioActual!!,
                    carrito = cartViewModel.ultimoCarrito?: emptyList(),
                    totalReal = cartViewModel.ultimoTotalPagado?: 0.0,
                    codigoOrden = cartViewModel.ultimoCodigoOrden ?: "N/A"
                )
            }

            // ✔️ ORDER FAILURE SCREEN
            composable(AppScreens.OrderFailure.route) {

                OrderFailureScreen(
                    navController = navController,
                    usuario = cartViewModel.usuarioActual!!,
                    carrito = cartViewModel.ultimoCarrito?: emptyList(),
                    totalReal = cartViewModel.ultimoTotalPagado?: 0.0,
                    codigoOrden = cartViewModel.ultimoCodigoOrden ?: "N/A"
                )
            }
            composable(AppScreens.Contacto.route) { ContactoScreen(navController = navController)}
            composable(AppScreens.Mas.route) { MasScreen(navController = navController) }
            composable(AppScreens.Perfil.route) { PerfilScreen() }
            composable(AppScreens.Registro.route){ RegistroScreen(navController) }
            composable(AppScreens.Login.route) { LoginScreen(navController) }
            composable(AppScreens.Nosotros.route){NosotrosScreen(navController)}
            composable(AppScreens.Pronto.route){ProntoScreen(navController)}


        }
    }
}

