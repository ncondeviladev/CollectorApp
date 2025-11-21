package com.example.collectorapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.collectorapp.viewmodels.ColeccionViewModel
import com.example.collectorapp.viewmodels.ItemViewModel
import androidx.compose.runtime.getValue


@Composable
fun AppNavHost(
    coleccionVM: ColeccionViewModel,
    itemVM: ItemViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.LISTA_COLECCIONESS
    ) {
        composable(Rutas.LISTA_COLECCIONESS) {
            val colecciones by coleccionVM.colecciones.collectAsState()
            ColeccionPantalla(
                viewModel = coleccionVM,
                onClickColeccion = { coleccionId ->
                    itemVM.cargarItems(coleccionId)
                    navController.navigate("listaItems/$coleccionId")
                },
                onClickNuevo = {
                    navController.navigate(Rutas.FORMULARIO_COLECCION)
                }
            )
        }

        composable(
            route = Rutas.LISTA_ITEMS,
            arguments = listOf(navArgument("idColeccion") { type = NavType.IntType })
        ) { backStackEntry ->
            val idColeccion = backStackEntry.arguments?.getInt("idColeccion") ?: 0
            val items by itemVM.items.collectAsState()
            val itemsDeColeccion = items.filter { it.idColeccion == idColeccion }

            ItemPantalla(
                titulo = "Items",
                navController = navController,
                items = itemsDeColeccion,
                onClickElemento = { itemId ->
                    navController.navigate("formularioItem/$idColeccion?id=$itemId")
                },
                onClickNuevo = { navController.navigate("formularioItem/$idColeccion?id=0") },
                onBack = { navController.popBackStack() },
                idColeccion = idColeccion
            )
        }

        composable(Rutas.FORMULARIO_COLECCION) {
            FormularioColeccion(
                navController = navController,
                onGuardar = { coleccion ->
                    coleccionVM.insertar(coleccion)
                    navController.popBackStack()
                },
                onCancelar = { navController.popBackStack() }
            )
        }

        composable(
            route = Rutas.FORMULARIO_ITEM,
            arguments = listOf(navArgument("idColeccion") { type = NavType.IntType })
        ) { backStackEntry ->
            val idColeccion = backStackEntry.arguments?.getInt("idColeccion") ?: 0
            val colecciones by coleccionVM.colecciones.collectAsState()

            FormularioItem(
                navController = navController,
                coleccionesDisponibles = colecciones,
                idColeccion = idColeccion,
                onGuardar = { item ->
                    itemVM.insertar(item)
                    navController.popBackStack()
                },
                onCancelar = { navController.popBackStack() }
            )
        }
    }
}
