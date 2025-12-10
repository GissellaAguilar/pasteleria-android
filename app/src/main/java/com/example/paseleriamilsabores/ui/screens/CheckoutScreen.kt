package com.example.paseleriamilsabores.ui.screens

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
import com.example.paseleriamilsabores.model.Usuario
import com.example.paseleriamilsabores.viewmodel.CarritoViewModel
import com.example.paseleriamilsabores.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    viewModel: CarritoViewModel,
    loginViewModel: LoginViewModel,
    onCompraExitosa: (String?) -> Unit,
    onCompraFallida: (String?) -> Unit
) {
    val carrito by viewModel.carrito.collectAsState()
    val totalPagar by viewModel.totalPagar.collectAsState()
    val scope = rememberCoroutineScope()
    val usuarioLogeado by loginViewModel.usuarioLogeado.collectAsState()


    // Estado del formulario
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var comuna by remember { mutableStateOf("") }
    var run by remember { mutableStateOf("") }

    LaunchedEffect(usuarioLogeado) {
        usuarioLogeado?.let { user ->
            nombre = user.nombre ?: ""
            apellidos = user.apellidos ?: ""
            correo = user.correo ?: ""
            direccion = user.direccion ?: ""
            region = user.region ?: ""
            comuna = user.comuna ?: ""
            run = user.run ?: ""
        }
    }


    Scaffold(
        topBar = { TopAppBar(title = { Text("Checkout") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Carrito
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Carrito de compra", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))

                        if (carrito.isEmpty()) {
                            Text("Tu carrito está vacío.")
                        } else {
                            carrito.forEach { detalle ->
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("${detalle.producto.nombre} x${detalle.cantidad}")
                                    Text("$${"%,.0f".format(detalle.subtotal)}")
                                }
                            }

                            Divider(Modifier.padding(vertical = 8.dp))

                            Text(
                                text = "Total a pagar: $${"%,.0f".format(totalPagar)}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }

            // Formulario del cliente
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Información del cliente", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = run,
                            onValueChange = { run = it },
                            label = { Text("RUN / RUT") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = apellidos,
                            onValueChange = { apellidos = it },
                            label = { Text("Apellidos") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = correo,
                            onValueChange = { correo = it },
                            label = { Text("Correo electrónico") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = direccion,
                            onValueChange = { direccion = it },
                            label = { Text("Dirección") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = region,
                            onValueChange = { region = it },
                            label = { Text("Región") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = comuna,
                            onValueChange = { comuna = it },
                            label = { Text("Comuna") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    val usuarioActual = Usuario(
                                        nombre = nombre,
                                        apellidos = apellidos,
                                        correo = correo,
                                        direccion = direccion,
                                        region = region,
                                        comuna = comuna,
                                        run = run,
                                        fechaNac = "",
                                        password = ""
                                    )

                                    // Llama a realizarPago con callback que devuelve (exito, codigo)
                                    viewModel.realizarPago(usuarioActual) { exito, codigo ->
                                        if (exito) {
                                            onCompraExitosa(codigo)
                                        } else {
                                            onCompraFallida(codigo)
                                        }
                                    }
                                }
                            },
                            enabled = nombre.isNotBlank() &&
                                    apellidos.isNotBlank() &&
                                    correo.isNotBlank() &&
                                    direccion.isNotBlank() &&
                                    region.isNotBlank() &&
                                    comuna.isNotBlank() &&
                                    run.isNotBlank() &&
                                    carrito.isNotEmpty(),
                            modifier = Modifier
                                .align(Alignment.End)
                                .height(50.dp)
                        ) {
                            Text("Pagar ahora $${"%,.0f".format(totalPagar)}")
                        }
                    }
                }
            }
        }
    }
}
