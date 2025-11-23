package com.example.listaappkotlin.pantallas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listaappkotlin.adapter.EjercicioAdapter
import com.example.listaappkotlin.data.models.Ejercicios
import com.example.listaappkotlin.data.models.Rutina
import com.example.listaappkotlin.databinding.ActivityAddRutinaBinding

class AddRutina : AppCompatActivity() {
    private lateinit var binding: ActivityAddRutinaBinding
    
    private val listaDeEjercicios = mutableListOf<Ejercicios>()
    private lateinit var ejerciciosAdapter: EjercicioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRutinaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupButtons()
    }

    private fun setupRecyclerView() {
        ejerciciosAdapter = EjercicioAdapter(listaDeEjercicios)
        binding.rvEjercicios.apply {
            layoutManager = LinearLayoutManager(this@AddRutina)
            adapter = ejerciciosAdapter
        }
    }

    private fun setupButtons() {
        binding.btnAnadirEjercicio.setOnClickListener {
            addEjercicioToList()
        }

        binding.btnGuardarRutina.setOnClickListener {
            saveRutina()
        }
    }

    private fun addEjercicioToList() {
        val nombre = binding.edtNombreEjercicio.text.toString().trim()
        if (nombre.isEmpty()) {
            Toast.makeText(this, "El nombre del ejercicio no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val series = binding.edtSeries.text.toString().toIntOrNull()
        val repeticiones = binding.edtRepeticiones.text.toString().toIntOrNull()
        val duracion = binding.edtDuracion.text.toString().toIntOrNull()

        val nuevoEjercicio = Ejercicios(
            nombre = nombre,
            series = series,
            repeticiones = repeticiones,
            duracionMin = duracion
        )

        listaDeEjercicios.add(nuevoEjercicio)
        ejerciciosAdapter.notifyItemInserted(listaDeEjercicios.size - 1)

        // Clear input fields
        binding.edtNombreEjercicio.text?.clear()
        binding.edtSeries.text?.clear()
        binding.edtRepeticiones.text?.clear()
        binding.edtDuracion.text?.clear()
    }

    private fun saveRutina() {
        val nombreRutina = binding.edtNombreRutina.text.toString().trim()
        if (nombreRutina.isEmpty()) {
            Toast.makeText(this, "El nombre de la rutina no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        if (listaDeEjercicios.isEmpty()) {
            Toast.makeText(this, "Debes añadir al menos un ejercicio", Toast.LENGTH_SHORT).show()
            return
        }
        
        val descripcion = binding.edtDescripcionRutina.text.toString().trim()
        val photoUrl = binding.edtPhotoUrl.text.toString().trim()

        val rutinaCompleta = Rutina(
            nombreRutina = nombreRutina,
            descripcionRutina = descripcion,
            photo = photoUrl.ifEmpty { "https://via.placeholder.com/150" }, // Default image
            ejercicios = listaDeEjercicios
        )

        val resultIntent = Intent()
        resultIntent.putExtra("NUEVA_RUTINA", rutinaCompleta)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}