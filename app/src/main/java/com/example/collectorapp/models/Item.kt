package com.example.collectorapp.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = Coleccion::class,
            parentColumns = ["id"],
            childColumns = ["idColeccion"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["idColeccion"])])
data class Item(
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0,
    override var nombre: String,
    override var descripcion: String? = null,
    override var categoria: String? = null,
    override var imagen: String? = null,
    val idColeccion: Int,
    val imageUris: List<String> = emptyList()
) : Displayable