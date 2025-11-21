package com.example.collectorapp.ui.components

import androidx.compose.runtime.Composable

@Composable
fun TarjetaItem(
    nombre: String,
    descripcion: String?,
    categoria: String?,
) {
    val lienas = listOfNotNull( //Lista de descripcion, categoria, idColeccion)
        descripcion,
        "Categoría: $categoria"
    )
    Tarjeta(
        titulo = nombre,
        lineas = lienas,
        onClick = null //Los items no navegan por ahora
    )
}