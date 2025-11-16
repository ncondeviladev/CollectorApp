package com.example.collectorapp.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//Convierte al clase modelo en tabla Room de base de datos
@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey( //Referencia de idColeccion a id de coleeciones
            entity = Coleccion::class,
            parentColumns = ["id"],
            childColumns = ["idColeccion"],
            onDelete = ForeignKey.CASCADE //Si se elimina una coleccion se eliminan sus items
        )
    ]
)
class Item(
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0,
    override val nombre: String,
    override val descripcion: String? = null,
    override val categoria: String? = null,
    override val imagen: String? = null,
    val idColeccion: Int
) : Displayable { //Implementa interfaz Displayable
}