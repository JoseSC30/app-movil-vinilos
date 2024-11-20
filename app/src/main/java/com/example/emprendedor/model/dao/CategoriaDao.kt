package com.example.emprendedor.model.dao

import androidx.room.*
import com.example.emprendedor.model.Categoria

@Dao
interface CategoriaDao {
    @Query("SELECT * FROM categorias")
    fun obtenerTodas(): List<Categoria>

    @Query("SELECT * FROM categorias WHERE id = :id")
    fun obtenerPorId(id: Int): Categoria?

    @Insert
    fun insertarCategoria(categoria: Categoria)

    @Update
    fun actualizarCategoria(categoria: Categoria)

    @Delete
    fun eliminarCategoria(categoria: Categoria)
}