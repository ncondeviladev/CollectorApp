package com.example.collectorapp.ui.screens.coleccion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.ui.components.DialogoConfirmacion
import com.example.collectorapp.ui.navigation.Rutas
import com.example.collectorapp.ui.screens.PantallaLista
import com.example.collectorapp.viewmodels.ColeccionViewModel

@Composable
fun ColeccionPantalla(
    viewModel: ColeccionViewModel,
    navController: NavController,
    onClickColeccion: (Int) -> Unit,
    onClickNuevo: () -> Unit,
    onToggleTheme: () -> Unit
) {
    val colecciones by viewModel.colecciones.collectAsState(initial = emptyList())
    var mostrarDialogo by remember { mutableStateOf(false) }
    var coleccionAEliminar by remember { mutableStateOf<Coleccion?>(null) }

    PantallaLista(
        titulo = "Colecciones",
        elementos = colecciones,
        onClickNuevo = onClickNuevo,
        onToggleTheme = onToggleTheme,
        navController = navController,
        onBack = null,

        onClickElemento = onClickColeccion,

        onEditElemento = { coleccionId ->
            // Construimos la ruta de forma segura, reemplazando el placeholder
            val ruta = Rutas.FORMULARIO_COLECCION.replace("{id}", coleccionId.toString())
            navController.navigate(ruta)
        },
        onDeleteElemento = { coleccionId ->
            coleccionAEliminar = colecciones.find { it.id == coleccionId }
            mostrarDialogo = true
        }
    )

    if (mostrarDialogo) {
        coleccionAEliminar?.let { coleccion ->
            DialogoConfirmacion(
                onConfirmar = {
                    viewModel.eliminar(coleccion)
                    mostrarDialogo = false
                },
                onCancelar = { mostrarDialogo = false },
                titulo = "Eliminar Coleccion",
                texto = "Se eliminará '${coleccion.nombre}' y todos sus ítems. ¿Estás seguro?"
            )
        }
    }
}