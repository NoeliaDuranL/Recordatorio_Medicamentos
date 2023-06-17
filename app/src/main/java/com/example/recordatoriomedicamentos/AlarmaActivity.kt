package com.example.recordatoriomedicamentos
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.ToggleButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AlarmaActivity : AppCompatActivity() {
    private lateinit var alarmTimePicker: TimePicker
    private lateinit var pendingIntent: PendingIntent
    private lateinit var alarmManager: AlarmManager

    private lateinit var medicamentoSpinner: Spinner
    private lateinit var editTextFechaInicio: TextView
    private lateinit var editTextDias: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarma)

        alarmTimePicker = findViewById(R.id.timePicker)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        medicamentoSpinner = findViewById(R.id.medicamentoSpinner)
        editTextFechaInicio = findViewById(R.id.editTextFechaInicio)
        editTextDias = findViewById(R.id.editTextText11)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.medicamentos_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        medicamentoSpinner.adapter = adapter

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }
        editTextFechaInicio.setOnClickListener {
            DatePickerDialog(
                this, datePicker, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    fun onToggleClicked(view: View) {
        val isChecked = (view as ToggleButton).isChecked
        if (isChecked) {
            Toast.makeText(this, "Alarm ENCENDIDO", Toast.LENGTH_SHORT).show()

            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = alarmTimePicker.hour
            calendar[Calendar.MINUTE] = alarmTimePicker.minute
            calendar[Calendar.SECOND] = 0

            val intent = Intent(this, AlarmaReceiver::class.java)
            pendingIntent = PendingIntent.getBroadcast(
                this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT
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
        } else {
            alarmManager.cancel(pendingIntent)
            Toast.makeText(this, "Alarm APAGADO", Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        editTextFechaInicio.text = sdf.format(myCalendar.time)
    }
}
