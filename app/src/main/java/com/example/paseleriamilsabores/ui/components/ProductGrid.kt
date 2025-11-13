package com.example.paseleriamilsabores.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.paseleriamilsabores.R
import com.example.paseleriamilsabores.model.Producto

@Composable
fun ProductGrid() {
    val productos = listOf(
        Producto("Torta Chocolate", "$45.000", R.drawable.torta_chocolate),
        Producto("Torta Vainilla", "$40.000", R.drawable.torta_vainilla_circular),
        Producto("Brownie", "$4.000", R.drawable.postre_brownie),
        Producto("Empanada Manzana", "$3.000", R.drawable.postre_empanada_manzana)
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(productos.chunked(2)) { fila ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                fila.forEach { producto ->
                    ProductCard(producto, Modifier.weight(1f))
                }
            }
        }
    }
}
