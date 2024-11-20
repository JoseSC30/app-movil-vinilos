package com.example.emprendedor.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//
import com.example.emprendedor.model.dao.CategoriaDao
import com.example.emprendedor.model.dao.ClienteDao
import com.example.emprendedor.model.dao.DetalleVentaDao
import com.example.emprendedor.model.dao.ProductoDao
import com.example.emprendedor.model.dao.TelefonoDao
import com.example.emprendedor.model.dao.VentaDao

@Database(
    entities = [
        Producto::class,
        Categoria::class,
        Cliente::class,
        Telefono::class,
        Venta::class,
        DetalleVenta::class
    ],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun clienteDao(): ClienteDao
    abstract fun telefonoDao(): TelefonoDao
    abstract fun ventaDao(): VentaDao
    abstract fun detalleVentaDao(): DetalleVentaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app-database-emprendedor"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}