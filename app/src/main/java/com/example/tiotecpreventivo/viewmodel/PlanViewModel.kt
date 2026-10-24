package com.example.tiotecpreventivo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiotecpreventivo.model.AccionPreventiva
import com.example.tiotecpreventivo.model.EvaluacionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlanViewModel : ViewModel() {

    private val _acciones =
        MutableStateFlow<List<AccionPreventiva>>(emptyList())

    val acciones = _acciones.asStateFlow()

    fun generarPlan(evaluacion: EvaluacionUiState) {
        val nuevasAcciones = mutableListOf<AccionPreventiva>()

        if (evaluacion.tieneRespaldos == false) {
            nuevasAcciones.add(
                AccionPreventiva(
                    id = "respaldos",
                    descripcion = "Configura respaldos periódicos."
                )
            )
        }

        if (evaluacion.tieneActualizaciones == false) {
            nuevasAcciones.add(
                AccionPreventiva(
                    id = "actualizaciones",
                    descripcion = "Revisa las actualizaciones disponibles."
                )
            )
        }

        if (evaluacion.tieneDobleFactor == false) {
            nuevasAcciones.add(
                AccionPreventiva(
                    id = "dobleFactor",
                    descripcion = "Activa la autenticación de dos factores."
                )
            )
        }

        if (evaluacion.tieneRecuperacion == false) {
            nuevasAcciones.add(
                AccionPreventiva(
                    id = "recuperacion",
                    descripcion = "Configura la recuperación de tus cuentas."
                )
            )
        }

        // Conserva el avance de las acciones que siguen en el plan.
        _acciones.update { anteriores ->
            nuevasAcciones.map { nueva ->
                val anterior = anteriores.find { accion ->
                    accion.id == nueva.id
                }

                nueva.copy(
                    completada = anterior?.completada ?: false
                )
            }
        }
    }

    fun cambiarEstado(id: String, completada: Boolean) {
        _acciones.update { actuales ->
            actuales.map { accion ->
                if (accion.id == id) {
                    accion.copy(completada = completada)
                } else {
                    accion
                }
            }
        }
    }
}