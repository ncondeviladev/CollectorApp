package com.example.collectorapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectorapp.data.repositorio.Repositorio
import com.example.collectorapp.models.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemViewModel (private val repo: Repositorio): ViewModel(){

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    var itemSeleccionado: Item? = null

    private val _idColeccion = MutableStateFlow<Int>(0)
    val idColeccion: StateFlow<Int> = _idColeccion.asStateFlow()

    fun actualizarIdColeccion(id: Int) {
        _idColeccion.value = id
    }

    fun cargarItems(idColeccion: Int) {
        viewModelScope.launch {
            repo.obtenerPorColeccion(idColeccion).collectLatest { lista ->
                _items.value = lista
            }
        }
    }

    fun insertar(nombre: String, descripcion: String) {
        viewModelScope.launch {
            val item = Item(nombre = nombre, descripcion = descripcion, idColeccion = _idColeccion.value)
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