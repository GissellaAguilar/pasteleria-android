package com.example.paseleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// Importaciones de tus modelos de la capa de datos (usados en la UI)
import com.example.paseleriamilsabores.data.ItemCarrito
import com.example.paseleriamilsabores.data.Producto as ProductoData
import com.example.paseleriamilsabores.data.Usuario as UsuarioData

// Importaciones de los modelos de la API/Backend
import com.example.paseleriamilsabores.model.DetallePedido
import com.example.paseleriamilsabores.model.Pedido
import com.example.paseleriamilsabores.model.Usuario as UsuarioBackend
import com.example.paseleriamilsabores.model.Producto as ProductoBackend

// Importación del Repositorio y Cliente Retrofit
import com.example.paseleriamilsabores.repository.PedidoRepository
import com.example.paseleriamilsabores.remote.RetrofitInstance

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime // Si usas fechas de Kotlin
import kotlin.random.Random

// Inicialización de la dependencia del Repositorio
// Se recomienda usar DI (Hilt/Koin) en un proyecto grande.
val pedidoRepository = PedidoRepository(RetrofitInstance.apiService)

class CarritoViewModel(
    private val repository: PedidoRepository = pedidoRepository
) : ViewModel() {

    var ultimoUsuario: UsuarioBackend? = null
    var ultimoCodigoOrden: String? = null
    var ultimoTotalPagado: Double? = null
    var ultimoCarrito: List<ItemCarrito>? = null
    var usuarioActual: UsuarioBackend? = null

    private val _carrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val carrito: StateFlow<List<ItemCarrito>> = _carrito.asStateFlow()

    private val _totalPagar = MutableStateFlow(0.0)
    val totalPagar: StateFlow<Double> = _totalPagar.asStateFlow()

    init {
        viewModelScope.launch {
            _carrito.collect { items ->
                // Actualiza el totalPagar automáticamente con la suma de los subtotales
                _totalPagar.value = items.sumOf { it.producto.precio * it.cantidad }
            }
        }
    }

    // =================================================================
    // FUNCIONES DE GESTIÓN DE CARRITO (INCLUYENDO LAS SOLICITADAS)
    // =================================================================

    fun agregarProducto(producto: ProductoData) {
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

    /**
     * Modifica la cantidad de un producto en el carrito.
     * Si la cantidad es <= 0, elimina el producto.
     */
    fun modificarCantidad(productoId: Int, cantidad: Int) {
        _carrito.update { items ->
            if (cantidad <= 0) items.filter { it.producto.id != productoId }
            else items.map {
                if (it.producto.id == productoId) it.copy(cantidad = cantidad) else it // <--- CORRECCIÓN: Usar la nueva 'cantidad'
            }
        }
    }

    /**
     * Elimina un producto del carrito.
     */
    fun eliminarProducto(productoId: Int) {
        _carrito.update { items -> items.filter { it.producto.id != productoId } }
    }

    fun limpiarCarrito() {
        _carrito.value = emptyList()
    }

    // =================================================================
    // FUNCIÓN DE PAGO CON INTEGRACIÓN A LA API
    // =================================================================
    suspend fun realizarPago(usuario: UsuarioBackend): Boolean {
        this.usuarioActual = usuario
        val itemsCarrito = carrito.value
        val total = totalPagar.value

        if (itemsCarrito.isEmpty() || total <= 0) return false

        // 1. DATO DE USUARIO (Usando el alias UsuarioBackend)
        val runUsuario = usuario.run ?: "GUEST-${System.currentTimeMillis()}"

        // Se usa UsuarioBackend (el modelo de la API)
        val usuarioParaPedido = UsuarioBackend(
            run = runUsuario,
            nombre = usuario.nombre,
            password = "", // Placeholder
            rol = "USER",
            apellidos = usuario.apellidos,
            correo = usuario.correo,
            direccion = usuario.direccion,
            region = usuario.region,
            comuna = usuario.comuna,
            fechaNac = "",
            codigo = ""
        )

        // 2. DETALLES DEL PEDIDO (Mapeando ItemCarrito a DetallePedido)
        val detallesPedido = itemsCarrito.map { item ->

            // Mapeo ProductoData (UI) -> ProductoBackend (API)
            // Se usa ProductoBackend (el modelo de la API)
            val productoBackend = ProductoBackend(
                id = item.producto.id.toLong(), // Convertir Int a Long si es necesario
                nombre = item.producto.nombre,
                precio = item.producto.precio
            )

            DetallePedido(
                producto = productoBackend,
                cantidad = item.cantidad,
                subtotal = item.producto.precio * item.cantidad
            )
        }

        // 3. CONSTRUIR EL PEDIDO FINAL
        val nuevoPedido = Pedido(
            usuario = usuarioParaPedido,
            detalles = detallesPedido,
            total = total
        )

        // 4. LLAMAR AL REPOSITORIO
        val pedidoCreado = repository.crearPedido(nuevoPedido)

        // 5. GESTIONAR RESULTADO
        return if (pedidoCreado != null) {
            // Éxito (HTTP 201 Created)
            val codigo = pedidoCreado.idPedido.toString()

            // Guardar datos de la orden
            ultimoUsuario = usuario
            ultimoCodigoOrden = codigo
            ultimoTotalPagado = total
            ultimoCarrito = itemsCarrito.toList()

            limpiarCarrito()
            true
        } else {
            // Falla de la API (Error 4xx, 5xx, etc.)
            ultimoCodigoOrden = "ERROR_API"
            false
        }
    }
}