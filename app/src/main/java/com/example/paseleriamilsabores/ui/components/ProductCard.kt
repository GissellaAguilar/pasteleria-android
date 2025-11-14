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
<<<<<<< Updated upstream
import com.example.paseleriamilsabores.data.Producto
=======
import com.example.paseleriamilsabores.data.ItemCarrito
import com.example.paseleriamilsabores.model.Producto
import com.example.paseleriamilsabores.ui.screens.BrightPink
>>>>>>> Stashed changes

@Composable
fun ProductCard(producto: Producto, modifier: Modifier = Modifier) {
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

            /*
            // 💰 Precio destacado con el color primario
            Text(
                text = producto.precio,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(top = 4.dp)
            )
<<<<<<< Updated upstream

             */
=======
            Spacer(modifier = Modifier.height(8.dp))

            // --- BOTÓN AGREGAR ---
            Button(
                onClick = { ItemCarrito.(producto) },
                colors = ButtonDefaults.buttonColors(containerColor = BrightPink),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar", color = Color.White, fontWeight = FontWeight.Bold)
            }


>>>>>>> Stashed changes
        }
    }
}
