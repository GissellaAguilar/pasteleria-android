package com.example.paseleriamilsabores.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.paseleriamilsabores.ui.components.BottomNavBar
import com.example.paseleriamilsabores.ui.components.CarouselSection
import com.example.paseleriamilsabores.ui.components.ProductGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController : NavController) {
    Scaffold(
        bottomBar = { BottomNavBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Tienda Online",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Bienvenido a nuestra tienda online, donde encontrarás los mejores productos al mejor precio.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            CarouselSection()

            Spacer(modifier = Modifier.height(16.dp))

            ProductGrid()
        }
    }
}
