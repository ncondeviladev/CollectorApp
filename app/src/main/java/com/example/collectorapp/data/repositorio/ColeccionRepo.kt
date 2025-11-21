package com.example.collectorapp.data.repositorio

import com.example.collectorapp.data.dao.ColeccionDao
import com.example.collectorapp.models.Coleccion

class ColeccionRepo(private val coleccionDao: ColeccionDao) {

    //suspend se ejecutta en corrutina para no bloquear la UI
    suspend fun insertar(coleccion: Coleccion) = coleccionDao.insertar(coleccion)

    suspend fun actualizar(coleccion: Coleccion) = coleccionDao.actualizar(coleccion)

    suspend fun eliminar(coleccion: Coleccion) = coleccionDao.eliminar(coleccion)

    fun obtenerColecciones() = coleccionDao.obtenerColecciones()

    suspend fun obtenerPorId(id: Int) = coleccionDao.obtenerPorId(id)


}