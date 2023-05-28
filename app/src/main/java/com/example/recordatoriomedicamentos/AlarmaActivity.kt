package com.example.recordatoriomedicamentos

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AlarmaActivity : AppCompatActivity() {
    private lateinit var tvDate: TextView
    private lateinit var btnDatePicker: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarma)

        tvDate = findViewById(R.id.tvDate)
        btnDatePicker = findViewById(R.id.btnDatePicker)

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }
        btnDatePicker.setOnClickListener{
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        val button9 = findViewById<Button>(R.id.button9)
        button9.setOnClickListener {
            val intent = Intent(this, HorariosActivity::class.java)
            startActivity(intent)
        }
    }
    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDate.text = sdf.format(myCalendar.time)
    }

}
