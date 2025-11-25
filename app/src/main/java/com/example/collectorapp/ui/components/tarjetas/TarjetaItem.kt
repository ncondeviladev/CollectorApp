package com.example.collectorapp.ui.components.tarjetas

import androidx.compose.runtime.Composable

@Composable
fun TarjetaItem(
    nombre: String,
    descripcion: String?,
    categoria: String?,
    imagenUri: String?,
    onClick: () -> Unit,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    val lineas = listOfNotNull(
        descripcion,
        if (!categoria.isNullOrBlank()) "Categoría: $categoria" else null
    )
    Tarjeta(
        titulo = nombre,
        lineas = lineas,
        imagenUri = imagenUri,
        onClick = onClick,
        onEdit = onEdit,
        onDelete = onDelete
    )
}
