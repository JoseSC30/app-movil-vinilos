package com.example.emprendedor.model.dao

import androidx.room.*
import com.example.emprendedor.model.Producto

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos")
    fun obtenerTodos(): List<Producto>

    @Query("SELECT * FROM productos WHERE id = :id")
    fun obtenerPorId(id: Int): Producto?

    @Insert
    fun insertarProducto(producto: Producto): Long

    @Update
    fun actualizarProducto(producto: Producto)

    @Delete
    fun eliminarProducto(producto: Producto)
}