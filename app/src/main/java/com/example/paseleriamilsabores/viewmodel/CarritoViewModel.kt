package com.example.paseleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paseleriamilsabores.data.ItemCarrito
import com.example.paseleriamilsabores.data.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CarritoViewModel : ViewModel() {

    // -------------------------------------------------------
    // DATOS PARA LAS PANTALLAS OrderSuccess y OrderFailure
    // -------------------------------------------------------
    var ultimoUsuario: Usuario? = null
    var ultimoCodigoOrden: String? = null
    var ultimoTotalPagado: Double? = null
    var ultimoCarrito: List<ItemCarrito>? = null
    var usuarioActual: Usuario? = null

    // -------------------------------------------------------
    // CARRITO
    // -------------------------------------------------------
    private val _carrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val carrito: StateFlow<List<ItemCarrito>> = _carrito.asStateFlow()

    // -------------------------------------------------------
    // TOTAL (sin descuentos)
    // -------------------------------------------------------
    private val _totalPagar = MutableStateFlow(0.0)
    val totalPagar: StateFlow<Double> = _totalPagar.asStateFlow()

    init {
        // Observa cambios en el carrito para recalcular total
        viewModelScope.launch {
            _carrito.collect { items ->
                val subtotal = items.sumOf { it.subtotal }
                _totalPagar.value = subtotal  // SIN DESCUENTO
            }
        }
    }

    // -------------------------------------------------------
    // DATOS DE PRUEBA
    // -------------------------------------------------------
    fun cargarDatosIniciales() {
        if (_carrito.value.isEmpty()) {
            _carrito.value = listOf(
                ItemCarrito("1", "Torta Tres Leches", 15000.0, 1),
                ItemCarrito("2", "Cheesecake Frambuesa", 12000.0, 2),
                ItemCarrito("3", "Brownie Chocolate", 8000.0, 3)
            )
        }
    }

    // -------------------------------------------------------
    // PROCESO DE PAGO
    // -------------------------------------------------------
    fun realizarPago(usuario: Usuario): Boolean {

        this.usuarioActual = usuario
        val pagoExitoso = _totalPagar.value > 0 && (Math.random() > 0.1)

        // Código de orden
        val codigo = (10000000..99999999).random().toString()

        // Guardar datos para pantallas de éxito/fallo
        ultimoUsuario = usuario
        ultimoCodigoOrden = codigo
        ultimoTotalPagado = totalPagar.value      // SIN DESCUENTO
        ultimoCarrito = carrito.value.toList()

        if (pagoExitoso) limpiarCarrito()

        return pagoExitoso
    }

    // -------------------------------------------------------
    // MUTACIONES DEL CARRITO
    // -------------------------------------------------------
    fun agregarItem(item: ItemCarrito) {
        _carrito.update { items ->
            val existente = items.find { it.id == item.id }
            if (existente != null) {
                items.map {
                    if (it.id == item.id) it.copy(cantidad = it.cantidad + 1) else it
                }
            } else {
                items + item.copy(cantidad = 1)
            }
        }
    }

    fun modificarCantidad(itemId: String, cantidad: Int) {
        _carrito.update { items ->
            if (cantidad <= 0) items.filter { it.id != itemId }
            else items.map {
                if (it.id == itemId) it.copy(cantidad = cantidad) else it
            }
        }
    }

    fun eliminarItem(itemId: String) {
        _carrito.update { items -> items.filter { it.id != itemId } }
    }

    fun limpiarCarrito() {
        _carrito.value = emptyList()
    }
}
