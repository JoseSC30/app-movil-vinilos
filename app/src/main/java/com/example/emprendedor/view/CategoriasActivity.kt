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
import com.example.emprendedor.R
import com.example.emprendedor.controller.CategoriaController
import com.example.emprendedor.controller.ProductoController
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriasActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var productoController: ProductoController
    private lateinit var categoriaController: CategoriaController

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoriaAdapter: CategoriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_base_drawer)

        //Inflar el layout especifico de categorias en el content_frame
        val contentFrameLayout = findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(R.layout.activity_categorias, contentFrameLayout, true)

        // Inicializar controladores
        categoriaController = CategoriaController(this)
        productoController = ProductoController(this)

        //Accion del boton "Nueva Categoria".
        val btnNuevaCategoria = contentFrameLayout.findViewById<Button>(R.id.btnNuevaCategoria)
        btnNuevaCategoria.setOnClickListener {
            categoriaController.navigateToAgregarCategoria()
        }
        // Configuraciones de RecyclerView
        recyclerView = contentFrameLayout.findViewById<RecyclerView>(R.id.recyclerViewCategorias)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Vistas
        toolbarYSidebar()
        listarCategorias()
    }

    // Recargar la lista de categorías
    override fun onResume() {
        super.onResume()
        listarCategorias()
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

    private fun listarCategorias() {
        CoroutineScope(Dispatchers.IO).launch {
            val categorias = categoriaController.obtenerCategorias()
            withContext(Dispatchers.Main) {
                categoriaAdapter = CategoriaAdapter(categorias.toMutableList(), categoriaController)
                recyclerView.adapter = categoriaAdapter
            }
        }
    }

    private fun toolbarYSidebar() {
        //Configuracion de el Toolbar y el boton de hamburguesa
        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()//Sincroniza el estado del icono de hamburguesa

        navigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_vinilos -> {
                    productoController.navigateToProductos()
                }
                R.id.nav_categorias -> { /* Ya estamos en la vista de categorías */
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}