package com.example.tiotecpreventivo.camera

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class FotoHelper(private val context: Context) {

    fun crearUriFoto(): Uri {
        val carpeta = File(context.filesDir, "fotos")

        if (!carpeta.exists()) {
            check(carpeta.mkdirs()) {
                "No se pudo crear la carpeta de fotografías."
            }
        }

        val archivo = File.createTempFile(
            "dispositivo_",
            ".jpg",
            carpeta
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            archivo
        )
    }
}