package com.example.collectorapp.ui.components.formulario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.models.Displayable
import com.example.collectorapp.models.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioElemento(
    coleccionesDisponibles: List<Coleccion>, //En caso de que se guarde item
    onGuardar: (Displayable, tipo: String) -> Unit, //Llama al displayable correspondiente para guardar el elemento
    onCancelar: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Coleccion o Item") }
    var coleccionSeleccionada by remember { mutableStateOf<Coleccion?>(null) }
    var imagen by remember { mutableStateOf<String?>(null) } //Placeholder para futura imagen

    //Scroll de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        //Selector de tipo
        Text("Tipo de elemento")
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            RadioButton(
                selected = tipo == "Coleccion",
                onClick = { tipo = "Coleccion" }
            )
            Text("Coleccion")

            RadioButton(
                selected = tipo == "Item",
                onClick = { tipo = "Item" }
            )
            Text("Item")
        }
        //Campos de formulario
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

        //Selector de coleccion solo si es item
        if (tipo == "Item") {
            Text("Asignar a coleccion")
            DropdownMenu(
                expanded = coleccionesDisponibles.isNotEmpty(),
                onDismissRequest = {}
            ) {
                coleccionesDisponibles.forEach { coleccion ->
                    DropdownMenuItem(
                        text = { Text(coleccion.nombre) },
                        onClick = { coleccionSeleccionada = coleccion }
                    )
                }
            }

            //Selñector de imaghen
            Button(onClick = {/* TODO: abrir selector de imagen */ }) {
                Text(if (imagen != null) "Imagen seleccionada" else "Seleccionar imagen")
            }
            //Botones guardar y cancelar
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = {
                        if (nombre.isNotBlank()) {
                            //Crea Displayable temporal y envia onGuardar
                            if (tipo == "Coleccion") {
                                val coleccion = Coleccion(
                                    nombre = nombre,
                                    descripcion = descripcion,
                                    categoria = categoria
                                )
                                onGuardar(coleccion, tipo)

                            } else if (tipo == "Item" && coleccionSeleccionada != null) {
                                val item = Item(
                                    nombre = nombre,
                                    descripcion = descripcion,
                                    categoria = categoria,
                                    idColeccion = coleccionSeleccionada!!.id
                                )
                                onGuardar(item, tipo)
                            }
                        }
                    }
                ) {
                    Text("Guardar")
                }
                OutlinedButton(onClick = onCancelar) {
                    Text("Cancelar")
                }

            }

        }
    }
}