package com.example.collectorapp.ui.navigation

object Rutas {
    const val SPLASH = "splash"
    const val LISTA_COLECCIONESS = "listaColecciones"
    
    const val LISTA_ITEMS_PREFIX = "listaItems/"
    const val LISTA_ITEMS = "${LISTA_ITEMS_PREFIX}{idColeccion}"

    const val DETALLE_ITEM_PREFIX = "detalleItem/"
    const val DETALLE_ITEM = "${DETALLE_ITEM_PREFIX}{itemId}"
    
    const val FORMULARIO_COLECCION = "formularioColeccion"
    
    const val FORMULARIO_ITEM_PREFIX = "formularioItem"
    const val FORMULARIO_ITEM = "${FORMULARIO_ITEM_PREFIX}/{idColeccion}"
}