package com.example.paseleriamilsabores.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.errorContainerLight
import com.example.paseleriamilsabores.data.ItemCarrito
import com.example.paseleriamilsabores.data.Usuario

@Composable
fun OrderSuccessScreen(
    navController: NavController,
    usuario: Usuario,
    carrito: List<ItemCarrito>,
    totalReal: Double,
    codigoOrden: String
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize().background(color = errorContainerLight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.errorContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "✅ Compra realizada correctamente",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Número de orden: $codigoOrden",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // --- Información del usuario ---
                    Text("Gracias por tu compra, ${usuario.nombre} ${usuario.apellidos}!", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("📧 Correo: ${usuario.correo}", style = MaterialTheme.typography.bodyMedium)
                    Text("📍 Dirección: ${usuario.direccion}, ${usuario.comuna}, ${usuario.region}", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(16.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))

                    // --- Lista del carrito ---
                    LazyColumn {
                        items(carrito) { item ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(item.producto.nombre, style = MaterialTheme.typography.bodyMedium)
                                Text("x${item.cantidad}", style = MaterialTheme.typography.bodyMedium)
                                Text("$${"%,.0f".format(item.subtotal)}", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // --- Total final ---
                    Text(
                        text = "Total pagado: $${"%,.0f".format(totalReal)}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.End)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- Botón volver al inicio ---
                    Button(
                        onClick = { navController.navigate("home") },
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary)
                    ) {
                        Text("Volver al inicio" , style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
