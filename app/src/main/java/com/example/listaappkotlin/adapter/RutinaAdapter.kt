package com.example.listaappkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listaappkotlin.R
import com.example.listaappkotlin.data.models.Rutina

class RutinaAdapter(private val listaRutinas:List<Rutina>,
                    private val onClickListener: (Rutina) -> Unit,
                    private val onClickDelete:(Int) -> Unit) : RecyclerView.Adapter<RutinaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);
        return RutinaViewHolder(layoutInflater.inflate(R.layout.item_rutina, parent, false));
    }

    override fun onBindViewHolder(holder: RutinaViewHolder, position: Int
    ) {
        val item= listaRutinas[position]
        holder.render(item, onClickListener, onClickDelete)
    }

    override fun getItemCount(): Int =
         listaRutinas.size;



}