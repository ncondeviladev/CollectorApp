package com.example.collectorapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "colecciones",
    ignoredColumns = ["categoria"] // Instrucción explícita para Room
)
data class Coleccion (
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0,
    override var nombre: String,
    override var descripcion: String? = null,
    override var imagen: String? = null,

) : Displayable {
    // El campo categoria es requerido por la interfaz, pero ignorado por Room.
    override var categoria: String? = null
}