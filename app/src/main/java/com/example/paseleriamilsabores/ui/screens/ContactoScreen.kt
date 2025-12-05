package com.example.paseleriamilsabores.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.compose.backgroundLight
import com.example.compose.errorContainerLight
import com.example.compose.primaryContainerLight
import com.example.paseleriamilsabores.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import kotlinx.coroutines.tasks.await

// Coordenadas de la tienda (Alameda 1588, Santiago)
private val TIENDA_LAT = -33.46531032154523
private val TIENDA_LNG = -70.67322997477157
private val TIENDA_POINT = Point.fromLngLat(TIENDA_LNG, TIENDA_LAT)


// ----------------------------------------------------------------------
// Función Composable Principal
// ----------------------------------------------------------------------

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactoScreen(navController: NavController) {

    val contexto = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(contexto)
    }

    // Cargar el ícono de tienda como Bitmap (solo se hace una vez)
    val storeBitmap: Bitmap? = remember {
        bitmapFromResource(contexto, R.drawable.casa)
    }

    var userLocation by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    var locationMessage by remember { mutableStateOf("Buscando ubicación...") }
    val isPermissionGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                contexto,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val mapViewportState = rememberMapViewportState()

    suspend fun recuperarCurrentLocation() {
        if (!isPermissionGranted.value) {
            locationMessage = "Permiso de ubicación no concedido."
            return
        }
        try {
            val location = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, null
            ).await()

            if (location != null) {
                userLocation = Pair(location.latitude, location.longitude)
                locationMessage = "Ubicación recuperada correctamente."
            } else {
                locationMessage = "No se pudo obtener la ubicación."
            }
        } catch (e: Exception) {
            locationMessage = "Error: ${e.message}"
        }
    }

    val locationPermissionLaunch = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            isPermissionGranted.value = granted
            locationMessage = if (granted) "Permiso concedido" else "Permiso denegado."
        }
    )

    // 1. Efecto que recupera la ubicación del usuario
    LaunchedEffect(isPermissionGranted.value) {
        if (isPermissionGranted.value) {
            recuperarCurrentLocation()
        }
    }

    // 2. Efecto que centra la cámara en la tienda al cargar la pantalla
    LaunchedEffect(mapViewportState) {
        mapViewportState.setCameraOptions {
            zoom(14.0)
            center(TIENDA_POINT)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contáctanos", style = MaterialTheme.typography.titleMedium) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = errorContainerLight)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize().background(color = errorContainerLight)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Si tienes alguna pregunta, no dudes en contactarnos.",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Card(
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .fillMaxWidth().background(color = MaterialTheme.colorScheme.errorContainer),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .background(color = backgroundLight),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InfoRow(icon = Icons.Outlined.LocationOn, text = "📍 Alameda 1588, Santiago",)
                    Spacer(Modifier.height(8.dp))
                    InfoRow(icon = Icons.Outlined.Phone, text = "📞 +569 45676798")
                    Spacer(Modifier.height(8.dp))
                    InfoRow(icon = Icons.Outlined.Email, text = "✉️ info@milsabores.cl")

                    Spacer(Modifier.height(16.dp))
                    Text(locationMessage, style = MaterialTheme.typography.titleMedium)

                    // MapboxMap
                    MapboxMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(12.dp))
                        .background(color = backgroundLight),
                        mapViewportState = mapViewportState
                    ) {
                        // Carga de estilo y manejo de anotaciones (híbrido)
                        MapEffect(userLocation, storeBitmap) { mapView ->
                            // 1. Cargar estilo
                            mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)

                            // 2. Crear Manager para las anotaciones
                            val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
                            pointAnnotationManager.deleteAll()

                            // 3. Añadir marcador de la TIENDA (con icono)
                            val tiendaOptions = PointAnnotationOptions()
                                .withPoint(TIENDA_POINT)

                            if (storeBitmap != null) {
                                // Asigna el Bitmap al marcador de la tienda
                                tiendaOptions.withIconImage(storeBitmap)
                            }
                            pointAnnotationManager.create(tiendaOptions)

                            // 4. Añadir marcador del USUARIO (si está disponible)
                            userLocation?.let { (lat, lng) ->
                                val userOptions = PointAnnotationOptions()
                                    .withPoint(Point.fromLngLat(lng, lat))
                                // Usará el icono por defecto de Mapbox o un Bitmap simple

                                pointAnnotationManager.create(userOptions)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------------------------
// Componentes y Helpers
// ----------------------------------------------------------------------

/**
 * Función para convertir un recurso Drawable (como un Vector Asset) a Bitmap,
 * que es el formato requerido por Mapbox para los iconos personalizados.
 */
fun bitmapFromResource(context: Context, @DrawableRes resourceId: Int): Bitmap? {
    val drawable = ContextCompat.getDrawable(context, resourceId) ?: return null

    // Se ajusta el tamaño del bitmap si el drawable tiene dimensiones intrínsecas
    val width = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else 64
    val height = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else 64

    val bitmap = Bitmap.createBitmap(
        width,
        height,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}


@Composable
private fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = text)
        Spacer(Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}