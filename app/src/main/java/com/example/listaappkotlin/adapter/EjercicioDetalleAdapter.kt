package com.example.listaappkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listaappkotlin.R
import com.example.listaappkotlin.data.models.Ejercicios

/**
 * Adaptador para el RecyclerView que muestra los detalles de los ejercicios dentro del diálogo de detalles de la rutina.
 */
class EjercicioDetalleAdapter(private val ejercicios: List<Ejercicios>) :
    RecyclerView.Adapter<EjercicioDetalleAdapter.EjercicioDetalleViewHolder>() {

    /**
     * Crea y devuelve un ViewHolder para un ítem de la lista.
     * Infla el layout del ítem de detalle del ejercicio.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EjercicioDetalleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ejercicio_detalle, parent, false)
        return EjercicioDetalleViewHolder(view)
    }

    /**
     * Vincula los datos de un ejercicio en una posición específica con el ViewHolder.
     */
    override fun onBindViewHolder(holder: EjercicioDetalleViewHolder, position: Int) {
        val ejercicio = ejercicios[position]
        holder.bind(ejercicio)
    }

    /**
     * Devuelve el número total de ítems en la lista de ejercicios.
     */
    override fun getItemCount(): Int = ejercicios.size

    /**
     * ViewHolder que representa la vista de un solo ejercicio en la lista de detalles.
     */
    class EjercicioDetalleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.tvNombreEjercicioDetalle)
        private val detallesTextView: TextView = itemView.findViewById(R.id.tvDetallesEjercicioDetalle)

        /**
         * Vincula los datos de un objeto Ejercicios a las vistas correspondientes.
         * @param ejercicio El ejercicio a mostrar.
         */
        fun bind(ejercicio: Ejercicios) {
            nombreTextView.text = ejercicio.nombre

            // Construye la cadena de detalles y la muestra solo si hay detalles disponibles.
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
