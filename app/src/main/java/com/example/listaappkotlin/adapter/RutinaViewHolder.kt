package com.example.listaappkotlin.adapter

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.listaappkotlin.data.models.Rutina
import com.example.listaappkotlin.databinding.ItemRutinaBinding

class RutinaViewHolder (view: View) : RecyclerView.ViewHolder(view){

    val binding= ItemRutinaBinding.bind(view)
   /* val rutinaTitle = view.findViewById<TextView>(R.id.titleTV)
    val descripcion = view.findViewById<TextView>(R.id.descripcionTV);
    val photo = view.findViewById<ImageView>(R.id.itemIcon)*/


    fun render(rutinaModel: Rutina, onClickListener: (Rutina) -> Unit, onClickDelete: (Int) -> Unit){
       binding.titleTV.text = rutinaModel.nombreRutina;
        binding.descripcionTV.text= rutinaModel.descripcionRutina;
        Glide.with(binding.itemIcon.context).load(rutinaModel.photo).into(binding.itemIcon)

        binding.titleTV.setOnClickListener {
            AlertDialog.Builder(binding.root.context)
                .setTitle(binding.titleTV.text)
                .setMessage(binding.descripcionTV.text)
                .setPositiveButton("OK", null)
                .show()
        }

        binding.buttonDelete.setOnClickListener { onClickDelete(adapterPosition) }
    }

}