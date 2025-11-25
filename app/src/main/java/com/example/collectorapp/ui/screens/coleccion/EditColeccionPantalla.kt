package com.example.collectorapp.ui.screens.coleccion

import androidx.compose.runtime.Composable
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.models.Displayable
import com.example.collectorapp.ui.components.formulario.FormularioElemento
import com.example.collectorapp.viewmodels.ColeccionViewModel

@Composable
fun EditColeccionPantalla( 
    viewModel: ColeccionViewModel,
    coleccionExistente: Coleccion? = null, 
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    FormularioElemento(
        elemento = coleccionExistente,
        tipoDeElemento = "Coleccion",
        coleccionesDisponibles = emptyList(), 
        onGuardar = {
            displayable ->
            val coleccion = displayable as Coleccion
            if (coleccionExistente == null) {
                viewModel.insertar(coleccion)
            } else {
                viewModel.actualizar(coleccion)
            }
            onGuardar()
        },
        onCancelar = onCancelar
    )
}