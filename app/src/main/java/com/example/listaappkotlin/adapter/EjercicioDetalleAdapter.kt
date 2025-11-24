package com.example.listaappkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listaappkotlin.R
import com.example.listaappkotlin.data.models.Ejercicios

class EjercicioDetalleAdapter(private val ejercicios: List<Ejercicios>) :
    RecyclerView.Adapter<EjercicioDetalleAdapter.EjercicioDetalleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercicioDetalleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ejercicio_detalle, parent, false)
        return EjercicioDetalleViewHolder(view)
    }

    override fun onBindViewHolder(holder: EjercicioDetalleViewHolder, position: Int) {
        val ejercicio = ejercicios[position]
        holder.bind(ejercicio)
    }

    override fun getItemCount(): Int = ejercicios.size

    class EjercicioDetalleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.tvNombreEjercicioDetalle)
        private val detallesTextView: TextView = itemView.findViewById(R.id.tvDetallesEjercicioDetalle)

        fun bind(ejercicio: Ejercicios) {
            nombreTextView.text = ejercicio.nombre

            val detalles = mutableListOf<String>()
            ejercicio.series?.let { detalles.add("Series: $it") }
            ejercicio.repeticiones?.let { detalles.add("Reps: $it") }
            ejercicio.duracionMin?.let { detalles.add("Duración: ${it}min") }

            if (detalles.isNotEmpty()) {
                detallesTextView.text = detalles.joinToString(", ")
                detallesTextView.visibility = View.VISIBLE
            } else {
                detallesTextView.visibility = View.GONE
            }
        }
    }
}
