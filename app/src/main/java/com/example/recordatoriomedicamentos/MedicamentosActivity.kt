package com.example.recordatoriomedicamentos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MedicamentosActivity : AppCompatActivity() {
    private lateinit var editTextMedicinaNombre: EditText  // Campo de texto para ingresar el nombre del medicamento
    private lateinit var editTextCantidad: EditText  // Campo de texto para ingresar la cantidad del medicamento
    private lateinit var buttonGuardar: Button  // Botón para guardar el medicamento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicamentos)

        editTextMedicinaNombre = findViewById(R.id.editTextMedicinaNombre)  // Obtener referencia del campo de texto del nombre del medicamento
        editTextCantidad = findViewById(R.id.editTextCantidad)  // Obtener referencia del campo de texto de la cantidad del medicamento
        buttonGuardar = findViewById(R.id.buttonGuardar)  // Obtener referencia del botón de guardar

        buttonGuardar.setOnClickListener {
            val medicinaNombre = editTextMedicinaNombre.text.toString()  // Obtener el texto ingresado en el campo de texto del nombre del medicamento
            val cantidad = editTextCantidad.text.toString()  // Obtener el texto ingresado en el campo de texto de la cantidad del medicamento

            guardarMedicamento(medicinaNombre, cantidad)  // Llamar a la función para guardar el medicamento
        }
    }

    private fun guardarMedicamento(medicinaNombre: String, cantidad: String) {
        // Aquí puedes implementar la lógica para guardar los datos del medicamento en la base de datos o realizar cualquier otra acción requerida.

        // Ejemplo: Mostrar un mensaje de éxito después de guardar los datos.
        Toast.makeText(this, "Medicamento guardado: $medicinaNombre, Cantidad: $cantidad", Toast.LENGTH_SHORT).show()
    }
}
