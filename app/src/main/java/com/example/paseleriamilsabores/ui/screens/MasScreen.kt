package com.example.paseleriamilsabores.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.paseleriamilsabores.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasScreen(navController: NavController) {

    var user by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }

        DisposableEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener {
            user = it.currentUser
        }
        FirebaseAuth.getInstance().addAuthStateListener(listener)

        onDispose {
            FirebaseAuth.getInstance().removeAuthStateListener(listener)
        }
    }

    val isUserLoggedIn = user != null
    val userName = user?.displayName ?: "Inicia sesión"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = userName,
                        modifier = Modifier.clickable {
                            if (isUserLoggedIn) {
                                navController.navigate(AppScreens.Perfil.route)
                            } else {
                                navController.navigate(AppScreens.Login.route)
                            }
                        }
                    )
                },
                actions = {
                    IconButton(onClick = {
                        if (isUserLoggedIn) {
                            navController.navigate(AppScreens.Perfil.route)
                        } else {
                            navController.navigate(AppScreens.Login.route)
                        }
                    }) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OpcionItem("Inicio", Icons.Default.Home) {
                navController.navigate(AppScreens.Home.route)
            }

            OpcionItem("Buscar", Icons.Default.Search) {}

            OpcionItem("Contacto", Icons.Default.Email) {
                navController.navigate(AppScreens.Contacto.route)
            }

            OpcionItem("Nosotros", Icons.Default.Info) {}

            if (isUserLoggedIn) {
                OpcionItem("Cerrar sesión", Icons.Default.ExitToApp) {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(AppScreens.Login.route)
                }
            }
        }
    }
}


@Composable
fun OpcionItem(texto: String, icono: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icono, contentDescription = texto)
            Spacer(modifier = Modifier.width(16.dp))
            Text(texto, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
