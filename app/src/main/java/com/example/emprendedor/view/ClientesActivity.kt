package com.example.emprendedor.view

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emprendedor.R
import com.example.emprendedor.controller.ClienteController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClientesActivity : AppCompatActivity() {

    private lateinit var clienteController: ClienteController
    private lateinit var recyclerView: RecyclerView
    private lateinit var clienteAdapter: ClienteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_base_drawer)

        val contentFrameLayout = findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(R.layout.activity_clientes, contentFrameLayout, true)

        clienteController = ClienteController(this)

        val btnNuevoCliente = contentFrameLayout.findViewById<Button>(R.id.btnNuevoCliente)
        btnNuevoCliente.setOnClickListener {
            clienteController.navigateToAgregarCliente()
        }

        // Botón para generar reporte PDF
        val btnReportePDF = contentFrameLayout.findViewById<Button>(R.id.btnReportePDF)
        btnReportePDF.setOnClickListener {
            clienteController.generarReportee("PDF")
        }

        // Botón para generar reporte Word
        val btnReporteWord = contentFrameLayout.findViewById<Button>(R.id.btnReporteWord)
        btnReporteWord.setOnClickListener {
            clienteController.generarReportee("Word")
        }

        recyclerView = contentFrameLayout.findViewById(R.id.recyclerViewClientes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        listarClientes()
    }

    override fun onResume() {
        super.onResume()
        listarClientes()
    }

    private fun listarClientes() {
        CoroutineScope(Dispatchers.IO).launch {
            val clientes = clienteController.obtenerClientes()
            withContext(Dispatchers.Main) {
                clienteAdapter = ClienteAdapter(clientes.toMutableList(), clienteController)
                recyclerView.adapter = clienteAdapter
            }
        }
    }
}


