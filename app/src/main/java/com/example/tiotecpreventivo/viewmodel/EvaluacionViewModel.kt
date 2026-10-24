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
}