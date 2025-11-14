package com.example.paseleriamilsabores.data

import androidx.annotation.DrawableRes
import com.example.paseleriamilsabores.R


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
    val ImgProduct:Int,
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
            precio = 5000,
            ImgProduct = R.drawable.torta_chocolate, // Reemplazar con URL real
            categoria = ProductoCategoria.POSTRES
        ),
    Producto(
            id = 2,
            nombre = "Tiramisú Clásico",
            descripcion = "Un postre italiano individual con capas de café, mascarpone y cacao,\n" +
                    "      perfecto para finalizar cualquier comida.",
            precio = 5500,
            ImgProduct = R.drawable.postre_tiramisu_clasico,
            categoria = ProductoCategoria.POSTRES
        ),
    Producto(
            id = 3,
            nombre = "Cheesecake Sin Azúcar",
            descripcion = "Suave y cremoso, este cheesecake es una opción perfecta para disfrutar sin culpa.",
            precio = 47000,
            ImgProduct = R.drawable.postre_cheescake_noazucar,
            categoria = ProductoCategoria.POSTRES
        ),
    Producto(
            id = 4,
            nombre = "Empanada de Manzana",
            descripcion = "Pastelería tradicional rellena de manzanas especiadas, perfecta para un dulce desayuno o merienda",
            precio = 3000,
            ImgProduct = R.drawable.postre_empanada_manzana, // Reemplazar con URL real
            categoria = ProductoCategoria.POSTRES
        ),
        // Agrega más productos aquí para probar el desplazamiento y el filtrado
    Producto(
            id = 5,
            nombre = "Tarta Santiago",
            descripcion = "Tradicional tarta española hecha con almendras, azúcar, y huevos, \n" +
                    "      una delicia para los amantes de los postres clásicos.",
            precio = 6000,
            ImgProduct = R.drawable.postre_tarta_santiago,
            categoria = ProductoCategoria.POSTRES
        ),
    Producto(
        id = 6,
        nombre = "Brownie Sin Gluten",
        descripcion = "Rico y denso, este brownie es perfecto para quienes necesitan evitar el gluten sin sacrificar el sabor",
        precio = 4000,
        ImgProduct = R.drawable.postre_brownie,
        categoria = ProductoCategoria.POSTRES

    ),
    Producto(
        id= 7,
        nombre = "Pan Sin Gluten",
        descripcion = "Suave y esponjoso, ideal para sándwiches o para acompañar cualquier comida",
        precio = 3500,
        ImgProduct = R.drawable.postre_pan_nogluten,
        categoria = ProductoCategoria.POSTRES

    ),
    Producto(
        id=8,
        nombre="Galletas Veganas de Avena",
        descripcion = "Crujientes y sabrosas, estas galletas son una excelente opción para un snack saludable y vegano",
        precio = 4000,
        ImgProduct = R.drawable.postre_galletas_avena,
        categoria= ProductoCategoria.POSTRES

    ),
    Producto(
        id = 9,
        nombre = "Torta Chocolate",
        descripcion = "Deliciosa torta de chocolate con capas de ganache y un toque de\n" +
                "      avellanas. Personalizable con mensajes especiales",
        precio = 45000,
        ImgProduct = R.drawable.torta_chocolate,
        categoria = ProductoCategoria.TORTAS

    ),
    Producto(
        id=10,
        nombre = "Torta de Frutas",
        descripcion = "Una mezcla de frutas frescas y crema chantilly sobre un suave\n" +
                "      bizcocho de vainilla, ideal para celebraciones",
        precio = 50000,
        ImgProduct = R.drawable.torta_fruta,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id=11,
        nombre="Torta Vainilla",
        descripcion = "Bizcocho de vainilla clásico relleno con crema pastelera y cubierto\n" +
                "     con un glaseado dulce, perfecto para cualquier ocasión.",
        precio = 40000,
        ImgProduct = R.drawable.torta_vainilla_circular,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id=12,
        "Torta Circular Manjar",
        descripcion = "Torta tradicional chilena con manjar y nueces, un deleite para los\n" +
                "      amantes de los sabores dulces y clásicos",
        precio = 42000,
        ImgProduct = R.drawable.torta_manjar_redonda,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id=13,
        nombre = "Torta Sin Azucar Naranja",
        descripcion = "Torta ligera y deliciosa, endulzada naturalmente, ideal para\n" +
                "      quienes buscan opciones más saludables",
        precio = 48000,
        ImgProduct = R.drawable.torta_naranja,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id=14,
        nombre="Torta Vegana Chocolate",
        descripcion = "Torta de chocolate húmeda y deliciosa, hecha sin productos de\n" +
                "      origen animal, perfecta para veganos",
        precio = 50000,
        ImgProduct = R.drawable.torta_chocolate_vegan,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id=15,
        nombre="Torta Especial Cumpleaños",
        descripcion = "Diseñada especialmente para celebraciones, personalizable\n" +
                "      con decoraciones y mensajes únicos",
        precio = 55000,
        ImgProduct = R.drawable.torta_cumpleanos_torta,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id=16,
        nombre="Torta Especial de Boda",
        descripcion = "Elegante y deliciosa, esta torta está diseñada para ser el centro de\n" +
                "      atención en cualquier boda",
        precio = 60000,
        ImgProduct = R.drawable.torta_boda_torta,
        categoria = ProductoCategoria.TORTAS
    )


)