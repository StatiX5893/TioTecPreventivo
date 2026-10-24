package com.example.tiotecpreventivo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiotecpreventivo.model.EvaluacionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EvaluacionViewModel : ViewModel() {

    private val _estado = MutableStateFlow(EvaluacionUiState())
    val estado = _estado.asStateFlow()

    fun onRespaldosChange(respuesta: Boolean) {
        _estado.update { actual ->
            actual.copy(tieneRespaldos = respuesta, error = null)
        }
    }

    fun onActualizacionesChange(respuesta: Boolean) {
        _estado.update { actual ->
            actual.copy(tieneActualizaciones = respuesta, error = null)
        }
    }

    fun onDobleFactorChange(respuesta: Boolean) {
        _estado.update { actual ->
            actual.copy(tieneDobleFactor = respuesta, error = null)
        }
    }

    fun onRecuperacionChange(respuesta: Boolean) {
        _estado.update { actual ->
            actual.copy(tieneRecuperacion = respuesta, error = null)
        }
    }

    fun validarEvaluacion(): Boolean {
        val respuestas = _estado.value

        val completa =
            respuestas.tieneRespaldos != null &&
                    respuestas.tieneActualizaciones != null &&
                    respuestas.tieneDobleFactor != null &&
                    respuestas.tieneRecuperacion != null

        _estado.update { actual ->
            actual.copy(
                error = if (completa) {
                    null
                } else {
                    "Responde todas las preguntas antes de continuar."
                }
            )
        }

        return completa
    }

    // Cuenta las medidas que el usuario respondió con "Sí".
    fun contarMedidasActivas(): Int {
        val respuestas = _estado.value

        return listOf(
            respuestas.tieneRespaldos,
            respuestas.tieneActualizaciones,
            respuestas.tieneDobleFactor,
            respuestas.tieneRecuperacion
        ).count { respuesta ->
            respuesta == true
        }
    }

    // Regla sencilla de orientación para este prototipo académico.
    fun obtenerOrientacion(): String {
        return when (contarMedidasActivas()) {
            4 -> "Mantén tus hábitos preventivos"
            2, 3 -> "Hay medidas por mejorar"
            else -> "Prioriza las medidas pendientes"
        }
    }

    // Agrega una recomendación por cada respuesta "No".
    fun obtenerRecomendaciones(): List<String> {
        val respuestas = _estado.value
        val recomendaciones = mutableListOf<String>()

        if (respuestas.tieneRespaldos == false) {
            recomendaciones.add(
                "Configura respaldos periódicos de tus archivos."
            )
        }

        if (respuestas.tieneActualizaciones == false) {
            recomendaciones.add(
                "Revisa las actualizaciones del sistema y las aplicaciones."
            )
        }

        if (respuestas.tieneDobleFactor == false) {
            recomendaciones.add(
                "Activa la autenticación de dos factores en tus cuentas."
            )
        }

        if (respuestas.tieneRecuperacion == false) {
            recomendaciones.add(
                "Revisa las opciones de recuperación de tus cuentas."
            )
        }

        return recomendaciones
    }
}