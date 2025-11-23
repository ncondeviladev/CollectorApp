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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.collectorapp.models.Item
import com.example.collectorapp.ui.components.DialogoConfirmacion
import com.example.collectorapp.ui.components.TopBar
import com.example.collectorapp.viewmodels.ItemViewModel

@Composable
fun FormularioItem(
    onToggleTheme: () -> Unit,
    navController: NavController,
    idColeccion: Int,
    itemVM: ItemViewModel,
    modoFormulario: String?
) {
    val esModoEdicion = modoFormulario == "editar"
    val itemAEditar = if (esModoEdicion) itemVM.itemSeleccionado else null

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf<String?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    LaunchedEffect(itemAEditar) {
        if (esModoEdicion) { // Solo rellenamos si estamos editando
            itemAEditar?.let {
                nombre = it.nombre
                descripcion = it.descripcion ?: ""
                categoria = it.categoria ?: ""
                imagen = it.imagen
            }
        }
    }

    val tituloTopBar = if (esModoEdicion) "Editar ${itemAEditar?.nombre ?: "Item"}" else "Añadir Item"

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
                modifier = Modifier.fillMaxWidth()
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
            Button(
                onClick = { /* Lógica para seleccionar imagen */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text(if (imagen != null) "Imagen seleccionada" else "Seleccionar imagen")
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = {
                        if (nombre.isNotBlank()) {
                            val item = itemAEditar?.copy(
                                nombre = nombre,
                                descripcion = descripcion,
                                categoria = categoria,
                                imagen = imagen
                            ) ?: Item(
                                nombre = nombre,
                                descripcion = descripcion,
                                categoria = categoria,
                                imagen = imagen,
                                idColeccion = idColeccion
                            )

                            if (esModoEdicion) {
                                itemVM.actualizar(item)
                            } else {
                                itemVM.insertar(item)
                            }
                            navController.popBackStack()
                        }
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

                if (esModoEdicion) {
                    itemAEditar?.let { item ->
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
    }

    if (mostrarDialogo) {
        itemAEditar?.let { item ->
            DialogoConfirmacion(
                onConfirmar = {
                    itemVM.eliminar(item)
                    mostrarDialogo = false
                    navController.popBackStack()
                },
                onCancelar = { mostrarDialogo = false },
                titulo = "Eliminar Item",
                texto = "Se eliminará '${item.nombre}'. ¿Estás seguro?"
            )
        }
    }
}