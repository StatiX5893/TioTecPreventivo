package com.example.tiotecpreventivo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tiotecpreventivo.ui.screens.DispositivoScreen
import com.example.tiotecpreventivo.ui.screens.EvaluacionScreen
import com.example.tiotecpreventivo.ui.screens.HistorialScreen
import com.example.tiotecpreventivo.ui.screens.InicioScreen
import com.example.tiotecpreventivo.ui.screens.PerfilScreen
import com.example.tiotecpreventivo.ui.screens.PlanScreen
import com.example.tiotecpreventivo.ui.screens.ResultadoScreen
import com.example.tiotecpreventivo.viewmodel.DispositivoViewModel
import com.example.tiotecpreventivo.viewmodel.EvaluacionViewModel
import com.example.tiotecpreventivo.viewmodel.HistorialViewModel
import com.example.tiotecpreventivo.viewmodel.PerfilViewModel
import com.example.tiotecpreventivo.viewmodel.PlanViewModel

@Composable
fun AppNavigation(
    perfilViewModel: PerfilViewModel,
    dispositivoViewModel: DispositivoViewModel,
    evaluacionViewModel: EvaluacionViewModel,
    planViewModel: PlanViewModel,
    historialViewModel: HistorialViewModel
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
                },
                onVerPlan = {
                    navController.navigate("plan")
                },
                onVerHistorial = {
                    navController.navigate("historial")
                }
            )
        }

        composable("perfil") {
            PerfilScreen(
                viewModel = perfilViewModel,
                onContinuar = {
                    navController.navigate("dispositivo")
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("dispositivo") {
            DispositivoScreen(
                viewModel = dispositivoViewModel,
                onContinuar = {
                    navController.navigate("evaluacion")
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("evaluacion") {
            EvaluacionScreen(
                viewModel = evaluacionViewModel,
                onVerResultado = {
                    val respuestas = evaluacionViewModel.estado.value

                    planViewModel.generarPlan(respuestas)

                    historialViewModel.guardarEvaluacion(
                        alias = perfilViewModel.estado.value.alias,
                        dispositivo =
                            dispositivoViewModel.estado.value.nombre,
                        respuestas = respuestas
                    )

                    navController.navigate("resultado")
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("resultado") {
            ResultadoScreen(
                viewModel = evaluacionViewModel,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("plan") {
            PlanScreen(
                viewModel = planViewModel,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("historial") {
            HistorialScreen(
                viewModel = historialViewModel,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}