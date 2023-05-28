package com.example.recordatoriomedicamentos

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText

class MedicationDialog(context: Context, private val listener: (String, Int) -> Unit) : Dialog(context) {

    private lateinit var editTextName: EditText
    private lateinit var editTextQuantity: EditText
    private lateinit var buttonAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_medication_dialog)

        editTextName = findViewById(R.id.editTextName)
        editTextQuantity = findViewById(R.id.editTextQuantity)
        buttonAdd = findViewById(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val quantity = editTextQuantity.text.toString().toIntOrNull()

            if (name.isNotBlank() && quantity != null && quantity > 0) {
                listener.invoke(name, quantity)
                dismiss()
            }
        }
    }
}
