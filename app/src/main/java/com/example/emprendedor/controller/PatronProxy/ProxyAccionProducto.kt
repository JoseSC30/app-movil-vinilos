package com.example.emprendedor.controller.PatronProxy

import android.content.Context
import android.widget.Toast
import com.example.emprendedor.model.Producto
import com.example.emprendedor.controller.ProductoController

class ProxyAccionProducto (private val context: Context){
    private var accionProducto: AccionProducto? = null

    fun registrarProducto(producto: Producto) : Boolean {
        var habilitado = true
        if (accionProducto == null) {
            accionProducto = AccionProducto(context)
        }
        if(hayInicioConMinusculas(producto)){
            habilitado = false
        }
        if(yearIncorrecto(producto)){
            habilitado = false
        }

        if(habilitado == true) {
            accionProducto?.registrarProducto(producto)
        } else {
            Toast.makeText(context, "Por favor, ingrese bien los parametros", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    fun hayInicioConMinusculas(producto: Producto): Boolean {
        //Verificar que cada palabra de "nombre" y "artista" inicie con mayuscula
        val nombre = producto.nombre.split(" ")
        val artista = producto.artista.split(" ")

        for (palabra in nombre) {
            if (palabra.first().isLowerCase()) {
                return true
            }
        }
        for (palabra in artista) {
            if (palabra.first().isLowerCase()) {
                return true
            }
        }
        return false
    }

    fun yearIncorrecto(producto: Producto): Boolean {
        //Cada año debe tener 4 digitos, mayor a 1900 y menor o igual al año actual.
        if (producto.year.length != 4 ||
            producto.year.toInt() < 1900 ||
            producto.year.toInt() > 2024 ) {
            return true
        }
        return false
    }
}