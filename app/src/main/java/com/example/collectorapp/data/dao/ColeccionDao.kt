package com.example.collectorapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.collectorapp.models.Coleccion
import kotlinx.coroutines.flow.Flow

@Dao
interface ColeccionDao {

    //suspend se ejecutta en corrutina para no bloquear la UI
    @Insert
    suspend fun insertar(coleccion: Coleccion): Long //retorna numero long

    @Update
    suspend fun actualizar(coleccion: Coleccion)

    @Delete
    suspend fun eliminar(coleccion: Coleccion)

    //flow la UI se actualiza autoamticamente cuando cambian los datos
    @Query("SELECT * FROM colecciones ORDER BY nombre ASC")
    fun obtenerColecciones(): Flow<List<Coleccion>>

    @Query("SELECT * FROM colecciones WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Coleccion? //Retorna null si no lo encuentra




}