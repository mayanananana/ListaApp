package com.example.listaappkotlin.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.listaappkotlin.data.models.Rutina
import com.example.listaappkotlin.databinding.ItemRutinaBinding

/**
 * ViewHolder para un ítem de rutina en el RecyclerView.
 * Se encarga de vincular los datos de una rutina a la vista `item_rutina`.
 *
 * @param view La vista del ítem, que se utiliza para inicializar el ViewHolder.
 */
class RutinaViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // Vinculación de vistas generada para el layout item_rutina.
    val binding = ItemRutinaBinding.bind(view)

    /**
     * Renderiza los datos de un objeto `Rutina` en las vistas del ViewHolder.
     * También configura los listeners para los eventos de clic en el ítem y en el botón de eliminar.
     *
     * @param rutinaModel El modelo de datos de la rutina a mostrar.
     * @param onClickListener El listener para el clic en el ítem completo.
     * @param onClickDelete El listener para el clic en el botón de eliminar.
     */
    fun render(rutinaModel: Rutina, onClickListener: (Rutina) -> Unit, onClickDelete: (Int) -> Unit) {
        // Asigna el nombre y la descripción de la rutina a los TextViews correspondientes.
        binding.titleTV.text = rutinaModel.nombreRutina
        binding.descripcionTV.text = rutinaModel.descripcionRutina
        // Carga la imagen de la rutina usando Glide.
        Glide.with(binding.itemIcon.context).load(rutinaModel.photo).into(binding.itemIcon)

        // Configura el listener para cuando se hace clic en toda la vista del ítem.
        itemView.setOnClickListener { onClickListener(rutinaModel) }

        // Configura el listener para el botón de eliminar.
        binding.buttonDelete.setOnClickListener { onClickDelete(adapterPosition) }
    }
}