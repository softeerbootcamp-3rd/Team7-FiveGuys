package org.softeer.robocar.ui.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.kakao.vectormap.*
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.route.*
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivityMapBinding
import org.softeer.robocar.ui.fragment.HeadcountDialogFragment
import java.util.*
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import kotlinx.coroutines.launch
import org.softeer.robocar.BuildConfig.kakao_rest_api_key
import org.softeer.robocar.data.repository.addresssearch.AddressSearchRepository
import org.softeer.robocar.ui.viewmodel.MapViewModel
import org.softeer.robocar.ui.viewmodel.RouteViewModel
import org.softeer.robocar.ui.viewmodel.RouteSoloViewModel
import org.softeer.robocar.ui.viewmodel.OnboardViewModel


@AndroidEntryPoint
class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    private lateinit var mapView: MapView
    private lateinit var locationManager: LocationManager
    private var kakaoMap: KakaoMap? = null
    private var currentLocation: android.location.Location? = null
    private val routeViewModel: RouteViewModel by viewModels()
    private val routeSoloViewModel: RouteSoloViewModel by viewModels()
    private val onboardViewModel: OnboardViewModel by viewModels()
    private var currentLocationLabel: Label? = null
    private val mapViewModel: MapViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            setupLocationUpdates()
        }

        mapView = binding.mapView
        mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                // 지도 API가 정상적으로 종료될 때 호출됨
            }

            override fun onMapError(error: Exception) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                this@MapActivity.kakaoMap = kakaoMap // kakaoMap 객체 저장
                observeRouteSoloData() // 혼자 타는 경로 데이터 관찰 시작
                observeRouteData() // 경로 데이터 관찰 시작
                updateCameraToCurrentLocation()

            }
        })
        mapViewModel.addressResult.observe(this) { address ->
            Log.d("주소 변환 결과", address)
            // 주소 변환 결과를 사용하여 경로 최적화 요청
            onboardViewModel.onboardDetails.value?.let { onboard ->
                requestOptimizedRoute(address, onboard.hostDestAddress, onboard.guestDestAddress, onboard.hostId, onboard.guestId)
            }
        }

//        val inOperationId = intent.getIntExtra("inOperationId", 0)
//        val inOperationId = 1
//        if (inOperationId != 0) {
//            fetchAndDisplayRoute(inOperationId)
//        }
        routeSoloViewModel.getOptimizedRouteSolo("서울특별시 서대문구 남가좌동 122-1", "영등포동5가 34-1")
        routeViewModel.getOptimizedRoute("영등포동5가 34-1", "서울특별시 강남구 학동로 180", "분당구 정자동 50-3", 1, 2)
        HeadcountDialogFragment().show(supportFragmentManager, "headCount")
        setupCurrentLocationButton()
    }

    // 경로 데이터 관찰 및 경로 그리기
    private fun observeRouteData() {
        routeViewModel.route.observe(this) { route ->
            route?.let {
                drawRoute(kakaoMap!!, it.guestNodes) // API 호출 결과에 따라 hostNodes 또는 guestNodes 사용
            }
        }
    }
    private fun observeRouteSoloData() {
        routeSoloViewModel.routeSolo.observe(this) { routeSolo ->
            routeSolo?.let {
                drawRoute(kakaoMap!!, it.nodes)
            }
        }
    }
    private fun drawRoute(kakaoMap: KakaoMap, routeNodes: List<org.softeer.robocar.data.dto.route.response.Coordinate>) {
        val routeLineManager = kakaoMap.routeLineManager ?: return

        val stylesSet = RouteLineStylesSet.from(
            "routeStyles",
            RouteLineStyles.from(RouteLineStyle.from(10f, Color.BLUE))
        )

        // 모든 좌표를 포함하는 하나의 세그먼트를 만듭니다.
        val segment = RouteLineSegment.from(
            routeNodes.map { LatLng.from(it.latitude, it.longitude) }
        ).setStyles(stylesSet.getStyles(0))

        // 세그먼트를 지도에 그립니다.
        val options = RouteLineOptions.from(segment).setStylesSet(stylesSet)
        routeLineManager.layer.addRouteLine(options)
    }
    private fun setupLocationUpdates() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = LocationListener { location ->
            currentLocation = location
            addCurrentLocationLabel(location.latitude, location.longitude)
            // inOperationId 값을 Intent에서 가져오거나 다른 방식으로 결정
//            val inOperationId = intent.getIntExtra("inOperationId", 0)
            val inOperationId = 1
            if (inOperationId != 0) {
                fetchAndDisplayRoute(inOperationId)
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10f, locationListener, Looper.getMainLooper())
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            lastKnownLocation?.let {
                currentLocation = it
                addCurrentLocationLabel(it.latitude, it.longitude)
                updateCameraToCurrentLocation()
            }
        }
    }


    private fun fetchAndDisplayRoute(inOperationId: Int) {
        onboardViewModel.fetchOnboardDetails(inOperationId)
        observeOnboardDetails()
    }

    private fun observeOnboardDetails() {
        onboardViewModel.onboardDetails.observe(this) { onboard ->
            currentLocation?.let {
                // 주소 변환 요청만 하고, 결과는 LiveData를 통해 받음
                requestAddressConversion(it)
            }
        }
    }

    private fun requestAddressConversion(location: Location) {
        lifecycleScope.launch {
            mapViewModel.convertLocationToAddress(location)
        }
    }

    // 최적화된 경로 요청
    private fun requestOptimizedRoute(currentAddress: String, hostDestAddress: String, guestDestAddress: String, hostId: Int, guestId: Int) {
        // 주소 변환 결과를 사용하여 경로 최적화 요청
        routeViewModel.getOptimizedRoute(currentAddress, hostDestAddress, guestDestAddress, hostId.toLong(), guestId.toLong())
    }

    private fun updateCameraToCurrentLocation() {
        currentLocation?.let { location ->
            kakaoMap?.let { map ->
                val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(location.latitude, location.longitude))
                map.moveCamera(cameraUpdate)
            } ?: Log.d("MapActivity", "kakaoMap is null, cannot update camera to current location")
        }
    }

    private fun addCurrentLocationLabel(lat: Double, lon: Double) {
        kakaoMap?.let { map ->
            val labelManager = map.getLabelManager()
            val labelLayer = labelManager?.getLayer()

            // 이전 라벨이 있다면 삭제
            currentLocationLabel?.let {
                labelLayer?.remove(it)
            }

            // 스타일 정의
            val labelStyles = LabelStyles.from("myCurrentLocationStyle",
                LabelStyle.from(R.drawable.icon_car_location)
                    .setTextStyles(30, Color.BLACK, 1, Color.WHITE)
            )

            // 스타일 추가
            val styles = labelManager?.addLabelStyles(labelStyles)

            // 라벨 옵션 설정
            val options = LabelOptions.from(LatLng.from(lat, lon))
                .setStyles(styles)
                .setTexts("현재 위치")

            // 새 라벨 추가 및 참조 저장
            currentLocationLabel = labelLayer?.addLabel(options)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupLocationUpdates()
        }
    }

    private fun setupCurrentLocationButton() {
        binding.btnCurrentLocation.setOnClickListener {
            currentLocation?.let { location ->
                val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(location.latitude, location.longitude))
                // 카메라를 새 위치로 이동시키며 애니메이션 효과 적용
                kakaoMap?.moveCamera(cameraUpdate, CameraAnimation.from(500, true, true))
            }
        }
    }
}