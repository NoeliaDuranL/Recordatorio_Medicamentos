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

        nameEditText.setText(sharedPreferences.getString("NAME", ""))
        lastNameEditText.setText(sharedPreferences.getString("LAST_NAME", ""))
        ageEditText.setText(sharedPreferences.getString("AGE", ""))

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val age = ageEditText.text.toString()

            val editor = sharedPreferences.edit()
            editor.putString("NAME", name)
            editor.putString("LAST_NAME", lastName)
            editor.putString("AGE", age)
            editor.apply()

            val intent = Intent(this, VerPerfilActivity::class.java)
            startActivity(intent)
        }
    }
}
