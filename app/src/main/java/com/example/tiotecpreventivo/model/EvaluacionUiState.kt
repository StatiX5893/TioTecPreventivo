package com.example.tiotecpreventivo.model

data class EvaluacionUiState(
    val tieneRespaldos: Boolean? = null,
    val tieneActualizaciones: Boolean? = null,
    val tieneDobleFactor: Boolean? = null,
    val tieneRecuperacion: Boolean? = null,
    val error: String? = null
)