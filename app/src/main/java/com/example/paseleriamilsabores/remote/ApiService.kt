package com.example.paseleriamilsabores.remote

    import com.example.paseleriamilsabores.model.DetallePedido
    import com.example.paseleriamilsabores.model.Pedido
    import com.example.paseleriamilsabores.model.Producto
    import com.example.paseleriamilsabores.model.Usuario
    import retrofit2.Response
    import retrofit2.http.*

    interface ApiService {

        // =================================================================
        // 1. ENDPOINTS DE USUARIO (Base: /api/usuario)
        // =================================================================

        // POST: /api/usuario
        @POST("/api/usuario")
        suspend fun crearUsuario(@Body usuario: Usuario): Usuario

        // GET: /api/usuario/{run}
        @GET("/api/usuario/{run}")
        suspend fun getUsuarioByRun(@Path("run") run: String): Usuario

        // GET: /api/usuario
        @GET("/api/usuario")
        suspend fun listUsuarios(): List<Usuario>

        // PUT: /api/usuario/{run}
        @PUT("/api/usuario/{run}")
        suspend fun updateUsuario(
            @Path("run") run: String,
            @Body usuario: Usuario
        ): Usuario

        // DELETE: /api/usuario/{run}
        @DELETE("/api/usuario/{run}")
        suspend fun deleteUsuario(@Path("run") run: String): Unit


        // =================================================================
        // 2. ENDPOINTS DE PRODUCTO (Base: /api/productos)
        // =================================================================

        // POST: /api/productos
        @POST("/api/productos")
        suspend fun createProducto(@Body producto: Producto): Producto

        // GET: /api/productos
        @GET("/api/productos")
        suspend fun getAllProductos(): List<ProductoRemote>

        // GET: /api/productos/categoria/{categoria}
        @GET("/api/productos/categoria/{categoria}")
        suspend fun getProductosByCategoria(@Path("categoria") categoria: String): List<Producto>

        // GET: /api/productos/{id}
        @GET("/api/productos/{id}")
        suspend fun getProductoById(@Path("id") id: Long): Producto

        // PUT: /api/productos/{id}
        @PUT("/api/productos/{id}")
        suspend fun updateProducto(
            @Path("id") id: Long,
            @Body producto: Producto
        ): Producto

        // DELETE: /api/productos/{id}
        @DELETE("/api/productos/{id}")
        suspend fun deleteProducto(@Path("id") id: Long): Unit


        // =================================================================
        // 3. ENDPOINTS DE PEDIDO (Base: /api/pedido)
        // =================================================================

        // POST: /api/pedido
        @POST("/api/pedido")
        suspend fun crearPedido(@Body pedido: Pedido): Response<Pedido>

        // GET: /api/pedido/{idPedido}
        @GET("/api/pedido/{idPedido}")
        suspend fun obtenerPedidoPorId(@Path("idPedido") id: Long): Response<Pedido>

        // PUT: /api/pedido/{idPedido}
        @PUT("/api/pedido/{idPedido}")
        suspend fun actualizarPedido(
            @Path("idPedido") id: Long,
            @Body pedido: Pedido
        ): Response<Pedido>

        // DELETE: /api/pedido/{idPedido}
        @DELETE("/api/pedido/{idPedido}")
        suspend fun eliminarPedido(@Path("idPedido") id: Long): Response<Unit>


        // =================================================================
        // 4. ENDPOINTS DE DETALLE PEDIDO (Base: /api/detalles-pedido)
        // =================================================================

        // POST: /api/detalles-pedido
        @POST("/api/detalles-pedido")
        suspend fun crearDetallePedido(@Body detalle: DetallePedido): Response<DetallePedido>

        // GET: /api/detalles-pedido
        @GET("/api/detalles-pedido")
        suspend fun obtenerTodosLosDetalles(): Response<List<DetallePedido>>

        // GET: /api/detalles-pedido/{id}
        @GET("/api/detalles-pedido/{id}")
        suspend fun obtenerDetallePorId(@Path("id") id: Long): Response<DetallePedido>

        // PUT: /api/detalles-pedido/{id}
        @PUT("/api/detalles-pedido/{id}")
        suspend fun actualizarDetallePedido(
            @Path("id") id: Long,
            @Body detalle: DetallePedido
        ): Response<DetallePedido>

        // DELETE: /api/detalles-pedido/{id}
        @DELETE("/api/detalles-pedido/{id}")
        suspend fun eliminarDetallePedido(@Path("id") id: Long): Response<Unit>
    }
