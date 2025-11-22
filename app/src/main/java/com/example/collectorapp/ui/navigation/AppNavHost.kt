package com.example.collectorapp.ui.navigation

import android.view.Surface
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
    itemVM: ItemViewModel,
    onToggleTheme: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Rutas.LISTA_COLECCIONESS,

            ) {
            composable(Rutas.LISTA_COLECCIONESS) {
                val colecciones by coleccionVM.colecciones.collectAsState()
                ColeccionPantalla(
                    onToggleTheme = onToggleTheme,
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

                val colecciones by coleccionVM.colecciones.collectAsState()
                val coleccionActual = colecciones.find { it.id == idColeccion }
                val nombreColeccion = coleccionActual?.nombre ?: "Colección sin nombre"

                ItemPantalla(
                    onToggleTheme = onToggleTheme,
                    titulo = nombreColeccion,
                    navController = navController,
                    items = itemsDeColeccion,
                    onClickElemento = { itemId ->
                        val item = itemsDeColeccion.find { it.id == itemId }!!
                        itemVM.itemSeleccionado = item  //  guardamos el item para editar
                        navController.navigate("formularioItem/$idColeccion?modoFormulario=editar")
                    },
                    onClickNuevo = {
                        itemVM.itemSeleccionado = null // limpiar antes de añadir
                        navController.navigate("formularioItem/$idColeccion?modoFormulario=añadir")
                    },
                    onBack = { navController.popBackStack() },
                    idColeccion = idColeccion
                )
            }

            composable(Rutas.FORMULARIO_COLECCION) {
                FormularioColeccion(
                    onToggleTheme = onToggleTheme,
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
                arguments = listOf(
                    navArgument("idColeccion") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val idColeccion = backStackEntry.arguments?.getInt("idColeccion") ?: 0
                val colecciones by coleccionVM.colecciones.collectAsState()

                FormularioItem(
                    onToggleTheme = onToggleTheme,
                    navController = navController,
                    coleccionesDisponibles = colecciones,
                    idColeccion = idColeccion,
                    itemVM = itemVM,
                    onGuardar = { item ->
                        itemVM.insertar(item)
                        navController.popBackStack()
                    },
                    onCancelar = { navController.popBackStack() }
                )
            }

        }
    }
}
