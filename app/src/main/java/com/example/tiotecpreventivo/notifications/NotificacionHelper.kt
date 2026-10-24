package com.example.tiotecpreventivo.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.tiotecpreventivo.MainActivity

class NotificacionHelper(private val context: Context) {

    private val canalId = "prevencion_digital"

    fun mostrarRecordatorio(): Boolean {

        // Android 13 o superior requiere autorización del usuario.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permiso = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )

            if (permiso != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }

        val administrador = NotificationManagerCompat.from(context)

        if (!administrador.areNotificationsEnabled()) {
            return false
        }

        // Android 8 o superior requiere un canal de notificaciones.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                canalId,
                "Recordatorios preventivos",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            canal.description = "Avisos para revisar tu plan preventivo"

            val sistema = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

            sistema.createNotificationChannel(canal)

            if (
                sistema.getNotificationChannel(canalId)?.importance ==
                NotificationManager.IMPORTANCE_NONE
            ) {
                return false
            }
        }

        // Al tocar la notificación, abre la aplicación.
        val intent = Intent(context, MainActivity::class.java)

        val abrirApp = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val notificacion = NotificationCompat.Builder(context, canalId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("TíoTec Preventivo")
            .setContentText("Revisa las acciones de tu plan preventivo.")
            .setContentIntent(abrirApp)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        return try {
            administrador.notify(1001, notificacion)
            true
        } catch (excepcion: SecurityException) {
            false
        }
    }
}