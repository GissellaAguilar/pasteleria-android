package com.example.paseleriamilsabores.ui.screens

import android.content.Intent
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.paseleriamilsabores.ui.components.CarouselSection
import com.example.paseleriamilsabores.ui.components.ProductGrid
import androidx.compose.ui.Alignment


import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.paseleriamilsabores.viewmodel.ProductoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    // CONTEXTO → necesario para abrir la cámara
    val context = LocalContext.current

    // Estados del SearchBar
    var query by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    val productoViewModel: ProductoViewModel = viewModel()
    val productosBackend by productoViewModel.productos.collectAsState()


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,

        // 🔹 Nueva barra superior
        topBar = {
            DockedSearchBar(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                inputField = {
                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = { query = it },
                        onSearch = {
                            expanded = false
                        },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },

                        placeholder = { Text("Buscar productos…") },

                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Buscar"
                            )
                        },

                        // 🔹 Botón de cámara
                        trailingIcon = {
                            IconButton(onClick = {
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                try {
                                    context.startActivity(intent)
                                } catch (_: Exception) {
                                }
                            }) {
                                Icon(
                                    Icons.Filled.CameraAlt,
                                    contentDescription = "Buscar con cámara"
                                )
                            }
                        }
                    )
                }
            ) {
                // 🔹 Contenido del menú desplegable
                Text(
                    "Sugerencia: torta de chocolate",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }

        }

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
                ProductGrid(productos = productosBackend)
            }
        }
    }
}
