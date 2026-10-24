package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiotecpreventivo.viewmodel.HistorialViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistorialScreen(
    viewModel: HistorialViewModel,
    onVolver: () -> Unit
) {
    val evaluaciones by viewModel.evaluaciones.collectAsState()
    val error by viewModel.error.collectAsState()

    val formatoFecha = remember {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    }

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
                text = "Historial de evaluaciones",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            error?.let { mensaje ->
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (evaluaciones.isEmpty()) {
                Text("Todavía no hay evaluaciones guardadas.")
            }

            evaluaciones.forEach { evaluacion ->
                val medidasActivas = listOf(
                    evaluacion.tieneRespaldos,
                    evaluacion.tieneActualizaciones,
                    evaluacion.tieneDobleFactor,
                    evaluacion.tieneRecuperacion
                ).count { respuesta ->
                    respuesta
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = evaluacion.dispositivoFicticio,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text("Perfil: ${evaluacion.aliasFicticio}")

                        Text(
                            text = formatoFecha.format(
                                Date(evaluacion.fecha)
                            )
                        )

                        Text("$medidasActivas de 4 medidas activas")
                    }
                }
            }

            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }
}