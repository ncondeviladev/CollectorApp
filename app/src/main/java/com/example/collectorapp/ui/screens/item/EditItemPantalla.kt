package com.example.collectorapp.ui.screens.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.collectorapp.models.Item
import com.example.collectorapp.viewmodels.ItemViewModel

@Composable
fun EditItemPantalla(  //Puede guardar nuevo elemento o editarlo si ya existe
    viewModel: ItemViewModel,
    idColeccion: Int,
    itemExistente: Item? = null, // si es null creamos nuevo
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {

    var nombre by remember { mutableStateOf(itemExistente?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(itemExistente?.descripcion ?: "") }
    var categoria by remember { mutableStateOf(itemExistente?.categoria ?: "") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        TextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        Button(
            onClick = {
                val item = Item(
                    id = itemExistente?.id ?: 0, // si es nueva id se ignora
                    idColeccion = idColeccion,
                    nombre = nombre,
                    descripcion = descripcion,
                    categoria = categoria
                )
                if (itemExistente == null) {
                    viewModel.insertar(item)
                } else {
                    viewModel.actualizar(item)
                }
                onGuardar()
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(if (itemExistente == null) "Crear" else "Guardar cambios")
        }

        Button(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Cancelar")
        }
    }
}
