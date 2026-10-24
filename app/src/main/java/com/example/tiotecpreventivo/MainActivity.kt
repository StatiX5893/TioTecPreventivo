package com.example.tiotecpreventivo

import com.example.tiotecpreventivo.viewmodel.EvaluacionViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.tiotecpreventivo.navigation.AppNavigation
import com.example.tiotecpreventivo.ui.theme.TioTecPreventivoTheme
import com.example.tiotecpreventivo.viewmodel.PerfilViewModel
import com.example.tiotecpreventivo.viewmodel.DispositivoViewModel
import com.example.tiotecpreventivo.viewmodel.PlanViewModel
import com.example.tiotecpreventivo.viewmodel.HistorialViewModel

class MainActivity : ComponentActivity() {

    private val perfilViewModel: PerfilViewModel by viewModels()
    private val dispositivoViewModel: DispositivoViewModel by viewModels()

    private val evaluacionViewModel: EvaluacionViewModel by viewModels()

    private val planViewModel: PlanViewModel by viewModels()

    private val historialViewModel: HistorialViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TioTecPreventivoTheme {
                AppNavigation(
                    perfilViewModel = perfilViewModel,
                    dispositivoViewModel = dispositivoViewModel,
                    evaluacionViewModel = evaluacionViewModel,
                    planViewModel = planViewModel,
                    historialViewModel = historialViewModel
                )
            }
        }
    }
}