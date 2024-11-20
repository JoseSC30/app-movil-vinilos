package com.example.emprendedor.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.emprendedor.R
import com.example.emprendedor.controller.CategoriaController
import com.example.emprendedor.model.Categoria

class CategoriaAdapter(
    private val categorias: MutableList<Categoria>,
    private val categoriaController: CategoriaController
) :
    RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreCategoria: TextView = itemView.findViewById(R.id.textNombreCategoria)
        val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminar)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.nombreCategoria.text = categoria.nombreCategoria

        //Configuracion del boton eliminar
        holder.btnEliminar.setOnClickListener {
            // Eliminar la categoría de la base de datos y de la lista
            categoriaController.eliminarCategoria(categoria)
            categorias.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, categorias.size)
            //Mensaje de "Categoria Eliminada"
            Toast.makeText(holder.itemView.context, "Categoria Eliminada", Toast.LENGTH_SHORT)
                .show()
        }

        //Configuracion del boton editar
        holder.btnEditar.setOnClickListener {
            // Navegar a AgregarCategoriaActivity y enviar datos para editar
            val intent = Intent(holder.itemView.context, AgregarCategoriaActivity::class.java)
            intent.putExtra("id", categoria.id)
            intent.putExtra("nombreCategoria", categoria.nombreCategoria)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return categorias.size
    }
}
