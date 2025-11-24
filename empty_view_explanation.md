# Explicación del Patrón "Empty View"

Este documento explica el concepto de "Empty View" (Vista Vacía) y cómo se ha implementado en la `ListActivity` de tu proyecto.

## ¿Qué es un "Empty View"?

Un "Empty View" es un componente de la interfaz de usuario que se muestra en la pantalla únicamente cuando una lista, como un `RecyclerView`, no tiene elementos para mostrar.

En lugar de presentar una pantalla en blanco (lo que puede confundir al usuario), se muestra un mensaje o un layout que explica la situación y, a menudo, guía al usuario sobre cómo proceder.

## ¿Por qué es útil?

1.  **Mejora la Experiencia de Usuario (UX)**: Evita la sensación de que la aplicación está rota o vacía.
2.  **Guía al Usuario**: Proporciona un "llamado a la acción" (Call to Action). En nuestro caso, el mensaje "Añade una rutina nueva" le indica al usuario el siguiente paso lógico.
3.  **Informa del Estado**: Comunica claramente el estado actual de la aplicación (no hay rutinas).

## Implementación en `ListaAppKotlin`

La implementación se realizó en dos pasos:

### 1. Modificación del Layout (`activity_list.xml`)

Se añadió un `TextView` al archivo de layout. Este `TextView` será nuestro "Empty View".

-   **`android:id="@+id/emptyView"`**: Se le dio un identificador único para poder controlarlo desde el código.
-   **`android:layout_gravity="center"`**: Se centró en la pantalla para que sea visible.
-   **`android:text="Añade una rutina nueva"`**: Contiene el mensaje a mostrar.
-   **`android:visibility="gone"`**: Muy importante. Por defecto, esta vista está oculta (`gone`), lo que significa que no se muestra y no ocupa espacio en el layout.

```xml
<!-- app/src/main/res/layout/activity_list.xml -->

<androidx.coordinatorlayout.widget.CoordinatorLayout ...>

    <!-- ... (AppBarLayout y Toolbar) ... -->

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerworkout"
        ... />

    <!-- ESTA ES LA EMPTY VIEW -->
    <TextView
        android:id="@+id/emptyView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="Añade una rutina nueva"
        android:textColor="@android:color/white"
        android:textSize="18sp"
        android:visibility="gone" />

    <!-- ... (FloatingActionButton) ... -->

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

### 2. Lógica en `ListActivity.kt`

Para controlar cuándo se muestra el `TextView` y cuándo el `RecyclerView`, se creó una función `checkEmptyView()` y se llamó desde los lugares apropiados.

#### La función `checkEmptyView()`

Esta función comprueba si la lista de rutinas está vacía.
-   Si la lista **está vacía**, oculta el `RecyclerView` y muestra el `emptyView`.
-   Si la lista **tiene elementos**, muestra el `RecyclerView` y oculta el `emptyView`.

```kotlin
// app/src/main/java/com/example/listaappkotlin/pantallas/ListActivity.kt

private fun checkEmptyView() {
    if (routinesMutableList.isEmpty()) {
        binding.emptyView.visibility = android.view.View.VISIBLE
        binding.recyclerworkout.visibility = android.view.View.GONE
    } else {
        binding.emptyView.visibility = android.view.View.GONE
        binding.recyclerworkout.visibility = android.view.View.VISIBLE
    }
}
```

#### Puntos de llamada

La función `checkEmptyView()` se llama en tres momentos clave para asegurar que la UI esté siempre sincronizada:

1.  **`initRecyclerView()`**: Para establecer el estado inicial correcto cuando la actividad se crea.
2.  **`crearRutina()`**: Después de añadir una nueva rutina, para asegurarse de que el `emptyView` se oculte si era el primer elemento.
3.  **`onDeletedItem()`**: Después de borrar una rutina, para comprobar si la lista ha quedado vacía y, en ese caso, mostrar el `emptyView`.
