package com.example.emprendedor.view

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//
import com.example.emprendedor.R
import com.example.emprendedor.controller.CategoriaController
import com.example.emprendedor.controller.ClienteController
import com.example.emprendedor.controller.ProductoController
//
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductosActivity : AppCompatActivity() {

    private lateinit var categoriaController: CategoriaController
    private lateinit var productoController: ProductoController
    private lateinit var clienteController: ClienteController

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_base_drawer)

        val contentFrameLayout = findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(R.layout.activity_productos, contentFrameLayout, true)
//------------------------ CONTINUAR ABAJO --------------------------
        // Inicializar el controlador de categorías
        categoriaController = CategoriaController(this)
        productoController = ProductoController(this)
        clienteController = ClienteController(this)

        //Accion del boton "Nuevo Vinilo".
        val btnNuevoProducto = contentFrameLayout.findViewById<Button>(R.id.btnNuevoProducto)
        btnNuevoProducto.setOnClickListener {
            productoController.navigateToAgregarProducto()
        }
        // Configuraciones de RecyclerView
        recyclerView = contentFrameLayout.findViewById<RecyclerView>(R.id.recyclerViewProductos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Vistas
        toolbarYSidebar()
        listarProductos()
    }

    // Recargar la lista de productos
    override fun onResume() {
        super.onResume()
        listarProductos()
    }
    // Manejar el botón de atrás para cerrar el sidebar si está abierto
    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun listarProductos() {
        CoroutineScope(Dispatchers.IO).launch {
            val productos = productoController.obtenerProductos()
            withContext(Dispatchers.Main) {
                val productoAdapter = ProductoAdapter(productos.toMutableList(), productoController)
                recyclerView.adapter = productoAdapter
            }
        }
    }

    private fun toolbarYSidebar() {
        // Configurar el Toolbar y el botón de hamburguesa
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configurar el listener para la navegación del sidebar
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_vinilos -> { /* Ya estamos en la vista de vinilos */
                }
                R.id.nav_categorias -> {
                    categoriaController.navigateToCategorias()
                }
                R.id.nav_clientes -> {
                    clienteController.navigateToClientes()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}