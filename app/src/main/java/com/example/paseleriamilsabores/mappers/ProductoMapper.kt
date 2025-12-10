package com.example.paseleriamilsabores.mappers

import com.example.paseleriamilsabores.R
import com.example.paseleriamilsabores.model.Producto
import com.example.paseleriamilsabores.model.ProductoCategoria
import com.example.paseleriamilsabores.remote.ProductoRemote

fun ProductoRemote.toUI(): Producto {
    return Producto(
        id = this.id.toInt(),
        nombre = this.nombre_producto,
        precio = this.precio,
        descripcion = this.descripcion,
        img = asignarImagenPorId(this.id.toInt()),
        categoria = mapCategoria(this.categoria)
    )
}

/** Asigna imagen según ID del producto */
fun asignarImagenPorId(id: Int): Int {
    return when (id) {

        // POSTRES (1–8)
        1 -> R.drawable.postre_brownie
        2 -> R.drawable.postre_cheescake_noazucar
        3 -> R.drawable.postre_empanada_manzana
        4 -> R.drawable.postre_galletas_avena
        5 -> R.drawable.postre_mousse_chocolate
        6 -> R.drawable.postre_pan_nogluten
        7 -> R.drawable.postre_tarta_santiago
        8 -> R.drawable.postre_tiramisu_clasico

        // TORTAS (10–16)
        10 -> R.drawable.torta_chocolate_vegan
        11 -> R.drawable.torta_cumpleanos_torta
        12 -> R.drawable.torta_fruta
        13 -> R.drawable.torta_manjar_redonda
        14 -> R.drawable.torta_naranja
        15 -> R.drawable.torta_chocolate
        16 -> R.drawable.torta_vainilla_circular

        // IMAGEN POR DEFECTO
        else -> R.drawable.ic_launcher_foreground
    }
}

/** Convierte texto del backend a enum local */
fun mapCategoria(cat: String): ProductoCategoria {
    return when (cat.lowercase()) {
        "torta", "tortas" -> ProductoCategoria.TORTA
        "postre", "postres" -> ProductoCategoria.POSTRE
        else -> ProductoCategoria.OTRO
    }
}
