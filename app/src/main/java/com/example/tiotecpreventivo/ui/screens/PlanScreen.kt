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
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.tiotecpreventivo.notifications.NotificacionHelper
@Composable
fun PlanScreen(
    viewModel: PlanViewModel,
    onVolver: () -> Unit
) {
    val acciones by viewModel.acciones.collectAsState()
    val completadas = acciones.count { it.completada }

    val context = LocalContext.current

    var mensajeNotificacion by remember {
        mutableStateOf("")
    }

    val notificacionHelper = remember(context) {
        NotificacionHelper(context)
    }

    val solicitarPermiso = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { permitido ->
        mensajeNotificacion = if (permitido) {
            if (notificacionHelper.mostrarRecordatorio()) {
                "Aviso enviado. Revisa la barra de notificaciones."
            } else {
                "No se pudo enviar. Revisa los ajustes de notificaciones."
            }
        } else {
            "Permiso no concedido. Puedes seguir usando el plan."
        }
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
            Button(
                onClick = {
                    if (
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        solicitarPermiso.launch(
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                    } else {
                        mensajeNotificacion =
                            if (notificacionHelper.mostrarRecordatorio()) {
                                "Aviso enviado. Revisa la barra de notificaciones."
                            } else {
                                "No se pudo enviar. Revisa los ajustes de notificaciones."
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Probar aviso preventivo")
            }

            if (mensajeNotificacion.isNotEmpty()) {
                Text(mensajeNotificacion)
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