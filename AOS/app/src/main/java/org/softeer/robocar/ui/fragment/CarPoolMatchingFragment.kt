package org.softeer.robocar.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.R
import org.softeer.robocar.databinding.FragmentCarPoolMatchingBinding
import org.softeer.robocar.ui.viewmodel.CarPoolMatchingViewModel

@AndroidEntryPoint
class CarPoolMatchingFragment : Fragment() {

    private var _binding: FragmentCarPoolMatchingBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CarPoolMatchingViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCarPoolMatchingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.registerCarPool()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            messageReceiver, IntentFilter("org.softeer.robocar.NEW_MESSAGE")
        )
    }

    private val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val dialogIsShowing = findNavController().currentBackStackEntry?.destination?.id == R.id.receivedCarPoolRequestDialogFragment
            if(dialogIsShowing)
                return

            val maleCount = intent?.getStringExtra("maleCount") ?: "0"
            val femaleCount = intent?.getStringExtra("femaleCount") ?: "0"
            val guestId = intent?.getStringExtra("guestId") ?: "0"
            val guestAddress = intent?.getStringExtra("guestAddress") ?: "주소"
            val nickname = intent?.getStringExtra("nickname") ?: "닉네임"

            val action = CarPoolMatchingFragmentDirections.actionCarPoolMatchingFragmentToReceivedCarPoolRequestDialogFragment(
                guestId = guestId.toLong(),
                maleCount = maleCount.toInt(),
                femaleCount = femaleCount.toInt(),
                guestAddress = guestAddress,
                nickname = nickname
            )
            findNavController().navigate(action)
        }
    }
}