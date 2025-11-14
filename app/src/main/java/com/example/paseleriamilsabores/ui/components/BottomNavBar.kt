package com.example.paseleriamilsabores.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.paseleriamilsabores.navigation.AppScreens

@Composable
fun BottomNavBar(navController : NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == AppScreens.Home.route,
            onClick = {
                navController.navigate(AppScreens.Home.route) {
                    popUpTo(AppScreens.Home.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )


        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(AppScreens.Producto.route){
                    popUpTo(AppScreens.Home.route)
                    launchSingleTop = true
                }

            },
            icon = { Icon(Icons.Default.List, contentDescription = "Productos") },
            label = { Text("Productos") }
        )

        NavigationBarItem(
            selected = currentRoute == AppScreens.Carrito.route,
            onClick = {
                navController.navigate(AppScreens.Carrito.route) {
                    popUpTo(AppScreens.Home.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito") },
            label = { Text("Carrito") }
        )


        NavigationBarItem(
            selected = currentRoute == AppScreens.Mas.route,
            onClick = {
                navController.navigate(AppScreens.Mas.route) {
                    popUpTo(AppScreens.Home.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.MoreVert, contentDescription = "Más") },
            label = { Text("Más") }
        )


    }
}
