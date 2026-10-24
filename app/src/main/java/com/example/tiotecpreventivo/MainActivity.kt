package com.example.tiotecpreventivo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.tiotecpreventivo.navigation.AppNavigation
import com.example.tiotecpreventivo.ui.theme.TioTecPreventivoTheme
import com.example.tiotecpreventivo.viewmodel.PerfilViewModel

class MainActivity : ComponentActivity() {

    private val perfilViewModel: PerfilViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TioTecPreventivoTheme {
                AppNavigation(
                    perfilViewModel = perfilViewModel
                )
            }
        }
    }
}