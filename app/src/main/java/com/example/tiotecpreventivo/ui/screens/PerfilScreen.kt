package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiotecpreventivo.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel,
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
                text = "Perfil de práctica",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text("Utiliza un alias ficticio, sin datos personales.")

            OutlinedTextField(
                value = estado.alias,
                onValueChange = viewModel::onAliasChange,
                label = { Text("Alias ficticio") },
                singleLine = true,
                isError = errores.alias != null,
                supportingText = {
                    errores.alias?.let { mensaje ->
                        Text(mensaje)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Nivel de experiencia digital",
                style = MaterialTheme.typography.titleMedium
            )

            val niveles = listOf("Básico", "Intermedio", "Avanzado")

            niveles.forEach { nivel ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = estado.nivelExperiencia == nivel,
                        onClick = {
                            viewModel.onNivelChange(nivel)
                        }
                    )
                    Text(nivel)
                }
            }

            errores.nivelExperiencia?.let { mensaje ->
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = estado.aceptaDatosFicticios,
                    onCheckedChange = viewModel::onAceptaDatosChange
                )

                Text("Confirmo que utilizaré datos ficticios.")
            }

            errores.aceptaDatosFicticios?.let { mensaje ->
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