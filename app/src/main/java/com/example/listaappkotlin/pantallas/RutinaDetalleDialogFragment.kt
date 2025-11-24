package com.example.listaappkotlin.pantallas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.listaappkotlin.adapter.EjercicioDetalleAdapter
import com.example.listaappkotlin.data.models.Rutina
import com.example.listaappkotlin.databinding.DialogRutinaDetalleBinding

class RutinaDetalleDialogFragment : DialogFragment() {

    private var _binding: DialogRutinaDetalleBinding? = null
    private val binding get() = _binding!!

    private lateinit var rutina: Rutina

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rutina = it.getParcelable("RUTINA_EXTRA")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRutinaDetalleBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
            setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvRutinaTitulo.text = rutina.nombreRutina

        val ejercicioAdapter = EjercicioDetalleAdapter(rutina.ejercicios)
        binding.rvEjerciciosDialog.adapter = ejercicioAdapter

        binding.btnCerrarDialog.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(rutina: Rutina): RutinaDetalleDialogFragment {
            val fragment = RutinaDetalleDialogFragment()
            val args = Bundle()
            args.putParcelable("RUTINA_EXTRA", rutina)
            fragment.arguments = args
            return fragment
        }
    }
}
