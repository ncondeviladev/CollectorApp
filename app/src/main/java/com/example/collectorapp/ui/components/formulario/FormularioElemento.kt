package com.example.collectorapp.ui.components.formulario

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.collectorapp.R
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.models.Displayable
import com.example.collectorapp.models.Item

@Composable
fun FormularioElemento(
    elemento: Displayable?,
    tipoDeElemento: String,
    coleccionesDisponibles: List<Coleccion>,
    onGuardar: (Displayable) -> Unit,
    onCancelar: () -> Unit
) {
    var nombre by remember(elemento) { mutableStateOf(elemento?.nombre ?: "") }
    var descripcion by remember(elemento) { mutableStateOf(elemento?.descripcion ?: "") }
    var categoria by remember(elemento) { mutableStateOf(elemento?.categoria ?: "") }

    val imageUris = remember(elemento) {
        ((elemento as? Item)?.imageUris?.map { it.toUri() } ?: emptyList()).toMutableStateList()
    }
    var selectedImageUri by remember(elemento) { mutableStateOf(elemento?.imagen?.let { it.toUri() }) }

    val singleImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }
    )

    val multipleImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris: List<Uri> ->
            imageUris.addAll(uris)
        }
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(stringResource(R.string.nombre)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text(stringResource(R.string.descripcion)) },
            modifier = Modifier.fillMaxWidth()
        )

        if (tipoDeElemento == "Item") {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text(stringResource(R.string.categoria)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { multipleImagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                Text(stringResource(R.string.anadir_fotos))
            }
        } else { // Es una Coleccion
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { singleImagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                Text(stringResource(R.string.anadir_cambiar_foto))
            }
        }

        // Galería para Items
        if (tipoDeElemento == "Item" && imageUris.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.instruccion_imagenes), style = MaterialTheme.typography.bodySmall)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(imageUris) { uri ->
                    Box(modifier = Modifier.clickable { selectedImageUri = uri }) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(uri).crossfade(true).build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(100.dp)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(4.dp))
                                .border(
                                    width = if (uri == selectedImageUri) 2.dp else 0.dp,
                                    color = if (uri == selectedImageUri) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                        IconButton(
                            onClick = {
                                imageUris.remove(uri)
                                if (selectedImageUri == uri) {
                                    selectedImageUri = imageUris.firstOrNull()
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(4.dp)
                                .size(20.dp)
                                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.eliminar_imagen),
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
        // Preview para Coleccion
        else if (tipoDeElemento == "Coleccion" && selectedImageUri != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.imagen_coleccion), style = MaterialTheme.typography.bodySmall)
            AsyncImage(model = selectedImageUri, contentDescription = null, modifier = Modifier.height(100.dp).clip(RoundedCornerShape(4.dp)))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = {
                val elementoGuardado: Displayable = if (tipoDeElemento == "Coleccion") {
                    (elemento as? Coleccion)?.copy(nombre = nombre, descripcion = descripcion, imagen = selectedImageUri?.toString()) ?: Coleccion(nombre = nombre, descripcion = descripcion, imagen = selectedImageUri?.toString())
                } else {
                    val idColeccion = (elemento as? Item)?.idColeccion ?: coleccionesDisponibles.firstOrNull()?.id ?: 0
                    val itemExistente = elemento as? Item
                    val nuevasUrisString = imageUris.map { it.toString() }

                    itemExistente?.copy(
                        nombre = nombre,
                        descripcion = descripcion,
                        categoria = categoria,
                        imagen = selectedImageUri?.toString(),
                        imageUris = nuevasUrisString
                    ) ?: Item(
                        nombre = nombre,
                        descripcion = descripcion,
                        categoria = categoria,
                        idColeccion = idColeccion,
                        imagen = selectedImageUri?.toString(),
                        imageUris = nuevasUrisString
                    )
                }
                onGuardar(elementoGuardado)
            }) {
                Text(stringResource(R.string.guardar))
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = onCancelar) {
                Text(stringResource(R.string.cancelar))
            }
        }
    }
}