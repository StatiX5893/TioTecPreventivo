package com.example.tiotecpreventivo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiotecpreventivo.model.DispositivoErrores
import com.example.tiotecpreventivo.model.DispositivoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DispositivoViewModel : ViewModel() {

    private val _estado = MutableStateFlow(DispositivoUiState())
    val estado = _estado.asStateFlow()

    private val _errores = MutableStateFlow(DispositivoErrores())
    val errores = _errores.asStateFlow()

    fun onNombreChange(nombre: String) {
        _estado.update { actual ->
            actual.copy(nombre = nombre)
        }
    }

    fun onTipoChange(tipo: String) {
        _estado.update { actual ->
            actual.copy(tipo = tipo)
        }
    }

    fun onSistemaChange(sistema: String) {
        _estado.update { actual ->
            actual.copy(sistemaOperativo = sistema)
        }
    }

    fun validarFormulario(): Boolean {
        val dispositivo = _estado.value

        val nuevosErrores = DispositivoErrores(
            nombre = if (dispositivo.nombre.trim().length < 3) {
                "Ingresa un nombre ficticio de al menos 3 caracteres."
            } else {
                null
            },

            tipo = if (
                dispositivo.tipo !in listOf(
                    "Celular", "Tablet", "Computador"
                )
            ) {
                "Selecciona un tipo de dispositivo."
            } else {
                null
            },

            sistemaOperativo = if (
                dispositivo.sistemaOperativo !in listOf(
                    "Android", "iOS", "Windows", "macOS", "Linux", "Otro"
                )
            ) {
                "Selecciona un sistema operativo."
            } else {
                null
            }
        )

        _errores.value = nuevosErrores

        return nuevosErrores.nombre == null &&
                nuevosErrores.tipo == null &&
                nuevosErrores.sistemaOperativo == null
    }
}