package com.example.recordatoriomedicamentos
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class VerPerfilActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_perfil)

        val textView = findViewById<TextView>(R.id.textView)
        val backButton = findViewById<Button>(R.id.backButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        val name = sharedPreferences.getString("NAME", "")
        val lastName = sharedPreferences.getString("LAST_NAME", "")
        val age = sharedPreferences.getString("AGE", "")

        val message = "Name: $name\nLast Name: $lastName\nAge: $age"
        textView.text = message.orEmpty()

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
