package com.example.paseleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.paseleriamilsabores.model.Usuario
import com.example.paseleriamilsabores.remote.RetrofitInstance
import com.example.paseleriamilsabores.repository.UsuarioRepository
import com.example.paseleriamilsabores.repository.UsuarioRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// ✅ ViewModel ahora depende de la INTERFAZ, no de la implementación
class RegistroViewModel(
    private val usuarioRepository: UsuarioRepository, // <--- Cambio clave
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun registrarUsuario(usuario: Usuario, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            try {
                // La llamada al repositorio ya no necesita withContext(Dispatchers.IO)
                // porque el repositorio se encargará de eso si es necesario.
                val usuarioCreado = usuarioRepository.crearUsuario(usuario)
                _loading.value = false

                if (usuarioCreado != null) {
                    onResult(true, null) // Éxito
                } else {
                    onResult(false, "El usuario no pudo ser creado.") // Falla controlada
                }

            } catch (e: Exception) {
                _loading.value = false
                onResult(false, e.message) // Falla por excepción
            }
        }
    }
}

// ✅ Fábrica para crear el ViewModel en la UI, ya que ahora tiene dependencias
class RegistroViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistroViewModel::class.java)) {
            // La UI crea la implementación real del repositorio
            val repository = UsuarioRepositoryImpl(RetrofitInstance.api)
            return RegistroViewModel(repository, Dispatchers.Main) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
    