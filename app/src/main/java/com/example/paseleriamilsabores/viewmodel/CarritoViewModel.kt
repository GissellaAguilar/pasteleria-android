package com.example.paseleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paseleriamilsabores.data.ItemCarrito
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CarritoViewModel : ViewModel() {

    // Lista observable de ítems en el carrito
    private val _carrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val carrito: StateFlow<List<ItemCarrito>> = _carrito.asStateFlow()

    // Estados para Totales y Descuentos
    private val _totalPagar = MutableStateFlow(0.0)
    private val _descuentoAplicado = MutableStateFlow(0.0)

    // Exponer versiones públicas e INMUTABLES
    val totalPagar: StateFlow<Double> = _totalPagar.asStateFlow()
    val descuentoAplicado: StateFlow<Double> = _descuentoAplicado.asStateFlow()

    private val DESCUENTO_PERCENTAGE = 0.10 // 10%

    init {
        // Observa cambios en el carrito para recalcular totales
        viewModelScope.launch {
            _carrito.collect { items ->
                val subtotal = items.sumOf { it.subtotal }
                val descuento = if (subtotal > 0) subtotal * DESCUENTO_PERCENTAGE else 0.0

                _totalPagar.value = subtotal - descuento
                _descuentoAplicado.value = descuento
            }
        }
    }

    // --------------------------------------------------------
    // ✅ NUEVA FUNCIÓN: Carga de datos iniciales para testing
    // --------------------------------------------------------

    /**
     * Carga ítems de prueba en el carrito.
     * Ideal para testing o vista previa.
     */
    fun cargarDatosIniciales() {
        if (_carrito.value.isEmpty()) {
            val itemsDePrueba = listOf(
                ItemCarrito(id = "1", nombre = "Torta Tres Leches", precio = 15000.0, cantidad = 1),
                ItemCarrito(id = "2", nombre = "Cheesecake Frambuesa", precio = 12000.0, cantidad = 2),
                ItemCarrito(id = "3", nombre = "Brownie Chocolate", precio = 8000.0, cantidad = 3)
            )
            _carrito.value = itemsDePrueba
        }
    }

    // --------------------------------------------------------
    // Lógica de Pago
    // --------------------------------------------------------

    fun realizarPago(): Boolean {
        val pagoExitoso = _totalPagar.value > 0.0 && (Math.random() > 0.1)

        if (pagoExitoso) {
            limpiarCarrito()
        }

        return pagoExitoso
    }

    // --------------------------------------------------------
    // Funciones de Mutación
    // --------------------------------------------------------

    fun agregarItem(item: ItemCarrito) {
        _carrito.update { currentItems ->
            val existingItem = currentItems.find { it.id == item.id }
            if (existingItem != null) {
                currentItems.map {
                    if (it.id == item.id) it.copy(cantidad = it.cantidad + 1) else it
                }
            } else {
                currentItems + item.copy(cantidad = 1)
            }
        }
    }

    fun modificarCantidad(itemId: String, nuevaCantidad: Int) {
        _carrito.update { currentItems ->
            if (nuevaCantidad <= 0) {
                currentItems.filter { it.id != itemId }
            } else {
                currentItems.map {
                    if (it.id == itemId) it.copy(cantidad = nuevaCantidad) else it
                }
            }
        }
    }

    fun eliminarItem(itemId: String) {
        _carrito.update { currentItems ->
            currentItems.filter { it.id != itemId }
        }
    }

    fun limpiarCarrito() {
        _carrito.value = emptyList()
    }
}
