package com.example.listaappkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listaappkotlin.R
import com.example.listaappkotlin.data.models.Ejercicios

class EjercicioAdapter(private val ejercicios: List<Ejercicios>) :
    RecyclerView.Adapter<EjercicioAdapter.EjercicioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercicioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ejercicio_display, parent, false)
        return EjercicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: EjercicioViewHolder, position: Int) {
        val ejercicio = ejercicios[position]
        holder.bind(ejercicio)
    }

    override fun getItemCount(): Int = ejercicios.size

    class EjercicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.tvNombreEjercicio)
        private val detallesTextView: TextView = itemView.findViewById(R.id.tvDetallesEjercicio)

        fun bind(ejercicio: Ejercicios) {
            nombreTextView.text = ejercicio.nombre

            val detalles = mutableListOf<String>()
            ejercicio.series?.let { detalles.add("Series: $it") }
            ejercicio.repeticiones?.let { detalles.add("Reps: $it") }
            ejercicio.duracionMin?.let { detalles.add("Duración: ${it}min") }

            detallesTextView.text = detalles.joinToString(", ")
        }
    }
}
