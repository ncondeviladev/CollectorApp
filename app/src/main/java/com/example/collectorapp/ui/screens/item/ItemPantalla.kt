package com.example.collectorapp.ui.screens.item

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.collectorapp.models.Item
import com.example.collectorapp.ui.screens.PantallaLista

@Composable
fun ItemPantalla(
    titulo: String,
    navController: NavController,
    items: List<Item>,
    onClickElemento: (Int) -> Unit,
    onClickNuevo: () -> Unit,
    onBack: () -> Unit,
    idColeccion: Int
)  {
    PantallaLista(
        titulo = titulo,
        elementos = items,
        onClickElemento = onClickElemento,
        onClickNuevo = onClickNuevo,
        onBack = onBack
    )
}