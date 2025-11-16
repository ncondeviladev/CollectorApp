package com.example.collectorapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

//Convierte al clase modelo en tabla Room de base de datos
@Entity(tableName = "colecciones")
data class Coleccion (
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0,
    override val nombre: String,
    override val descripcion: String? = null,
    override val categoria: String? = null,
    override val imagen: String? = null
) : Displayable { //Implementa interfaz Displayable
}