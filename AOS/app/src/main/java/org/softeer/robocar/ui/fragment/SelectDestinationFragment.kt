package org.softeer.robocar.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.R
import org.softeer.robocar.databinding.FragmentPathSettingBinding
import org.softeer.robocar.databinding.FragmentSelectDestinationBinding
import org.softeer.robocar.ui.activity.MapActivity
import org.softeer.robocar.ui.viewmodel.SelectDestinationViewModel


@AndroidEntryPoint
class SelectDestinationFragment : Fragment() {
    private var _binding: FragmentSelectDestinationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectDestinationViewModel by viewModels()
    private val args: SelectDestinationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectDestinationBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@SelectDestinationFragment
            selectDestinationViewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val placeAttr = args.placeItem!!

        viewModel.setDestInfo(placeAttr.place_name,placeAttr.road_address_name)

        binding.finishSelectDest.setOnClickListener {
            val action =
                SelectDestinationFragmentDirections.actionSelectDestinationFragmentToInternalControlFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}