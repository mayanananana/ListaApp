# ListaAppKotlin

## Descripción

ListaAppKotlin es una aplicación para Android desarrollada en Kotlin que permite a los usuarios gestionar sus rutinas de ejercicios. Con esta app, puedes crear, ver y eliminar rutinas personalizadas. Cada rutina consiste en un nombre, una descripción y una lista de ejercicios, donde cada ejercicio tiene su nombre, número de series, repeticiones y duración.

## Características

- **Visualización de Rutinas**: Muestra una lista de rutinas de ejercicio.
- **Añadir Nuevas Rutinas**: Permite a los usuarios añadir nuevas rutinas a la lista.
- **Añadir Ejercicios a Rutinas**: Dentro de una rutina, se pueden añadir múltiples ejercicios con detalles como series, repeticiones y duración.
- **Eliminar Rutinas**: Desliza para eliminar una rutina de la lista.
- **Vista de Detalle**: Al hacer clic en una rutina, se muestra un diálogo con los detalles y la lista de ejercicios.
- **Notificación con Deshacer**: Al añadir una nueva rutina, aparece una notificación (Snackbar) que permite deshacer la acción.
- **Vista para Lista Vacía**: Muestra un mensaje amigable cuando no hay rutinas en la lista.

## Cómo Utilizar

1. Clona el repositorio a tu máquina local.
2. Abre el proyecto en Android Studio.
3. Construye y ejecuta el proyecto en un emulador de Android o en un dispositivo físico.

Una vez que la aplicación esté en funcionamiento:
- Verás una lista de rutinas precargadas.
- Para añadir una nueva rutina, pulsa el botón `+`.
- Rellena los detalles de la rutina y añade tantos ejercicios como desees.
- Para eliminar una rutina, desliza el elemento de la lista hacia la izquierda o la derecha.
- Para ver los detalles de una rutina, simplemente pulsa sobre ella.

## Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **Arquitectura**: Model-View-ViewModel (MVVM)
- **UI**: Android XML Layouts, Material Design
- **Componentes Principales**:
  - `RecyclerView` para mostrar listas.
  - `DialogFragment` para mostrar detalles de la rutina.
  - `Snackbar` para notificaciones.
  - `ActivityResultLauncher` para manejar los resultados de las actividades.
