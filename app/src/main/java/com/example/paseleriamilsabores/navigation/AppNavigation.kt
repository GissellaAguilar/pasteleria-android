package com.example.paseleriamilsabores.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paseleriamilsabores.ui.screens.ContactoScreen
import com.example.paseleriamilsabores.ui.screens.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.Contacto.route) {
        //composable(AppScreens.Home.route) { HomeScreen(navController) }
        //composable(AppScreens.Login.route) { /* Aquí llamarás a tu LoginScreen() */ }
        //composable(AppScreens.Registro.route) { /* ... */ }
        //composable(AppScreens.Tortas.route) { /* ... */ }
        //composable(AppScreens.Postres.route) { /* ... */ }
        //composable(AppScreens.Carrito.route) { /* ... */ }
        //composable(AppScreens.Checkout.route) { /* ... */ }
        composable(AppScreens.Contacto.route) { ContactoScreen(navController = navController) }
        // ... y así con las 11 pantallas
    }
}