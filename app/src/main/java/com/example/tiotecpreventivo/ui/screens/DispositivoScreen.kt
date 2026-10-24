package com.example.tiotecpreventivo.ui.screens

import android.content.ActivityNotFoundException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tiotecpreventivo.camera.FotoHelper
import com.example.tiotecpreventivo.viewmodel.DispositivoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DispositivoScreen(
    viewModel: DispositivoViewModel,
    onContinuar: () -> Unit,
    onVolver: () -> Unit
) {
    val estado by viewModel.estado.collectAsState()
    val errores by viewModel.errores.collectAsState()

    val context = LocalContext.current
    val fotoHelper = remember(context) {
        FotoHelper(context)
    }

    // Guardamos las direcciones como texto.
    var fotoPendiente by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var fotoConfirmada by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var mensajeFoto by rememberSaveable {
        mutableStateOf("")
    }

    // Recibe el resultado de la aplicación de cámara.
    val abrirCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { guardada ->
        if (guardada) {
            fotoConfirmada = fotoPendiente
            mensajeFoto = "Foto capturada."
        } else {
            mensajeFoto = "Captura cancelada. Puedes intentarlo nuevamente."
        }
    }

    // Carga una versión reducida sin bloquear la pantalla.
    val imagen by produceState<Bitmap?>(
        initialValue = null,
        key1 = fotoConfirmada
    ) {
        value = null
        val direccion = fotoConfirmada

        if (direccion != null) {
            value = withContext(Dispatchers.IO) {
                try {
                    val opciones = BitmapFactory.Options().apply {
                        inSampleSize = 4
                    }

                    context.contentResolver.openInputStream(
                        Uri.parse(direccion)
                    )?.use { archivo ->
                        BitmapFactory.decodeStream(
                            archivo,
                            null,
                            opciones
                        )
                    }
                } catch (excepcion: java.io.IOException) {
                    null
                }
            }
        }
    }

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

            Text(
                text = "Fotografía de prueba",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Opcional: fotografía un objeto de prueba, " +
                        "sin personas ni información personal."
            )

            OutlinedButton(
                onClick = {
                    try {
                        val uri = fotoHelper.crearUriFoto()
                        fotoPendiente = uri.toString()
                        abrirCamara.launch(uri)
                    } catch (excepcion: ActivityNotFoundException) {
                        mensajeFoto = "No hay una aplicación de cámara."
                    } catch (excepcion: java.io.IOException) {
                        mensajeFoto = "No se pudo crear el archivo."
                    } catch (excepcion: SecurityException) {
                        mensajeFoto = "No se pudo acceder a la cámara."
                    } catch (excepcion: IllegalArgumentException) {
                        mensajeFoto =
                            "Revisa la configuración de FileProvider."
                    } catch (excepcion: IllegalStateException) {
                        mensajeFoto = "No se pudo preparar la carpeta."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tomar foto de prueba")
            }

            if (mensajeFoto.isNotEmpty()) {
                Text(mensajeFoto)
            }

            imagen?.let { foto ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        bitmap = foto.asImageBitmap(),
                        contentDescription =
                            "Fotografía del dispositivo de prueba",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Fit
                    )
                }
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

            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al perfil")
            }
        }
    }
}