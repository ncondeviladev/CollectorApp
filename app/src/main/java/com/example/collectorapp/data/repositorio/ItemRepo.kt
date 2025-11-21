package com.example.collectorapp.data.repositorio

import com.example.collectorapp.data.dao.ItemDao
import com.example.collectorapp.models.Item

class ItemRepo(private val itemDao: ItemDao) {

    //suspend se ejecutta en corrutina para no bloquear la UI
    suspend fun insertar(item: Item) = itemDao.insertar(item)

    suspend fun actualizar(item: Item) = itemDao.actualizar(item)

    suspend fun eliminar(item: Item) = itemDao.eliminar(item)

    fun obtenerPorColeccion(idColeccion: Int) = itemDao.obtenerPorColeccion(idColeccion)

    suspend fun obtenerPorId(id: Int) = itemDao.obtenerPorId(id)




}