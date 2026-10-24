package com.example.tiotecpreventivo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiotecpreventivo.model.EvaluacionGuardada
import com.example.tiotecpreventivo.model.EvaluacionUiState
import com.example.tiotecpreventivo.repository.HistorialRepository
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistorialViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = HistorialRepository(application)

    private val _evaluaciones =
        MutableStateFlow<List<EvaluacionGuardada>>(emptyList())

    val evaluaciones = _evaluaciones.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                repository.evaluaciones.collect { guardadas ->
                    _evaluaciones.value = guardadas
                }
            } catch (excepcion: java.io.IOException) {
                _error.value = "No se pudo leer el historial."
            }
        }
    }

    fun guardarEvaluacion(
        alias: String,
        dispositivo: String,
        respuestas: EvaluacionUiState
    ) {
        // Solo guardamos cuestionarios completos.
        val respaldos = respuestas.tieneRespaldos ?: return
        val actualizaciones = respuestas.tieneActualizaciones ?: return
        val dobleFactor = respuestas.tieneDobleFactor ?: return
        val recuperacion = respuestas.tieneRecuperacion ?: return

        val evaluacion = EvaluacionGuardada(
            id = UUID.randomUUID().toString(),
            fecha = System.currentTimeMillis(),
            aliasFicticio = alias,
            dispositivoFicticio = dispositivo,
            tieneRespaldos = respaldos,
            tieneActualizaciones = actualizaciones,
            tieneDobleFactor = dobleFactor,
            tieneRecuperacion = recuperacion
        )

        viewModelScope.launch {
            try {
                repository.guardar(evaluacion)
                _error.value = null
            } catch (excepcion: java.io.IOException) {
                _error.value = "No se pudo guardar la evaluación."
            }
        }
    }
}