package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.databinding.FragmentCarPoolDetailBinding
import org.softeer.robocar.ui.viewmodel.CarPoolDetailViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CarPoolRequestDetailFragment : Fragment() {

    private val viewModel: CarPoolDetailViewModel by viewModels()
    private var _binding: FragmentCarPoolDetailBinding? = null
    private lateinit var navController: NavController
    private val binding
        get() = _binding!!
    private val args: CarPoolRequestDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCarPoolDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CarPoolRequestDetailFragment
            detailViewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val carPool = args.carPool
        val originalCharge = args.originalCharge
        viewModel.setCarPoolDetail(carPool, originalCharge)
        navController = findNavController()

        binding.requestCarPoolButton.setOnClickListener{
            viewModel.requestCarPool(carPool, args.destinationLocation)
            val action = CarPoolRequestDetailFragmentDirections.actionCarPoolRequestDetailToCarPoolRequestDialog(args.destinationLocation)
            navController.navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}