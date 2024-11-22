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
import com.example.emprendedor.controller.ClienteController
import com.example.emprendedor.model.Cliente

class ClienteAdapter(
    private val clientes: MutableList<Cliente>,
    private val clienteController: ClienteController
) :
    RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreCliente: TextView = itemView.findViewById(R.id.textNombreCliente)
        val direccionCliente: TextView = itemView.findViewById(R.id.textDireccionCliente)
        val emailCliente: TextView = itemView.findViewById(R.id.textEmailCliente)
        val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminar)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.nombreCliente.text = cliente.nombre
        holder.direccionCliente.text = cliente.direccion
        holder.emailCliente.text = cliente.email

        holder.btnEliminar.setOnClickListener {
            clienteController.eliminarCliente(cliente)
            clientes.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, clientes.size)
            Toast.makeText(holder.itemView.context, "Cliente Eliminado", Toast.LENGTH_SHORT)
                .show()
        }

        holder.btnEditar.setOnClickListener {
            val intent = Intent(holder.itemView.context, AgregarClienteActivity::class.java)
            intent.putExtra("id", cliente.id)
            intent.putExtra("nombre", cliente.nombre)
            intent.putExtra("direccion", cliente.direccion)
            intent.putExtra("email", cliente.email)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return clientes.size
    }
}
