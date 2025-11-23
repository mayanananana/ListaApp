package com.example.listaappkotlin.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rutina (val nombreRutina:String,
                   val descripcionRutina:String,
                   val photo:String,
                   val ejercicios: List<Ejercicios> ) : Parcelable