package com.example.listaappkotlin.pantallas

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listaappkotlin.R
import com.example.listaappkotlin.data.models.Ejercicios
import com.example.listaappkotlin.data.models.Rutina
import com.example.listaappkotlin.databinding.ActivityAddRutinaBinding

class AddRutina : AppCompatActivity() {
    private lateinit var binding: ActivityAddRutinaBinding
    private val listaEjercicios = mutableListOf<Ejercicios>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRutinaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_rutina)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val contenedor = binding.listaEjercicios
        val addEjercicio= binding.btnMostrarFormulario
        addEjercicio.setOnClickListener {
            val vistaEjercicio = layoutInflater.inflate(R.layout.item_ejercicio, null)
            contenedor.addView(vistaEjercicio)
        }

        // Mostrar formulario de ejercicio solo una vez
        binding.btnMostrarFormulario.setOnClickListener {
            binding.formularioEjercicio.visibility = View.VISIBLE
            binding.btnMostrarFormulario.visibility = View.GONE
        }

        // Guardar un ejercicio
        binding.btnGuardarEjercicio.setOnClickListener {
            val nombre = binding.edtNombreEjercicio.text.toString().trim()
            if (nombre.isEmpty()) {
                Toast.makeText(this, "El nombre del ejercicio es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ejercicio = Ejercicios(
                nombre = nombre,
                series = binding.edtSeries.text.toString().toIntOrNull(),
                repeticiones = binding.edtRepeticiones.text.toString().toIntOrNull(),
                duracionMin = binding.edtDuracion.text.toString().toIntOrNull()
            )

            listaEjercicios.add(ejercicio)
            mostrarEjercicioEnLista(ejercicio.nombre)

            // Limpiar formulario
            binding.edtNombreEjercicio.text.clear()
            binding.edtSeries.text.clear()
            binding.edtRepeticiones.text.clear()
            binding.edtDuracion.text.clear()
        }

        // Guardar rutina completa
        binding.btnGuardarRutina.setOnClickListener {
            val nombreRutina = binding.edtNombreRutina.text.toString().trim()
            val descripcionRutina = binding.edtDescripcionRutina.text.toString().trim()

            if (nombreRutina.isEmpty()) {
                Toast.makeText(this, "Ingresa un nombre para la rutina", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (listaEjercicios.isEmpty()) {
                Toast.makeText(this, "Agrega al menos un ejercicio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rutina = Rutina(
                nombreRutina = nombreRutina,
                descripcionRutina = descripcionRutina,
                photo="https://images.unsplash.com/photo-1558611848-73f7eb4001a1",
                ejercicios = listaEjercicios
            )

            // Aquí podrías guardar la rutina en la base de datos o pasarla a otra Activity
            Toast.makeText(this, "Rutina '${rutina.nombreRutina}' creada con ${rutina.ejercicios.size} ejercicios", Toast.LENGTH_LONG).show()
            finish()
        }


    }
    private fun mostrarEjercicioEnLista(nombre: String) {
        val textView = TextView(this)
        textView.text = nombre
        textView.textSize = 16f
        textView.setPadding(8, 8, 8, 8)
        binding.listaEjercicios.addView(textView)
    }
}