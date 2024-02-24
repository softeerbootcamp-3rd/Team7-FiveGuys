package org.softeer.robocar.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.databinding.FragmentSelectDestinationBinding
import org.softeer.robocar.ui.activity.RequestCarPoolActivity
import org.softeer.robocar.ui.viewmodel.MapViewModel


@AndroidEntryPoint
class SelectDestinationFragment : Fragment() {
    private var _binding: FragmentSelectDestinationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapViewModel by activityViewModels()
    private val args: SelectDestinationFragmentArgs by navArgs()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectDestinationBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this@SelectDestinationFragment
        binding.mapViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setDestInfo(args.placeName, args.addressName)
        navController = findNavController()

        binding.finishSelectDest.setOnClickListener {
            val info = viewModel.emitInfo()
            val action =SelectDestinationFragmentDirections.actionSelectDestinationFragmentToRequestCarPoolActivity(info)
            navController.navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}