package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiotecpreventivo.viewmodel.DispositivoViewModel

@Composable
fun DispositivoScreen(
    viewModel: DispositivoViewModel,
    onContinuar: () -> Unit
) {
    val estado by viewModel.estado.collectAsState()
    val errores by viewModel.errores.collectAsState()

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
                text = "Dispositivo de prueba",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Registra un equipo ficticio. " +
                        "No ingreses números de serie ni datos personales."
            )

            OutlinedTextField(
                value = estado.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre ficticio del equipo") },
                singleLine = true,
                isError = errores.nombre != null,
                supportingText = {
                    errores.nombre?.let { mensaje ->
                        Text(mensaje)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Tipo de dispositivo",
                style = MaterialTheme.typography.titleMedium
            )

            val tipos = listOf("Celular", "Tablet", "Computador")

            tipos.forEach { tipo ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = estado.tipo == tipo,
                        onClick = {
                            viewModel.onTipoChange(tipo)
                        }
                    )
                    Text(tipo)
                }
            }

            errores.tipo?.let { mensaje ->
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Text(
                text = "Sistema operativo",
                style = MaterialTheme.typography.titleMedium
            )

            val sistemas = listOf(
                "Android", "iOS", "Windows", "macOS", "Linux", "Otro"
            )

            sistemas.forEach { sistema ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = estado.sistemaOperativo == sistema,
                        onClick = {
                            viewModel.onSistemaChange(sistema)
                        }
                    )
                    Text(sistema)
                }
            }

            errores.sistemaOperativo?.let { mensaje ->
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    if (viewModel.validarFormulario()) {
                        onContinuar()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar")
            }
        }
    }
}