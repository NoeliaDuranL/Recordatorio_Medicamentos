package com.example.recordatoriomedicamentos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RefillActivity : AppCompatActivity() {

    private val medicationList = mutableListOf<Medication>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refill)

        val buttonAdd= findViewById<Button>(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            showAddMedicationDialog()
        }
    }

    private fun showAddMedicationDialog() {
        val dialog = MedicationDialog(this) { name, quantity ->
            val medication = Medication(name, quantity)
            medicationList.add(medication)
            updateMedicationList()
        }
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun updateMedicationList() {
        val container = findViewById<LinearLayout>(R.id.containerMedicationList)
        container.removeAllViews()

        for (medication in medicationList) {
            val textView = TextView(this)
            textView.text = "Medicamento: ${medication.name}, Cantidad: ${medication.quantity}"
            val layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 0, 0, 16)
            textView.layoutParams = layoutParams
            textView.gravity = Gravity.CENTER_VERTICAL
            container.addView(textView)
        }
    }
}

data class Medication(val name: String, var quantity: Int)
