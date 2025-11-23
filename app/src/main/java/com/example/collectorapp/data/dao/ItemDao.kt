package com.example.collectorapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.collectorapp.models.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    //suspend se ejecutta en corrutina para no bloquear la UI
    @Insert
    suspend fun insertar(item: Item): Long

    @Update
    suspend fun actualizar(item: Item)

    @Delete
    suspend fun eliminar(item: Item)

    //flow la UI se actualiza autoamticamente cuando cambian los datos
    @Query("SELECT * FROM items WHERE idColeccion = :idColeccion ORDER BY nombre ASC")
    fun obtenerPorColeccion(idColeccion: Int): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE id = :itemId")
    suspend fun obtenerPorId(itemId: Int): Item? //Devuelve null si no lo encuentra


    @Query("DELETE FROM items WHERE idColeccion = :idColeccion")
    suspend fun eliminarItemsPorColeccion(idColeccion: Int)

}



