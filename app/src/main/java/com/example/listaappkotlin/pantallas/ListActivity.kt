package com.example.listaappkotlin.pantallas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listaappkotlin.R
import com.example.listaappkotlin.adapter.RutinaAdapter
import com.example.listaappkotlin.data.RoutinesProvider
import com.example.listaappkotlin.data.models.Ejercicios
import com.example.listaappkotlin.data.models.Rutina
import com.example.listaappkotlin.databinding.ActivityListBinding
import com.example.listaappkotlin.databinding.ActivityMainBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private var routinesMutableList: MutableList<Rutina> = RoutinesProvider.routines.toMutableList()
    private lateinit var adapter: RutinaAdapter
    private val llmanager= LinearLayoutManager(this)
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

        binding.btnAdd.setOnClickListener{
            val intent = Intent(this, AddRutina::class.java)
            startActivity(intent)
        }
        initRecyclerView()
    }

//    private fun crearRutina(){
//        val rutina = Rutina("Full Body", "Trabajo completo", "https://images.unsplash.com/photo-1558611848-73f7eb4001a1", ejercicios = listOf(
//            Ejercicios("Sentadillas", series = 3, repeticiones = 12) ))
//        routinesMutableList.add(3, rutina)
//        adapter.notifyItemInserted(3)
//        llmanager.scrollToPositionWithOffset(3, 20)
//
//
//        /*
//        * En caso de querer que se añadan al final
//        *         routinesMutableList.add(rutina)quito el index
//        *          adapter.notifyItemInserted(routinesMutableList.size) (ya que se añadiria al final
//        * */
//    }

    private fun initRecyclerView() {
        adapter= RutinaAdapter(
            routinesMutableList,
            onClickListener = {rutina -> onItemSelected(rutina)},
            onClickDelete = {position -> onDeletedItem(position)}
        )
        binding.recyclerworkout.layoutManager = llmanager
        binding.recyclerworkout.adapter = adapter

    }

    private fun onDeletedItem(position:Int){
        routinesMutableList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun onItemSelected(rutina: Rutina){
        Toast.makeText(this, rutina.nombreRutina, Toast.LENGTH_SHORT).show()
    }
}
