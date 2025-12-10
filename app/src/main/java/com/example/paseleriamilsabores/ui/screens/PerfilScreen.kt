package com.example.paseleriamilsabores.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.paseleriamilsabores.navigation.AppScreens
import com.example.paseleriamilsabores.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val usuario by loginViewModel.usuarioLogeado.collectAsState()

    // FOTO TOMADA CON LA CÁMARA
    var fotoPerfil by remember { mutableStateOf<Bitmap?>(null) }

    // Launcher de cámara (nativo)
    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            fotoPerfil = bitmap
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // FOTO PERFIL (editable)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable { launcherCamera.launch() }, // 👍 toca la foto para cambiarla
                contentAlignment = Alignment.Center
            ) {

                if (fotoPerfil != null) {
                    Image(
                        bitmap = fotoPerfil!!.asImageBitmap(),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // CÍRCULO CON INICIAL SI NO HAY FOTO
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = usuario?.nombre?.firstOrNull()?.uppercase() ?: "?",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // NOMBRE
            Text(
                text = "${usuario?.nombre ?: ""} ${usuario?.apellidos ?: ""}".trim(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            // CORREO
            Text(
                text = usuario?.correo ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(25.dp))

            // CARD DATOS PERSONALES
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Text(
                        "Datos personales",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(12.dp))

                    PerfilDato("RUN / RUT", usuario?.run)
                    PerfilDato("Correo", usuario?.correo)
                    PerfilDato("Dirección", usuario?.direccion)
                    PerfilDato("Comuna", usuario?.comuna)
                    PerfilDato("Región", usuario?.region)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // BOTÓN CERRAR SESIÓN
            Button(
                onClick = {
                    loginViewModel.logout()
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Home.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}

@Composable
fun PerfilDato(titulo: String, valor: String?) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            titulo,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            valor ?: "No registrado",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
