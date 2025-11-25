package com.example.collectorapp.ui.screens.coleccion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.collectorapp.ui.screens.PantallaLista
import com.example.collectorapp.viewmodels.ColeccionViewModel

@Composable
fun ColeccionPantalla(
    viewModel: ColeccionViewModel,
    onColeccionClick: (Int) -> Unit,
    onEditColeccion: (Int) -> Unit
) {
    val colecciones by viewModel.colecciones.collectAsState()

    PantallaLista(
        elementos = colecciones,
        onClickElemento = onColeccionClick,
        onEditElemento = onEditColeccion,
        onDeleteElemento = { coleccionId ->
            viewModel.eliminar(colecciones.find { it.id == coleccionId }!!)
        }
    )
}