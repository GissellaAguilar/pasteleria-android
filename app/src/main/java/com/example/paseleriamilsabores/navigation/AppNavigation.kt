package com.example.paseleriamilsabores.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paseleriamilsabores.ui.screens.CarritoScreen
import com.example.paseleriamilsabores.ui.screens.ContactoScreen
import com.example.paseleriamilsabores.ui.screens.HomeScreen
import com.example.paseleriamilsabores.viewmodel.CarritoViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val cartViewModel: CarritoViewModel = viewModel()
    NavHost(navController = navController, startDestination = AppScreens.Carrito.route) {
        //composable(AppScreens.Home.route) { HomeScreen(navController = navController) }
        //composable(AppScreens.Login.route) { /* Aquí llamarás a tu LoginScreen() */ }
        //composable(AppScreens.Registro.route) { /* ... */ }
        //composable(AppScreens.Tortas.route) { /* ... */ }
        //composable(AppScreens.Postres.route) { /* ... */ }
        composable(AppScreens.Carrito.route) { CarritoScreen(navController = navController, viewModel = cartViewModel) }
        //composable(AppScreens.Checkout.route) { }
        //composable(AppScreens.Contacto.route) { ContactoScreen(navController = navController) }
        // ... y así con las 11 pantallas
    }
}