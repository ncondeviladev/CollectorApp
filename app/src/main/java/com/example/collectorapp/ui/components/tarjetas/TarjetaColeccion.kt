package com.example.collectorapp.ui.components.tarjetas

import androidx.compose.runtime.Composable

@Composable
fun TarjetaColeccion(
    nombre: String,
    descripcion: String?,
    onClick: () -> Unit
) {
    val lineas = listOfNotNull(//Lista de descripcion, categoria, idColeccion)
        descripcion
    )
    Tarjeta(
        titulo = nombre,
        lineas = lineas,
        onClick = onClick
    )
}