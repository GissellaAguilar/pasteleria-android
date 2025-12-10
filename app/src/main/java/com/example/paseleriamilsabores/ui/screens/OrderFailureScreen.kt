package com.example.paseleriamilsabores.ui.screens

import android.R
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
import com.example.compose.onTertiaryContainerLight
import com.example.paseleriamilsabores.model.DetallePedido
import com.example.paseleriamilsabores.model.Usuario

@Composable
fun OrderFailureScreen(
    navController: NavController,
    usuario: Usuario,
    carrito: List<DetallePedido>,
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
                modifier = Modifier.fillMaxWidth().background(color = errorContainerLight),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)
                    .fillMaxSize().background(color = MaterialTheme.colorScheme.errorContainer)) {

                    Text(
                        text = "❌ No se pudo realizar el pago",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )

                    Text(
                        text = "Número de orden: $codigoOrden",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("checkout") },
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = onTertiaryContainerLight,
                            contentColor = MaterialTheme.colorScheme.onSecondary)
                    ) {
                        Text("Volver a intentar", style = MaterialTheme.typography.bodyMedium)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn {
                        items(carrito) { item ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(item.producto.nombre)
                                Text("x${item.cantidad}")
                                Text("$${"%,.0f".format(item.subtotal)}")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Total a pagar: $${"%,.0f".format(totalReal)}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}
