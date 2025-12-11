package com.example.paseleriamilsabores.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.paseleriamilsabores.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Panel de Administración") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text("Gestión de usuarios y productos", style = MaterialTheme.typography.titleLarge)

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { /* Abrir lista de usuarios */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Usuarios")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { /* Abrir productos */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Productos")
            }
        }
    }
}
