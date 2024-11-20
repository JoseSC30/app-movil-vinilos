package com.example.emprendedor.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.emprendedor.R
import com.example.emprendedor.controller.CategoriaController

class AgregarCategoriaActivity : AppCompatActivity() {

    private lateinit var categoriaController: CategoriaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_base_drawer)

        //Inflar el layout especifico de categorias en el content_frame
        val contentFrameLayout = findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(R.layout.activity_agregar_categoria, contentFrameLayout, true)

        categoriaController = CategoriaController(this)

        val id = intent.getIntExtra("id", -1)
        val nombreCategoria = intent.getStringExtra("nombreCategoria")

        // Si hay datos de categoría para editar
        if (id != -1 && nombreCategoria != null) {
            // Cargar datos en el EditText
            val editTextNombre = findViewById<EditText>(R.id.editTextNombreCategoria)
            editTextNombre.setText(nombreCategoria)

            // Cambiar la acción del botón guardar para actualizar
            val btnGuardar = contentFrameLayout.findViewById<Button>(R.id.btnGuardar)
            btnGuardar.setOnClickListener {
                categoriaController.actualizarCategoria(id, editTextNombre.text.toString())
                finish() // Regresar
            }
        } else {
            // Obtener referencia al campo de texto donde se ingresa la nueva categoría
            val nombreCategoriaEditText =
                contentFrameLayout.findViewById<EditText>(R.id.editTextNombreCategoria)
            // Manejar la acción del botón "Guardar"
            val btnGuardar = contentFrameLayout.findViewById<Button>(R.id.btnGuardar)
            btnGuardar.setOnClickListener {
                val nombreCategoria = nombreCategoriaEditText.text.toString().trim()
                if (nombreCategoria.isNotEmpty()) {
                    categoriaController.agregarCategoria(nombreCategoria)
                } else {
                    // Mostrar un mensaje de error o realizar alguna acción si el campo está vacío
                    Toast.makeText(
                        this,
                        "Por favor, ingrese un nombre de categoría",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        //Esto maneja la accion al presionar el boton "Cancelar"
        val btnCancelar = contentFrameLayout.findViewById<Button>(R.id.btnCancelar)
        btnCancelar.setOnClickListener {
            categoriaController.navigateToCategoriasAfterFinishing()
        }
    }
}