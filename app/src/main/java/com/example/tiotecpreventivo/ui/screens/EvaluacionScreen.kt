package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiotecpreventivo.viewmodel.EvaluacionViewModel

@Composable
fun EvaluacionScreen(
    viewModel: EvaluacionViewModel,
    onVerResultado: () -> Unit,
    onVolver: () -> Unit
) {
    val estado by viewModel.estado.collectAsState()

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
                text = "Evaluación preventiva",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Responde para el dispositivo ficticio. " +
                        "Esta evaluación se basa en tus respuestas; " +
                        "no analiza el equipo automáticamente."
            )

            PreguntaPreventiva(
                titulo = "¿Realizas respaldos de tus archivos?",
                respuesta = estado.tieneRespaldos,
                onRespuesta = viewModel::onRespaldosChange
            )

            PreguntaPreventiva(
                titulo = "¿Mantienes actualizado el dispositivo?",
                respuesta = estado.tieneActualizaciones,
                onRespuesta = viewModel::onActualizacionesChange
            )

            PreguntaPreventiva(
                titulo = "¿Utilizas autenticación de dos factores?",
                respuesta = estado.tieneDobleFactor,
                onRespuesta = viewModel::onDobleFactorChange
            )

            PreguntaPreventiva(
                titulo = "¿Tienes configurada la recuperación de tus cuentas?",
                respuesta = estado.tieneRecuperacion,
                onRespuesta = viewModel::onRecuperacionChange
            )

            estado.error?.let { mensaje ->
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    if (viewModel.validarEvaluacion()) {
                        onVerResultado()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver resultado")
            }
            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al dispositivo")
            }
        }
    }
}

@Composable
fun PreguntaPreventiva(
    titulo: String,
    respuesta: Boolean?,
    onRespuesta: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = respuesta == true,
                    onClick = { onRespuesta(true) }
                )
                Text("Sí")

                Spacer(modifier = Modifier.width(16.dp))

                RadioButton(
                    selected = respuesta == false,
                    onClick = { onRespuesta(false) }
                )
                Text("No")
            }
        }
    }
}