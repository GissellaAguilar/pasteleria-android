package com.example.paseleriamilsabores.viewmodeltest

import com.example.paseleriamilsabores.model.LoginRequest
import com.example.paseleriamilsabores.model.LoginResponse
import com.example.paseleriamilsabores.remote.ApiService
import com.example.paseleriamilsabores.remote.RetrofitInstance
import com.example.paseleriamilsabores.viewmodel.LoginViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    // ✅ TEST 1 — LOGIN EXITOSO
    @Test
    fun `login exitoso debe actualizar usuarioLogeado`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        // Mock del ApiService
        val apiMock = mockk<ApiService>()

        // Interceptamos el singleton RetrofitInstance
        mockkObject(RetrofitInstance)
        every { RetrofitInstance.api } returns apiMock

        val responseMock = LoginResponse(
            token = "token123",
            correo = "test@test.com",
            rol = "USER"
        )

        coEvery {
            apiMock.login(any())
        } returns responseMock

        val viewModel = LoginViewModel()

        // ACT
        viewModel.login("test@test.com", "1234")
        advanceUntilIdle()

        // ASSERT
        assertNotNull(viewModel.usuarioLogeado.value)
        assertEquals("test@test.com", viewModel.usuarioLogeado.value?.correo)

        coVerify { apiMock.login(any()) }

        Dispatchers.resetMain()
        unmockkAll()
    }

    // ✅ TEST 2 — LOGIN CON ERROR (credenciales incorrectas / servidor)
    @Test
    fun `login con error debe actualizar mensaje de error`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val apiMock = mockk<ApiService>()
        mockkObject(RetrofitInstance)
        every { RetrofitInstance.api } returns apiMock

        coEvery {
            apiMock.login(any())
        } throws RuntimeException("Error servidor")

        val viewModel = LoginViewModel()

        // ACT
        viewModel.login("mal@test.com", "1234")
        advanceUntilIdle()

        // ASSERT
        assertNull(viewModel.usuarioLogeado.value)
        assertEquals(
            "Credenciales incorrectas o error de servidor",
            viewModel.error.value
        )

        coVerify { apiMock.login(any()) }

        Dispatchers.resetMain()
        unmockkAll()
    }

    // ✅ TEST 3 — LOGOUT DEBE LIMPIAR EL USUARIO
    @Test
    fun `logout debe limpiar usuarioLogeado`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val apiMock = mockk<ApiService>()
        mockkObject(RetrofitInstance)
        every { RetrofitInstance.api } returns apiMock

        val responseMock = LoginResponse(
            token = "token123",
            correo = "test@test.com",
            rol = "USER"
        )

        coEvery { apiMock.login(any()) } returns responseMock

        val viewModel = LoginViewModel()

        // Simulamos login
        viewModel.login("test@test.com", "1234")
        advanceUntilIdle()
        assertNotNull(viewModel.usuarioLogeado.value)

        // ACT → Logout
        viewModel.logout()

        // ASSERT
        assertNull(viewModel.usuarioLogeado.value)

        Dispatchers.resetMain()
        unmockkAll()
    }
}
