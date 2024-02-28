package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.data.model.CarDetails
import org.softeer.robocar.databinding.FragmentTaxiInformationBinding
import org.softeer.robocar.ui.viewmodel.RouteSoloViewModel
import org.softeer.robocar.ui.viewmodel.MapViewModel

@AndroidEntryPoint
class TaxiInformationFragment : Fragment() {

    private var _binding: FragmentTaxiInformationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MapViewModel by activityViewModels()
    companion object {
        private const val ARG_IN_OPERATION_ID = "inOperationId"

        fun newInstance(inOperationId: Long): TaxiInformationFragment {
            val fragment = TaxiInformationFragment()
            val args = Bundle()
            args.putLong(ARG_IN_OPERATION_ID, inOperationId)
            fragment.arguments = args
            return fragment
        }
    }
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

        // inOperationId를 arguments에서 추출
        val inOperationId = arguments?.getLong(ARG_IN_OPERATION_ID) ?: return
        Log.d("아이디","$inOperationId")

// 운행 상세 정보 요청
        viewModel.fetchOnboardDetails(inOperationId)

        // 운행 상세 정보 LiveData 객체 관찰
        viewModel.onboardDetails.observe(viewLifecycleOwner) { onboardDetails ->
            // 차량 ID 추출
            val carId = onboardDetails.carId
            Log.d("Car ID", "$carId")

            // 차량 ID를 사용하여 차량 정보 요청
            viewModel.fetchCarDetails(carId)
        }

        // 차량 정보 LiveData 객체 관찰
        viewModel.carDetails.observe(viewLifecycleOwner) { carDetails ->
            // 차량 정보가 업데이트되면 UI 업데이트
            updateUI(carDetails)
        }
    }

    // 차량 정보를 UI에 반영하는 메소드
    private fun updateUI(carDetails: CarDetails) {
        with(binding) {
            taxiImage.loadImageFromUrl(carDetails.carImage)
            taxiCarName.text = carDetails.carName
            taxiLicensePlate.text = carDetails.carNumber
            // 추가적으로 필요한 UI 업데이트 로직
        }
    }
    // ImageView 확장 함수
    private fun ImageView.loadImageFromUrl(url: String) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
