package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.databinding.FragmentHomeBinding
import org.softeer.robocar.ui.viewmodel.HomeViewModel
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@HomeFragment
            homeViewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goToRidingTaxi.setOnClickListener {
            goToMap()
        }
    }

    private fun goToMap(){
        val taxiType = viewModel.taxiType.value!!
        val carPoolType =viewModel.carPoolType.value!!
        val action = HomeFragmentDirections.actionHomeToMapActivity(carPoolType, taxiType)
        findNavController().navigate(action)
    }
}