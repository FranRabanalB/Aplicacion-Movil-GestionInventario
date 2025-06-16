package com.example.aplicaciongestion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicaciongestion.database.AppDatabase
import com.example.aplicaciongestion.repository.MaterialRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.aplicaciongestion.database.Material

class MaterialViewModel(database: AppDatabase) : ViewModel() {

    private val repository = MaterialRepository(database)

    private val _materiales = MutableStateFlow<List<Material>>(emptyList())
    val materiales: StateFlow<List<Material>> = _materiales

    init {
        loadMateriales()
    }

    fun loadMateriales() {
        viewModelScope.launch {
            _materiales.value = repository.getAll()
        }
    }

    fun agregarMaterial(nombre: String, descripcion: String, cantidad: Long, valor: Double) {
        viewModelScope.launch {
            repository.insert(nombre, descripcion, cantidad, valor)
            loadMateriales() // actualiza la lista
        }
    }

    fun eliminarMaterial(material: Material) {
        viewModelScope.launch {
            repository.deleteById(material.id)
            loadMateriales()
        }
    }

    fun editarMaterial(original: Material, actualizado: Material) {
        viewModelScope.launch {
            repository.deleteById(original.id)
            repository.insert(
                nombre = actualizado.nombre,
                descripcion = actualizado.descripcion,
                cantidad = actualizado.cantidad,
                valor = actualizado.valor
            )
            loadMateriales()
        }
    }

}
