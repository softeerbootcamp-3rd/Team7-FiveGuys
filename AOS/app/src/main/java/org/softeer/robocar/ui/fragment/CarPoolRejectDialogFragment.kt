package org.softeer.robocar.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.softeer.robocar.databinding.FragmentDialogCarPoolRejectBinding

class CarPoolRejectDialogFragment : DialogFragment() {

    private var _binding: FragmentDialogCarPoolRejectBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var navController: NavController
    private val args: CarPoolRejectDialogFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDialogCarPoolRejectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        navController = findNavController()

        binding.confirmButton.setOnClickListener {
            val action = CarPoolRejectDialogFragmentDirections.actionCarPoolRejectDialogFragmentToCarPoolList(args.destinationLocation)
            navController.navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}