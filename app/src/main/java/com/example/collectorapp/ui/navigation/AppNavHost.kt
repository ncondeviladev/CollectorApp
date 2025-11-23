package com.example.collectorapp.ui.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.collectorapp.ui.components.formulario.FormularioColeccion
import com.example.collectorapp.ui.components.formulario.FormularioItem
import com.example.collectorapp.ui.screens.coleccion.ColeccionPantalla
import com.example.collectorapp.ui.screens.item.ItemPantalla
import com.example.collectorapp.ui.screens.splash.SplashScreen
import com.example.collectorapp.viewmodels.ColeccionViewModel
import com.example.collectorapp.viewmodels.ItemViewModel

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
            startDestination = Rutas.SPLASH // nueva pantalla de inicio, 2s y lleva a colecciones
        ) {
            composable(Rutas.SPLASH) {
                SplashScreen(navController = navController)
            }

            composable(Rutas.LISTA_COLECCIONESS) {
                ColeccionPantalla(
                    onToggleTheme = onToggleTheme,
                    viewModel = coleccionVM,
                    navController = navController,
                    onClickColeccion = { coleccionId ->
                        itemVM.cargarItems(coleccionId)
                        navController.navigate(Rutas.LISTA_ITEMS.replace("{idColeccion}", coleccionId.toString()))
                    },
                    onClickNuevo = {
                        navController.navigate(Rutas.FORMULARIO_COLECCION.substringBefore("?"))
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
                    itemVM = itemVM,
                    onClickNuevo = {
                        itemVM.itemSeleccionado = null // limpiar antes de añadir
                        navController.navigate(
                            Rutas.FORMULARIO_ITEM
                                .replace("{idColeccion}", idColeccion.toString())
                                .replace("{modoFormulario}", "añadir")
                        )
                    },
                    onBack = { navController.popBackStack() },
                    idColeccion = idColeccion
                )
            }

            composable(
                route = Rutas.FORMULARIO_COLECCION,
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()

                FormularioColeccion(
                    coleccionId = id,
                    viewModel = coleccionVM,
                    navController = navController,
                    onToggleTheme = onToggleTheme
                )
            }

            composable(
                route = Rutas.FORMULARIO_ITEM,
                arguments = listOf(
                    navArgument("idColeccion") { type = NavType.IntType },
                    navArgument("modoFormulario") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val idColeccion = backStackEntry.arguments?.getInt("idColeccion") ?: 0
                val modo = backStackEntry.arguments?.getString("modoFormulario")

                FormularioItem(
                    onToggleTheme = onToggleTheme,
                    navController = navController,
                    idColeccion = idColeccion,
                    itemVM = itemVM,
                    modoFormulario = modo
                )
            }
        }
    }
}
