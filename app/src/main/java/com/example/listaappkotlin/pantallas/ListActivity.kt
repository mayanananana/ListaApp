package com.example.listaappkotlin.pantallas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listaappkotlin.adapter.RutinaAdapter
import com.example.listaappkotlin.data.RoutinesProvider
import com.example.listaappkotlin.data.models.Rutina
import com.example.listaappkotlin.databinding.ActivityListBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Actividad que muestra una lista de rutinas de ejercicios.
 * Permite al usuario ver las rutinas existentes, añadir nuevas rutinas
 * y eliminar las que ya no necesite.
 */
class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    // Lista mutable que contiene las rutinas, inicializada con datos de prueba.
    private var routinesMutableList: MutableList<Rutina> = RoutinesProvider.routines.toMutableList()
    private lateinit var adapter: RutinaAdapter
    private val llmanager = LinearLayoutManager(this)

    // Launcher para iniciar la actividad de añadir rutina y recibir el resultado.
    private lateinit var addRutinaLauncher: ActivityResultLauncher<Intent>

    /**
     * Método que se llama al crear la actividad.
     * Inicializa la vista, la barra de herramientas, el launcher para añadir rutinas
     * y el RecyclerView.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura la barra de herramientas con botón de retroceso.
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Ajusta el padding para las barras del sistema.
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa el launcher para el resultado de la actividad AddRutina.
        addRutinaLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                // Recibe la nueva rutina creada.
                val nuevaRutina: Rutina? = data?.getParcelableExtra("NUEVA_RUTINA")

                if (nuevaRutina != null) {
                    crearRutina(nuevaRutina)
                }
            }
        }

        // Configura el botón para añadir una nueva rutina.
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddRutina::class.java)
            addRutinaLauncher.launch(intent)
        }
        initRecyclerView()
    }

    /**
     * Añade una nueva rutina a la lista y notifica al adaptador.
     * Muestra un Snackbar con opción de deshacer.
     */
    private fun crearRutina(rutina: Rutina) {
        val position = routinesMutableList.size
        routinesMutableList.add(rutina)
        adapter.notifyItemInserted(position)
        llmanager.scrollToPositionWithOffset(position, 20) // Se desplaza a la nueva rutina.
        checkEmptyView() // Comprueba si la lista está vacía.

        // Muestra un mensaje de confirmación con opción de deshacer.
        Snackbar.make(binding.root, "Rutina añadida", Snackbar.LENGTH_LONG)
            .setAction("Deshacer") {
                routinesMutableList.removeAt(position)
                adapter.notifyItemRemoved(position)
                checkEmptyView()
            }
            .show()
    }

    /**
     * Inicializa el RecyclerView con el adaptador y el LayoutManager.
     */
    private fun initRecyclerView() {
        adapter = RutinaAdapter(
            routinesMutableList,
            onClickListener = { rutina -> onItemSelected(rutina) },
            onClickDelete = { position -> onDeletedItem(position) }
        )
        binding.recyclerworkout.layoutManager = llmanager
        binding.recyclerworkout.adapter = adapter
        checkEmptyView() // Comprueba si debe mostrar la vista de lista vacía.
    }

    /**
     * Elimina una rutina de la lista en la posición especificada.
     */
    private fun onDeletedItem(position: Int) {
        routinesMutableList.removeAt(position)
        adapter.notifyItemRemoved(position)
        checkEmptyView() // Comprueba si la lista está vacía tras la eliminación.
    }

    /**
     * Muestra un diálogo con los detalles de la rutina seleccionada.
     */
    private fun onItemSelected(rutina: Rutina) {
        val dialog = RutinaDetalleDialogFragment.newInstance(rutina)
        dialog.show(supportFragmentManager, "RutinaDetalleDialog")
    }

    /**
     * Comprueba si la lista de rutinas está vacía y muestra u oculta una vista informativa.
     */
    private fun checkEmptyView() {
        if (routinesMutableList.isEmpty()) {
            binding.emptyView.visibility = android.view.View.VISIBLE
            binding.recyclerworkout.visibility = android.view.View.GONE
        } else {
            binding.emptyView.visibility = android.view.View.GONE
            binding.recyclerworkout.visibility = android.view.View.VISIBLE
        }
    }

    /**
     * Maneja la selección de ítems en el menú de opciones, como el botón de retroceso.
     */
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed() // Vuelve a la pantalla anterior.
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
