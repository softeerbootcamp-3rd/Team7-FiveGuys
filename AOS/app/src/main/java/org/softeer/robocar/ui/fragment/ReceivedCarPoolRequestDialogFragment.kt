package org.softeer.robocar.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.databinding.FragmentDialogReceivedCarPoolRequestBinding
import org.softeer.robocar.ui.viewmodel.ReceivedCarPoolRequestDialogViewModel

@AndroidEntryPoint
class ReceivedCarPoolRequestDialogFragment : DialogFragment() {

    private var _binding: FragmentDialogReceivedCarPoolRequestBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel : ReceivedCarPoolRequestDialogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogReceivedCarPoolRequestBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@ReceivedCarPoolRequestDialogFragment
            dialogViewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val guestId = arguments?.getLong("guestId") ?: 0
        val maleCount = arguments?.getInt("maleCount")?: 0
        val femaleCount = arguments?.getInt("femaleCount")?: 0
        val nickname = arguments?.getString("nickname")?: "닉네임"
        val guestAddress = arguments?.getString("guestAddress")?: "주소"

        viewModel.setRequestCarPoolInfo(
            guestId = guestId,
            maleCount = maleCount,
            femaleCount = femaleCount,
            nickname = nickname,
            guestAddress = guestAddress
        )

        with(binding) {
            rejectButton.setOnClickListener {
                viewModel.rejectCarPoolRequest()
                dismiss()
            }
            acceptButton.setOnClickListener {
                viewModel.acceptCarPoolRequest()
                if(viewModel.carPoolId.equals(-1)){
                    // TODO 수락 실패 예외처리
                } else {
                    val action =ReceivedCarPoolRequestDialogFragmentDirections.actionReceivedCarPoolRequestDialogFragmentToMapActivity()
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}