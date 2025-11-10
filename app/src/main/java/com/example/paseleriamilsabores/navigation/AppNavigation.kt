package com.example.paseleriamilsabores.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.Home.route) {
        composable(AppScreens.Home.route) { /* Aquí llamarás a tu HomeScreen() */ }
        composable(AppScreens.Login.route) { /* Aquí llamarás a tu LoginScreen() */ }
        composable(AppScreens.Registro.route) { /* ... */ }
        composable(AppScreens.Tortas.route) { /* ... */ }
        composable(AppScreens.Postres.route) { /* ... */ }
        composable(AppScreens.Carrito.route) { /* ... */ }
        composable(AppScreens.Checkout.route) { /* ... */ }
        composable(AppScreens.Contacto.route) { /* Aquí irá la pantalla con geolocalización */ }
        // ... y así con las 11 pantallas
    }
}