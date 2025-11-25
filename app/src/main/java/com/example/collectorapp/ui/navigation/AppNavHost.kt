package com.example.collectorapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.collectorapp.R
import com.example.collectorapp.ui.components.TopBar
import com.example.collectorapp.ui.screens.coleccion.ColeccionPantalla
import com.example.collectorapp.ui.screens.item.ItemDetailScreen
import com.example.collectorapp.ui.screens.item.ItemPantalla
import com.example.collectorapp.ui.screens.splash.SplashScreen
import com.example.collectorapp.ui.screens.coleccion.EditColeccionPantalla
import com.example.collectorapp.ui.screens.item.EditItemPantalla
import com.example.collectorapp.viewmodels.ColeccionViewModel
import com.example.collectorapp.viewmodels.ItemViewModel

@Composable
fun AppNavHost(
    coleccionVM: ColeccionViewModel,
    itemVM: ItemViewModel,
    onToggleTheme: () -> Unit
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Rutas.SPLASH

    val colecciones by coleccionVM.colecciones.collectAsState()
    val items by itemVM.items.collectAsState()

    val titulo = when {
        currentRoute == Rutas.LISTA_COLECCIONESS -> stringResource(R.string.titulo_mis_colecciones)
        currentRoute.startsWith(Rutas.LISTA_ITEMS_PREFIX) -> {
            val id = navBackStackEntry?.arguments?.getInt("idColeccion") ?: -1
            colecciones.find { it.id == id }?.nombre ?: stringResource(R.string.titulo_items)
        }
        currentRoute.startsWith(Rutas.DETALLE_ITEM_PREFIX) -> {
            val id = navBackStackEntry?.arguments?.getInt("itemId") ?: -1
            items.find { it.id == id }?.nombre ?: stringResource(R.string.titulo_detalle_item)
        }
        currentRoute.startsWith(Rutas.FORMULARIO_COLECCION) -> {
            if (navBackStackEntry?.arguments?.getString("id") != null) stringResource(R.string.titulo_editar_coleccion) else stringResource(R.string.titulo_nueva_coleccion)
        }
        currentRoute.startsWith(Rutas.FORMULARIO_ITEM_PREFIX) -> {
            if (navBackStackEntry?.arguments?.getString("modoFormulario") == "editar") stringResource(R.string.titulo_editar_item) else stringResource(R.string.titulo_nuevo_item)
        }
        else -> ""
    }

    val showFab = currentRoute == Rutas.LISTA_COLECCIONESS || currentRoute.startsWith(Rutas.LISTA_ITEMS_PREFIX)

    Scaffold(
        topBar = {
            if (currentRoute != Rutas.SPLASH) {
                TopBar(
                    navController = navController,
                    titulo = titulo,
                    onToggleTheme = onToggleTheme
                )
            }
        },
        floatingActionButton = {
            if (showFab) {
                FloatingActionButton(
                    onClick = {
                        if (currentRoute == Rutas.LISTA_COLECCIONESS) {
                            navigationActions.navigateToNewColeccionForm()
                        } else {
                            val idColeccion = navBackStackEntry?.arguments?.getInt("idColeccion") ?: 0
                            navigationActions.navigateToNewItemForm(idColeccion)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.fab_nuevo_contenido))
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = Rutas.SPLASH
        ) {
            composable(Rutas.SPLASH) {
                SplashScreen(onTimeout = { navigationActions.navigateToColeccionList() })
            }
            composable(Rutas.LISTA_COLECCIONESS) {
                ColeccionPantalla(
                    viewModel = coleccionVM,
                    onColeccionClick = { coleccionId ->
                        itemVM.cargarItems(coleccionId)
                        navigationActions.navigateToItemList(coleccionId)
                    },
                    onEditColeccion = { coleccionId ->
                        navigationActions.navigateToEditColeccionForm(coleccionId)
                    }
                )
            }
            composable(Rutas.LISTA_ITEMS, arguments = listOf(navArgument("idColeccion") { type = NavType.IntType })) { backStackEntry ->
                val idColeccion = backStackEntry.arguments?.getInt("idColeccion") ?: 0
                ItemPantalla(
                    items = items.filter { it.idColeccion == idColeccion },
                    itemVM = itemVM,
                    onItemClick = { itemId -> navigationActions.navigateToItemDetail(itemId) },
                    onEditItem = { itemId ->
                        itemVM.itemSeleccionado = items.find { it.id == itemId }
                        navigationActions.navigateToEditItemForm(idColeccion)
                    }
                )
            }
            composable(Rutas.DETALLE_ITEM, arguments = listOf(navArgument("itemId") { type = NavType.IntType })) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getInt("itemId") ?: -1
                val item = items.find { it.id == itemId }
                if (item != null) {
                    ItemDetailScreen(item = item)
                }
            }
            composable(
                route = "${Rutas.FORMULARIO_COLECCION}?id={id}",
                arguments = listOf(navArgument("id") { nullable = true; defaultValue = null })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                val coleccion = id?.let { colecciones.find { c -> c.id == it } }
                EditColeccionPantalla(
                    viewModel = coleccionVM,
                    coleccionExistente = coleccion,
                    onGuardar = { navigationActions.popBackStack() },
                    onCancelar = { navigationActions.popBackStack() }
                )
            }
            composable(
                route = Rutas.FORMULARIO_ITEM + "?modoFormulario={modoFormulario}",
                arguments = listOf(
                    navArgument("idColeccion") { type = NavType.IntType },
                    navArgument("modoFormulario") { nullable = true }
                )
            ) { backStackEntry ->
                val modo = backStackEntry.arguments?.getString("modoFormulario")
                val item = if (modo == "editar") itemVM.itemSeleccionado else null
                EditItemPantalla(
                    itemViewModel = itemVM,
                    coleccionViewModel = coleccionVM,
                    itemExistente = item,
                    onGuardar = { navigationActions.popBackStack() },
                    onCancelar = { navigationActions.popBackStack() }
                )
            }
        }
    }
}