package com.example.listaappkotlin.data

import com.example.listaappkotlin.data.models.Ejercicios
import com.example.listaappkotlin.data.models.Rutina

/**
 * Clase que provee una lista de rutinas predefinidas.
 * Utiliza un objeto compañero para que la lista de rutinas sea accesible de forma estática,
 * sirviendo como una fuente de datos de ejemplo para la aplicación.
 */
class RoutinesProvider {
    companion object {
        /**
         * Lista de rutinas de ejemplo.
         * Cada rutina tiene un nombre, una descripción, una URL de imagen y una lista de ejercicios.
         */
        val routines = listOf(
            Rutina(
                "Full Body Principiantes",
                "Rutina general de cuerpo completo para empezar.",
                "https://images.unsplash.com/photo-1558611848-73f7eb4001a1",
                ejercicios = listOf(
                    Ejercicios("Sentadillas", series = 3, repeticiones = 12),
                    Ejercicios("Flexiones", series = 3, repeticiones = 10),
                    Ejercicios("Plancha", duracionMin = 2),
                    Ejercicios("Zancadas", series = 3, repeticiones = 12)
                )
            ),
            Rutina(
                "Pecho y Tríceps",
                "Enfocada en fuerza del tren superior.",
                "https://images.unsplash.com/photo-1517964108010-58d7c6e0a3e8",
                ejercicios = listOf(
                    Ejercicios("Flexiones clásicas", series = 4, repeticiones = 12),
                    Ejercicios("Fondos en banco", series = 3, repeticiones = 10),
                    Ejercicios("Flexiones diamante", series = 3, repeticiones = 8),
                    Ejercicios("Estiramiento final", duracionMin = 2)
                )
            ),
            Rutina(
                "Piernas y Glúteos",
                "Tonificación y fuerza para piernas.",
                "https://images.unsplash.com/photo-1599058917212-d750089bc07e",
                ejercicios = listOf(
                    Ejercicios("Sentadilla", series = 4, repeticiones = 15),
                    Ejercicios("Puente de glúteos", series = 4, repeticiones = 20),
                    Ejercicios("Zancadas", series = 3, repeticiones = 12),
                    Ejercicios("Elevaciones laterales", duracionMin = 3)
                )
            ),
            Rutina(
                "Abdomen Completo",
                "Rutina rápida para fortalecer el core.",
                "https://images.unsplash.com/photo-1594737625785-c6c19c1d7a9a",
                ejercicios = listOf(
                    Ejercicios("Crunches", series = 3, repeticiones = 20),
                    Ejercicios("Elevaciones de piernas", series = 3, repeticiones = 12),
                    Ejercicios("Plancha", duracionMin = 2),
                    Ejercicios("Mountain climbers", series = 3, repeticiones = 20)
                )
            ),
            Rutina(
                "Espalda y Bíceps",
                "Fortalecimiento del tren superior.",
                "https://images.unsplash.com/photo-1579758629938-03607ccdbaba",
                ejercicios = listOf(
                    Ejercicios("Remo con banda", series = 4, repeticiones = 12),
                    Ejercicios("Curl bíceps", series = 3, repeticiones = 15),
                    Ejercicios("Superman", duracionMin = 2),
                    Ejercicios("Remo inclinado", series = 3, repeticiones = 10)
                )
            ),
            Rutina(
                "Cardio HIIT",
                "Alta intensidad en poco tiempo.",
                "https://images.unsplash.com/photo-1597076537201-985c7f0a4e44",
                ejercicios = listOf(
                    Ejercicios("Jumping jacks", series = 3, repeticiones = 30),
                    Ejercicios("Burpees", series = 3, repeticiones = 12),
                    Ejercicios("Sprint en sitio", duracionMin = 1),
                    Ejercicios("Plancha dinámica", duracionMin = 1)
                )
            ),
            Rutina(
                "Yoga Suave",
                "Estiramientos y respiración.",
                "https://images.unsplash.com/photo-1552196563-55cd4e45efb3",
                ejercicios = listOf(
                    Ejercicios("Saludo al sol", duracionMin = 3),
                    Ejercicios("Perro boca abajo", duracionMin = 2),
                    Ejercicios("Guerrero I", duracionMin = 2),
                    Ejercicios("Postura del niño", duracionMin = 2)
                )
            ),
            Rutina(
                "Movilidad Matutina",
                "Para comenzar el día con energía.",
                "https://images.unsplash.com/photo-1599058917863-61a4ab05a796",
                ejercicios = listOf(
                    Ejercicios("Movilidad de hombros", duracionMin = 2),
                    Ejercicios("Cadera y piernas", duracionMin = 3),
                    Ejercicios("Estiramiento de espalda", duracionMin = 2),
                    Ejercicios("Respiración guiada", duracionMin = 1)
                )
            ),
            Rutina(
                "Fuerza con Mancuernas",
                "Rutina usando peso libre.",
                "https://images.unsplash.com/photo-1599058917760-3a3f04dc3d6a",
                ejercicios = listOf(
                    Ejercicios("Press militar", series = 3, repeticiones = 12),
                    Ejercicios("Curl de bíceps", series = 3, repeticiones = 15),
                    Ejercicios("Sentadilla con mancuernas", series = 3, repeticiones = 12),
                    Ejercicios("Peso muerto rumano", series = 3, repeticiones = 10)
                )
            ),
            Rutina(
                "Estiramientos Full Body",
                "Ideal para finalizar el día.",
                "https://images.unsplash.com/photo-1599058917377-7b94f2e8def5",
                ejercicios = listOf(
                    Ejercicios("Estiramiento de cuello", duracionMin = 2),
                    Ejercicios("Estiramiento de brazos", duracionMin = 2),
                    Ejercicios("Estiramiento de piernas", duracionMin = 3),
                    Ejercicios("Relajación final", duracionMin = 2)
                )
            )
        )
    }
}