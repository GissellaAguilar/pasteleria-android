package com.example.paseleriamilsabores.viewmodeltest

import com.example.paseleriamilsabores.model.Usuario
import com.example.paseleriamilsabores.repository.UsuarioRepository
import com.example.paseleriamilsabores.viewmodel.RegistroViewModel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegistroViewModelTest {

    @Test
    fun `registro con exito debe devolver true`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val usuarioRepository = mockk<UsuarioRepository>()
        val viewModel = RegistroViewModel(usuarioRepository, testDispatcher)

        val usuario = Usuario(
            run = "12345678", nombre = "Juan", apellidos = "Perez",
            correo = "juan@test.com", password = "1234", direccion = "Calle 123",
            region = "RM", comuna = "Santiago", fechaNac = "2000-01-01",
            codigo = null, rol = "USER"
        )
        coEvery { usuarioRepository.crearUsuario(any()) } returns usuario

        var resultado = false

        // ACT (Actuar)
        viewModel.registrarUsuario(usuario) { exito, _ ->
            resultado = exito
        }

        // ASSERT (Verificar)
        assertTrue(resultado)
        coVerify { usuarioRepository.crearUsuario(usuario) }

        // ✅ 2. Limpieza explícita DENTRO del test.
        Dispatchers.resetMain()
    }

    @Test
    fun `registro con error de red debe devolver false`() = runTest {
        // ARRANGE (Preparar)
        // ✅ 1. Configuración explícita DENTRO del test.
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val usuarioRepository = mockk<UsuarioRepository>()
        val viewModel = RegistroViewModel(usuarioRepository, testDispatcher)

        val usuario = Usuario(
            run = "test", nombre = "test", apellidos = "test",
            correo = "test@test.com", password = "123", direccion = "test",
            region = "test", comuna = "test", fechaNac = "2000-01-01",
            codigo = null, rol = "USER"
        )
        coEvery { usuarioRepository.crearUsuario(any()) } returns null

        var resultado = true

        // ACT (Actuar)
        viewModel.registrarUsuario(usuario) { exito, _ ->
            resultado = exito
        }

        // ASSERT (Verificar)
        assertFalse(resultado)
        coVerify { usuarioRepository.crearUsuario(usuario) }

        // ✅ 2. Limpieza explícita DENTRO del test.
        Dispatchers.resetMain()
    }

    @Test
    fun `registro con excepcion debe devolver false`() = runTest {
        // ARRANGE
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val usuarioRepository = mockk<UsuarioRepository>()
        val viewModel = RegistroViewModel(usuarioRepository, testDispatcher)

        val usuario = Usuario(
            run = "99999999",
            nombre = "Error",
            apellidos = "Test",
            correo = "error@test.com",
            password = "1234",
            direccion = "Error 123",
            region = "RM",
            comuna = "Santiago",
            fechaNac = "2000-01-01",
            codigo = null,
            rol = "USER"
        )

        // Simula excepción de red, timeout, backend caído, etc.
        coEvery { usuarioRepository.crearUsuario(any()) } throws RuntimeException("Error de conexión")

        var resultado = true
        var mensajeError: String? = null

        // ACT
        viewModel.registrarUsuario(usuario) { exito, error ->
            resultado = exito
            mensajeError = error
        }

        // ASSERT
        assertFalse(resultado)
        assertTrue(mensajeError != null)

        coVerify { usuarioRepository.crearUsuario(usuario) }

        // LIMPIEZA
        Dispatchers.resetMain()
    }

}
