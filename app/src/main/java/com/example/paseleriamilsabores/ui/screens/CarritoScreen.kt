package com.example.paseleriamilsabores.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.paseleriamilsabores.data.ItemCarrito
import com.example.paseleriamilsabores.navigation.AppScreens
import com.example.paseleriamilsabores.viewmodel.CarritoViewModel
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Color
import com.example.compose.backgroundLight
import com.example.compose.errorContainerLight
import com.example.compose.onTertiaryContainerLight
import com.example.compose.primaryLight
import com.mapbox.maps.extension.style.style


// Formato de moneda CLP
private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    .apply { maximumFractionDigits = 0 }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavController, viewModel: CarritoViewModel
) {

    val carritoItems by viewModel.carrito.collectAsState()
    val totalPagar by viewModel.totalPagar.collectAsState()

    val total = carritoItems.sumOf { it.subtotal }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Compras", style = MaterialTheme.typography.titleMedium) },
                        colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = errorContainerLight)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (carritoItems.isEmpty()) {
                EmptyCartMessage()
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(carritoItems) { item ->
                        CartItemRow(item = item, viewModel = viewModel)
                    }
                }

                // Resumen y botones
                CartSummary(
                    total = totalPagar, // Usar el total calculado por el ViewModel (que incluye descuentos)
                    onClearCart = viewModel::limpiarCarrito,
                    onCheckout = { navController.navigate(AppScreens.Checkout.route) }
                )
            }
        }
    }
}

@Composable
fun CartItemRow(item: ItemCarrito, viewModel: CarritoViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        // Usa un SurfaceVariant (fondo claro) que contrasta con el primaryContainer del Summary
        colors = CardDefaults.cardColors(containerColor = errorContainerLight),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)
                .fillMaxSize().background(color = errorContainerLight)) {
                // El título usa el color por defecto (onSurface) que debería ser el Café Oscuro
                Text(item.producto.nombre, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text("Precio: ${currencyFormat.format(item.producto.precio)}", style = MaterialTheme.typography.bodyMedium)
            }

            QuantitySelector(
                cantidad = item.cantidad,
                onIncrease = { viewModel.modificarCantidad(item.producto.id, item.cantidad + 1) },
                onDecrease = { viewModel.modificarCantidad(item.producto.id, item.cantidad - 1) }
            )

            Spacer(Modifier.width(16.dp))

            Text(
                // Muestra el subtotal del ítem, no el total general
                currencyFormat.format(item.subtotal),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(80.dp),
                textAlign = TextAlign.End
            )

            IconButton(onClick = { viewModel.eliminarProducto(item.producto.id) }) { // Corregido: usar eliminarItem
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun QuantitySelector(cantidad: Int, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = onDecrease,
            enabled = cantidad > 0,
            modifier = Modifier.size(32.dp),
            contentPadding = PaddingValues(0.dp)
        ) { Text("-") }

        Spacer(Modifier.width(8.dp))
        Text(cantidad.toString(), style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.width(8.dp))

        Button(
            onClick = onIncrease,
            modifier = Modifier.size(32.dp),
            contentPadding = PaddingValues(0.dp)
        ) { Text("+", style = MaterialTheme.typography.bodySmall) }
    }
}

@Composable
fun CartSummary(
    total: Double,
    onClearCart: () -> Unit,
    onCheckout: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth().background(color = errorContainerLight),
        // Usar primaryContainer para darle un fondo de color (Rosa Fuerte/Claro)
        color = errorContainerLight,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        shadowElevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SummaryRow(
                label = "TOTAL A PAGAR:",
                value = total,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = onClearCart, enabled = total > 0) {
                    Text("Vaciar Carrito",
                        style = MaterialTheme.typography.bodyLarge,
                        color = primaryLight
                    )
                }

                Button(
                    onClick = onCheckout,
                    enabled = total > 0,
                    modifier = Modifier.height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = onTertiaryContainerLight,
                        contentColor = MaterialTheme.colorScheme.onSecondary)
                ) {
                    Text("Ir a Pagar ${currencyFormat.format(total)}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
fun SummaryRow(
    label: String,
    value: Double,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = style)
        Text(
            currencyFormat.format(value),
            style = style,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EmptyCartMessage() {
    Column(
        modifier = Modifier
            .fillMaxSize().background(color = errorContainerLight)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "¡Tu carrito está vacío!",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Parece que no has añadido ninguna delicia aún.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}