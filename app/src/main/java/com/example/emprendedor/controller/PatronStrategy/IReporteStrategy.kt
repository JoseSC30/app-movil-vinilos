package com.example.emprendedor.controller.PatronStrategy

import com.example.emprendedor.model.Cliente

interface IReporteStrategy {
    fun generarReporte(clientes: List<Cliente>)
}