package com.example.recordatoriomedicamentos

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Vibrator
import android.widget.Toast
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.MainScope

class AlarmaReceiver : BroadcastReceiver() {

    private var ringtone: Ringtone? = null
    private val durationMillis: Long = 30000 // Duración de la alarma en milisegundos (30 segundos)
    private val notificationId = 1
    private val channelId = "medication_channel"

    override fun onReceive(context: Context?, intent: Intent?) {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(4000)

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(context, alarmSound)
        ringtone?.play()

        Toast.makeText(context, "¡Es hora de tomar tu Medicamento!", Toast.LENGTH_LONG).show()

        // Crear una notificación
        createNotification(context)

        // Detener la reproducción del sonido después de la duración especificada
        Handler().postDelayed({
            ringtone?.stop()
        }, durationMillis)
    }

    private fun createNotification(context: Context?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear un canal de notificación para Android Oreo y versiones superiores
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Medication Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Intent para dirigir al usuario a Medicamentos.kt al hacer clic en la notificación
        val intent = Intent(context, MainActivity()::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        // Construir la notificación
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.iconopildora)
            .setContentTitle("¡Es hora de tomar tu Medicamento!")
            .setContentText("Haz clic aquí para abrir Medicamentos")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Mostrar la notificación
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}