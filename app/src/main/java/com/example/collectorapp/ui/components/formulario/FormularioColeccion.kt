package com.example.collectorapp.ui.components.formulario

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.ui.components.DialogoConfirmacion
import com.example.collectorapp.ui.components.TopBar
import com.example.collectorapp.viewmodels.ColeccionViewModel

@Composable
fun FormularioColeccion(
    coleccionId: Int?,
    viewModel: ColeccionViewModel,
    navController: NavController,
    onToggleTheme: () -> Unit
) {
    val colecciones by viewModel.colecciones.collectAsState()
    
    val coleccionAEditar = remember(coleccionId, colecciones) {
        if (coleccionId != null) colecciones.find { it.id == coleccionId } else null
    }

    var nombre by remember(coleccionAEditar) { mutableStateOf(coleccionAEditar?.nombre ?: "") }
    var descripcion by remember(coleccionAEditar) { mutableStateOf(coleccionAEditar?.descripcion ?: "") }
    var categoria by remember(coleccionAEditar) { mutableStateOf(coleccionAEditar?.categoria ?: "") }
    var mostrarDialogo by remember { mutableStateOf(false) }

    val tituloTopBar = if (coleccionAEditar != null) "Editar ${coleccionAEditar.nombre}" else "Nueva Colección"

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            navController = navController,
            titulo = tituloTopBar,
            onToggleTheme = onToggleTheme
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = {
                        val coleccion = coleccionAEditar?.copy(
                            nombre = nombre,
                            descripcion = descripcion,
                            categoria = categoria
                        ) ?: Coleccion(
                            nombre = nombre,
                            descripcion = descripcion,
                            categoria = categoria
                        )

                        if (coleccionAEditar != null) {
                            viewModel.actualizar(coleccion)
                        } else {
                            viewModel.insertar(coleccion)
                        }
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text("Guardar")
                }

                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text("Cancelar")
                }

                if (coleccionAEditar != null) {
                    Button(
                        onClick = { mostrarDialogo = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }

    if (mostrarDialogo) {
        coleccionAEditar?.let { coleccion ->
            DialogoConfirmacion(
                onConfirmar = {
                    viewModel.eliminar(coleccion)
                    mostrarDialogo = false
                    navController.popBackStack() 
                },
                onCancelar = { mostrarDialogo = false },
                titulo = "Eliminar Colección",
                texto = "Se eliminará '${coleccion.nombre}' y todos sus ítems. ¿Estás seguro?"
            )
        }
    }
}