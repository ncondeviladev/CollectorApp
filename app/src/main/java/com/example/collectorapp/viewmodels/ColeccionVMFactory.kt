package com.example.collectorapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.collectorapp.data.repositorio.Repositorio

// Factory para crear ColeccionVM con parametros
class ColeccionVMFactory(private val repo: Repositorio) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST") //evita advertencia de cast inseguro
    override fun <CVM : ViewModel> create(modelClass: Class<CVM>): CVM {
        //comprobamos si la clase solicitada es ColeccionVM
        if (modelClass.isAssignableFrom(ColeccionViewModel::class.java)) {
            return ColeccionViewModel(repo) as CVM //devolvemos la isntancia con el repositorio
        }
        throw IllegalArgumentException("No se puede crear ColeccionVM")
    }
}
