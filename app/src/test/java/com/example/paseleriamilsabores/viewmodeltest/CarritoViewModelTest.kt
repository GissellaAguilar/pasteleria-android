package com.example.paseleriamilsabores.viewmodeltest
import app.cash.turbine.test
import com.example.paseleriamilsabores.model.Producto
import com.example.paseleriamilsabores.model.ProductoCategoria
import com.example.paseleriamilsabores.viewmodel.CarritoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain

@OptIn(ExperimentalCoroutinesApi::class)

class CarritoViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val viewModel = CarritoViewModel()
    private val productoMock = Producto(
        1,
        "Pastel Chocolate",
        100.0,
        "rica torta",
        0,
        categoria = ProductoCategoria.TORTA)

    @Test
    fun agregarDosVecesMismoProducto_incrementaCantidad() = runTest {
        viewModel.agregarProducto(productoMock)
        viewModel.agregarProducto(productoMock)
        viewModel.carrito.test {
            awaitItem()

            val lista = awaitItem()
            assertEquals(1, lista.size)
            assertEquals(2, lista[0].cantidad)
            assertEquals(100.0, lista[0].producto.precio, 0.0)
            cancelAndIgnoreRemainingEvents()
        }

    }

}