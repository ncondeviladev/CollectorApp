package com.example.collectorapp.ui.screens.coleccion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.collectorapp.ui.screens.PantallaLista
import com.example.collectorapp.viewmodels.ColeccionViewModel

@Composable
fun ColeccionPantalla(
    viewModel: ColeccionViewModel,
    onClickColeccion: (Int) -> Unit,
    onClickNuevo: () -> Unit
) {
    val colecciones by viewModel.colecciones.collectAsState(initial = emptyList())

    PantallaLista(
        titulo = "Colecciones",
        elementos = colecciones,
        onClickElemento = onClickColeccion,
        onClickNuevo = onClickNuevo
    )
}