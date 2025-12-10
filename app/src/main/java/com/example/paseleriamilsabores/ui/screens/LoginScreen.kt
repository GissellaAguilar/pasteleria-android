package com.example.paseleriamilsabores.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.paseleriamilsabores.navigation.AppScreens
import com.example.paseleriamilsabores.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val context = LocalContext.current

    var run by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val usuarioLogeado by viewModel.usuarioLogeado.collectAsState()
    val error by viewModel.error.collectAsState()

    // Si el login fue exitoso → Navegar
    LaunchedEffect(usuarioLogeado) {
        if (usuarioLogeado != null) {
            Toast.makeText(context, "Ingreso exitoso 🎉", Toast.LENGTH_SHORT).show()

            navController.currentBackStackEntry?.savedStateHandle?.set("usuarioRegistrado", usuarioLogeado)

            navController.navigate(AppScreens.Home.route) {
                popUpTo(AppScreens.Login.route) { inclusive = true }
            }
        }
    }

    // Si hubo error → mostrar Toast
    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Iniciar sesión") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // RUN
            OutlinedTextField(
                value = run,
                onValueChange = { run = it },
                label = { Text("RUN (sin guion)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // Login
            Button(
                onClick = {
                    if (run.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Ingresa RUN y contraseña", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    viewModel.login(run, password)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Iniciar sesión")
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate(AppScreens.Registro.route) }) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}

