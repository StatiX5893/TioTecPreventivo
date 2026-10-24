package com.example.tiotecpreventivo.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tiotecpreventivo.model.EvaluacionGuardada
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.historialDataStore by preferencesDataStore(
    name = "historial_evaluaciones"
)

class HistorialRepository(context: Context) {

    private val appContext = context.applicationContext
    private val claveHistorial = stringPreferencesKey("evaluaciones")

    val evaluaciones: Flow<List<EvaluacionGuardada>> =
        appContext.historialDataStore.data.map { preferencias ->
            leerEvaluaciones(preferencias[claveHistorial] ?: "[]")
        }

    suspend fun guardar(evaluacion: EvaluacionGuardada) {
        appContext.historialDataStore.edit { preferencias ->
            val anteriores = leerEvaluaciones(
                preferencias[claveHistorial] ?: "[]"
            )

            // El registro nuevo aparece primero.
            val actualizadas = listOf(evaluacion) +
                    anteriores.filter { it.id != evaluacion.id }

            preferencias[claveHistorial] =
                convertirATexto(actualizadas)
        }
    }

    private fun leerEvaluaciones(
        texto: String
    ): List<EvaluacionGuardada> {
        val arreglo = JSONArray(texto)
        val lista = mutableListOf<EvaluacionGuardada>()

        for (indice in 0 until arreglo.length()) {
            val objeto = arreglo.getJSONObject(indice)

            lista.add(
                EvaluacionGuardada(
                    id = objeto.getString("id"),
                    fecha = objeto.getLong("fecha"),
                    aliasFicticio = objeto.getString("alias"),
                    dispositivoFicticio = objeto.getString("dispositivo"),
                    tieneRespaldos = objeto.getBoolean("respaldos"),
                    tieneActualizaciones =
                        objeto.getBoolean("actualizaciones"),
                    tieneDobleFactor = objeto.getBoolean("dobleFactor"),
                    tieneRecuperacion = objeto.getBoolean("recuperacion")
                )
            )
        }

        return lista
    }

    private fun convertirATexto(
        evaluaciones: List<EvaluacionGuardada>
    ): String {
        val arreglo = JSONArray()

        evaluaciones.forEach { evaluacion ->
            val objeto = JSONObject()

            objeto.put("id", evaluacion.id)
            objeto.put("fecha", evaluacion.fecha)
            objeto.put("alias", evaluacion.aliasFicticio)
            objeto.put("dispositivo", evaluacion.dispositivoFicticio)
            objeto.put("respaldos", evaluacion.tieneRespaldos)
            objeto.put(
                "actualizaciones",
                evaluacion.tieneActualizaciones
            )
            objeto.put("dobleFactor", evaluacion.tieneDobleFactor)
            objeto.put("recuperacion", evaluacion.tieneRecuperacion)

            arreglo.put(objeto)
        }

        return arreglo.toString()
    }
}