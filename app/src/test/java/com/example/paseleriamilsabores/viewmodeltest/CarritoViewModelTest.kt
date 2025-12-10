package com.example.paseleriamilsabores.viewmodeltest

import com.example.paseleriamilsabores.model.Producto
import com.example.paseleriamilsabores.model.ProductoCategoria
import com.example.paseleriamilsabores.viewmodel.CarritoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CarritoViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val testScope = TestScope(dispatcher)
    private lateinit var viewModel: CarritoViewModel

    @Before
    fun setUp() {
        viewModel = CarritoViewModel(dispatcher)
    }

    @Test
    fun agregarDosVecesMismoProducto_incrementaCantidad() = testScope.runTest {
        val producto = Producto(
            id = 1,
            nombre = "Pastel Chocolate",
            precio = 5000.0,
            descripcion = "Delicioso",
            img = 0,
            categoria = ProductoCategoria.TORTA
        )

        viewModel.agregarProducto(producto)
        viewModel.agregarProducto(producto)

        dispatcher.scheduler.advanceUntilIdle()

        val lista = viewModel.carrito.first()
        val resultado = lista.first { it.producto.id == 1 }

        assertEquals(2, resultado.cantidad)
    }
}
