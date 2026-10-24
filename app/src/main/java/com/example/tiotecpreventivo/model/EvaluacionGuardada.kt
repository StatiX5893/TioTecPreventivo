package com.example.tiotecpreventivo.model

data class EvaluacionGuardada(
    val id: String,
    val fecha: Long,
    val aliasFicticio: String,
    val dispositivoFicticio: String,
    val tieneRespaldos: Boolean,
    val tieneActualizaciones: Boolean,
    val tieneDobleFactor: Boolean,
    val tieneRecuperacion: Boolean
)