package com.example.listaappkotlin.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Clase de datos que representa una rutina de ejercicios completa.
 * Esta clase es parcelable para poder ser pasada entre componentes de Android, como de una Activity a otra.
 *
 * @property nombreRutina El nombre de la rutina (p. ej., "Full Body Principiantes").
 * @property descripcionRutina Una breve descripción de la rutina.
 * @property photo La URL de la imagen que representa la rutina.
 * @property ejercicios Una lista de objetos [Ejercicios] que componen la rutina.
 */
@Parcelize
data class Rutina(
    val nombreRutina: String,
    val descripcionRutina: String,
    val photo: String,
    val ejercicios: List<Ejercicios>
) : Parcelable