package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun InicioScreen(
    onComenzar: () -> Unit,
    onVerPlan: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "TíoTec Preventivo",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Cuida tu seguridad digital paso a paso.",
                style = MaterialTheme.typography.bodyLarge
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Evaluación preventiva",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Revisa tus hábitos de respaldo, " +
                                "actualización y protección de cuentas."
                    )
                }
            }

            Button(
                onClick = onComenzar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Comenzar evaluación")
            }

            OutlinedButton(
                onClick = onVerPlan,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver plan preventivo")
            }

            Text(
                text = "Aplicación académica con datos ficticios.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InicioScreenPreview() {
    MaterialTheme {
        InicioScreen(
            onComenzar = {},
            onVerPlan = {}
        )
    }
}