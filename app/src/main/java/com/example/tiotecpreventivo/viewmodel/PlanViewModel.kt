package com.example.tiotecpreventivo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiotecpreventivo.model.AccionPreventiva
import com.example.tiotecpreventivo.model.EvaluacionUiState
import com.example.tiotecpreventivo.repository.PlanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class PlanViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = PlanRepository(application)

    private val _acciones =
        MutableStateFlow<List<AccionPreventiva>>(emptyList())

    val acciones = _acciones.asStateFlow()

    // Ejecuta los cambios de guardado uno por uno.
    private val mutex = Mutex()

    init {
        viewModelScope.launch {
            repository.acciones.collect { guardadas ->
                _acciones.value = guardadas
            }
        }
    }

    fun generarPlan(evaluacion: EvaluacionUiState) {
        viewModelScope.launch {
            mutex.withLock {
                val anteriores = repository.acciones.first()
                val nuevas = mutableListOf<AccionPreventiva>()

                if (evaluacion.tieneRespaldos == false) {
                    nuevas.add(
                        AccionPreventiva(
                            id = "respaldos",
                            descripcion =
                                "Configura respaldos periódicos."
                        )
                    )
                }

                if (evaluacion.tieneActualizaciones == false) {
                    nuevas.add(
                        AccionPreventiva(
                            id = "actualizaciones",
                            descripcion =
                                "Revisa las actualizaciones disponibles."
                        )
                    )
                }

                if (evaluacion.tieneDobleFactor == false) {
                    nuevas.add(
                        AccionPreventiva(
                            id = "dobleFactor",
                            descripcion =
                                "Activa la autenticación de dos factores."
                        )
                    )
                }

                if (evaluacion.tieneRecuperacion == false) {
                    nuevas.add(
                        AccionPreventiva(
                            id = "recuperacion",
                            descripcion =
                                "Configura la recuperación de tus cuentas."
                        )
                    )
                }

                val plan = nuevas.map { nueva ->
                    val anterior = anteriores.find {
                        it.id == nueva.id
                    }

                    nueva.copy(
                        completada = anterior?.completada ?: false
                    )
                }

                repository.guardarAcciones(plan)
            }
        }
    }

    fun cambiarEstado(id: String, completada: Boolean) {
        viewModelScope.launch {
            mutex.withLock {
                val actuales = repository.acciones.first()

                val actualizadas = actuales.map { accion ->
                    if (accion.id == id) {
                        accion.copy(completada = completada)
                    } else {
                        accion
                    }
                }

                repository.guardarAcciones(actualizadas)
            }
        }
    }
}