package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiotecpreventivo.viewmodel.PlanViewModel

@Composable
fun PlanScreen(
    viewModel: PlanViewModel,
    onVolver: () -> Unit
) {
    val acciones by viewModel.acciones.collectAsState()
    val completadas = acciones.count { it.completada }

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
                text = "Mi plan preventivo",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text("Marca las acciones realizadas para el equipo ficticio.")

            if (acciones.isEmpty()) {
                Text(
                    text = "No hay acciones pendientes en el plan. " +
                            "Si aún no has realizado una evaluación, " +
                            "completa primero el cuestionario."
                )
            } else {
                Text(
                    text = "$completadas de ${acciones.size} completadas",
                    style = MaterialTheme.typography.titleMedium
                )

                acciones.forEach { accion ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = accion.completada,
                                onCheckedChange = { marcada ->
                                    viewModel.cambiarEstado(
                                        id = accion.id,
                                        completada = marcada
                                    )
                                }
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(accion.descripcion)

                                Text(
                                    text = if (accion.completada) {
                                        "Completada"
                                    } else {
                                        "Pendiente"
                                    },
                                    style =
                                        MaterialTheme.typography.bodySmall
                                )
                            }
                        }
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