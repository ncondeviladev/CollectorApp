package com.example.collectorapp.ui.screens.coleccion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.viewmodels.ColeccionViewModel

@Composable
fun EditColeccionPantalla( //Puede guardar nuevo elemento o editarlo si ya existe
    viewModel: ColeccionViewModel,
    coleccionExistente: Coleccion? = null, // Si es null estamos creando nueva
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {

    var nombre by remember { mutableStateOf(coleccionExistente?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(coleccionExistente?.descripcion ?: "") }
    var categoria by remember { mutableStateOf(coleccionExistente?.categoria ?: "") }

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
                val coleccion = Coleccion(
                    id = coleccionExistente?.id ?: 0, // si es nueva id se ignora
                    nombre = nombre,
                    descripcion = descripcion,
                    categoria = categoria
                )
                if (coleccionExistente == null) {
                    viewModel.insertar(coleccion)
                } else {
                    viewModel.actualizar(coleccion)
                }
                onGuardar() // volver a la lista
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(if (coleccionExistente == null) "Crear" else "Guardar cambios")
        }

        Button(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Cancelar")
        }
    }
}
