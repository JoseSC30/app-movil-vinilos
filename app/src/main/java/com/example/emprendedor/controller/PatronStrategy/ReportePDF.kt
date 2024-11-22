package com.example.emprendedor.controller.PatronStrategy

import com.example.emprendedor.model.Cliente
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.FileProvider
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReportePDF(private val context: Context) : IReporteStrategy {

    override fun generarReporte(clientes: List<Cliente>) {
        Thread {
            try {
                // Ruta para guardar el PDF
                val file = File(context.getExternalFilesDir(null), "reporte_clientes.pdf")
                val fileOutputStream = FileOutputStream(file)

                val writer = PdfWriter(fileOutputStream)
                val pdfDocument = PdfDocument(writer)
                val document = Document(pdfDocument)

                // Obtener la fecha y hora actual
                val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

                // Crear el título en negrita, tamaño grande y centrado
                val titulo = Paragraph("Lista de Clientes")
                    .setFont(PdfFontFactory.createFont())
                    .setFontSize(20f)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                document.add(titulo)

                // Agregar fecha y hora actual como subtítulo
                val subtitulo = Paragraph("Fecha y hora: $fechaActual")
                    .setFont(PdfFontFactory.createFont())
                    .setFontSize(12f)
                    .setItalic()
                    .setTextAlignment(TextAlignment.LEFT)
                document.add(subtitulo)

                // Crear la tabla
                val table = Table(floatArrayOf(4f, 8f, 4f)) // Proporciones: Nombre: 4, Dirección: 8, Email: 4
                table.setWidth(UnitValue.createPercentValue(100f)) // Ancho al 100%

                // Encabezados de la tabla
                table.addHeaderCell(Paragraph("Nombre").setBold())
                table.addHeaderCell(Paragraph("Dirección").setBold())
                table.addHeaderCell(Paragraph("Email").setBold())

                // Agregar los detalles de los clientes en la tabla
                clientes.forEach { cliente ->
                    table.addCell(cliente.nombre)
                    table.addCell(cliente.direccion)
                    table.addCell(cliente.email)
                }

                document.add(table)
                document.close()

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Reporte PDF generado en: ${file.absolutePath}", Toast.LENGTH_SHORT).show()
                    abrirPDF(file)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Error al generar el reporte PDF", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun abrirPDF(pdfFile: File) {
        // Crear una URI segura para el archivo utilizando FileProvider
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", pdfFile)

        // Crear un Intent para abrir el PDF
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        // Verificar que hay una aplicación para abrir PDFs
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent) // Intent para abrir el PDF
        } else {
            Toast.makeText(context, "No hay aplicación disponible para abrir el PDF", Toast.LENGTH_SHORT).show()
        }
    }
}
