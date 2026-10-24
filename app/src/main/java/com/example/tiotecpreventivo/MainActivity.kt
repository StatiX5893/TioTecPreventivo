package com.example.tiotecpreventivo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiotecpreventivo.ui.screens.InicioScreen
import com.example.tiotecpreventivo.ui.screens.PerfilScreen
import com.example.tiotecpreventivo.ui.theme.TioTecPreventivoTheme
import com.example.tiotecpreventivo.viewmodel.PerfilViewModel

class MainActivity : ComponentActivity() {

    private val perfilViewModel: PerfilViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TioTecPreventivoTheme {
                var pantalla by remember { mutableStateOf("inicio") }

                when (pantalla) {
                    "inicio" -> InicioScreen(
                        onComenzar = {
                            pantalla = "perfil"
                        }
                    )

                    "perfil" -> PerfilScreen(
                        viewModel = perfilViewModel,
                        onContinuar = {
                            pantalla = "confirmacion"
                        }
                    )

                    "confirmacion" -> {
                        Scaffold { innerPadding ->
                            Column(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .padding(24.dp),
                                verticalArrangement =
                                    Arrangement.spacedBy(16.dp)
                            ) {
                                Text("¡Perfil validado!")

                                Button(
                                    onClick = {
                                        pantalla = "perfil"
                                    }
                                ) {
                                    Text("Volver al perfil")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}