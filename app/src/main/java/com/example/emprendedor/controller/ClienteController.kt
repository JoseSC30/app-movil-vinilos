package com.example.emprendedor.controller

import com.example.emprendedor.controller.PatronStrategy.ReporteContext
import com.example.emprendedor.controller.PatronStrategy.ReportePDF
import com.example.emprendedor.controller.PatronStrategy.ReporteWord
import com.example.emprendedor.model.AppDatabase
import com.example.emprendedor.model.Cliente

import android.content.Context
import android.content.Intent
import com.example.emprendedor.view.AgregarClienteActivity
import com.example.emprendedor.view.ClientesActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClienteController(private val context: Context) {

    private val db: AppDatabase = AppDatabase.getInstance(context)
    private val reporteContext = ReporteContext()

    fun obtenerClientes(): List<Cliente> {
        return db.clienteDao().obtenerTodos()
    }

    fun agregarCliente(nombre: String, direccion: String?, email: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val nuevoCliente = Cliente(nombre = nombre, direccion = direccion, email = email)
            db.clienteDao().insertarCliente(nuevoCliente)

            withContext(Dispatchers.Main) {
                navigateToClientesAfterFinishing()
            }
        }
    }

    fun actualizarCliente(id: Int, nombre: String, direccion: String?, email: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val cliente = db.clienteDao().obtenerPorId(id)
            if (cliente != null) {
                cliente.nombre = nombre
                cliente.direccion = direccion
                cliente.email = email
                db.clienteDao().actualizarCliente(cliente)
            }
        }
    }

    fun eliminarCliente(cliente: Cliente) {
        CoroutineScope(Dispatchers.IO).launch {
            db.clienteDao().eliminarCliente(cliente)
        }
    }

    // Método para generar el reporte, dependiendo del tipo (PDF o Word)
    fun generarReportee(tipo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val clientes = obtenerClientes()

            withContext(Dispatchers.Main) {
                // Configura la estrategia en tiempo de ejecución
                 when (tipo) {
                    "PDF" -> reporteContext.setEstrategia(ReportePDF(context))
                    "Word" -> reporteContext.setEstrategia(ReporteWord(context))
                    else -> throw IllegalArgumentException("Tipo de reporte no válido")
                }

                reporteContext.generarReporte(clientes)
            }
        }
    }




    //------ Metodos de navegacion ------

    fun navigateToClientes() {
        val intent = Intent(context, ClientesActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToClientesAfterFinishing() {
        (context as AgregarClienteActivity).finish()
    }

    fun navigateToAgregarCliente() {
        val intent = Intent(context, AgregarClienteActivity::class.java)
        context.startActivity(intent)
    }
}
