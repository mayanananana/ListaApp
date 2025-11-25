package com.example.listaappkotlin.pantallas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listaappkotlin.adapter.EjercicioAdapter
import com.example.listaappkotlin.data.models.Ejercicios
import com.example.listaappkotlin.data.models.Rutina
import com.example.listaappkotlin.databinding.ActivityAddRutinaBinding

/**
 * Actividad para añadir una nueva rutina de ejercicios.
 * Permite al usuario nombrar una rutina, darle una descripción y añadir múltiples ejercicios
 * con detalles como series, repeticiones y duración.
 */
class AddRutina : AppCompatActivity() {
    private lateinit var binding: ActivityAddRutinaBinding

    // Lista mutable para almacenar los ejercicios que se van añadiendo a la rutina.
    private val listaDeEjercicios = mutableListOf<Ejercicios>()
    // Adaptador para el RecyclerView que mostrará la lista de ejercicios.
    private lateinit var ejerciciosAdapter: EjercicioAdapter

    /**
     * Método que se llama al crear la actividad.
     * Se encarga de inicializar la vista, la barra de herramientas, el RecyclerView y los botones.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRutinaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajusta el padding de la vista para evitar que el contenido se solape con las barras del sistema.
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupToolbar()
        setupRecyclerView()
        setupButtons()
    }

    /**
     * Configura la barra de herramientas (Toolbar) con un título y un botón de retroceso.
     */
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarAddRutina)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Añadir Nueva Rutina"
    }

    /**
     * Maneja la selección de ítems en el menú de opciones, como el botón de retroceso.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed() // Vuelve a la pantalla anterior.
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Configura el RecyclerView para mostrar la lista de ejercicios añadidos.
     */
    private fun setupRecyclerView() {
        ejerciciosAdapter = EjercicioAdapter(listaDeEjercicios)
        binding.rvEjercicios.apply {
            layoutManager = LinearLayoutManager(this@AddRutina)
            adapter = ejerciciosAdapter
        }
    }

    /**
     * Configura los listeners de los botones para añadir ejercicios y guardar la rutina.
     */
    private fun setupButtons() {
        binding.btnAnadirEjercicio.setOnClickListener {
            addEjercicioToList()
        }

        binding.btnGuardarRutina.setOnClickListener {
            saveRutina()
        }
    }

    /**
     * Añade un nuevo ejercicio a la lista a partir de los datos introducidos en los campos de texto.
     * Realiza validaciones para asegurar que el nombre del ejercicio no esté vacío.
     */
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
        ejerciciosAdapter.notifyItemInserted(listaDeEjercicios.size - 1) // Notifica al adaptador del nuevo ítem.

        // Limpia los campos de texto después de añadir el ejercicio.
        binding.edtNombreEjercicio.text?.clear()
        binding.edtSeries.text?.clear()
        binding.edtRepeticiones.text?.clear()
        binding.edtDuracion.text?.clear()
    }

    /**
     * Guarda la rutina completa.
     * Valida que el nombre de la rutina y la lista de ejercicios no estén vacíos.
     * Crea un objeto Rutina y lo envía de vuelta a la actividad anterior.
     */
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
        val photoUrl = "https://images.unsplash.com/photo-1558611848-73f7eb4001a1" // URL de foto por defecto.

        val rutinaCompleta = Rutina(
            nombreRutina = nombreRutina,
            descripcionRutina = descripcion,
            photo = photoUrl,
            ejercicios = listaDeEjercicios
        )

        // Prepara el resultado para enviarlo de vuelta a la actividad que inició esta.
        val resultIntent = Intent()
        resultIntent.putExtra("NUEVA_RUTINA", rutinaCompleta)
        setResult(Activity.RESULT_OK, resultIntent)
        finish() // Cierra la actividad.
    }
}