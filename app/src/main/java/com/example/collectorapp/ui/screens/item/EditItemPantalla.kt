package com.example.collectorapp.ui.screens.item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.collectorapp.models.Item
import com.example.collectorapp.ui.components.formulario.FormularioElemento
import com.example.collectorapp.viewmodels.ColeccionViewModel
import com.example.collectorapp.viewmodels.ItemViewModel

@Composable
fun EditItemPantalla(
    itemViewModel: ItemViewModel,
    coleccionViewModel: ColeccionViewModel,
    itemExistente: Item?,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    val colecciones by coleccionViewModel.colecciones.collectAsState()

    FormularioElemento(
        elemento = itemExistente,
        tipoDeElemento = "Item",
        coleccionesDisponibles = colecciones,
        onGuardar = { displayable ->
            val item = displayable as Item
            if (itemExistente == null) {
                itemViewModel.insertar(item)
            } else {
                // Nos aseguramos de mantener el ID original al actualizar
                itemViewModel.actualizar(item.copy(id = itemExistente.id))
            }
            onGuardar()
        },
        onCancelar = onCancelar
    )
}