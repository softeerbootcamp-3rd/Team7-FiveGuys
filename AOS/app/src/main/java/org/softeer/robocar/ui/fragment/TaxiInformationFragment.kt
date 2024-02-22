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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ViewModel을 통해 최적화된 경로를 가져오는 API 호출을 시작
        //TODO
        // viewModel.getOptimizedRoute("출발주소", "호스트도착주소", "게스트도착주소", hostId, guestId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
