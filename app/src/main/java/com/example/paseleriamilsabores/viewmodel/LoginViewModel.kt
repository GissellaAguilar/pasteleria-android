package com.example.paseleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paseleriamilsabores.model.Usuario
import com.example.paseleriamilsabores.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val api = RetrofitInstance.api

    private val _usuarioLogeado = MutableStateFlow<Usuario?>(null)
    val usuarioLogeado: StateFlow<Usuario?> = _usuarioLogeado

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(run: String, password: String) {
        viewModelScope.launch {
            try {
                val usuario = api.getUsuarioByRun(run)

                if (usuario.password == password) {
                    _usuarioLogeado.value = usuario
                } else {
                    _error.value = "Contraseña incorrecta"
                }

            } catch (e: Exception) {
                _error.value = "Usuario no encontrado o error de servidor"
            }
        }
    }
}
