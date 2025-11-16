package com.example.collectorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.models.Displayable
import com.example.collectorapp.models.Item
import com.example.collectorapp.ui.navigation.AppNavHost
import com.example.collectorapp.ui.theme.CollectorAppTheme

class MainActivity : ComponentActivity() {

    private val colecciones = mutableListOf<Coleccion>()
    private val items = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loadTestData()
        setContent {

            CollectorAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    //Llamamos a NavHost pasando coleciones y funcion onGuardar
                    AppNavHost(
                        colecciones = colecciones,
                        items = items,
                        onGuardar = { elemento: Displayable, tipo: String ->
                            if(tipo == "Coleccion") {
                                val nuevaColeccion = elemento as Coleccion
                                nuevaColeccion.id = (colecciones.maxOfOrNull { it.id } ?: 0) + 1
                                colecciones.add(nuevaColeccion)
                            } else {
                                val nuevoItem = elemento as Item
                                nuevoItem.id = (items.maxOfOrNull { it.id } ?: 0) + 1
                                items.add(nuevoItem)
                            }
                        }
                    )
                }
            }

        }
    }

    private fun loadTestData() {
        colecciones.addAll(listOf(
            Coleccion(1, "Monedas Antiguas", "Colección de monedas de diferentes épocas y países."),
            Coleccion(2, "Sellos Postales", "Sellos postales de edición limitada."),
            Coleccion(3, "Figuras de Acción", "Figuras de acción de superhéroes y villanos.")
        ))
        items.addAll(listOf(
            Item(1, "Moneda de Oro", "Moneda de oro del siglo XVIII.", "Monedas", idColeccion = 1),
            Item(2, "Sello del Centenario", "Sello conmemorativo del centenario de la ciudad.", "Otros", idColeccion = 2),
            Item(3, "Figura de Batman", "Figura de acción de Batman de la serie animada.", "Otros", idColeccion = 3)
        ))
    }
}





@Preview(showBackground = true)
@Composable
fun PreviewAppConContenido() {



}