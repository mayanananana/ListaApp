package com.example.listaappkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listaappkotlin.R
import com.example.listaappkotlin.data.models.Rutina

/**
 * Adaptador para el RecyclerView que muestra la lista principal de rutinas.
 *
 * @param listaRutinas La lista de rutinas a mostrar.
 * @param onClickListener Lambda que se ejecuta al hacer clic en un ítem de la rutina.
 * @param onClickDelete Lambda que se ejecuta al hacer clic en el botón de eliminar de un ítem.
 */
class RutinaAdapter(private val listaRutinas:List<Rutina>,
                    private val onClickListener: (Rutina) -> Unit,
                    private val onClickDelete:(Int) -> Unit) : RecyclerView.Adapter<RutinaViewHolder>() {

    /**
     * Crea un nuevo ViewHolder inflando el layout para un ítem de rutina.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);
        return RutinaViewHolder(layoutInflater.inflate(R.layout.item_rutina, parent, false));
    }

    /**
     * Vincula los datos de una rutina en una posición específica con el ViewHolder.
     * Pasa los listeners de clic al método `render` del ViewHolder.
     */
    override fun onBindViewHolder(holder: RutinaViewHolder, position: Int
    ) {
        val item= listaRutinas[position]
        holder.render(item, onClickListener, onClickDelete)
    }

    /**
     * Devuelve el número total de rutinas en la lista.
     */
    override fun getItemCount(): Int =
         listaRutinas.size;
}