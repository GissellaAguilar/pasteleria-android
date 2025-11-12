package com.example.paseleriamilsabores.ui.screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage // <-- Importación necesaria para Coil
import com.example.paseleriamilsabores.data.Producto
import com.example.paseleriamilsabores.data.ProductoCategoria
import com.example.paseleriamilsabores.data.sampleProducto
import java.util.*

// Colores personalizados basados en el mockup (usando Material 3)
val PastelPink = Color(0xFFF7A2BB) // Rosa pastel para el fondo
val BrightPink = Color(0xFFFF4081) // Rosa brillante para botones y acentos
val CardPink = Color(0xFFFDE9EE) // Rosa muy claro para el fondo de las tarjetas

// Composable principal de la pantalla de productos
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen() {
    // 1. Estado para la categoría seleccionada (filtrado)
    var seleccionCategoria by remember { mutableStateOf(ProductoCategoria.TODOS) } // <-- VARIABLE DE ESTADO

    // 2. Estado para el texto de búsqueda (UI)
    var searchText by remember { mutableStateOf("") }

    // 3. Obtener la lista de productos filtrada
    val filtrar= remember(seleccionCategoria) {
        if (seleccionCategoria == ProductoCategoria.TODOS) {
            sampleProducto
        } else {
            sampleProducto.filter { it.categoria == seleccionCategoria}
        }
    }

    Scaffold(
        // Barra inferior (replicando la navegación inferior del mockup)
        bottomBar = { PasteleriaBottomBar() },
        modifier = Modifier.fillMaxSize().background(PastelPink)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(CardPink) // Fondo principal
                .systemBarsPadding(), // Manejo de barras del sistema
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- Encabezado y Barra de Búsqueda ---
            item {
                HeaderSection()
            }

            item {
                SearchBar(searchText, onSearchTextChanged = { searchText = it })
            }

            // --- Fila de Chips de Categoría (Filtros) ---
            item {
                categoriaChipsRow(
                    seleccionCategoria = seleccionCategoria,
                    onseleccionCategoria = { seleccionCategoria = it } // <-- CORRECCIÓN: Asigna el nuevo valor 'it'
                )
            }

            // --- Lista de Productos ---
            items(filtrar) { producto ->
                ProductCard(producto = producto)
            }

            // Espacio al final de la lista para el margen de la barra inferior
            item { Spacer(modifier = Modifier.height(60.dp)) }
        }
    }
}

// ------------------------------------------
// COMPONENTES REUTILIZABLES DE LA PANTALLA
// ------------------------------------------

@Composable
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
                    color = Color.Black
                )
            )
            Text(
                text = "Hola, lalala",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
        // Icono de flecha de salida (Logout/Acceder)
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Salir",
            tint = BrightPink,
            modifier = Modifier.size(28.dp).clickable {
                // Lógica para ir a inicio de sesión/logout
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge),
        placeholder = { Text("Buscar productos...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar", tint = BrightPink)
        },
        // --- CORRECCIÓN: Usando TextFieldDefaults.colors() de Material 3 ---
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = BrightPink
        )
        // -------------------------------------------------------------------
    )
}

@Composable
fun categoriaChipsRow(
    seleccionCategoria: ProductoCategoria,
    onseleccionCategoria: (ProductoCategoria) -> Unit // <-- FIRMA CORREGIDA: Acepta ProductoCategoria
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
                onClick = { onseleccionCategoria(categoria) }, // <-- Pasa la categoría al callback
                label = {
                    Text(
                        // Formatear el nombre de la categoría (ej: TORTAS -> Tortas)
                        text = categoria.name.lowercase(Locale.ROOT)
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                },
                shape = MaterialTheme.shapes.extraLarge,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = BrightPink,
                    containerColor = Color(0xFFFAD1DC), // Rosa claro para no seleccionados
                    selectedLabelColor = Color.White,
                    labelColor = Color.Black
                ),
                border = FilterChipDefaults.filterChipBorder(
                    selectedBorderColor = Color.Transparent,
                    borderColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun ProductCard(producto: Producto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Lógica para ver detalles del producto */ },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF5F7) // Fondo claro rosado
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del producto con Coil
            AsyncImage(
                model = producto.ImgProduct,
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
                    text = "Precio: $${producto.precio}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = BrightPink
                    )
                )
            }
        }
    }
}


@Composable
fun PasteleriaBottomBar() {
    val items = listOf("Inicio", "Productos", "Carrito", "Envíos", "Pago")
    val selectedItem = "Productos" // Simula que estamos en esta pantalla

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach { item ->
            val isSelected = item == selectedItem
            NavigationBarItem(
                icon = {
                    // Icono de Material Design (reemplazar)
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_help), // Reemplazar con iconos reales
                        contentDescription = item,
                        tint = if (isSelected) BrightPink else Color.Gray
                    )
                },
                label = {
                    Text(
                        item,
                        color = if (isSelected) BrightPink else Color.Gray
                    )
                },
                selected = isSelected,
                onClick = { /* Navegar a la pantalla correspondiente */ }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewProductosScreen() {
    MaterialTheme {
        ProductosScreen()
    }
}