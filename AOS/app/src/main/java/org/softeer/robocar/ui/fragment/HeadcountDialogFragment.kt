package org.softeer.robocar.ui.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import org.softeer.robocar.R
import org.softeer.robocar.databinding.FragmentHeadcountDialogBinding
import org.softeer.robocar.ui.viewmodel.MapViewModel

class HeadcountDialogFragment : DialogFragment(R.layout.fragment_headcount_dialog) {
    private var _binding: FragmentHeadcountDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadcountDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val builder = AlertDialog.Builder(activity)
        builder.setView(binding.root)

        binding.mapViewModel = viewModel
        binding.lifecycleOwner = this@HeadcountDialogFragment

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false

        //TODO: TaxiType에 따라 +/- 활성/비활성화
        binding.btnMinusMale.setOnClickListener {
            val act = viewModel.subtractMale()
            if(act==0) it.isClickable = false
        }

        binding.btnPlusMale.setOnClickListener {
            viewModel.addMale()
        }

        binding.btnMinusFemale.setOnClickListener {
            val act = viewModel.subtractFemale()
            if(act==0) it.isClickable = false
        }

        binding.btnPlusFemale.setOnClickListener {
            viewModel.addFemale()
        }

        binding.finishHeadCount.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}