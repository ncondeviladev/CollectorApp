package com.example.collectorapp.models

//Interfaz de UI para que colecion e item puedan mostrarse con tarjetas.
//La interfa no puede ser en las Entity(modelos) porque tienen campos y relaciones concretas
interface Displayable {
    val id: Int
    val nombre: String
    val descripcion: String?
    val categoria: String?

    val imagen: String?
}