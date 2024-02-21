package org.softeer.robocar.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.databinding.FragmentTaxiInformationBinding
import org.softeer.robocar.ui.viewmodel.RouteViewModel

@AndroidEntryPoint
class TaxiInformationFragment : Fragment() {

    private var _binding: FragmentTaxiInformationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RouteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaxiInformationBinding.inflate(inflater, container, false).apply {
            viewModel = this@TaxiInformationFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
