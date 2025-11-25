package com.example.listaappkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listaappkotlin.R
import com.example.listaappkotlin.data.models.Ejercicios

/**
 * Adaptador para el RecyclerView que muestra una lista de ejercicios.
 * Este adaptador se utiliza en la pantalla de añadir rutina para mostrar los ejercicios que se van agregando.
 */
class EjercicioAdapter(private val ejercicios: List<Ejercicios>) :
    RecyclerView.Adapter<EjercicioAdapter.EjercicioViewHolder>() {

    /**
     * Crea y devuelve un ViewHolder para un ítem de la lista.
     * Infla el layout del ítem.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercicioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ejercicio_display, parent, false)
        return EjercicioViewHolder(view)
    }

    /**
     * Vincula los datos de un ejercicio en una posición específica con el ViewHolder.
     */
    override fun onBindViewHolder(holder: EjercicioViewHolder, position: Int) {
        val ejercicio = ejercicios[position]
        holder.bind(ejercicio)
    }

    /**
     * Devuelve el número total de ítems en la lista de ejercicios.
     */
    override fun getItemCount(): Int = ejercicios.size

    /**
     * ViewHolder que representa la vista de un solo ejercicio en la lista.
     * Se encarga de vincular los datos del ejercicio con los elementos de la vista.
     */
    class EjercicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.tvNombreEjercicio)
        private val detallesTextView: TextView = itemView.findViewById(R.id.tvDetallesEjercicio)

        /**
         * Vincula los datos de un objeto Ejercicios a las vistas correspondientes.
         * @param ejercicio El ejercicio a mostrar.
         */
        fun bind(ejercicio: Ejercicios) {
            nombreTextView.text = ejercicio.nombre

            // Construye una cadena con los detalles del ejercicio (series, repeticiones, duración).
            val detalles = mutableListOf<String>()
            ejercicio.series?.let { detalles.add("Series: $it") }
            ejercicio.repeticiones?.let { detalles.add("Reps: $it") }
            ejercicio.duracionMin?.let { detalles.add("Duración: ${it}min") }

            detallesTextView.text = detalles.joinToString(", ")
        }
    }
}
