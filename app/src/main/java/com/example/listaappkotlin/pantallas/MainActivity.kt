package com.example.listaappkotlin.pantallas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listaappkotlin.R

/**
 * Actividad principal de la aplicación.
 * Esta es la primera pantalla que se muestra al usuario. Su propósito es servir como
 * pantalla de bienvenida y dirigir al usuario a la lista de rutinas.
 */
class MainActivity : AppCompatActivity() {
    /**
     * Método que se llama al crear la actividad.
     * Configura la vista inicial, ajusta los márgenes para las barras del sistema
     * y establece un listener en el botón de inicio para navegar a `ListActivity`.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el modo de pantalla completa.
        setContentView(R.layout.activity_main)

        // Ajusta el padding de la vista principal para evitar que el contenido se solape con las barras del sistema.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val startPage = findViewById<Button>(R.id.buttonStart)
        // Configura el OnClickListener para el botón de inicio.
        startPage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // Crea un Intent para navegar a ListActivity.
                val listPage: Intent = Intent(this@MainActivity, ListActivity::class.java)
                startActivity(listPage) // Inicia la actividad.
            }
        })
    }
}