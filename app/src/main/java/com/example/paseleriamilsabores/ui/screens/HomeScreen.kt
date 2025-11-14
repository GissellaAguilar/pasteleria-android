package com.example.paseleriamilsabores.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.paseleriamilsabores.ui.components.CarouselSection
import com.example.paseleriamilsabores.ui.components.ProductGrid


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomeScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold(
        // 🔹 Fondo principal de la pantalla
        containerColor = MaterialTheme.colorScheme.background,

        // 🔹 Barra superior (SearchBar)
        topBar = {
            TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                cursorColor = MaterialTheme.colorScheme.primary
            )
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text("Buscar productos...") },

                // 🔹 Colores coherentes con el tema
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    dividerColor = MaterialTheme.colorScheme.outlineVariant
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 1.dp)
            ) {
                // Contenido desplegable de la barra de búsqueda
                Text(
                    text = "Sugerencia: torta de chocolate",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },


    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            item {
                CarouselSection()

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ProductGrid()
            }

        }
    }
}
