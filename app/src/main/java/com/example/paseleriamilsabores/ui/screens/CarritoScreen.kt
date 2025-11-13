package com.example.paseleriamilsabores.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
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

// Formato de moneda CLP
private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    .apply { maximumFractionDigits = 0 }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavController, viewModel: CarritoViewModel = viewModel()) {

    val carritoItems by viewModel.carrito.collectAsState()
    val totalPagar by viewModel.totalPagar.collectAsState()

    // Cargar datos iniciales solo una vez
    LaunchedEffect(Unit) {
        viewModel.cargarDatosIniciales()
    }

    val total = carritoItems.sumOf { it.subtotal }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🛒 Carrito de Compras") }
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
                    total = total,
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.nombre, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text("Precio: ${currencyFormat.format(item.precio)}", style = MaterialTheme.typography.bodySmall)
            }

            QuantitySelector(
                cantidad = item.cantidad,
                onIncrease = { viewModel.modificarCantidad(item.id, item.cantidad + 1) },
                onDecrease = { viewModel.modificarCantidad(item.id, item.cantidad - 1) }
            )

            Spacer(Modifier.width(16.dp))

            Text(
                currencyFormat.format(item.subtotal),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(80.dp),
                textAlign = TextAlign.End
            )

            IconButton(onClick = { viewModel.eliminarItem(item.id) }) {
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
        ) { Text("+") }
    }
}

@Composable
fun CartSummary(
    total: Double,
    onClearCart: () -> Unit,
    onCheckout: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        shadowElevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SummaryRow(label = "TOTAL A PAGAR:", value = total, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = onClearCart, enabled = total > 0) {
                    Text("Vaciar Carrito")
                }

                Button(
                    onClick = onCheckout,
                    enabled = total > 0,
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("Ir a Pagar ${currencyFormat.format(total)}")
                }
            }
        }
    }
}

@Composable
fun SummaryRow(
    label: String,
    value: Double,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
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
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("¡Tu carrito está vacío!", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("Parece que no has añadido ninguna delicia aún.", style = MaterialTheme.typography.bodyLarge)
    }
}
