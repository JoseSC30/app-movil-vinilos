package com.example.emprendedor.controller

import android.content.Intent
import android.content.Context
import com.example.emprendedor.model.AppDatabase
import com.example.emprendedor.model.Categoria
import com.example.emprendedor.view.AgregarCategoriaActivity
import com.example.emprendedor.view.CategoriasActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriaController (
    private val context: Context ) {

    private val db: AppDatabase = AppDatabase.getInstance(context)

    fun obtenerCategorias(): List<Categoria> {
        return db.categoriaDao().obtenerTodas()
    }

    fun agregarCategoria(nombreCategoria: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val nuevaCategoria = Categoria(nombreCategoria = nombreCategoria)
            db.categoriaDao().insertarCategoria(nuevaCategoria)

            withContext(Dispatchers.Main) {
                navigateToCategoriasAfterFinishing()
            }
        }
    }

    fun actualizarCategoria(id: Int, nombreCategoria: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val categoria = db.categoriaDao().obtenerPorId(id)
            if (categoria != null) {
                categoria.nombreCategoria = nombreCategoria
                db.categoriaDao().actualizarCategoria(categoria)
            }
        }
    }

    fun eliminarCategoria(categoria: Categoria) {
        CoroutineScope(Dispatchers.IO).launch {
            db.categoriaDao().eliminarCategoria(categoria)
        }
    }

    //------ Metodos de navegacion ------


    fun navigateToCategorias() {
        val intent = Intent(context, CategoriasActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToCategoriasAfterFinishing() {
        (context as AgregarCategoriaActivity).finish()
    }

    fun navigateToAgregarCategoria() {
        val intent = Intent(context, AgregarCategoriaActivity::class.java)
        context.startActivity(intent)
    }

}



