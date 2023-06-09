package com.example.recordatoriomedicamentos

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle



    val db = Firebase.firestore
    val TAG = "Datos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        toolbar.title = "Recordatorios Médicos💊"
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

            val navigationView: NavigationView = findViewById(R.id.nav_view)
            navigationView.setNavigationItemSelectedListener(this)

        val VerAlarmas: Button = findViewById(R.id.buttonVerAlarmas)
        VerAlarmas.setOnClickListener {
            val intent = Intent(this, AlarmasProgramadasActivity::class.java)
            startActivity(intent)
        }

        val fabButton = findViewById<FloatingActionButton>(R.id.app_bar_button)
        fabButton.setOnClickListener {
            Toast.makeText(this, "Agregar alarma", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AlarmaActivity::class.java)
            startActivity(intent)
        }

    }

    fun getData() {

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<ItemsViewModel>()

        //Log.d(TAG,"Recuperando")
        db.collection("Medicamentos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    data.add(ItemsViewModel("Medicamentos",document.data.get("nombre").toString()))
                    Log.d(TAG, "${document.id} => ${document.data}")

                }

                val adapter = CustomAdapter(data)
                recyclerview.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }




    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_usuario -> {
                val intent = Intent(this, VerPerfilActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Usuario", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_item_medicamentos -> {
                Toast.makeText(this, "Medicamentos", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MedicamentosActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_item_doctor -> {
                Toast.makeText(this, "Doctores", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, DoctorActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_item_alarma-> {
                Toast.makeText(this, "Alarma", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AlarmaActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_item_agregar -> {
                Toast.makeText(this, "Agregar Usuario", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UsuarioActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_item_ver_bd -> {
                Toast.makeText(this, "Ver Base de Datos", Toast.LENGTH_SHORT).show()
                getData()
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?){
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}