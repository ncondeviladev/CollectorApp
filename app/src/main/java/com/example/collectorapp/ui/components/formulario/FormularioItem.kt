package com.example.collectorapp.ui.components.formulario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.models.Item
import com.example.collectorapp.ui.components.TopBar
import com.example.collectorapp.viewmodels.ItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioItem(
    coleccionesDisponibles: List<Coleccion>,
    idColeccion: Int,
    onGuardar: (Item) -> Unit,
    onCancelar: () -> Unit,
    navController: NavController,
    itemVM: ItemViewModel
) {
    val item = itemVM.itemSeleccionado
    var nombre by remember { mutableStateOf(item?.nombre  ?: "") }
    var descripcion by remember { mutableStateOf(item?.descripcion ?: "") }
    var categoria by remember { mutableStateOf(item?.categoria ?: "") }
    var imagen by remember { mutableStateOf<String?>(null) } // Imagen opcional
    val nombreColeccion = coleccionesDisponibles.find { it.id == idColeccion }?.nombre ?: "Colección desconocida"
    val tituloTopBar = if(item != null) "Editar ${item.nombre}" else "Añadir a $nombreColeccion"

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(navController = navController, titulo = tituloTopBar)

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
            Button(onClick = { imagen = if (imagen == null) "placeholder" else null }) {
                Text(if (imagen != null) "Imagen seleccionada" else "Seleccionar imagen")
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = {
                    if (nombre.isNotBlank()) {
                        onGuardar(
                            Item(
                                nombre = nombre,
                                descripcion = descripcion,
                                categoria = categoria,
                                imagen = imagen,
                                idColeccion = idColeccion
                            )
                        )
                    }
                }) { Text("Guardar") }

                OutlinedButton(onClick = onCancelar) { Text("Cancelar") }

                if(item != null){
                    Button(
                        onClick = {
                            itemVM.eliminar(item)
                            navController.popBackStack()
                        },
                    ) {
                        Text("Eliminar")
                    }
                }

            }
        }
    }
}
