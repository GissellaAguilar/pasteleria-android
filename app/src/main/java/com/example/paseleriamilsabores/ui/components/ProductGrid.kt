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

    val productos = sampleProducto.take(6)

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        productos.chunked(2).forEach { fila ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                fila.forEach { producto ->
                    ProductCard(
                        producto,
                        Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

