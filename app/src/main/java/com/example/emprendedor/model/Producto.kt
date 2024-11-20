package com.example.emprendedor.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "productos",
    foreignKeys = [
        ForeignKey(
            entity = Categoria::class,
            parentColumns = ["id"],
            childColumns = ["categoriaId"],
            onDelete = ForeignKey.CASCADE
        )
                ],
    indices = [
        Index(value = ["categoriaId"])
    ]
)
data class Producto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var nombre: String,
    var artista: String,
    var year: String,
    var precio: Double,
    var stock: Int,
    var descripcion: String? = null,
    var imagen: String? = null,
    var categoriaId: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)