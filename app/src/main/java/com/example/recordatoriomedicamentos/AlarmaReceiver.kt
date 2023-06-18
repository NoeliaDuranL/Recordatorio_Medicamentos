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

class AlarmaReceiver : BroadcastReceiver() {

    private var ringtone: Ringtone? = null
    private val durationMillis: Long = 30000 // Duración de la alarma en milisegundos (30 segundos)

    override fun onReceive(context: Context?, intent: Intent?) {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(4000)

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(context, alarmSound)
        ringtone?.play()

        Toast.makeText(context, "¡Es hora de tomar tu Medicamento!", Toast.LENGTH_LONG).show()

        // Detener la reproducción del sonido después de la duración especificada
        Handler().postDelayed({
            ringtone?.stop()
        }, durationMillis)
    }
}