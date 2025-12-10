package com.example.paseleriamilsabores.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.errorContainerLight
import com.example.paseleriamilsabores.model.Usuario
import com.example.paseleriamilsabores.viewmodel.CarritoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    viewModel: CarritoViewModel,
    onCompraExitosa: (String) -> Unit,
    onCompraFallida: (String) -> Unit
) {
    val carrito by viewModel.carrito.collectAsState()
    val totalPagar by viewModel.totalPagar.collectAsState()
    val scope = rememberCoroutineScope()

    // Recuperar usuario registrado desde savedStateHandle
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val usuarioRegistrado = savedStateHandle?.get<Usuario>("usuarioRegistrado")

    // Prellenar campos si el usuario existe
    var nombre by remember { mutableStateOf(usuarioRegistrado?.nombre ?: "") }
    var apellidos by remember { mutableStateOf(usuarioRegistrado?.apellidos ?: "") }
    var correo by remember { mutableStateOf(usuarioRegistrado?.correo ?: "") }
    var direccion by remember { mutableStateOf(usuarioRegistrado?.direccion ?: "") }
    var region by remember { mutableStateOf(usuarioRegistrado?.region ?: "") }
    var comuna by remember { mutableStateOf(usuarioRegistrado?.comuna ?: "") }

    // Limpiar usuario del savedStateHandle para no reutilizarlo
    LaunchedEffect(usuarioRegistrado) {
        if (usuarioRegistrado != null) {
            savedStateHandle?.remove<Usuario>("usuarioRegistrado")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Checkout") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = errorContainerLight))
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize().background(color = errorContainerLight),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ------------------------------
            // Carrito
            // ------------------------------
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().background(color = errorContainerLight),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Carrito de compra", style = MaterialTheme.typography.titleMedium)

                        Spacer(Modifier.height(8.dp))

                        carrito.forEach { item ->
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${item.producto.nombre} x${item.cantidad}", style = MaterialTheme.typography.bodyMedium)

                                Text("$${"%,.0f".format(item.subtotal)}", style = MaterialTheme.typography.bodyMedium)
                            }
                        }

                        Divider(Modifier.padding(vertical = 8.dp))

                        // Total real
                        Text(
                            text = "Total a pagar: $${"%,.0f".format(totalPagar)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // ------------------------------
            // Formulario del Cliente
            // ------------------------------
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text("Información del cliente", style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre", style = MaterialTheme.typography.bodySmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = apellidos,
                            onValueChange = { apellidos = it },
                            label = { Text("Apellidos", style = MaterialTheme.typography.bodySmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = correo,
                            onValueChange = { correo = it },
                            label = { Text("Correo electrónico", style = MaterialTheme.typography.bodySmall) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = direccion,
                            onValueChange = { direccion = it },
                            label = { Text("Dirección", style = MaterialTheme.typography.bodySmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = region,
                            onValueChange = { region = it },
                            label = { Text("Región", style = MaterialTheme.typography.bodySmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = comuna,
                            onValueChange = { comuna = it },
                            label = { Text("Comuna", style = MaterialTheme.typography.bodySmall) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    val usuarioParaPago = usuarioRegistrado?.copy(
                                        nombre = nombre,
                                        apellidos = apellidos,
                                        correo = correo,
                                        direccion = direccion,region = region,
                                        comuna = comuna
                                    ) ?: Usuario(
                                        run = "",
                                        password = "",
                                        fechaNac = "",
                                        nombre = nombre,
                                        apellidos = apellidos,
                                        correo = correo,
                                        direccion = direccion,
                                        region = region,
                                        comuna = comuna,
                                        codigo = "",
                                        rol = ""
                                    )

                                    val exito = viewModel.realizarPago(usuarioParaPago)
                                    val codigoOrden = viewModel.ultimoCodigoOrden ?: "N/A"

                                    if (exito) onCompraExitosa(codigoOrden)
                                    else onCompraFallida(codigoOrden)
                                }
                            },
                            enabled = nombre.isNotBlank() &&
                                    apellidos.isNotBlank() &&
                                    correo.isNotBlank() &&
                                    direccion.isNotBlank() &&
                                    region.isNotBlank() &&
                                    comuna.isNotBlank() &&
                                    carrito.isNotEmpty(),

                            modifier = Modifier
                                .align(Alignment.End)
                                .height(50.dp)
                        ) {
                            Text("Pagar ahora $${"%,.0f".format(totalPagar)}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}