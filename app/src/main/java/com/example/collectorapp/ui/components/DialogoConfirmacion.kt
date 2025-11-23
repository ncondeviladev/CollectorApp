package com.example.collectorapp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DialogoConfirmacion(
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit,
    titulo: String,
    texto: String
) {
    AlertDialog(
        onDismissRequest = onCancelar, // si el usuario toca fuera se cancela
        title = { Text(titulo) },
        text = { Text(texto) },
        confirmButton = {
            TextButton(onClick = onConfirmar) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}