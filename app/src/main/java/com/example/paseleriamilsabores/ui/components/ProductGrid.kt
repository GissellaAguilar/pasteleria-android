package com.example.paseleriamilsabores.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.paseleriamilsabores.data.Producto
import com.example.paseleriamilsabores.data.sampleProducto

@Composable
fun ProductGrid() {

    // ⬅️ USAR LA LISTA GLOBAL sampleProducto
    val productos = sampleProducto.take(6)

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
