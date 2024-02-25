package org.softeer.robocar.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.softeer.robocar.databinding.FragmentDialogCarPoolRequestBinding
import org.softeer.robocar.ui.adapter.DataPassListener

class CarPoolRequestDialogFragment : DialogFragment() {

    private var _binding: FragmentDialogCarPoolRequestBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var navController: NavController
    private val args: CarPoolRequestDialogFragmentArgs by navArgs()

    private var dataPassListener: DataPassListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDialogCarPoolRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        navController = findNavController()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            messageReceiver, IntentFilter("org.softeer.robocar.NEW_MESSAGE")
        )
        binding.cancelRequestButton.setOnClickListener {
            // TODO API 연결
            // But API가 음슴...
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(messageReceiver)
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

    private val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val type = intent?.getStringExtra("type") ?: "CAR_POOL_REJECT"
            if(type == "CAR_POOL_REJECT"){
                val action = CarPoolRequestDialogFragmentDirections.actionCarPoolRequestDialogToCarPoolRejectDialogFragment(args.destinationLocation)
                navController.navigate(action)
            } else {
                val carPoolId = intent?.getStringExtra("carPoolId")?.toLong() ?: -1
                goToMap(carPoolId)
            }
        }
    }

    fun goToMap(carPoolId: Long){
        dataPassListener?.onDataPassed(carPoolId)
    }
}