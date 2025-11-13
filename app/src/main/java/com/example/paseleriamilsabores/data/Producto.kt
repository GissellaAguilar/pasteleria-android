package com.example.paseleriamilsabores.data

import androidx.annotation.DrawableRes



enum class ProductoCategoria{
    TODOS,
    TORTAS,
    POSTRES
}

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val ImgProduct:String,//url de la imagen
    val categoria: ProductoCategoria,
    //@DrawableRes val imagen: Int // @DrawableRes es para imágenes en tu app
)

//Datos de Prueba para simular una base de datos
val sampleProducto= listOf(
    Producto(
            id = 1,
            nombre = "Mousse Chocolate",
            descripcion = "Postre individual cremoso y suave, hecho con chocolate de alta calidad,\n" +
                    "       ideal para los amantes del chocolate<.",
            precio = 5.000,
            ImgProduct = "https://placehold.co/600x400/FFC0CB/555?text=Tres+Leches", // Reemplazar con URL real
            categoria = ProductoCategoria.POSTRES
        ),
    Producto(
            id = 2,
            nombre = "Tiramisú Clásico",
            descripcion = "Un postre italiano individual con capas de café, mascarpone y cacao,\n" +
                    "      perfecto para finalizar cualquier comida.",
            precio = 5.500,
            ImgProduct = "url",
            categoria = ProductoCategoria.POSTRES
        ),
    Producto(
            id = 3,
            nombre = "Cheesecake Sin Azúcar",
            descripcion = "Suave y cremoso, este cheesecake es una opción perfecta para disfrutar sin culpa.",
            precio = 47.000,
            ImgProduct = "https://placehold.co/600x400/FFA500/FFF?text=Mousse+Maracuya", // Reemplazar con URL real
            categoria = ProductoCategoria.POSTRES
        ),
    Producto(
            id = 4,
            nombre = "Empanada de Manzana",
            descripcion = "Pastelería tradicional rellena de manzanas especiadas, perfecta para un dulce desayuno o merienda",
            precio = 3.000,
            ImgProduct = "https://placehold.co/600x400/40E0D0/FFF?text=Cu", // Reemplazar con URL real
            categoria = ProductoCategoria.POSTRES
        ),
        // Agrega más productos aquí para probar el desplazamiento y el filtrado
    Producto(
            id = 5,
            nombre = "Tarta Santiago",
            descripcion = "Tradicional tarta española hecha con almendras, azúcar, y huevos, \n" +
                    "      una delicia para los amantes de los postres clásicos.",
            precio = 6.000,
            ImgProduct = "https://placehold.co/600x400/8B0000/FFF?text=Red+Velvet", // Reemplazar con URL real
            categoria = ProductoCategoria.POSTRES
        ),
    Producto(
        id = 6,
        nombre = "Brownie Sin Gluten",
        descripcion = "Rico y denso, este brownie es perfecto para quienes necesitan evitar el gluten sin sacrificar el sabor",
        precio = 4.000,
        ImgProduct = "url",
        categoria = ProductoCategoria.POSTRES

    ),
    Producto(
        id= 7,
        nombre = "Pan Sin Gluten",
        descripcion = "Suave y esponjoso, ideal para sándwiches o para acompañar cualquier comida",
        precio = 3.500,
        ImgProduct = "url",
        categoria = ProductoCategoria.POSTRES

    ),
    Producto(
        id=8,
        nombre="Galletas Veganas de Avena",
        descripcion = "Crujientes y sabrosas, estas galletas son una excelente opción para un snack saludable y vegano",
        precio = 4.000,
        ImgProduct = "url",
        categoria= ProductoCategoria.POSTRES

    ),
    Producto(
        id = 9,
        nombre = "Torta Chocolate",
        descripcion = "Deliciosa torta de chocolate con capas de ganache y un toque de\n" +
                "      avellanas. Personalizable con mensajes especiales",
        precio = 45.000,
        ImgProduct = "url",
        categoria = ProductoCategoria.TORTAS

    ),
    Producto(
        id=10,
        nombre = "Torta de Frutas",
        descripcion = "Una mezcla de frutas frescas y crema chantilly sobre un suave\n" +
                "      bizcocho de vainilla, ideal para celebraciones",
        precio = 50.000,
        ImgProduct = "url",
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id=11,
        nombre="Torta Vainilla",
        descripcion = "Bizcocho de vainilla clásico relleno con crema pastelera y cubierto\n" +
                "     con un glaseado dulce, perfecto para cualquier ocasión.",
        precio = 40.000,
        ImgProduct = "url",
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id=12,
        "Torta Circular Manjar",
        descripcion = "Torta tradicional chilena con manjar y nueces, un deleite para los\n" +
                "      amantes de los sabores dulces y clásicos",
        precio = 42.000,
        ImgProduct = "url",
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id=13,
        nombre = "Torta Sin Azucar Naranja",
        descripcion = "Torta ligera y deliciosa, endulzada naturalmente, ideal para\n" +
                "      quienes buscan opciones más saludables",
        precio = 48.000,
        ImgProduct = "url",
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id=14,
        nombre="Torta Vegana Chocolate",
        descripcion = "Torta de chocolate húmeda y deliciosa, hecha sin productos de\n" +
                "      origen animal, perfecta para veganos",
        precio = 50.000,
        ImgProduct = "url",
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id=15,
        nombre="Torta Especial Cumpleaños",
        descripcion = "Diseñada especialmente para celebraciones, personalizable\n" +
                "      con decoraciones y mensajes únicos",
        precio = 55.000,
        ImgProduct = "url",
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id=16,
        nombre="orta Especial de Boda",
        descripcion = "Elegante y deliciosa, esta torta está diseñada para ser el centro de\n" +
                "      atención en cualquier boda",
        precio = 60.000,
        ImgProduct = "url",
        categoria = ProductoCategoria.TORTAS
    )


)