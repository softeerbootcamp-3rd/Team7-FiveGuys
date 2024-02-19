package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.databinding.FragmentCarPoolDetailBinding
import org.softeer.robocar.ui.viewmodel.CarPoolDetailViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CarPoolRequestDetailFragment : Fragment() {

    @Inject
    lateinit var viewModel: CarPoolDetailViewModel
    private var _binding: FragmentCarPoolDetailBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCarPoolDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CarPoolRequestDetailFragment
            detailViewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val carPool = arguments?.getParcelable<CarPool>("carPool")!!
        val originalCharge = arguments?.getInt("originalCharge")!!
        viewModel.setCarPoolDetail(carPool, originalCharge)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}