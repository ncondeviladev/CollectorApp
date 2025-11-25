package com.example.collectorapp.ui.components.tarjetas

import androidx.compose.runtime.Composable

@Composable
fun TarjetaColeccion(
    nombre: String,
    descripcion: String?,
    imagenUri: String?,
    onClick: () -> Unit
) {
    val lineas = listOfNotNull(
        descripcion
    )
    Tarjeta(
        titulo = nombre,
        lineas = lineas,
        imagenUri = imagenUri,
        onClick = onClick
    )
}