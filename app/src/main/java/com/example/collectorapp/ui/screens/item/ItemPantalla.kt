package com.example.collectorapp.ui.screens.item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.collectorapp.models.Item
import com.example.collectorapp.ui.components.DialogoConfirmacion
import com.example.collectorapp.ui.navigation.Rutas
import com.example.collectorapp.ui.screens.PantallaLista
import com.example.collectorapp.viewmodels.ItemViewModel

@Composable
fun ItemPantalla(
    titulo: String,
    navController: NavController,
    items: List<Item>,
    onClickNuevo: () -> Unit,
    onBack: () -> Unit,
    idColeccion: Int,
    onToggleTheme: () -> Unit,
    itemVM: ItemViewModel
) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var itemAEliminar by remember { mutableStateOf<Item?>(null) }

    PantallaLista(
        titulo = titulo,
        elementos = items,
        onClickNuevo = onClickNuevo,
        onBack = onBack,
        onToggleTheme = onToggleTheme,
        navController = navController,

        onClickElemento = {  },


        onEditElemento = { itemId ->
            items.find { it.id == itemId }?.let { item ->
                itemVM.itemSeleccionado = item
                val ruta = Rutas.FORMULARIO_ITEM
                    .replace("{idColeccion}", idColeccion.toString())
                    .replace("{modoFormulario}", "editar")
                navController.navigate(ruta)
            }
        },
        onDeleteElemento = { itemId ->
            itemAEliminar = items.find { it.id == itemId }
            mostrarDialogo = true
        }
    )

    if (mostrarDialogo) {
        itemAEliminar?.let { item ->
            DialogoConfirmacion(
                onConfirmar = {
                    itemVM.eliminar(item)
                    mostrarDialogo = false
                },
                onCancelar = { mostrarDialogo = false },
                titulo = "Eliminar Item",
                texto = "Se eliminará '${item.nombre}'. ¿Estás seguro?"
            )
        }
    }
}