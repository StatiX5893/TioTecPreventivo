package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiotecpreventivo.viewmodel.EvaluacionViewModel

@Composable
fun ResultadoScreen(
    viewModel: EvaluacionViewModel,
    onVolver: () -> Unit
) {
    // Observamos las respuestas para actualizar el resultado.
    val estado by viewModel.estado.collectAsState()

    val completa =
        estado.tieneRespaldos != null &&
                estado.tieneActualizaciones != null &&
                estado.tieneDobleFactor != null &&
                estado.tieneRecuperacion != null

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Resultado preventivo",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            if (completa) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "${viewModel.contarMedidasActivas()} " +
                                    "de 4 medidas activas",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = viewModel.obtenerOrientacion()
                        )
                    }
                }

                Text(
                    text = "Recomendaciones",
                    style = MaterialTheme.typography.titleLarge
                )

                val recomendaciones =
                    viewModel.obtenerRecomendaciones()

                if (recomendaciones.isEmpty()) {
                    Text(
                        text = "No hay medidas pendientes según tus " +
                                "respuestas. Revisa tus hábitos periódicamente."
                    )
                } else {
                    recomendaciones.forEach { recomendacion ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = recomendacion,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            } else {
                Text("Completa el cuestionario para ver el resultado.")
            }

            Text(
                text = "Orientación académica basada en tus respuestas. " +
                        "No representa un diagnóstico del dispositivo.",
                style = MaterialTheme.typography.bodySmall
            )

            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al cuestionario")
            }
        }
    }
}