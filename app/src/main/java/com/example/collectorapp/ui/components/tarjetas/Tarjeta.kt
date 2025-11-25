package com.example.collectorapp.ui.components.tarjetas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.collectorapp.R
import com.example.collectorapp.ui.theme.LargeCornerRadius

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tarjeta(
    titulo: String,
    lineas: List<String> = emptyList(),
    imagenUri: String? = null,
    onClick: () -> Unit,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    val cardShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = LargeCornerRadius,
        bottomEnd = 0.dp,
        bottomStart = LargeCornerRadius
    )
    val shadowColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)

    var menuVisible by remember { mutableStateOf(false) }

    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = cardShape,
                    ambientColor = shadowColor,
                    spotColor = shadowColor
                )
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = {
                        if (onEdit != null || onDelete != null) {
                            menuVisible = true
                        }
                    }
                ),
            shape = cardShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (imagenUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imagenUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.descripcion_imagen_tarjeta),
                        contentScale = ContentScale.Crop, // Cambio a Crop
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 0.dp,
                                bottomEnd = 8.dp,
                                bottomStart = 0.dp
                            ))
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 0.dp,
                                    bottomEnd = 8.dp,
                                    bottomStart = 0.dp
                                )
                            )
                    )
                }
                Spacer(Modifier.width(16.dp))

                Column {
                    Text(
                        text = titulo,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    lineas.forEach { linea ->
                        Text(
                            text = linea,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        DropdownMenu(
            expanded = menuVisible,
            onDismissRequest = { menuVisible = false },
        ) {
            onEdit?.let {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.editar)) },
                    onClick = {
                        it()
                        menuVisible = false
                    }
                )
            }
            onDelete?.let {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.eliminar)) },
                    onClick = {
                        it()
                        menuVisible = false
                    }
                )
            }
        }
    }
}