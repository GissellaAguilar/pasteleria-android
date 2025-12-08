package com.example.paseleriamilsabores.repository

import com.example.paseleriamilsabores.model.Pedido
import com.example.paseleriamilsabores.model.Usuario
import com.example.paseleriamilsabores.remote.ApiService

class PedidoRepository(private val apiService: ApiService) {

    // 1. CREAR Pedido (POST)
    suspend fun crearPedido(pedido: Pedido): Pedido? {
        val response = apiService.crearPedido(pedido)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Se devuelve null si falla (código 4xx, 5xx)
            null
        }
    }

    // 2. OBTENER Pedido por ID (GET)
    suspend fun obtenerPedidoPorId(idPedido: Long): Pedido? {
        val response = apiService.obtenerPedidoPorId(idPedido)
        return if (response.isSuccessful) {
            response.body()
        } else {
            // Se devuelve null si no se encuentra (404) o hay un error
            null
        }
    }

    // 3. ACTUALIZAR Pedido (PUT)
    suspend fun actualizarPedido(idPedido: Long, pedidoActualizado: Pedido): Pedido? {
        val response = apiService.actualizarPedido(idPedido, pedidoActualizado)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    // 4. ELIMINAR Pedido (DELETE)
    suspend fun eliminarPedido(idPedido: Long): Boolean {
        val response = apiService.eliminarPedido(idPedido)
        // Solo verificamos si fue exitoso (código 204 No Content)
        return response.isSuccessful
    }
}