package org.softeer.robocar.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.softeer.robocar.databinding.FragmentDialogReceivedCarPoolRequestBinding

class ReceivedCarPoolRequestDialogFragment : DialogFragment() {

    private var _binding: FragmentDialogReceivedCarPoolRequestBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogReceivedCarPoolRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        with(binding) {
            rejectButton.setOnClickListener {
                // TODO API 연결
                dismiss()
            }
            acceptButton.setOnClickListener {
                // TODO API 연결
                dismiss()
            }
        }
    }
}