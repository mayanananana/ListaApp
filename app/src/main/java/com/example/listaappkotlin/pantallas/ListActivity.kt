package com.example.listaappkotlin.pantallas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listaappkotlin.R
import com.example.listaappkotlin.adapter.RutinaAdapter
import com.example.listaappkotlin.data.RoutinesProvider
import com.example.listaappkotlin.databinding.ActivityListBinding
import com.example.listaappkotlin.databinding.ActivityMainBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerworkout.layoutManager = LinearLayoutManager(this)
        binding.recyclerworkout.adapter = RutinaAdapter(RoutinesProvider.routines)
    }
}
