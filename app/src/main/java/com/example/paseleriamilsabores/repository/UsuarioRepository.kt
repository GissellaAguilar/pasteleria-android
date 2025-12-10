package com.example.paseleriamilsabores.repository

import com.example.paseleriamilsabores.model.Usuario

// 1. La interfaz define el "contrato"
interface UsuarioRepository {
    suspend fun crearUsuario(usuario: Usuario): Usuario?
}

// 2. La implementación real usa la API
class UsuarioRepositoryImpl(private val apiService: com.example.paseleriamilsabores.remote.ApiService) : UsuarioRepository {
    override suspend fun crearUsuario(usuario: Usuario): Usuario? {
        // Ponemos un try-catch para manejar errores de red directamente aquí
        return try {
            apiService.crearUsuario(usuario)
        } catch (e: Exception) {
            null
        }
    }
}
