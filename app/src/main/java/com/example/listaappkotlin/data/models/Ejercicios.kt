package com.example.listaappkotlin.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Clase de datos que representa un solo ejercicio.
 * Esta clase es parcelable para poder ser pasada entre componentes de Android (p. ej., Activities, Fragments).
 *
 * @property nombre El nombre del ejercicio (p. ej., "Sentadillas").
 * @property series El número de series a realizar. Es nullable para ejercicios basados en tiempo.
 * @property repeticiones El número de repeticiones por serie. Es nullable para ejercicios basados en tiempo.
 * @property duracionMin La duración del ejercicio en minutos. Es nullable para ejercicios basados en repeticiones.
 */
@Parcelize
data class Ejercicios(
    val nombre: String,
    val series: Int? = null, // Permite que las series sean nulas (p. ej., para planchas).
    val repeticiones: Int? = null, // Permite que las repeticiones sean nulas (p. ej., para estiramientos).
    val duracionMin: Int? = null // Permite que la duración sea nula (p. ej., para ejercicios con repeticiones).
) : Parcelable