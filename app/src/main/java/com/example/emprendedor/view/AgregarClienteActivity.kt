package com.example.emprendedor.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.emprendedor.R
import com.example.emprendedor.controller.ClienteController

class AgregarClienteActivity : AppCompatActivity() {

    private lateinit var clienteController: ClienteController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_base_drawer) // Establecer el layout base

        // Inflar el layout específico de cliente dentro del FrameLayout
        val contentFrameLayout = findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(R.layout.activity_agregar_cliente, contentFrameLayout, true)

        // Inicializar el controlador
        clienteController = ClienteController(this)

        // Configurar la Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Recuperar los datos del cliente desde el Intent
        val id = intent.getIntExtra("id", -1)
        val nombre = intent.getStringExtra("nombre")
        val direccion = intent.getStringExtra("direccion")
        val email = intent.getStringExtra("email")

        // Referencias a los campos de texto en activity_agregar_cliente
        val editTextNombre = findViewById<EditText>(R.id.editTextNombreCliente)
        val editTextDireccion = findViewById<EditText>(R.id.editTextDireccionCliente)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmailCliente)

        // Si hay datos del cliente para editar
        if (id != -1 && nombre != null) {
            // Cargar los datos del cliente en los campos de texto
            editTextNombre.setText(nombre)
            editTextDireccion.setText(direccion ?: "")
            editTextEmail.setText(email ?: "")

            // Cambiar la acción del botón guardar para actualizar
            val btnGuardar = findViewById<Button>(R.id.btnGuardarCliente)
            btnGuardar.setOnClickListener {
                clienteController.actualizarCliente(
                    id,
                    editTextNombre.text.toString(),
                    editTextDireccion.text.toString(),
                    editTextEmail.text.toString()
                )
                finish() // Regresar a la lista de clientes después de actualizar
            }
        } else {
            // Configurar acción del botón para agregar un nuevo cliente
            val btnGuardar = findViewById<Button>(R.id.btnGuardarCliente)
            btnGuardar.setOnClickListener {
                val nombreCliente = editTextNombre.text.toString().trim()
                val direccionCliente = editTextDireccion.text.toString().trim()
                val emailCliente = editTextEmail.text.toString().trim()

                if (nombreCliente.isNotEmpty()) {
                    // Llamar al método para agregar el cliente
                    clienteController.agregarCliente(nombreCliente, direccionCliente, emailCliente)
                } else {
                    // Mostrar mensaje si no se ha ingresado el nombre
                    Toast.makeText(
                        this,
                        "Por favor, ingrese un nombre de cliente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Manejar la acción del botón "Cancelar"
        val btnCancelar = findViewById<Button>(R.id.btnCancelarCliente)
        btnCancelar.setOnClickListener {
            clienteController.navigateToClientesAfterFinishing()
        }
    }
}
