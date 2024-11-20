package com.example.emprendedor.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.emprendedor.R
import com.example.emprendedor.controller.ProductoController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgregarProductoActivity : AppCompatActivity() {

    private lateinit var productoController: ProductoController
    val categorias = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_base_drawer)

        //Inflar el layout especifico de productos en el content_frame
        val contentFrameLayout = findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(R.layout.activity_agregar_producto, contentFrameLayout, true)

        productoController = ProductoController(this)

        val id = intent.getIntExtra("id", -1)
        val nombre = intent.getStringExtra("nombre")
        val artista = intent.getStringExtra("artista")
        val year = intent.getStringExtra("year")
        val precio = intent.getDoubleExtra("precio", 0.0)
        val stock = intent.getIntExtra("stock", 0)
        val descripcion = intent.getStringExtra("descripcion")
        val imagen = intent.getStringExtra("imagen")
        val categoriaId = intent.getIntExtra("categoriaId", -1)

        // Si hay datos de producto para editar
        if (id != -1) {
            // Cargar datos en el EditText
            val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
            editTextNombre.setText(nombre)
            val editTextArtistas = findViewById<EditText>(R.id.editTextArtista)
            editTextArtistas.setText(artista)
            val editTextYear = findViewById<EditText>(R.id.editTextYear)
            editTextYear.setText(year)
            val editTextPrecio = findViewById<EditText>(R.id.editTextPrecio)
            editTextPrecio.setText(precio.toString())
            val editTextStock = findViewById<EditText>(R.id.editTextStock)
            editTextStock.setText(stock.toString())
            val editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcion)
            editTextDescripcion.setText(descripcion)
            val spinnerCategoria = findViewById<Spinner>(R.id.spinner)
            spinnerCategoria.setSelection(categorias.indexOf(categoriaId.toString()))

            val categorias = productoController.obtenerCategorias()
            val categoriaAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                categorias.map { it.nombreCategoria }
            )
            categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategoria.adapter = categoriaAdapter

            // Cambiar la acción del botón guardar para actualizar
            val btnGuardar = contentFrameLayout.findViewById<Button>(R.id.btnGuardar)
            btnGuardar.setOnClickListener {
                val categoriaIdd = spinnerCategoria.selectedItem.toString()
                productoController.actualizarProducto(
                    id,
                    editTextNombre.text.toString(),
                    editTextArtistas.text.toString(),
                    editTextYear.text.toString(),
                    editTextPrecio.text.toString().toDouble(),
                    editTextStock.text.toString().toInt(),
                    editTextDescripcion.text.toString()
                    //guardar la seleccion del spinner
                    , null,
                    categoriaIdd.toInt()
                )
                finish() // Regresar
            }
        } else {
            // Obtener referencia al campo de texto donde se ingresa el vinilo
            val nombreProductoEditText =
                contentFrameLayout.findViewById<EditText>(R.id.editTextNombre)
            val artistaEditText =
                contentFrameLayout.findViewById<EditText>(R.id.editTextArtista)
            val yearEditText =
                contentFrameLayout.findViewById<EditText>(R.id.editTextYear)
            val precioEditText =
                contentFrameLayout.findViewById<EditText>(R.id.editTextPrecio)
            val stockEditText =
                contentFrameLayout.findViewById<EditText>(R.id.editTextStock)
            val descripcionEditText =
                contentFrameLayout.findViewById<EditText>(R.id.editTextDescripcion)
            val categoriaSpinner = contentFrameLayout.findViewById<Spinner>(R.id.spinner)

            //obtener las categorias desde el controlador

            val categorias = productoController.obtenerCategorias()

            //Crear un adaptador para el spinner
            val categoriaAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                categorias.map { it.nombreCategoria }
            )

            // Establecer el adaptador en el spinner
            categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categoriaSpinner.adapter = categoriaAdapter


            // Manejar la acción del botón "Guardar"
            val btnGuardar = contentFrameLayout.findViewById<Button>(R.id.btnGuardar)
            btnGuardar.setOnClickListener {
                val nombre = nombreProductoEditText.text.toString().trim()
                val artista = artistaEditText.text.toString().trim()
                val year = yearEditText.text.toString().trim()
                val precio = precioEditText.text.toString().trim()
                val stock = stockEditText.text.toString().trim()
                val descripcion = descripcionEditText.text.toString().trim()
                val categoriaSeleccionada = categoriaSpinner.selectedItem.toString()

                if (nombre.isNotEmpty() &&
                    artista.isNotEmpty() &&
                    year.isNotEmpty() &&
                    precio.isNotEmpty() &&
                    stock.isNotEmpty()
                ) {
                    productoController.agregarProducto(
                        nombre,
                        artista,
                        year,
                        precio.toDouble(),
                        stock.toInt(),
                        descripcion,
                        null,
                        categorias.find { it.nombreCategoria == categoriaSeleccionada }?.id
//                        categoriaSeleccionada.toInt()
                    )
                } else {
                    // Mostrar un mensaje de error o realizar alguna acción si el campo está vacío
                    Toast.makeText(
                        this,
                        "Por favor, ingrese bien los parametros",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        //Esto maneja la accion al presionar el boton "Cancelar"
        val btnCancelar = contentFrameLayout.findViewById<Button>(R.id.btnCancelar)
        btnCancelar.setOnClickListener {
            productoController.navigateToProductosAfterFinishing()
        }

    }
}