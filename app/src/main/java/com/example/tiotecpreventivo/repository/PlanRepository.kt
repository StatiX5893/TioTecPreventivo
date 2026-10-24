package com.example.tiotecpreventivo.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tiotecpreventivo.model.AccionPreventiva
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

// Una sola instancia de DataStore para este archivo.
private val Context.planDataStore by preferencesDataStore(
    name = "plan_preventivo"
)

class PlanRepository(context: Context) {

    private val appContext = context.applicationContext

    private val claveAcciones = stringPreferencesKey("acciones")

    // Lee los datos guardados y los convierte en acciones.
    val acciones: Flow<List<AccionPreventiva>> =
        appContext.planDataStore.data.map { preferencias ->
            val texto = preferencias[claveAcciones] ?: "[]"
            val arreglo = JSONArray(texto)
            val lista = mutableListOf<AccionPreventiva>()

            for (indice in 0 until arreglo.length()) {
                val objeto = arreglo.getJSONObject(indice)

                lista.add(
                    AccionPreventiva(
                        id = objeto.getString("id"),
                        descripcion = objeto.getString("descripcion"),
                        completada = objeto.getBoolean("completada")
                    )
                )
            }

            lista
        }

    // Convierte las acciones en texto JSON y las guarda.
    suspend fun guardarAcciones(acciones: List<AccionPreventiva>) {
        val arreglo = JSONArray()

        acciones.forEach { accion ->
            val objeto = JSONObject()

            objeto.put("id", accion.id)
            objeto.put("descripcion", accion.descripcion)
            objeto.put("completada", accion.completada)

            arreglo.put(objeto)
        }

        appContext.planDataStore.edit { preferencias ->
            preferencias[claveAcciones] = arreglo.toString()
        }
    }
}