package com.example.collectorapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectorapp.data.repositorio.Repositorio
import com.example.collectorapp.models.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemViewModel (private val repo: Repositorio): ViewModel(){

    // _* es mutable privado interno de clase
    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    var itemSeleccionado: Item? = null //variable para formulario editar


    fun cargarItems(idColeccion: Int) {
        viewModelScope.launch {
            repo.obtenerPorColeccion(idColeccion).collectLatest { lista ->//devuelve Flow desde room
                _items.value = lista
            }
        }
    }
    
    fun insertar(item: Item) {
        viewModelScope.launch {
            repo.insertarItem(item)
        }
    }

    fun eliminar(item: Item) {
        viewModelScope.launch {
            repo.eliminarItem(item)
        }
    }

    fun actualizar(item: Item) {
        viewModelScope.launch {
            repo.actualizarItem(item)
        }
    }



}