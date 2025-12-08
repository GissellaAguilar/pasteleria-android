package com.example.paseleriamilsabores.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage // <-- Importación necesaria para Coil
import com.example.compose.backgroundLight
import com.example.compose.errorContainerLight
import com.example.compose.inversePrimaryLight
import com.example.compose.inversePrimaryLightMediumContrast
import com.example.compose.onBackgroundLight
import com.example.compose.onTertiaryContainerLight
import com.example.compose.secondaryContainerDarkMediumContrast
import com.example.paseleriamilsabores.model.Producto
import com.example.paseleriamilsabores.model.ProductoCategoria

import com.example.paseleriamilsabores.viewmodel.CarritoViewModel
import com.example.paseleriamilsabores.viewmodel.ProductoViewModel
import java.util.*


val BrightPink = Color(0xFFFF4081) // Rosa brillante para botones y acentos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(
    carritoViewModel: CarritoViewModel,
    productoViewModel: ProductoViewModel = viewModel()
) {
    val context = LocalContext.current

    // Obtiene productos desde el ViewModel
    val productos by productoViewModel.productos.collectAsState()

    // Estado categoría seleccionada
    var seleccionCategoria by remember { mutableStateOf(ProductoCategoria.TODOS) }

    // Estado de búsqueda
    var searchText by remember { mutableStateOf("") }

    // Filtrar productos por categoría + búsqueda
    val productosFiltrados = productos.filter { producto ->
        val coincideCategoria =
            seleccionCategoria == ProductoCategoria.TODOS ||
                    producto.categoria.toString().equals(seleccionCategoria.name, ignoreCase = true)

        val coincideBusqueda =
            searchText.isEmpty() || producto.nombre.contains(searchText, ignoreCase = true)

        coincideCategoria && coincideBusqueda
    }

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(errorContainerLight)
                .systemBarsPadding(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Barra de búsqueda
            item {
                SearchBar(
                    searchText = searchText,
                    onSearchTextChanged = { searchText = it }
                )
            }

            // Chips categorías
            item {
                categoriaChipsRow(
                    seleccionCategoria = seleccionCategoria,
                    onseleccionCategoria = { seleccionCategoria = it }
                )
            }

            // Lista de productos
            items(productosFiltrados) { producto ->
                Column {

                    ProductCard(producto)

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            carritoViewModel.agregarProducto(producto)
                            Toast.makeText(
                                context,
                                "${producto.nombre} agregado al carrito",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BrightPink),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Agregar al carrito", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

// ------------------------------------------
// COMPONENTES REUTILIZABLES DE LA PANTALLA
// ------------------------------------------
/*

val productosFiltrados = productos.filter { producto ->
    val coincideCategoria =
        seleccionCategoria == ProductoCategoria.TODOS ||
                producto.categoria.toString().equals(seleccionCategoria.name, ignoreCase = true)

    val coincideBusqueda =
        searchText.isEmpty() || producto.nombre.contains(searchText, ignoreCase = true)

    coincideCategoria && coincideBusqueda
}@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Pastelería mil sabores",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = onBackgroundLight
                )
            )

        }

    }
}
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge),
        placeholder = { Text("Buscar productos...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar", tint = inversePrimaryLight)
        },

        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = inversePrimaryLight
        )

    )
}

@Composable
fun categoriaChipsRow(
    seleccionCategoria: ProductoCategoria,
    onseleccionCategoria: (ProductoCategoria) -> Unit
) {
    // Definimos el orden de las categorías para los chips
    val categorias = ProductoCategoria.entries.toTypedArray()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(categorias) { categoria ->
            val isSelected = categoria == seleccionCategoria
            FilterChip(
                selected = isSelected,
                onClick = { onseleccionCategoria(categoria) }, // Pasa la categoría al callback
                label = {
                    Text(

                        text = categoria.name.lowercase(Locale.ROOT)
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                },
                shape = MaterialTheme.shapes.extraLarge,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = secondaryContainerDarkMediumContrast,
                    containerColor = inversePrimaryLight,
                    selectedLabelColor = Color.White,
                    labelColor = onBackgroundLight
                )
            )
        }
    }
}

@Composable
fun ProductCard(producto: Producto) {
    val precioFormateado = "$${producto.precio.toInt()}"
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF5F7)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = producto.img,
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Información del producto
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.Black
                )
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = precioFormateado,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = BrightPink
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewProductosScreen() {
    MaterialTheme {
        ProductosScreen(carritoViewModel = viewModel())
    }
}