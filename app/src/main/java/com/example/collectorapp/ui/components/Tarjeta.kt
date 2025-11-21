package com.example.collectorapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text


//Tarjeta generica para colecciones e items
@Composable
fun Tarjeta(
    titulo: String,
    lineas: List<String> = emptyList(), //Lista de lineas(descripcion, categoria, idColeccion)
    imagen: String? = null, //URL de la imagen
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row( //Fila de contenido en la tarjerta
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box( //Espacio para la imagen
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
            Spacer(Modifier.width(16.dp))

            Column { //Espacio de texto
                Text( //Titulo principal - Nombre
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium
                )
                //Varias lineas dependiendo de tipo tarjeta
                lineas.forEach { linea ->
                    Text(
                        text = linea,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
