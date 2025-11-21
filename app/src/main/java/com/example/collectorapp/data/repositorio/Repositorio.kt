package com.example.collectorapp.data.repositorio

import com.example.collectorapp.data.AppDatabase
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.models.Item


class Repositorio(private val database: AppDatabase) {

    private val coleccionRepo = ColeccionRepo(database.coleccionDao())
    private val itemRepo = ItemRepo(database.itemDao())

    suspend fun insertarColeccion(coleccion: Coleccion) = coleccionRepo.insertar(coleccion)
    suspend fun eliminarColeccion(coleccion: Coleccion) = coleccionRepo.eliminar(coleccion)
    suspend fun actualizarColeccion(coleccion: Coleccion) = coleccionRepo.actualizar(coleccion)
    suspend fun obtenerColeccionPorId(id: Int) = coleccionRepo.obtenerPorId(id)
    fun obtenerColecciones() = coleccionRepo.obtenerColecciones()


    suspend fun insertarItem(item: Item) = itemRepo.insertar(item)
    suspend fun eliminarItem(item: Item) = itemRepo.eliminar(item)
    suspend fun actualizarItem(item: Item) = itemRepo.actualizar(item)
    suspend fun obtenerItemPorId(id: Int) = itemRepo.obtenerPorId(id)
    fun obtenerPorColeccion(idColeccion: Int) = itemRepo.obtenerPorColeccion(idColeccion)


}

