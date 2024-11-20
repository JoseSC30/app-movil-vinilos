package com.example.emprendedor.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
//
import com.example.emprendedor.R
import com.example.emprendedor.controller.ProductoController
import com.example.emprendedor.model.Producto
//

class ProductoAdapter(
    private val productos: MutableList<Producto>,
    private val productoController: ProductoController
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.textNombre)
        val year: TextView = itemView.findViewById(R.id.textYear)
        val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminar)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.nombre.text = producto.nombre
        holder.year.text = producto.year

        //Configuracion del boton eliminar
        holder.btnEliminar.setOnClickListener {
            // Eliminar el producto de la base de datos y de la lista
            productoController.eliminarProducto(producto)
            productos.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, productos.size)
            //Mensaje de "Producto Eliminado"
            Toast.makeText(holder.itemView.context, "Producto Eliminado", Toast.LENGTH_SHORT).show()
        }

        //Configuracion del boton editar
        holder.btnEditar.setOnClickListener {
            // Navegar a AgregarProductoActivity y enviar datos para editar
            val intent = Intent(holder.itemView.context, AgregarProductoActivity::class.java)
            intent.putExtra("id", producto.id)
            intent.putExtra("nombre", producto.nombre)
            intent.putExtra("artista", producto.artista)
            intent.putExtra("year", producto.year)
            intent.putExtra("precio", producto.precio)
            intent.putExtra("stock", producto.stock)
            intent.putExtra("descripcion", producto.descripcion)
            intent.putExtra("imagen", producto.imagen)
            intent.putExtra("categoriaId", producto.categoriaId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}