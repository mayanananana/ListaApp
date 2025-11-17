package com.example.listaappkotlin.data.models

import java.io.Serializable

data class Rutina (val nombreRutina:String,
                   val descripcionRutina:String,
                   val photo:String,
                   val ejercicios: List<Ejercicios> ) : Serializable