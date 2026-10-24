package com.example.tiotecpreventivo.model

data class AccionPreventiva(
    val id: String,
    val descripcion: String,
    val completada: Boolean = false
)