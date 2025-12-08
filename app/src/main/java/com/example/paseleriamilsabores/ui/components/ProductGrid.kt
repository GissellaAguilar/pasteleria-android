package com.example.paseleriamilsabores.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.paseleriamilsabores.model.Producto


@Composable
fun ProductGrid(productos: List<Producto>) {

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


