package com.example.collectorapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectorapp.data.repositorio.ItemRepo
import com.example.collectorapp.data.repositorio.Repositorio
import com.example.collectorapp.models.Coleccion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ColeccionViewModel(private val repo: Repositorio) : ViewModel() {

    // _* es mutable privado interno de clase
    private val _colecciones = MutableStateFlow<List<Coleccion>>(emptyList())
    val colecciones: StateFlow<List<Coleccion>> = _colecciones

    private val _seleccionada = MutableStateFlow<Coleccion?>(null)
    val seleccionada: StateFlow<Coleccion?> = _seleccionada

    init { //carga datos al iniciar
        cargarColecciones()
    }
    fun cargarColecciones() {
        viewModelScope.launch {
            repo.obtenerColecciones().collect { lista -> //devuelve Flow desde room
                _colecciones.value = lista
            }
        }
    }
    //funciones de room
    fun insertar(coleccion: Coleccion) {
        viewModelScope.launch {
            repo.insertarColeccion(coleccion)
        }
    }

    fun actualizar(coleccion: Coleccion) {
        viewModelScope.launch {
            repo.actualizarColeccion(coleccion)
        }
    }

    fun eliminar(coleccion: Coleccion) {
        viewModelScope.launch {
            repo.eliminarItemsPorColeccion(coleccion.id)
            repo.eliminarColeccion(coleccion)
        }
    }


}