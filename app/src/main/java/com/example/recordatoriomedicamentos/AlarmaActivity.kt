package com.example.recordatoriomedicamentos
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.ArrayList
import java.util.Calendar
import java.util.Locale

class AlarmaActivity : AppCompatActivity() {

    //------------------
    private lateinit var timePicker: TimePicker
    private lateinit var buttonSetAlarm: Button
    private lateinit var buttonVerAlarmas:Button
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    //----------------------
    private val alarmas: MutableList<String> = mutableListOf()
    //-----------------
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarma)
        //-----------------------------------
        timePicker = findViewById(R.id.timePicker)
        buttonSetAlarm = findViewById(R.id.buttonSetAlarm)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        buttonSetAlarm.setOnClickListener {
            setAlarm()
        }
        buttonVerAlarmas = findViewById(R.id.buttonVerAlarmas)
        buttonVerAlarmas.setOnClickListener {
            val intent = Intent(this, AlarmasProgramadasActivity::class.java)
            startActivity(intent)
        }
        //-------------------
    }

    //-----------
    private fun setAlarm() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
        calendar.set(Calendar.MINUTE, timePicker.minute)
        calendar.set(Calendar.SECOND, 0)

        val intent = Intent(this, AlarmaReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val currentTime = System.currentTimeMillis()
        val alarmTime = calendar.timeInMillis

        if (alarmTime <= currentTime) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        Toast.makeText(this, "Alarma programada", Toast.LENGTH_SHORT).show()

        // Obtener los datos de la alarma programada
        val hora = timePicker.hour
        val minutos = timePicker.minute

        // Crear una cadena con el formato deseado (por ejemplo, "hora: minutos AM/PM")
        val alarmasProgramada = String.format(Locale.getDefault(), "%02d:%02d %s", hora, minutos, getAMPM(hora))

        // Agregar la alarma programada a la lista de alarmas
        val alarmaProgramada = String.format(Locale.getDefault(), "%02d:%02d %s", hora, minutos, getAMPM(hora))
        alarmas.add(alarmaProgramada)

        // Agregar la lista de alarmas al Intent
        val intentAlarmasProgramadas = Intent(this, AlarmasProgramadasActivity::class.java)
        intentAlarmasProgramadas.putStringArrayListExtra("alarmas", ArrayList(alarmas))
        startActivity(intentAlarmasProgramadas)

        val hour = timePicker.hour
        val minute = timePicker.minute
        val time = String.format("%02d:%02d", hour, minute)

        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val alarmas: MutableSet<String> = sharedPref.getStringSet("alarmas", null)?.toMutableSet() ?: mutableSetOf()
        alarmas.add(time)
        editor.putStringSet("alarmas", alarmas)
        editor.apply()

    }

    private fun getAMPM(hour: Int): String {
        return if (hour < 12) {
            "AM"
        } else {
            "PM"
        }
    }
    //----------------------------------------


}
