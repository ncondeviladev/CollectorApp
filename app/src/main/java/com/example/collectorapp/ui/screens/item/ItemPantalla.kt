package com.example.collectorapp.ui.screens.item

import androidx.compose.runtime.Composable
import com.example.collectorapp.models.Item
import com.example.collectorapp.ui.screens.PantallaLista
import com.example.collectorapp.viewmodels.ItemViewModel

@Composable
fun ItemPantalla(
    items: List<Item>,
    itemVM: ItemViewModel,
    onItemClick: (Int) -> Unit,
    onEditItem: (Int) -> Unit
) {
    PantallaLista(
        elementos = items,
        onClickElemento = onItemClick,
        onEditElemento = onEditItem,
        onDeleteElemento = { itemId ->
            items.find { it.id == itemId }?.let { itemVM.eliminar(it) }
        }
    )
}