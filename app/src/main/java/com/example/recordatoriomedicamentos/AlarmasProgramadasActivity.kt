package com.example.recordatoriomedicamentos

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class AlarmasProgramadasActivity : AppCompatActivity() {

    private lateinit var listViewAlarmas: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var alarmas: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarmas_programadas)

        listViewAlarmas = findViewById(R.id.listViewAlarmas)

        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        alarmas = sharedPref.getStringSet("alarmas", emptySet())?.toMutableList() as ArrayList<String>

        if (alarmas.isNullOrEmpty()) {
            // Si la lista de alarmas está vacía, mostrar mensaje de que no hay alarmas programadas
            val emptyMessage = "Aún no se han programado alarmas"
            val emptyList = listOf(emptyMessage)
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, emptyList)
        } else {
            // Si la lista de alarmas no está vacía, mostrar las alarmas en el ListView
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, alarmas)
        }

        listViewAlarmas.adapter = adapter

        listViewAlarmas.setOnItemClickListener { parent, view, position, id ->
            val selectedAlarm = adapter.getItem(position) as String
            showDeleteConfirmationDialog(selectedAlarm, position)
        }
    }

    private fun showDeleteConfirmationDialog(alarm: String, position: Int) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Eliminar alarma")
        dialogBuilder.setMessage("¿Estás seguro de que deseas eliminar esta alarma?")
        dialogBuilder.setPositiveButton("Eliminar") { dialog, which ->
            deleteAlarm(alarm, position)
        }
        dialogBuilder.setNegativeButton("Cancelar", null)
        dialogBuilder.create().show()
    }

    private fun deleteAlarm(alarm: String, position: Int) {
        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        alarmas.removeAt(position)
        editor.putStringSet("alarmas", alarmas.toSet())
        editor.apply()

        adapter.notifyDataSetChanged()

        if (alarmas.isEmpty()) {
            // Si se eliminó la única alarma, mostrar mensaje de que no hay alarmas programadas
            val emptyMessage = "Aún no se han programado alarmas"
            val emptyList = listOf(emptyMessage)
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, emptyList)
            listViewAlarmas.adapter = adapter
        }
    }
}
