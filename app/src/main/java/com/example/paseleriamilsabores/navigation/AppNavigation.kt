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
            composable(AppScreens.Home.route) { HomeScreen(navController) }
            composable(AppScreens.Login.route) { /* LoginScreen(navController) */ }
            composable(AppScreens.Registro.route) { /* RegistroScreen(navController) */ }
            composable(AppScreens.Tortas.route) { /* TortasScreen(navController) */ }
            composable(AppScreens.Postres.route) { /* PostresScreen(navController) */ }
            composable(AppScreens.Carrito.route) { CarritoScreen(navController = navController, viewModel = cartViewModel)}
            composable(AppScreens.Checkout.route) { /* CheckoutScreen(navController) */ }
            composable(AppScreens.Contacto.route) { ContactoScreen(navController = navController)}
            composable(AppScreens.Mas.route) { MasScreen(navController = navController) }
            composable(AppScreens.Perfil.route) { PerfilScreen() }
            composable(AppScreens.Registro.route){ RegistroScreen(navController) }


        }
    }
}

