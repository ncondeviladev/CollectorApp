package com.example.collectorapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.collectorapp.models.Displayable
import com.example.collectorapp.ui.components.tarjetas.Tarjeta
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton


//Pantalla generica de lista de colecciones e items
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLista(
    titulo: String,
    elementos: List<Displayable>,
    onClickElemento: (id: Int) -> Unit,
    onClickNuevo: () -> Unit,
    onBack: (() -> Unit)? = null
) {
    Scaffold( //Scaffold general con topbar y boton añadir
        topBar = {
            TopAppBar(
                title = { Text(titulo) },
                navigationIcon = { //Icono de retroceso si hay pantalla anterior
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClickNuevo) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo")
            }
        }
    ) { paddingValues -> //Contendio de la pantalla lista con scroll

        LazyColumn( //lazy column ajusta tamaño segun contenido
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            items(elementos) { elemento ->
                Tarjeta(
                    titulo = elemento.nombre,
                    lineas = listOfNotNull(elemento.descripcion, elemento.categoria),
                    onClick = { onClickElemento(elemento.id) },
                )
        }
    }
}
}

