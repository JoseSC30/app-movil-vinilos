package com.example.emprendedor.model.dao

import androidx.room.*
import com.example.emprendedor.model.DetalleVenta

@Dao
interface DetalleVentaDao {
    @Query("SELECT * FROM detalle_ventas")
    fun obtenerTodas(): List<DetalleVenta>
    @Insert
    fun insertarDetalleVenta(detalleVenta: DetalleVenta)
    @Update
    fun actualizarDetalleVenta(detalleVenta: DetalleVenta)
    @Delete
    fun eliminarDetalleVenta(detalleVenta: DetalleVenta)
}