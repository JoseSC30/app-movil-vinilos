package com.example.emprendedor.controller.PatronProxy

import com.example.emprendedor.model.Producto
import com.example.emprendedor.controller.ProductoController
import android.content.Context
import com.example.emprendedor.model.AppDatabase
import com.example.emprendedor.model.dao.ProductoDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

class AccionProducto (private val context: Context){

    private val db: AppDatabase = AppDatabase.getInstance(context)
    private val productoController = ProductoController(context)

    fun registrarProducto(producto: Producto) : Boolean {
        CoroutineScope(Dispatchers.IO).launch {
                val prodId = db.productoDao().insertarProducto(producto)
                withContext(Dispatchers.Main) {
                    productoController.navigateToProductosAfterFinishing()
                }
        }
        return true
    }

}