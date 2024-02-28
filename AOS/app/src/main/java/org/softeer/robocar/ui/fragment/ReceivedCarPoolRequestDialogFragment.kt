package org.softeer.robocar.ui.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.databinding.FragmentDialogReceivedCarPoolRequestBinding
import org.softeer.robocar.ui.adapter.DataPassListener
import org.softeer.robocar.ui.viewmodel.ReceivedCarPoolRequestDialogViewModel

@AndroidEntryPoint
class ReceivedCarPoolRequestDialogFragment : DialogFragment() {

    private var _binding: FragmentDialogReceivedCarPoolRequestBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel : ReceivedCarPoolRequestDialogViewModel by activityViewModels()
    private var dataPassListener: DataPassListener? = null

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
        isCancelable = false

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

                viewModel.carPoolId.observe(lifecycleOwner!!){
                    if(viewModel._carPoolId.equals(-1)){
                        // TODO 수락 실패 예외처리
                    } else {
//                    viewModel.saveCarPoolId()
                        goToMap(viewModel._carPoolId.value!!)
                    }
                }

//                if(viewModel._carPoolId.equals(-1)){
//                    // TODO 수락 실패 예외처리
//                } else {
////                    viewModel.saveCarPoolId()
//                    goToMap(viewModel._carPoolId.value!!)
//                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is DataPassListener){
            dataPassListener = context
        } else{
            // 에러처리..
        }
    }

    private fun goToMap(carPoolId: Long){
        dataPassListener?.onDataPassed(carPoolId)
    }
}