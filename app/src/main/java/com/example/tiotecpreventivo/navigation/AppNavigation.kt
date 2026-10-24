package com.example.tiotecpreventivo.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tiotecpreventivo.ui.screens.DispositivoScreen
import com.example.tiotecpreventivo.ui.screens.EvaluacionScreen
import com.example.tiotecpreventivo.ui.screens.InicioScreen
import com.example.tiotecpreventivo.ui.screens.PerfilScreen
import com.example.tiotecpreventivo.viewmodel.DispositivoViewModel
import com.example.tiotecpreventivo.viewmodel.EvaluacionViewModel
import com.example.tiotecpreventivo.viewmodel.PerfilViewModel

@Composable
fun AppNavigation(
    perfilViewModel: PerfilViewModel,
    dispositivoViewModel: DispositivoViewModel,
    evaluacionViewModel: EvaluacionViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            InicioScreen(
                onComenzar = {
                    navController.navigate("perfil")
                }
            )
        }

        composable("perfil") {
            PerfilScreen(
                viewModel = perfilViewModel,
                onContinuar = {
                    navController.navigate("dispositivo")
                }
            )
        }

        composable("dispositivo") {
            DispositivoScreen(
                viewModel = dispositivoViewModel,
                onContinuar = {
                    navController.navigate("evaluacion")
                }
            )
        }

        composable("evaluacion") {
            EvaluacionScreen(
                viewModel = evaluacionViewModel,
                onVerResultado = {
                    navController.navigate("confirmacion")
                }
            )
        }

        composable("confirmacion") {
            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("¡Cuestionario completado!")

                    Button(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text("Volver al cuestionario")
                    }
                }
            }
        }
    }
}