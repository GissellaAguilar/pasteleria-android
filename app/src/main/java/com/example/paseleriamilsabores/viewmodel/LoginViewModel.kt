package com.example.paseleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paseleriamilsabores.model.LoginRequest
import com.example.paseleriamilsabores.model.LoginResponse
import com.example.paseleriamilsabores.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val api = RetrofitInstance.api

    private val _usuarioLogeado = MutableStateFlow<LoginResponse?>(null)
    val usuarioLogeado: StateFlow<LoginResponse?> = _usuarioLogeado

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(correo: String, password: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(correo = correo, password = password)
                val response = api.login(request)

                _usuarioLogeado.value = response

            } catch (e: Exception) {
                _error.value = "Credenciales incorrectas o error de servidor"
            }
        }
    }


    fun logout() {
        _usuarioLogeado.value = null
    }



}
