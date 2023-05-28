package com.example.recordatoriomedicamentos

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DoctorActivity : AppCompatActivity() {

    private val doctorList = mutableListOf<Doctor>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)

        val doctorsContainer = findViewById<LinearLayout>(R.id.doctorsContainer)
        val imageViewDoctor = findViewById<ImageView>(R.id.imageViewDoctor)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextMedication = findViewById<EditText>(R.id.editTextMedication)
        val editTextDose = findViewById<EditText>(R.id.editTextDose)
        val buttonAddDoctor = findViewById<Button>(R.id.buttonAddDoctor)

        imageViewDoctor.setImageResource(R.drawable.doctor_image)

        buttonAddDoctor.setOnClickListener {
            val name = editTextName.text.toString()
            val medication = editTextMedication.text.toString()
            val dose = editTextDose.text.toString()
            val doctor = Doctor(name, medication, dose)
            doctorList.add(doctor)

            val textViewDoctor = TextView(this)
            textViewDoctor.text = "Doctor: $name\nMedicacion: $medication\nDosis: $dose"
            doctorsContainer.addView(textViewDoctor)

            editTextName.text.clear()
            editTextMedication.text.clear()
            editTextDose.text.clear()
        }
    }
}

data class Doctor(val name: String, val medication: String, val dose: String)
