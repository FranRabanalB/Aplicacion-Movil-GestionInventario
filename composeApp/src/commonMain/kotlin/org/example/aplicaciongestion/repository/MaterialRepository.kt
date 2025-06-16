package com.example.aplicaciongestion.repository

import com.example.aplicaciongestion.database.AppDatabase
import com.example.aplicaciongestion.database.Material



class MaterialRepository(private val db: AppDatabase) {

    fun getAll(): List<Material> {
        return db.materialQueries.selectAll().executeAsList()
    }

    fun insert(nombre: String, descripcion: String, cantidad: Long, valor: Double){
        db.materialQueries.insertMaterial(nombre, descripcion, cantidad, valor)
    }

    fun deleteById(id: Long) {
        db.materialQueries.deleteById(id)
    }
}
