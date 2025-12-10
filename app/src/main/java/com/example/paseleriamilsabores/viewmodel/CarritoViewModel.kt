package com.example.paseleriamilsabores.viewmodel
import android.content.ClipData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paseleriamilsabores.data.ItemCarrito
import com.example.paseleriamilsabores.model.Producto
import com.example.paseleriamilsabores.model.Usuario
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
class CarritoViewModel (private val dispatcher: CoroutineDispatcher = Dispatchers.Main):
    ViewModel() {
    var ultimoUsuario: Usuario? = null
    var ultimoCodigoOrden: String? = null
    var ultimoTotalPagado: Double? = null
    var ultimoCarrito: List<ItemCarrito>? = null
    var usuarioActual: Usuario? = null
    private val _carrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val carrito: StateFlow<List<ItemCarrito>> = _carrito.asStateFlow()

    private val _totalPagar = MutableStateFlow(0.0)
    val totalPagar: StateFlow<Double> = _totalPagar.asStateFlow()

    init {
        viewModelScope.launch (dispatcher){
            _carrito.collect { items ->
                _totalPagar.value = items.sumOf { it.subtotal }

            }

        }

    }

    fun agregarProducto(producto: Producto) {
        _carrito.update { items ->
            val existente = items.find { it.producto.id == producto.id }
            if (existente != null) {
                items.map {
                    if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + 1) else it
                }
            } else {
                items + ItemCarrito(producto, cantidad = 1)
            }
        }
    }

    fun modificarCantidad(productoId: Int, cantidad: Int) {
        _carrito.update { items ->
            if (cantidad <= 0) items.filter { it.producto.id != productoId }
            else items.map {
                if (it.producto.id == productoId) it.copy(cantidad = cantidad) else it
            }

        }

    }
    fun eliminarProducto(productoId: Int) {
        _carrito.update { items -> items.filter { it.producto.id != productoId } }
    }

    fun limpiarCarrito() {
        _carrito.value = emptyList()
    }

    fun realizarPago(usuario: Usuario): Boolean {
        this.usuarioActual = usuario
        val pagoExitoso = _totalPagar.value > 0 && (Math.random() > 0.1)
        val codigo = (10000000..99999999).random().toString()

        ultimoUsuario = usuario
        ultimoCodigoOrden = codigo
        ultimoTotalPagado = totalPagar.value
        ultimoCarrito = carrito.value.toList()

        if (pagoExitoso) limpiarCarrito()
        return pagoExitoso

    }

}