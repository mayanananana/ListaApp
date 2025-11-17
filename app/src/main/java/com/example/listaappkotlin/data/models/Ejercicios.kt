package com.example.listaappkotlin.data.models

data class Ejercicios (
    val nombre: String,
    val series: Int? = null, //aqui dejo que puedan ser nulos
    val repeticiones: Int? = null,
    val duracionMin: Int? = null
    )