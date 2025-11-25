package com.example.collectorapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.collectorapp.R
import com.example.collectorapp.models.Displayable
import com.example.collectorapp.ui.components.tarjetas.Tarjeta

/**
 * Un composable genérico y reutilizable que muestra una lista de elementos `Displayable`.
 * No contiene Scaffold ni lógica de navegación, solo se encarga de la presentación de la lista.
 */
@Composable
fun PantallaLista(
    elementos: List<Displayable>,
    modifier: Modifier = Modifier,
    onClickElemento: (id: Int) -> Unit,
    onEditElemento: ((id: Int) -> Unit)? = null,
    onDeleteElemento: ((id: Int) -> Unit)? = null
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(elementos) { elemento ->
            Tarjeta(
                titulo = elemento.nombre,
                lineas = listOfNotNull(
                    elemento.descripcion,
                    elemento.categoria?.let { stringResource(R.string.prefijo_categoria, it) }
                ),
                imagenUri = elemento.imagen,
                onClick = { onClickElemento(elemento.id) },
                onEdit = onEditElemento?.let { onEdit -> { onEdit(elemento.id) } },
                onDelete = onDeleteElemento?.let { onDelete -> { onDelete(elemento.id) } }
            )
        }
    }
}
