package com.example.tiotecpreventivo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tiotecpreventivo.ui.screens.DispositivoScreen
import com.example.tiotecpreventivo.ui.screens.EvaluacionScreen
import com.example.tiotecpreventivo.ui.screens.InicioScreen
import com.example.tiotecpreventivo.ui.screens.PerfilScreen
import com.example.tiotecpreventivo.ui.screens.PlanScreen
import com.example.tiotecpreventivo.ui.screens.ResultadoScreen
import com.example.tiotecpreventivo.viewmodel.DispositivoViewModel
import com.example.tiotecpreventivo.viewmodel.EvaluacionViewModel
import com.example.tiotecpreventivo.viewmodel.PerfilViewModel
import com.example.tiotecpreventivo.viewmodel.PlanViewModel

@Composable
fun AppNavigation(
    perfilViewModel: PerfilViewModel,
    dispositivoViewModel: DispositivoViewModel,
    evaluacionViewModel: EvaluacionViewModel,
    planViewModel: PlanViewModel
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
                    planViewModel.generarPlan(
                        evaluacionViewModel.estado.value
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
    }
}