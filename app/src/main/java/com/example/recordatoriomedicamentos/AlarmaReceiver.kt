package com.example.recordatoriomedicamentos

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Vibrator
import android.widget.Toast
import androidx.core.app.NotificationCompat

class AlarmaReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        Toast.makeText(context, "Alarm! Wake Up! Wake Up!", Toast.LENGTH_SHORT).show()

        val medicamento = intent?.getStringExtra("medicamento")

        showNotification(context, medicamento)
    }
    private fun showNotification(context: Context?, medicamento: String?) {
        val channelId = "AlarmChannel"
        val channelName = "Alarm Channel"
        val notificationId = 1

        // Obtiene el sonido de alarma predeterminado del dispositivo
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        // Intent para abrir la pantalla de medicamentos
        val medicamentosIntent = Intent(context, MedicamentosActivity::class.java)
        medicamentosIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val medicamentosPendingIntent = PendingIntent.getActivity(context, 0, medicamentosIntent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(context!!, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("¡Es hora de tomar tu medicamento!")
            .setContentText("Medicamento: $medicamento")
            .setAutoCancel(true)
            .setSound(alarmSound) // Establece el sonido de alarma predeterminado del dispositivo
            .setContentIntent(medicamentosPendingIntent) // Abre la pantalla de medicamentos al hacer clic en la notificación

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }



}