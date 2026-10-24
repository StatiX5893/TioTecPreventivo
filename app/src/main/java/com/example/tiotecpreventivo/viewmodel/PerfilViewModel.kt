package com.example.tiotecpreventivo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tiotecpreventivo.model.PerfilErrores
import com.example.tiotecpreventivo.model.PerfilUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PerfilViewModel : ViewModel() {

    // Datos del formulario: se modifican desde este ViewModel.
    private val _estado = MutableStateFlow(PerfilUiState())
    val estado = _estado.asStateFlow()

    // Mensajes de error que observará la pantalla.
    private val _errores = MutableStateFlow(PerfilErrores())
    val errores = _errores.asStateFlow()

    fun onAliasChange(nuevoAlias: String) {
        _estado.update { actual ->
            actual.copy(alias = nuevoAlias)
        }
    }

    fun onNivelChange(nuevoNivel: String) {
        _estado.update { actual ->
            actual.copy(nivelExperiencia = nuevoNivel)
        }
    }

    fun onAceptaDatosChange(acepta: Boolean) {
        _estado.update { actual ->
            actual.copy(aceptaDatosFicticios = acepta)
        }
    }

    fun validarFormulario(): Boolean {
        val perfil = _estado.value

        val nuevosErrores = PerfilErrores(
            alias = if (perfil.alias.trim().length < 3) {
                "El alias debe tener al menos 3 caracteres."
            } else {
                null
            },

            nivelExperiencia = if (
                perfil.nivelExperiencia !in listOf(
                    "Básico", "Intermedio", "Avanzado"
                )
            ) {
                "Selecciona tu nivel de experiencia."
            } else {
                null
            },

            aceptaDatosFicticios = if (!perfil.aceptaDatosFicticios) {
                "Confirma que utilizarás datos ficticios."
            } else {
                null
            }
        )

        _errores.value = nuevosErrores

        return nuevosErrores.alias == null &&
                nuevosErrores.nivelExperiencia == null &&
                nuevosErrores.aceptaDatosFicticios == null
    }
}