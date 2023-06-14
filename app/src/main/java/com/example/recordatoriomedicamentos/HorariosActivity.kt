package com.example.recordatoriomedicamentos

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import java.util.Calendar

class HorariosActivity : AppCompatActivity() {
    var alarmTimePicker: TimePicker? = null  // Selector de tiempo para la alarma
    var pendingIntent: PendingIntent? = null  // Intent pendiente para la emisión de la alarma
    var alarmManager: AlarmManager? = null  // Administrador de alarmas del sistema

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horarios)

        alarmTimePicker = findViewById(R.id.timePicker)  // Obtener la referencia del selector de tiempo del diseño
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager  // Obtener una instancia del servicio de alarmas del sistema
    }

    fun onToggleClicked(view: View) {
        var time: Long  // Variable para almacenar el tiempo de la alarma

        if ((view as ToggleButton).isChecked) {  // Verificar si el botón de alternancia está marcado (activado)
            Toast.makeText(this, "Alarm ENCENDIDO", Toast.LENGTH_SHORT).show()  // Mostrar mensaje de alarma encendida

            var calendar = Calendar.getInstance()  // Crear una instancia de Calendar con la fecha y hora actuales

            calendar[Calendar.HOUR_OF_DAY] = alarmTimePicker!!.currentHour  // Establecer la hora del calendario con el valor seleccionado en el selector de tiempo
            calendar[Calendar.MINUTE] = alarmTimePicker!!.currentMinute  // Establecer el minuto del calendario con el valor seleccionado en el selector de tiempo

            val intent = Intent(this, AlarmaReceiver::class.java)  // Crear un intento para iniciar la clase AlarmaReceiver

            pendingIntent = PendingIntent.getBroadcast(
                this,
                0, intent, PendingIntent.FLAG_IMMUTABLE
            )  // Obtener un PendingIntent para la emisión de difusión utilizando el intento creado

            time = calendar.timeInMillis - calendar.timeInMillis % 60000  // Calcular el tiempo en milisegundos al que se programará la alarma

            if (System.currentTimeMillis() > time) {  // Verificar si el tiempo programado ya ha pasado
                time = if (Calendar.AM_PM == 0) {  // Si es así, ajustar el tiempo programado para el próximo día
                    time + 1000 * 60 * 60 * 12  // Sumar 12 horas si es AM
                } else {
                    time + 1000 * 60 * 60 * 24  // Sumar 24 horas si es PM
                }
            }

            alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP, time, 1000, pendingIntent)  // Programar una alarma repetitiva utilizando el AlarmManager
        } else {
            alarmManager!!.cancel(pendingIntent)  // Cancelar la alarma programada anteriormente utilizando el mismo PendingIntent
            Toast.makeText(this, "Alarm APAGADO", Toast.LENGTH_SHORT).show()  // Mostrar mensaje de alarma apagada
        }
    }
}
