package com.example.paseleriamilsabores.viewmodeltest
import org.junit.Test
import org.junit.Before
import com.example.paseleriamilsabores.model.Producto
import com.example.paseleriamilsabores.model.ProductoCategoria
import com.example.paseleriamilsabores.viewmodel.CarritoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
public class CarritoViewModelTest {

    private lateinit var viewModel: CarritoViewModel

    @Before
    fun setUp() {
        viewModel = CarritoViewModel()
    }

    @Test
    fun `agregarProducto agrega un producto nuevo al carrito`() = runTest {
        // Producto de ejemplo
        val producto = Producto(
            id = 1,
            nombre = "Pastel",
            precio = 1000.0,
            descripcion = "Pastel rico",
            img = 0,
            categoria = ProductoCategoria.TORTA
        )

        // Acción
        viewModel.agregarProducto(producto)

        // Resultado esperado
        val carrito = viewModel.carrito.value

        assertEquals(1, carrito.size)
        assertEquals(1, carrito[0].cantidad)
        assertEquals(1000.0, carrito[0].subtotal, 0.001)
    }

    @Test
    fun `agregarProducto incrementa cantidad si el producto ya existe`() = runTest {
        val producto = Producto(
            id = 1,
            nombre = "Pastel",
            precio = 1000.0,
            descripcion = "Pastel rico",
            img = 0,
            categoria = ProductoCategoria.TORTA
        )

        // Agregado dos veces
        viewModel.agregarProducto(producto)
        viewModel.agregarProducto(producto)

        val carrito = viewModel.carrito.value

        assertEquals(1, carrito.size)
        assertEquals(2, carrito[0].cantidad)
        assertEquals(2000.0, carrito[0].subtotal, 0.001)
    }

}
