package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.databinding.FragmentCarPoolDetailBinding
import org.softeer.robocar.ui.viewmodel.CarPoolDetailViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CarPoolRequestDetailFragment : Fragment() {

    lateinit var viewModel: CarPoolDetailViewModel
    private var _binding: FragmentCarPoolDetailBinding? = null
    private lateinit var navController: NavController
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
        navController = findNavController()

        binding.requestCarPoolButton.setOnClickListener{
            // TODO 게스트 도착지 받아서 값 넣기
            viewModel.requestCarPool(carPool, "서울 강서구 하늘길 111 국내선 주차대기실")
            val action = CarPoolRequestDetailFragmentDirections.actionCarPoolRequestDetailToCarPoolRequestDialog()
            navController.navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}