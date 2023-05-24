package com.example.recordatoriomedicamentos

import android.content.ClipData.Item
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.content.Intent

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_item_usuario -> {
                // Crea un Intent para iniciar la UsuarioActivity
                val intent = Intent(this, UsuarioActivity::class.java)
                startActivity(intent) // Inicia la actividad UsuarioActivity

                // Muestra un mensaje Toast para verificar que se ha hecho clic correctamente
                Toast.makeText(this, "Usuario", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_item_medicamentos -> Toast.makeText(this, "Medicamentos", Toast.LENGTH_SHORT).show()
            R.id.nav_item_doctor -> Toast.makeText(this, "Doctor", Toast.LENGTH_SHORT).show()
            R.id.nav_item_refill -> Toast.makeText(this, "Refill", Toast.LENGTH_SHORT).show()
            R.id.nav_item_reporte -> Toast.makeText(this, "Reporte", Toast.LENGTH_SHORT).show()
            R.id.nav_item_agregar -> Toast.makeText(this, "Agregar Usuario", Toast.LENGTH_SHORT).show()
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