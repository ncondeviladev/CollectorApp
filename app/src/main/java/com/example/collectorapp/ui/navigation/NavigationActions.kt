package com.example.collectorapp.ui.navigation

import androidx.navigation.NavController

class NavigationActions(private val navController: NavController) {

    fun navigateToColeccionList() {
        navController.navigate(Rutas.LISTA_COLECCIONESS) {
            popUpTo(Rutas.SPLASH) { inclusive = true }
        }
    }

    fun navigateToItemList(coleccionId: Int) {
        navController.navigate("${Rutas.LISTA_ITEMS_PREFIX}$coleccionId")
    }

    fun navigateToItemDetail(itemId: Int) {
        navController.navigate("${Rutas.DETALLE_ITEM_PREFIX}$itemId")
    }

    fun navigateToNewColeccionForm() {
        navController.navigate(Rutas.FORMULARIO_COLECCION)
    }

    fun navigateToEditColeccionForm(coleccionId: Int) {
        navController.navigate("${Rutas.FORMULARIO_COLECCION}?id=$coleccionId")
    }

    fun navigateToNewItemForm(coleccionId: Int) {
        navController.navigate("${Rutas.FORMULARIO_ITEM_PREFIX}/$coleccionId?modoFormulario=añadir")
    }

    fun navigateToEditItemForm(coleccionId: Int) {
        navController.navigate("${Rutas.FORMULARIO_ITEM_PREFIX}/$coleccionId?modoFormulario=editar")
    }

    fun popBackStack() {
        navController.popBackStack()
    }
}