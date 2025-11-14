package com.example.paseleriamilsabores.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.paseleriamilsabores.data.Producto
import com.example.paseleriamilsabores.data.ItemCarrito
import com.example.paseleriamilsabores.ui.screens.BrightPink


@Composable
fun ProductCard(producto: Producto, modifier: Modifier = Modifier) {
    val precioFormateado = "$${producto.precio.toInt()}"

    Card(
        modifier = modifier
            .height(240.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.elevatedCardElevation(6.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            // 🖼️ Imagen local desde drawable
            Image(
                painter = painterResource(id = producto.ImgProduct),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🏷️ Nombre del producto
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )


            Text(
                text = precioFormateado,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )



            Spacer(modifier = Modifier.height(8.dp))



        }
    }
}
