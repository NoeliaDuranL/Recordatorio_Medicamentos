package com.example.recordatoriomedicamentos

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class AlarmasProgramadasActivity : AppCompatActivity() {

    private lateinit var listViewAlarmas: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarmas_programadas)

        listViewAlarmas = findViewById(R.id.listViewAlarmas)

        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val alarmas: MutableSet<String>? = sharedPref.getStringSet("alarmas", emptySet())?.toMutableSet()

        // Crear un ArrayAdapter para mostrar las alarmas en el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, alarmas?.toList() ?: emptyList())

        // Establecer el ArrayAdapter en el ListView
        listViewAlarmas.adapter = adapter
    }
}