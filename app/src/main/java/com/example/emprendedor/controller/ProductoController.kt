package com.example.emprendedor.controller

import android.content.Context
import android.content.Intent
import android.widget.Toast
//------ Importacion de ficheros propios ------
import com.example.emprendedor.model.AppDatabase
import com.example.emprendedor.model.Categoria
import com.example.emprendedor.model.Producto
import com.example.emprendedor.view.AgregarProductoActivity
import com.example.emprendedor.view.ProductosActivity
import com.example.emprendedor.controller.PatronProxy.ProxyAccionProducto
//
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ProductoController (
    private val context: Context) {

    private val db: AppDatabase = AppDatabase.getInstance(context)

    private val proxy = ProxyAccionProducto(context)


    fun obtenerProductos(): List<Producto> {
        return db.productoDao().obtenerTodos()
    }

    fun obtenerCategorias(): List<Categoria> {
//        return db.categoriaDao().obtenerTodas()
        // Obtener las categorías desde la base de datos con corrutinas
        return runBlocking {
            withContext(Dispatchers.IO) {
                db.categoriaDao().obtenerTodas()
            }
        }
    }

    fun agregarProducto(
        nombre: String,
        artista: String,
        year: String,
        precio: Double,
        stock: Int,
        descripcion: String? = null,
        imagen: String? = null,
        categoriaId: Int? = null) : Boolean {
        var verificador = true

        val nuevoProducto = Producto(
            nombre = nombre,
            artista = artista,
            year = year,
            precio = precio,
            stock = stock,
            descripcion = descripcion,
            imagen = imagen,
            categoriaId = categoriaId
        )

        var a = proxy.registrarProducto(nuevoProducto)
        return verificador
    }

    fun actualizarProducto(
        id: Int,
        nombre: String,
        artista: String,
        year: String,
        precio: Double,
        stock: Int,
        descripcion: String? = null,
        imagen: String? = null,
        categoriaId: Int? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            val producto = db.productoDao().obtenerPorId(id)
            if (producto != null) {
                producto.nombre = nombre
                producto.artista = artista
                producto.year = year
                producto.precio = precio
                producto.stock = stock
                producto.descripcion = descripcion
                producto.imagen = imagen
                producto.categoriaId = categoriaId
                db.productoDao().actualizarProducto(producto)
            }
        }
    }

    fun eliminarProducto(producto: Producto) {
        CoroutineScope(Dispatchers.IO).launch {
            db.productoDao().eliminarProducto(producto)
        }
    }

//------ Metodos de navegacion ------

    fun navigateToProductos() {
        val intent = Intent(context, ProductosActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToProductosAfterFinishing() {
        (context as AgregarProductoActivity).finish()
    }

    fun navigateToAgregarProducto() {
        val intent = Intent(context, AgregarProductoActivity::class.java)
        context.startActivity(intent)
    }
}