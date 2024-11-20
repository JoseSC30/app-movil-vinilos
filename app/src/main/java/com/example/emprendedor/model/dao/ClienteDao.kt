package com.example.emprendedor.model.dao

import androidx.room.*
import com.example.emprendedor.model.Cliente

@Dao
interface ClienteDao {
    @Query("SELECT * FROM clientes")
    fun obtenerTodos(): List<Cliente>

    @Query("SELECT * FROM clientes WHERE id = :id")
    fun obtenerPorId(id: Int): Cliente?

    @Insert
    fun insertarCliente(cliente: Cliente)

    @Update
    fun actualizarCliente(cliente: Cliente)

    @Delete
    fun eliminarCliente(cliente: Cliente)
}