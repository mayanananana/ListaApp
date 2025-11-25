package com.example.listaappkotlin.pantallas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.listaappkotlin.adapter.EjercicioDetalleAdapter
import com.example.listaappkotlin.data.models.Rutina
import com.example.listaappkotlin.databinding.DialogRutinaDetalleBinding

/**
 * Un DialogFragment que muestra los detalles de una rutina específica.
 * Muestra el nombre de la rutina y una lista de los ejercicios que la componen.
 */
class RutinaDetalleDialogFragment : DialogFragment() {

    private var _binding: DialogRutinaDetalleBinding? = null
    private val binding get() = _binding!!

    private lateinit var rutina: Rutina

    /**
     * Se llama al crear el fragmento.
     * Recupera la rutina pasada como argumento.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rutina = it.getParcelable("RUTINA_EXTRA")!!
        }
    }

    /**
     * Crea y devuelve la jerarquía de vistas asociada con el fragmento.
     * Infla el layout del diálogo y establece un fondo transparente.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRutinaDetalleBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return binding.root
    }

    /**
     * Se llama cuando el diálogo se hace visible.
     * Ajusta el ancho del diálogo al 90% del ancho de la pantalla.
     */
    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
            setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    /**
     * Se llama después de que la vista del fragmento ha sido creada.
     * Configura el título de la rutina, el adaptador para la lista de ejercicios y el botón de cerrar.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvRutinaTitulo.text = rutina.nombreRutina

        val ejercicioAdapter = EjercicioDetalleAdapter(rutina.ejercicios)
        binding.rvEjerciciosDialog.adapter = ejercicioAdapter

        binding.btnCerrarDialog.setOnClickListener {
            dismiss() // Cierra el diálogo.
        }
    }

    /**
     * Se llama cuando la vista del fragmento está a punto de ser destruida.
     * Limpia la referencia al binding para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Objeto compañero que proporciona un método estático para crear una nueva instancia del fragmento.
     * Esto asegura que los argumentos se pasen de forma segura.
     */
    companion object {
        /**
         * Crea una nueva instancia de RutinaDetalleDialogFragment con la rutina especificada.
         * @param rutina La rutina a mostrar.
         * @return Una nueva instancia del fragmento.
         */
        fun newInstance(rutina: Rutina): RutinaDetalleDialogFragment {
            val fragment = RutinaDetalleDialogFragment()
            val args = Bundle()
            args.putParcelable("RUTINA_EXTRA", rutina)
            fragment.arguments = args
            return fragment
        }
    }
}
