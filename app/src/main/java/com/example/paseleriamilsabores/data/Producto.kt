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
    val ImgProduct:Int,//url de la imagen
    val categoria: ProductoCategoria,
    //@DrawableRes val imagen: Int // @DrawableRes es para imágenes en tu app
)

//Datos de Prueba para simular una base de datos
val sampleProducto= listOf(
    Producto(
        id = 1,
        nombre = "Mousse Chocolate",
        precio = 5000.0,
        descripcion = "Postre individual cremoso y suave, hecho con chocolate de alta calidad, ideal para los amantes del chocolate",
        ImgProduct = R.drawable.postre_mousse_chocolate,
        categoria = ProductoCategoria.POSTRES
    ),
    Producto(
        id = 2,
        nombre = "Tiramisú Clásico",
        precio = 5500.0,
        descripcion = "Un postre italiano individual con capas de café, mascarpone y cacao, perfecto para finalizar cualquier comida.",
        ImgProduct = R.drawable.postre_tiramisu_clasico,
        categoria = ProductoCategoria.POSTRES
    ),
    Producto(
        id = 3,
        nombre = "Cheesecake Sin Azúcar",
        precio = 47000.0,
        descripcion = "Suave y cremoso, este cheesecake es una opción perfecta para disfrutar sin culpa",
        ImgProduct = R.drawable.postre_cheescake_noazucar,
        categoria = ProductoCategoria.POSTRES
    ),
    Producto(
        id = 4,
        nombre = "Empanada de Manzana",
        precio = 3000.0,
        descripcion = "Pastelería tradicional rellena de manzanas especiadas, perfecta para un dulce desayuno o merienda",
        ImgProduct = R.drawable.postre_empanada_manzana,
        categoria = ProductoCategoria.POSTRES
    ),
    Producto(
        id = 5,
        nombre = "Tarta Santiago",
        precio = 6000.0,
        descripcion = "Tradicional tarta española hecha con almendras, azúcar, y huevos, una delicia para los amantes de los postres clásicos",
        ImgProduct = R.drawable.postre_tarta_santiago,
        categoria = ProductoCategoria.POSTRES
    ),
    Producto(
        id = 6,
        nombre = "Brownie Sin Gluten",
        precio = 4000.0,
        descripcion = "Rico y denso, este brownie es perfecto para quienes necesitan evitar el gluten sin sacrificar el sabor",
        ImgProduct = R.drawable.postre_brownie,
        categoria = ProductoCategoria.POSTRES
    ),
    Producto(
        id = 7,
        nombre = "Pan Sin Gluten",
        precio = 3500.0,
        descripcion = "Suave y esponjoso, ideal para sándwiches o para acompañar cualquier comida",
        ImgProduct = R.drawable.postre_pan_nogluten,
        categoria = ProductoCategoria.POSTRES
    ),
    Producto(
        id = 8,
        nombre = "Galletas Veganas de Avena",
        precio = 4000.0,
        descripcion = "Crujientes y sabrosas, estas galletas son una excelente opción para un snack saludable y vegano",
        ImgProduct = R.drawable.postre_galletas_avena,
        categoria = ProductoCategoria.POSTRES
    ),

    Producto(
        id = 9,
        nombre = "Torta Cuadrada Chocolate",
        precio = 45000.0,
        descripcion = "Deliciosa torta de chocolate con capas de ganache y un toque de avellanas. Personalizable con mensajes especiales.",
        ImgProduct = R.drawable.torta_chocolate,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id = 10,
        nombre = "Torta Cuadrada Frutas",
        precio = 50000.0,
        descripcion = "Una mezcla de frutas frescas y crema chantilly sobre un suave bizcocho de vainilla, ideal para celebraciones.",
        ImgProduct = R.drawable.torta_fruta,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id = 11,
        nombre = "Torta Circular Vainilla",
        precio = 40000.0,
        descripcion = "Bizcocho de vainilla clásico relleno con crema pastelera y cubierto con un glaseado dulce, perfecto para cualquier ocasión.",
        ImgProduct = R.drawable.torta_vainilla_circular,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id = 12,
        nombre = "Torta Circular Manjar",
        precio = 42000.0,
        descripcion = "Torta tradicional chilena con manjar y nueces, un deleite para los amantes de los sabores dulces y clásicos",
        ImgProduct = R.drawable.torta_manjar_redonda,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id = 13,
        nombre = "Torta Sin Azucar Naranja",
        precio = 48000.0,
        descripcion = "Torta ligera y deliciosa, endulzada naturalmente, ideal para quienes buscan opciones más saludables",
        ImgProduct = R.drawable.torta_naranja,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id = 14,
        nombre = "Torta Vegana Chocolate",
        precio = 50000.0,
        descripcion = "Torta de chocolate húmeda y deliciosa, hecha sin productos de origen animal, perfecta para veganos",
        ImgProduct = R.drawable.torta_chocolate_vegan,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id = 15,
        nombre = "Torta Especial Cumpleaños",
        precio = 55000.0,
        descripcion = "Diseñada especialmente para celebraciones, personalizable con decoraciones y mensajes únicos",
        ImgProduct = R.drawable.torta_cumpleanos_torta,
        categoria = ProductoCategoria.TORTAS
    ),
    Producto(
        id = 16,
        nombre = "Torta Especial Boda",
        precio = 60000.0,
        descripcion = "Elegante y deliciosa, esta torta está diseñada para ser el centro de atención en cualquier boda",
        ImgProduct = R.drawable.torta_boda_torta,
        categoria = ProductoCategoria.TORTAS
    )


)