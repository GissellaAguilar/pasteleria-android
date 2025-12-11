package com.example.paseleriamilsabores.viewmodeltest

import com.example.paseleriamilsabores.model.LoginRequest
import com.example.paseleriamilsabores.model.LoginResponse
import com.example.paseleriamilsabores.remote.ApiService
import com.example.paseleriamilsabores.viewmodel.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var apiMock: ApiService

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        apiMock = mockk()

        // Inyectar mock en el viewModel mediante reflexión
        viewModel = LoginViewModel()
        val apiField = LoginViewModel::class.java.getDeclaredField("api")
        apiField.isAccessible = true
        apiField.set(viewModel, apiMock)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }


    // -------------------------------------------------------------
    // 1) Prueba unitaria con MOCK → Login exitoso
    // -------------------------------------------------------------
    @Test
    fun `login exitoso actualiza usuarioLogeado`() = runTest {
        // GIVEN
        val fakeResponse = LoginResponse(
            id = 1,
            nombre = "Juan",
            apellidos = "Gómez",
            correo = "test@test.com",
            direccion = null,
            region = null,
            comuna = null,
            run = null,
            fechaNac = null,
            rol = "USER"
        )

        coEvery {
            apiMock.login(LoginRequest("test@test.com", "1234"))
        } returns fakeResponse

        // WHEN
        viewModel.login("test@test.com", "1234")
        advanceUntilIdle()

        // THEN
        assertEquals(fakeResponse, viewModel.usuarioLogeado.value)
        assertNull(viewModel.error.value)
    }


    // -------------------------------------------------------------
    // 2) Prueba → Login con error lanza mensaje en _error
    // -------------------------------------------------------------
    @Test
    fun `login con error actualiza error`() = runTest {
        // GIVEN
        coEvery {
            apiMock.login(any())
        } throws Exception("Error servidor")

        // WHEN
        viewModel.login("correo", "pass")
        advanceUntilIdle()

        // THEN
        assertNotNull(viewModel.error.value)
        assertEquals("Credenciales incorrectas o error de servidor", viewModel.error.value)
        assertNull(viewModel.usuarioLogeado.value)
    }


    // -------------------------------------------------------------
    // 3) Prueba → logout limpia usuarioLogeado
    // -------------------------------------------------------------
    @Test
    fun `logout limpia usuarioLogeado`() = runTest {
        // GIVEN usuario logeado
        val user = LoginResponse(
            id = 7,
            nombre = "Carlos",
            apellidos = "Lopez",
            correo = "carlos@test.com",
            direccion = null,
            region = null,
            comuna = null,
            run = null,
            fechaNac = null,
            rol = "USER"
        )
        viewModel.apply {
            val field = LoginViewModel::class.java.getDeclaredField("_usuarioLogeado")
            field.isAccessible = true
            val state = field.get(this) as kotlinx.coroutines.flow.MutableStateFlow<LoginResponse?>
            state.value = user
        }

        // WHEN
        viewModel.logout()

        // THEN
        assertNull(viewModel.usuarioLogeado.value)
    }
}
