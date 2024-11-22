package com.example.emprendedor.controller.PatronStrategy

import com.example.emprendedor.model.Cliente
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFTable
import org.apache.poi.xwpf.usermodel.XWPFTableCell
import org.apache.poi.xwpf.usermodel.XWPFTableRow
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReporteWord(private val context: Context) : IReporteStrategy {

    override fun generarReporte(clientes: List<Cliente>) {
        try {
            val document = XWPFDocument()
            val file = File(context.getExternalFilesDir(null), "reporte_clientes.docx")
            val fileOutputStream = FileOutputStream(file)

            // Obtener la fecha y hora actual
            val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

            // Agregar título al documento
            val titulo = document.createParagraph()
            titulo.alignment = org.apache.poi.xwpf.usermodel.ParagraphAlignment.CENTER
            val runTitulo = titulo.createRun()
            runTitulo.isBold = true
            runTitulo.fontSize = 20
            runTitulo.setText("Reporte de Clientes")

            // Agregar subtítulo con fecha y hora
            val subtitulo = document.createParagraph()
            subtitulo.alignment = org.apache.poi.xwpf.usermodel.ParagraphAlignment.LEFT
            val runSubtitulo = subtitulo.createRun()
            runSubtitulo.isItalic = true
            runSubtitulo.fontSize = 12
            runSubtitulo.setText("Fecha y hora: $fechaActual")

            // Agregar espacio después del subtítulo
            document.createParagraph()

            // Crear una tabla con encabezados y ajustar anchos
            val table: XWPFTable = document.createTable()

            // Encabezados con ajuste de ancho
            val headerRow: XWPFTableRow = table.getRow(0)
            val cellNombre: XWPFTableCell = headerRow.getCell(0)
            cellNombre.text = "Nombre"
            cellNombre.setWidth("3000") // Ancho de la columna en Twips (~3cm)

            val cellDireccion: XWPFTableCell = headerRow.addNewTableCell()
            cellDireccion.text = "Dirección"
            cellDireccion.setWidth("6000") // Más ancho para dirección (~6cm)

            val cellEmail: XWPFTableCell = headerRow.addNewTableCell()
            cellEmail.text = "Email"
            cellEmail.setWidth("4000") // Ancho moderado para email (~4cm)

            // Agregar datos de los clientes con ajuste de columnas
            clientes.forEach { cliente ->
                val row = table.createRow()
                row.getCell(0).text = cliente.nombre
                row.getCell(0).setWidth("3000")
                row.getCell(1).text = cliente.direccion
                row.getCell(1).setWidth("6000")
                row.getCell(2).text = cliente.email
                row.getCell(2).setWidth("4000")
            }

            // Escribir el archivo
            document.write(fileOutputStream)
            fileOutputStream.close()

            // Mostrar la ruta del archivo generado
            Toast.makeText(context, "Reporte Word generado en: ${file.absolutePath}", Toast.LENGTH_SHORT).show()

            // Abrir el archivo generado
            abrirWord(file)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al generar el reporte Word", Toast.LENGTH_SHORT).show()
        }
    }

    // Método para abrir el archivo Word
    private fun abrirWord(wordFile: File) {
        // Crear una URI segura para el archivo utilizando FileProvider
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", wordFile)

        // Crear un Intent para abrir el archivo Word
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        // Verificar si hay alguna aplicación para abrir archivos Word
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "No hay aplicación disponible para abrir el archivo Word", Toast.LENGTH_SHORT).show()
        }
    }
}

