package com.example.paseleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paseleriamilsabores.mappers.toUI
import com.example.paseleriamilsabores.model.Producto
import com.example.paseleriamilsabores.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    init {
        loadProductos()
    }

    private fun loadProductos() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance. api.getAllProductos()
                _productos.value = response.map { it.toUI() }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
