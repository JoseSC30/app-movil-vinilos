package com.example.emprendedor.controller.PatronProxy

import com.example.emprendedor.model.Producto

interface IFiltroRegistroProducto {
    fun registrarProducto(producto: Producto) : Boolean
}