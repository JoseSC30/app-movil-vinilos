package com.example.emprendedor.model.dao

import androidx.room.*
import com.example.emprendedor.model.Venta

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas")
    fun obtenerTodas(): List<Venta>

    @Insert
    fun insertarVenta(venta: Venta)

    @Update
    fun actualizarVenta(venta: Venta)

    @Delete
    fun eliminarVenta(venta: Venta)
}
