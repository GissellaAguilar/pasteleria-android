package com.example.paseleriamilsabores.viewmodeltest

import com.example.paseleriamilsabores.model.Producto
import com.example.paseleriamilsabores.model.ProductoCategoria
import com.example.paseleriamilsabores.model.Usuario
import com.example.paseleriamilsabores.repository.PedidoRepository
import com.example.paseleriamilsabores.repository.DetallePedidoRepository
import com.example.paseleriamilsabores.viewmodel.CarritoViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class CarritoViewModelTest {

    private lateinit var viewModel: CarritoViewModel
    private lateinit var pedidoRepositoryMock: PedidoRepository
    private lateinit var detallePedidoRepositoryMock: DetallePedidoRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Creamos mocks
        pedidoRepositoryMock = mockk()
        detallePedidoRepositoryMock = mockk()

        // Inyectamos mocks usando reflexión
        viewModel = CarritoViewModel()
        val pedidoField = CarritoViewModel::class.java.getDeclaredField("pedidoRepository")
        pedidoField.isAccessible = true
        pedidoField.set(viewModel, pedidoRepositoryMock)

        val detalleField = CarritoViewModel::class.java.getDeclaredField("detallePedidoRepository")
        detalleField.isAccessible = true
        detalleField.set(viewModel, detallePedidoRepositoryMock)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `agregarProducto agrega un producto nuevo al carrito`() = runTest {
        val producto = Producto(1, "Pastel", 1000.0, "Rico", 0, ProductoCategoria.TORTA)
        viewModel.agregarProducto(producto)
        advanceUntilIdle()

        val carrito = viewModel.carrito.value
        assertEquals(1, carrito.size)
        assertEquals(1, carrito[0].cantidad)
        assertEquals(1000.0, carrito[0].subtotal, 0.001)
    }

    @Test
    fun `agregarProducto incrementa cantidad si el producto ya existe`() = runTest {
        val producto = Producto(1, "Pastel", 1000.0, "Rico", 0, ProductoCategoria.TORTA)
        viewModel.agregarProducto(producto)
        viewModel.agregarProducto(producto)
        advanceUntilIdle()

        val carrito = viewModel.carrito.value
        assertEquals(1, carrito.size)
        assertEquals(2, carrito[0].cantidad)
        assertEquals(2000.0, carrito[0].subtotal, 0.001)
    }

    @Test
    fun `realizarPago con mock de repositorio devuelve exito`() = runTest {
        val usuario = Usuario("1234", "Juan", "Perez", "juan@test.com", "1234", "Calle 123", "RM", "Santiago", "2000-01-01", null, "USER")
        val producto = Producto(1, "Pastel", 1000.0, "Rico", 0, ProductoCategoria.TORTA)
        viewModel.agregarProducto(producto)
        advanceUntilIdle()

        // Mock del repositorio para simular pago exitoso
        coEvery { pedidoRepositoryMock.crearPedido(any()) } returns mockk(relaxed = true) {
            coEvery { idPedido } returns 1
        }
        coEvery { detallePedidoRepositoryMock.crearDetallePedido(any()) } returns mockk(relaxed = true)

        var resultadoPago = false
        var codigo: String? = null

        viewModel.realizarPago(usuario) { exito, codigoOrden ->
            resultadoPago = exito
            codigo = codigoOrden
        }

        advanceUntilIdle()

        assertTrue(resultadoPago)
        assertEquals("1", codigo)
        assertEquals(0, viewModel.carrito.value.size)
    }
}
