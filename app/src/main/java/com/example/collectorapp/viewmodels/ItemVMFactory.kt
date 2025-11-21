package com.example.collectorapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.collectorapp.data.repositorio.Repositorio

// Factory para poder crear ItemVM con parametros
class ItemVMFactory(private val repo: Repositorio) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST") // evita advertencia de cast inseguro
    override fun <IVM : ViewModel> create(modelClass: Class<IVM>): IVM {
        // Comprobamos si la clase solicitada es ItemVM
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            return ItemViewModel(repo) as IVM // devolvemos la instancia con el repositorio
        }
        // Si no es ItemVM, lanzamos error
        throw IllegalArgumentException("No se puede crear ItemVM")
    }
}
