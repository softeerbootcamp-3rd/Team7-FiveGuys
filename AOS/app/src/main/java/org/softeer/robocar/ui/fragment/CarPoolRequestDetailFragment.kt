package org.softeer.robocar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.databinding.FragmentCarPoolDetailBinding
import org.softeer.robocar.ui.viewmodel.CarPoolDetailViewModel

@AndroidEntryPoint
class CarPoolRequestDetailFragment : Fragment() {

    private val viewModel: CarPoolDetailViewModel by viewModels()
    private var _binding: FragmentCarPoolDetailBinding? = null
    private lateinit var navController: NavController
    private val binding
        get() = _binding!!
    private lateinit var mapView: MapView
    private var kakaoMap: KakaoMap? = null

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
        mapView = binding.mapView
        drawMap()

        binding.requestCarPoolButton.setOnClickListener {
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

    private fun drawMap() = mapView.start(object : MapLifeCycleCallback() {
        override fun onMapDestroy() {
            // 지도 API가 정상적으로 종료될 때 호출됨
        }

        override fun onMapError(error: Exception) {
            // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
        }
    }, object : KakaoMapReadyCallback() {
        override fun onMapReady(kakaoMap: KakaoMap) {
            this@CarPoolRequestDetailFragment.kakaoMap = kakaoMap // kakaoMap 객체 저장
        }
    })
}