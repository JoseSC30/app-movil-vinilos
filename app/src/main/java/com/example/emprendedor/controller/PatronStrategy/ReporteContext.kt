package com.example.emprendedor.controller.PatronStrategy

import com.example.emprendedor.model.Cliente

class ReporteContext {
    private var estrategiaReporte: IReporteStrategy? = null

    // Definir la estrategia en tiempo de ejecución
    fun setEstrategia(estrategia: IReporteStrategy) {
        this.estrategiaReporte = estrategia
    }

    // Delegar la generación del reporte a la estrategia seleccionada
    fun generarReporte(clientes: List<Cliente>) {
        estrategiaReporte?.generarReporte(clientes)
    }
}