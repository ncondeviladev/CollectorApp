package com.example.collectorapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.collectorapp.R
import com.example.collectorapp.models.Displayable
import com.example.collectorapp.ui.components.tarjetas.Tarjeta
import com.example.collectorapp.ui.theme.MediumCornerRadius

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLista(
    titulo: String,
    elementos: List<Displayable>,
    onClickNuevo: () -> Unit,
    onBack: (() -> Unit)? = null,
    onToggleTheme: () -> Unit,
    navController: NavController,
    onClickElemento: (id: Int) -> Unit,
    onEditElemento: ((id: Int) -> Unit)? = null,
    onDeleteElemento: ((id: Int) -> Unit)? = null
) {
    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(
                    bottomStart = MediumCornerRadius,
                    bottomEnd = MediumCornerRadius
                )
            ) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.logobasico),
                                contentDescription = "App Logo",
                                modifier = Modifier.size(32.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(titulo)
                        }
                    },
                    navigationIcon = {
                        if (onBack != null) {
                            IconButton(onClick = onBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = onToggleTheme) {
                            Icon(Icons.Default.Brightness6, contentDescription = "Cambiar tema")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onSecondary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSecondary,
                        actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClickNuevo,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo")
            }
        }
    ) { paddingValues ->

        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(elementos) {
                elemento ->
                Tarjeta(
                    titulo = elemento.nombre,
                    lineas = listOfNotNull(elemento.descripcion, elemento.categoria),
                    onClick = { onClickElemento(elemento.id) },
                    onEdit = onEditElemento?.let { onEdit -> { onEdit(elemento.id) } },
                    onDelete = onDeleteElemento?.let { onDelete -> { onDelete(elemento.id) } }
                )
            }
        }
    }
}