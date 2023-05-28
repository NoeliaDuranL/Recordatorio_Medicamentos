package com.example.recordatoriomedicamentos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class VerPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_perfil)

        val textView = findViewById<TextView>(R.id.textView)
        val receiverNameMessage: String? = intent.getStringExtra("EXTRA_MESSAGE")
        val receiverLastName: String? = intent.getStringExtra("EXTRA_LAST_NAME")
        val receiverAge: String? = intent.getStringExtra("EXTRA_AGE")

        val message = "Name: $receiverNameMessage\nLast Name: $receiverLastName\nAge: $receiverAge"
        textView.text = message.orEmpty()

    }
}
