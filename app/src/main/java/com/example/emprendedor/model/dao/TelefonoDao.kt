package com.example.emprendedor.model.dao

import androidx.room.*
import com.example.emprendedor.model.Telefono

@Dao
interface TelefonoDao {
    @Query("SELECT * FROM telefonos")
    fun obtenerTodos(): List<Telefono>
    @Insert
    fun insertarTelefono(telefono: Telefono)
    @Update
    fun actualizarTelefono(telefono: Telefono)
    @Delete
    fun eliminarTelefono(telefono: Telefono)
}