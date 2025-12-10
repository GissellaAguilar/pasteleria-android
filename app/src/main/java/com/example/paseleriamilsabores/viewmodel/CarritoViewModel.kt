package com.example.paseleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paseleriamilsabores.model.DetallePedido
import com.example.paseleriamilsabores.model.Pedido
import com.example.paseleriamilsabores.model.Producto
import com.example.paseleriamilsabores.model.Usuario
import com.example.paseleriamilsabores.remote.RetrofitInstance
import com.example.paseleriamilsabores.repository.DetallePedidoRepository
import com.example.paseleriamilsabores.repository.PedidoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CarritoViewModel : ViewModel() {

    private val pedidoRepository = PedidoRepository(RetrofitInstance.api)
    private val detallePedidoRepository = DetallePedidoRepository(RetrofitInstance.api)

    private val _carrito = MutableStateFlow<List<DetallePedido>>(emptyList())
    val carrito: StateFlow<List<DetallePedido>> = _carrito

    private val _totalPagar = MutableStateFlow(0.0)
    val totalPagar: StateFlow<Double> = _totalPagar

    var ultimoCodigoOrden: String? = null
    var ultimoTotalPagado: Double? = null
    var ultimoCarrito: List<DetallePedido>? = null
    var usuarioActual: Usuario? = null

    init {
        viewModelScope.launch {
            _carrito.collect { items ->
                _totalPagar.value = items.sumOf { it.subtotal }
            }
        }
    }

    fun agregarProducto(producto: Producto) {
        _carrito.update { items ->
            val existente = items.find { it.producto.id == producto.id }

            if (existente != null) {
                items.map { item ->
                    if (item.producto.id == producto.id) {
                        val nuevaCantidad = item.cantidad + 1
                        val nuevoSubtotal = item.producto.precio * nuevaCantidad

                        item.copy(
                            cantidad = nuevaCantidad,
                            subtotal = nuevoSubtotal
                        )
                    } else item
                }
            } else {
                items + DetallePedido(
                    producto = producto,
                    cantidad = 1,
                    subtotal = producto.precio
                )
            }
        }
    }


    // ✅ MODIFICAR CANTIDAD
    fun modificarCantidad(idProducto: Int, nuevaCantidad: Int) {
        _carrito.value = _carrito.value.map { item ->
            if (item.producto.id == idProducto) {
                val nuevoSubtotal = item.producto.precio * nuevaCantidad
                item.copy(
                    cantidad = nuevaCantidad,
                    subtotal = nuevoSubtotal
                )
            } else item
        }
    }

    // ✅ ELIMINAR PRODUCTO
    fun eliminarProducto(idProducto: Int) {
        _carrito.value = _carrito.value.filter {
            it.producto.id != idProducto
        }
    }

    fun limpiarCarrito() {
        _carrito.value = emptyList()
        _totalPagar.value = 0.0
    }

    // ✅ REALIZAR PAGO COMPLETO
    fun realizarPago(usuario: Usuario, onResultado: (Boolean, String?) -> Unit = { _, _ -> }) {
        viewModelScope.launch {
            try {
                usuarioActual = usuario

                val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                val pedido = Pedido(
                    usuario = usuario,
                    detalles = emptyList(),
                    fechaPedido = fechaActual,
                    total = totalPagar.value
                )

                val pedidoCreado = pedidoRepository.crearPedido(pedido)

                if (pedidoCreado == null || pedidoCreado.idPedido == null) {
                    onResultado(false, null)
                    return@launch
                }

                val pedidoId = pedidoCreado.idPedido

                _carrito.value.forEach { detalle ->
                    val detalleParaEnviar = DetallePedido(
                        producto = detalle.producto,
                        cantidad = detalle.cantidad,
                        subtotal = detalle.subtotal,
                        pedidoId = pedidoId
                    )
                    detallePedidoRepository.crearDetallePedido(detalleParaEnviar)
                }

                ultimoCodigoOrden = pedidoId.toString()
                ultimoTotalPagado = totalPagar.value
                ultimoCarrito = _carrito.value.toList()

                limpiarCarrito()

                onResultado(true, ultimoCodigoOrden)

            } catch (e: Exception) {
                e.printStackTrace()
                onResultado(false, null)
            }
        }
    }
}
