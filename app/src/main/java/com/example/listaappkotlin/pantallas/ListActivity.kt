package com.example.listaappkotlin.pantallas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private var routinesMutableList: MutableList<Rutina> = RoutinesProvider.routines.toMutableList()
    private lateinit var adapter: RutinaAdapter
    private val llmanager = LinearLayoutManager(this)

    private lateinit var addRutinaLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addRutinaLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                // Using getParcelableExtra with the modern signature
                val nuevaRutina: Rutina? = data?.getParcelableExtra("NUEVA_RUTINA")
                
                if (nuevaRutina != null) {
                    crearRutina(nuevaRutina)
                }
            }
        }

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddRutina::class.java)
            addRutinaLauncher.launch(intent)
        }
        initRecyclerView()
    }

    private fun crearRutina(rutina: Rutina) {
        routinesMutableList.add(rutina)
        adapter.notifyItemInserted(routinesMutableList.size - 1)
        llmanager.scrollToPositionWithOffset(routinesMutableList.size - 1, 20)
        checkEmptyView()
    }

    private fun initRecyclerView() {
        adapter = RutinaAdapter(
            routinesMutableList,
            onClickListener = { rutina -> onItemSelected(rutina) },
            onClickDelete = { position -> onDeletedItem(position) }
        )
        binding.recyclerworkout.layoutManager = llmanager
        binding.recyclerworkout.adapter = adapter
        checkEmptyView()
    }

    private fun onDeletedItem(position: Int) {
        routinesMutableList.removeAt(position)
        adapter.notifyItemRemoved(position)
        checkEmptyView()
    }

    private fun onItemSelected(rutina: Rutina) {
        Toast.makeText(this, rutina.nombreRutina, Toast.LENGTH_SHORT).show()
    }

    private fun checkEmptyView() {
        if (routinesMutableList.isEmpty()) {
            binding.emptyView.visibility = android.view.View.VISIBLE
            binding.recyclerworkout.visibility = android.view.View.GONE
        } else {
            binding.emptyView.visibility = android.view.View.GONE
            binding.recyclerworkout.visibility = android.view.View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
