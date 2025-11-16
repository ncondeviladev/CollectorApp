package com.example.collectorapp.ui.screens.coleccion

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.ui.screens.PantallaLista
import com.example.collectorapp.viewmodels.ColeccionViewModel
import kotlin.collections.emptyList

@Composable
fun ColeccionPantalla (
    titulo: String,
    navController: NavController,
    colecciones: List<Coleccion>,
    onClickElemento: (coleccionId: Int) -> Unit,
    onClickNuevo: () -> Unit
) {

    PantallaLista(
        titulo = titulo,
        elementos = colecciones,
        onClickElemento = onClickElemento,
        onClickNuevo = onClickNuevo,
    )
}