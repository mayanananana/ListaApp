# Explaintrail del Proyecto ListaAppKotlin

Este documento explica el funcionamiento de varios componentes clave dentro del proyecto `ListaAppKotlin`, una aplicación para gestionar rutinas de ejercicios.

## 1. El Patrón ViewHolder

El patrón `ViewHolder` es una optimización crucial para las listas en Android que usan `RecyclerView`. Su propósito es evitar la repetición de llamadas a `findViewById()`, una operación que puede ser costosa y ralentizar el desplazamiento de la lista.

En este proyecto, `RutinaViewHolder.kt` es la implementación de este patrón.

### ¿Cómo funciona?

1.  **Almacena las Vistas**: Un objeto `ViewHolder` almacena las referencias a las vistas de un solo elemento de la lista (por ejemplo, un `TextView`, un `ImageView`, etc.). Esto se hace una sola vez cuando se crea el `ViewHolder`.
2.  **Reutilización**: `RecyclerView` reutiliza estos objetos `ViewHolder`. Cuando un elemento de la lista sale de la pantalla, su `ViewHolder` no se destruye, sino que se recicla para mostrar un nuevo elemento que entra en la pantalla.

### Implementación en `RutinaViewHolder.kt`

```kotlin
// app/src/main/java/com/example/listaappkotlin/adapter/RutinaViewHolder.kt

class RutinaViewHolder (view: View) : RecyclerView.ViewHolder(view){

    // Utiliza View Binding para acceder a las vistas de forma segura y eficiente.
    // Reemplaza a findViewById.
    val binding = ItemRutinaBinding.bind(view)

    // El método render se encarga de "pintar" los datos en las vistas.
    fun render(rutinaModel: Rutina, onClickListener: (Rutina) -> Unit, onClickDelete: (Int) -> Unit){
       binding.titleTV.text = rutinaModel.nombreRutina;
       binding.descripcionTV.text = rutinaModel.descripcionRutina;
       // Glide es una librería para cargar imágenes de forma eficiente.
       Glide.with(binding.itemIcon.context).load(rutinaModel.photo).into(binding.itemIcon)

       // Se asignan los eventos a los botones.
       binding.titleTV.setOnClickListener {
            // Muestra un diálogo con los detalles
       }

       binding.buttonDelete.setOnClickListener { onClickDelete(adapterPosition) }
    }
}
```

-   **`binding = ItemRutinaBinding.bind(view)`**: En lugar del tradicional `findViewById`, el proyecto utiliza **View Binding**. La clase `ItemRutinaBinding` es generada automáticamente por el sistema de build a partir del layout `item_rutina.xml`. Proporciona acceso directo y seguro a todas las vistas con un ID en ese layout.
-   **`render()`**: Este método es llamado por el `Adapter` para actualizar el contenido del `ViewHolder` con los datos de un nuevo objeto `Rutina`.

## 2. El Adapter

El `Adapter` es el componente que actúa como puente entre los datos (por ejemplo, una lista de rutinas) y el `RecyclerView` que los muestra. Es el responsable de crear y gestionar los `ViewHolder`.

En el proyecto hay dos: `RutinaAdapter.kt` y `EjercicioAdapter.kt`.

### Funciones Principales del Adapter

-   **`onCreateViewHolder()`**: Se llama cuando el `RecyclerView` necesita crear un nuevo `ViewHolder` (porque no hay ninguno disponible para reciclar). Aquí se "infla" (crea) el layout XML del item y se devuelve una nueva instancia del `ViewHolder`.
-   **`onBindViewHolder()`**: Se llama para asociar un `ViewHolder` existente con los datos de una posición específica. Aquí es donde se llama al método `render()` del `ViewHolder`.
-   **`getItemCount()`**: Devuelve el número total de elementos en la lista de datos.

### Implementación en `RutinaAdapter.kt`

```kotlin
// app/src/main/java/com/example/listaappkotlin/adapter/RutinaAdapter.kt

class RutinaAdapter(
    private val listaRutinas:List<Rutina>,
    private val onClickListener: (Rutina) -> Unit, // Función para manejar clics en el item
    private val onClickDelete:(Int) -> Unit      // Función para manejar clics en el botón de borrar
) : RecyclerView.Adapter<RutinaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaViewHolder {
        // Se infla el layout del item (item_rutina.xml)
        val layoutInflater = LayoutInflater.from(parent.context);
        return RutinaViewHolder(layoutInflater.inflate(R.layout.item_rutina, parent, false));
    }

    override fun onBindViewHolder(holder: RutinaViewHolder, position: Int) {
        val item = listaRutinas[position]
        // Se llama a render() para poblar el ViewHolder con los datos del item
        holder.render(item, onClickListener, onClickDelete)
    }

    override fun getItemCount(): Int = listaRutinas.size;
}
```

-   El constructor del `RutinaAdapter` es interesante porque recibe no solo la lista de datos, sino también **dos funciones lambda**: `onClickListener` y `onClickDelete`. Esto permite que la `ListActivity` (donde se usa el adapter) defina qué hacer cuando un item es pulsado o borrado, desacoplando la lógica de la vista.

## 3. Particularidades de "Añadir Ejercicio/Rutina"

La pantalla `AddRutina.kt` es donde el usuario crea una nueva rutina, le asigna un nombre, una descripción y le añade una lista de ejercicios.

### Flujo de Funcionamiento

1.  **Inputs de Usuario**: La pantalla (`activity_add_rutina.xml`) tiene campos de texto (`EditText`) para el nombre de la rutina, la descripción, y para los detalles de cada ejercicio (nombre, series, repeticiones, etc.).

2.  **Añadir un Ejercicio**:
    -   El usuario rellena los datos de un ejercicio.
    -   Al pulsar el botón "Añadir Ejercicio" (`btnAnadirEjercicio`), se ejecuta el método `addEjercicioToList()`.
    -   Este método crea un objeto `Ejercicios` con los datos introducidos.
    -   El nuevo objeto `Ejercicios` se añade a una lista local (`listaDeEjercicios`).
    -   Se notifica al `ejerciciosAdapter` que un nuevo item ha sido insertado, y el `RecyclerView` de esta pantalla (`rvEjercicios`) se actualiza para mostrar el ejercicio recién añadido.

    ```kotlin
    // app/src/main/java/com/example/listaappkotlin/pantallas/AddRutina.kt

    private fun addEjercicioToList() {
        // ... (se obtienen los datos de los EditText)
        val nuevoEjercicio = Ejercicios(nombre = nombre, series = series, ...)
        
        listaDeEjercicios.add(nuevoEjercicio)
        ejerciciosAdapter.notifyItemInserted(listaDeEjercicios.size - 1)
        
        // ... (se limpian los campos de texto)
    }
    ```

3.  **Guardar la Rutina**:
    -   Una vez el usuario ha añadido todos los ejercicios, pulsa el botón "Guardar Rutina" (`btnGuardarRutina`), que llama al método `saveRutina()`.
    -   Se crea un objeto `Rutina` que contiene el nombre, la descripción, una URL de foto por defecto y, lo más importante, la `listaDeEjercicios` que se ha ido construyendo.
    -   Esta nueva `Rutina` se empaqueta en un `Intent` como un "extra".
    -   Se establece el resultado de la actividad como `Activity.RESULT_OK` y se finaliza la actividad (`finish()`), volviendo a `ListActivity`.

    ```kotlin
    // app/src/main/java/com/example/listaappkotlin/pantallas/AddRutina.kt

    private fun saveRutina() {
        // ... (validaciones)
        val rutinaCompleta = Rutina(
            nombreRutina = nombreRutina,
            descripcionRutina = descripcion,
            photo = photoUrl,
            ejercicios = listaDeEjercicios // Se adjunta la lista de ejercicios
        )

        val resultIntent = Intent()
        resultIntent.putExtra("NUEVA_RUTINA", rutinaCompleta)
        setResult(Activity.RESULT_OK, resultIntent)
        finish() // Cierra AddRutina y vuelve a ListActivity
    }
    ```

4.  **Recepción en `ListActivity`**:
    -   `ListActivity` lanza `AddRutina` usando `registerForActivityResult`. Esto le permite esperar un resultado.
    -   Cuando `AddRutina` termina, el callback de `registerForActivityResult` se ejecuta.
    -   Se extrae la `Rutina` del `Intent`, se añade a la lista principal de rutinas y se notifica al `RutinaAdapter` para que actualice la lista en pantalla.

## 4. El Manifiesto de la Aplicación (`AndroidManifest.xml`)

El `AndroidManifest.xml` es el archivo de configuración central de la aplicación. Le dice al sistema operativo Android todo lo que necesita saber para poder ejecutar la app.

### Puntos Clave en este Proyecto

```xml
<!-- app/src/main/AndroidManifest.xml -->

<manifest xmlns:android="http://schemas.android.com/apk/res/android" ...>

    <!-- Pide permiso para acceder a Internet. Necesario para Glide. -->
    <uses-permission android:name="android.permission.INTERNET" />

    <application ...>

        <!-- Declara la actividad AddRutina. 
             exported="false" significa que solo puede ser lanzada por esta misma app. -->
        <activity
            android:name=".pantallas.AddRutina"
            android:exported="false" />
        
        <!-- Declara la actividad ListActivity. -->
        <activity
            android:name=".pantallas.ListActivity"
            android:exported="false" />

        <!-- Declara la actividad MainActivity. 
             exported="true" significa que puede ser lanzada por el sistema. -->
        <activity
            android:name=".pantallas.MainActivity"
            android:exported="true">
            
            <!-- Este intent-filter la marca como la actividad principal y de inicio. -->
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

    </application>
</manifest>
```

-   **`<uses-permission>`**: Se declara el permiso de `INTERNET`. Aunque la URL de la imagen está hardcodeada, la librería `Glide` la necesita para descargar y mostrar la imagen.
-   **`<application>`**: Contiene la configuración global de la app, como el icono (`android:icon`), el nombre (`android:label`) y el tema (`android:theme`).
-   **`<activity>`**: Cada `Activity` de la app debe ser declarada aquí.
    -   **`android:name`**: Especifica la clase Kotlin que implementa la actividad.
    -   **`android:exported`**: Controla si componentes de otras apps pueden iniciar esta actividad. `false` es una buena práctica de seguridad para actividades internas.
    -   **`<intent-filter>`**: La combinación de la acción `MAIN` y la categoría `LAUNCHER` dentro de `MainActivity` le dice al sistema que esta es la actividad que se debe lanzar cuando el usuario pulsa el icono de la app en su dispositivo.

```
This file has been created.
Now, I will remove the temporary files that I have created.
```