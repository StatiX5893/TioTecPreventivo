package com.example.tiotecpreventivo.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tiotecpreventivo.ui.screens.InicioScreen
import com.example.tiotecpreventivo.ui.screens.PerfilScreen
import com.example.tiotecpreventivo.viewmodel.PerfilViewModel

@Composable
fun AppNavigation(perfilViewModel: PerfilViewModel) {
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
                    Text("¡Perfil validado!")

                    Button(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text("Volver al perfil")
                    }
                }
            }
        }
    }
}