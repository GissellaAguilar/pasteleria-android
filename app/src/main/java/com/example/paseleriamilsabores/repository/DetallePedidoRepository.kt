package com.example.paseleriamilsabores.repository

import com.example.paseleriamilsabores.model.DetallePedido
import com.example.paseleriamilsabores.remote.ApiService

class DetallePedidoRepository(private val apiService: ApiService) {

    // 1. CREAR Detalle de Pedido (POST)
    suspend fun crearDetallePedido(detalle: DetallePedido): DetallePedido? {
        val response = apiService.crearDetallePedido(detalle)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    // 2. OBTENER Todos los Detalles de Pedido (GET)
    suspend fun obtenerTodosLosDetalles(): List<DetallePedido> {
        val response = apiService.obtenerTodosLosDetalles()
        return if (response.isSuccessful) {
            // Devuelve la lista o una lista vacía si el cuerpo es null (aunque es raro en un GET exitoso)
            response.body() ?: emptyList()
        } else {
            // Se devuelve una lista vacía en caso de error
            emptyList()
        }
    }

    // 3. OBTENER Detalle de Pedido por ID (GET)
    suspend fun obtenerDetallePorId(idDetalle: Long): DetallePedido? {
        val response = apiService.obtenerDetallePorId(idDetalle)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    // 4. ACTUALIZAR Detalle de Pedido (PUT)
    suspend fun actualizarDetallePedido(idDetalle: Long, detalleActualizado: DetallePedido): DetallePedido? {
        val response = apiService.actualizarDetallePedido(idDetalle, detalleActualizado)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    // 5. ELIMINAR Detalle de Pedido (DELETE)
    suspend fun eliminarDetallePedido(idDetalle: Long): Boolean {
        val response = apiService.eliminarDetallePedido(idDetalle)
        // Un DELETE es exitoso si el código es 204 No Content
        return response.isSuccessful
    }
}