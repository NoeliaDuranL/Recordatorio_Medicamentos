package com.example.recordatoriomedicamentos
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class UsuarioActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val lastNameEditText = findViewById<EditText>(R.id.lastNameEditText)
        val ageEditText = findViewById<EditText>(R.id.ageEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        nameEditText.setText(sharedPreferences.getString("Nombre", ""))
        lastNameEditText.setText(sharedPreferences.getString("Apellido", ""))
        ageEditText.setText(sharedPreferences.getString("Edad", ""))

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val age = ageEditText.text.toString()

            val editor = sharedPreferences.edit()
            editor.putString("Nombre", name)
            editor.putString("Apellido", lastName)
            editor.putString("Edad", age)
            editor.apply()

            val intent = Intent(this, VerPerfilActivity::class.java)
            startActivity(intent)
        }
    }
}

