package com.example.collectorapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.models.Displayable
import com.example.collectorapp.models.Item
import com.example.collectorapp.ui.screens.PantallaLista
import com.example.collectorapp.ui.components.TopBar
import com.example.collectorapp.ui.components.formulario.FormularioColeccion
import com.example.collectorapp.ui.components.formulario.FormularioItem
import com.example.collectorapp.ui.screens.coleccion.ColeccionPantalla
import com.example.collectorapp.ui.screens.item.ItemPantalla


@Composable
fun AppNavHost (
    colecciones: List<Coleccion>,
    items: List<Item>,
    onGuardar: (Displayable, String) -> Unit
) {
    val navController = rememberNavController()
    //Donde es mejhor el topbar, aqui en navHost o en PantallaLista??

        NavHost(
            navController = navController,
            startDestination = Rutas.LISTA_COLECCIONESS,
        ) {
            composable(Rutas.LISTA_COLECCIONESS) {
                ColeccionPantalla(
                    titulo = "Colecciones",
                    colecciones = colecciones,
                    onClickElemento = { coleccionId ->
                        navController.navigate("listaItems/$coleccionId")
                    },
                    onClickNuevo = { navController.navigate("formularioColeccion") },
                    navController = navController
                )
            }

            composable(
                route = Rutas.LISTA_ITEMS,
                arguments = listOf(navArgument("idColeccion") { type = NavType.IntType })
            ) { backStackEntry ->
                val idColeccion = backStackEntry.arguments?.getInt("idColeccion") ?: 0
                //Filtrar items de esa coleccion y los pasa a PantallaLista
                val itemsDeColeccion = items.filter { it.idColeccion == idColeccion }

                ItemPantalla(
                    titulo = "Items",
                    navController = navController,
                    items = itemsDeColeccion,
                    onClickElemento = {}, //Edita item implementacion futura
                    onClickNuevo = {
                        navController.navigate("formularioItem/$idColeccion")
                    },
                    onBack = {
                        navController.popBackStack()
                    },
                    idColeccion = idColeccion,
                )
            }

            composable(Rutas.FORMULARIO_COLECCION) {
                FormularioColeccion(
                    navController = navController,
                    onGuardar = { coleccion ->
                        onGuardar(coleccion, "Coleccion")
                        navController.popBackStack() //Volver a la lista
                    },
                    onCancelar = { navController.popBackStack() }
                )
            }
            composable(
                route = Rutas.FORMULARIO_ITEM,
                arguments = listOf(navArgument("idColeccion") { type = NavType.IntType })
            ) { backStackEntry ->
                val idColeccion = backStackEntry.arguments?.getInt("idColeccion") ?: 0
                FormularioItem(
                    navController = navController,
                    coleccionesDisponibles = colecciones,
                    idColeccion = idColeccion,
                    onGuardar = { item ->
                        onGuardar(item, "Item")
                        navController.popBackStack()
                    },
                    onCancelar = { navController.popBackStack() }
                )

            }
        }
}