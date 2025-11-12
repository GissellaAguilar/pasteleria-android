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

    // Lista observable de ítems en el carrito (Esto ya está bien)
    private val _carrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val carrito: StateFlow<List<ItemCarrito>> = _carrito.asStateFlow()

    // 1. Crear propiedades privadas y MUTABLES para uso interno del ViewModel
    private val _totalPagar = MutableStateFlow(0.0)
    private val _descuentoAplicado = MutableStateFlow(0.0)

    // 2. Exponer versiones públicas e INMUTABLES para que la UI las observe
    val totalPagar: StateFlow<Double> = _totalPagar.asStateFlow()
    val descuentoAplicado: StateFlow<Double> = _descuentoAplicado.asStateFlow()

    // ------------------------------------

    private val DESCUENTO_PERCENTAGE = 0.10 // 10%

    init {
        // Observa cambios en el carrito para recalcular totales
        viewModelScope.launch {
            _carrito.collect { items ->
                val subtotal = items.sumOf { it.subtotal }
                val descuento = if (subtotal > 0) subtotal * DESCUENTO_PERCENTAGE else 0.0

                // 3. Modificar las propiedades privadas y mutables.
                // ✅ Forma correcta, segura y limpia.
                _totalPagar.value = subtotal - descuento
                _descuentoAplicado.value = descuento
            }
        }
    }

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