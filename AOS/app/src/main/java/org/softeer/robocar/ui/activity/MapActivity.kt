package org.softeer.robocar.ui.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kakao.vectormap.*
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.route.*
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivityMapBinding
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import kotlinx.coroutines.launch
import org.softeer.robocar.ui.viewmodel.MapViewModel
import org.softeer.robocar.ui.viewmodel.OnboardViewModel
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.softeer.robocar.data.model.CarPoolType
import org.softeer.robocar.data.model.TaxiType
import org.softeer.robocar.ui.fragment.*


@AndroidEntryPoint
class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    private lateinit var mapView: MapView
    private lateinit var locationManager: LocationManager
    private var kakaoMap: KakaoMap? = null
    private var currentLocation: Location? = null
    private val onboardViewModel: OnboardViewModel by viewModels()
    private val mapViewModel: MapViewModel by viewModels()
    private var currentLocationLabel: Label? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        setCarPoolANDTaxiType()

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.destinationLayout)
        mapViewModel.bottomSheetState.observe(this, Observer {
            bottomSheetBehavior.state = it
        })
        mapViewModel.bottomSheetDraggable.observe(this, Observer {
            bottomSheetBehavior.isDraggable = it
        })

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
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
                updateCameraToCurrentLocation()
            }
        })
//        mapViewModel.addressResult.observe(this) { address ->
//            Log.d("MapActivity", "관찰된 주소 변환 결과: $address")
//            // 주소 변환 결과를 사용하여 경로 최적화 요청
////            onboardViewModel.onboardDetails.value?.let { onboard ->
//            requestOptimizedRoute(address, "서울 강남구 논현동", "서울 강남구 논현동 279-67", 1, 2)
//            Log.d("주소", address)
////            }
//        }

        setupLocationUpdates()

        val inOperationId = intent.getLongExtra("carPoolId", -1)
        println("$inOperationId")
        if (inOperationId > 0) {
            // 유효한 inOperationId를 사용하여 데이터 요청
            fetchAndDisplayRoute(inOperationId)
            showTaxiInfoFragment(inOperationId)
            observeRouteSoloData() // 혼자 타는 경로 데이터 관찰 시작
            observeRouteData() // 경로 데이터 관찰 시작
            fetchAndStartMovingLabel()
            supportFragmentManager.beginTransaction().
                replace(binding.destinationFragmentContainer.id, InternalControlFragment()).commit()

        } else {
            // 유효한 inOperationId가 없을 때는 택시 정보 프래그먼트를 표시하지 않음
            removeTaxiInfoFragment()
            HeadcountDialogFragment().show(supportFragmentManager, "headCount")
        }
//        mapViewModel.getOptimizedRouteSolo("논현동 40", "서울특별시 강남구 학동로 171")
//        mapViewModel.getOptimizedRoute("영등포동5가 34-1", "서울특별시 강남구 학동로 180", "분당구 정자동 50-3", 1, 2)
        setupCurrentLocationButton()
    }

    private fun showTaxiInfoFragment(inOperationId: Long) {
        // `TaxiInformationFragment`에 `inOperationId`를 전달하여 추가
        val fragment = TaxiInformationFragment.newInstance(inOperationId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.taxiInformationFragmentContainer, fragment) // ID 변경
            .commit()
    }

    private fun removeTaxiInfoFragment() {
        // `TaxiInformationFragment`를 찾아 제거
        val fragment = supportFragmentManager.findFragmentById(R.id.taxiInformationFragmentContainer) // ID 변경
        if (fragment is TaxiInformationFragment) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }
    }


    // 경로 데이터 관찰 및 경로 그리기
    private fun observeRouteData() {
        mapViewModel.route.observe(this) { route ->
            route?.let {
                drawRoute(kakaoMap!!, it.hostNodes) // API 호출 결과에 따라 hostNodes 또는 guestNodes 사용
            }
        }
    }

    private fun observeRouteSoloData() {
        mapViewModel.routeSolo.observe(this) { routeSolo ->
            routeSolo?.let {
                drawRoute(kakaoMap!!, it.nodes)
            }
        }
    }

    private fun drawRoute(
        kakaoMap: KakaoMap,
        routeNodes: List<org.softeer.robocar.data.dto.route.response.Coordinate>
    ) {
        val routeLineManager = kakaoMap.routeLineManager ?: return

        routeLineManager.layer.removeAll()

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
            val inOperationId = intent.getLongExtra("inOperationId", 0L)
            if (inOperationId != 0L) {
                fetchAndDisplayRoute(inOperationId)
            }

            // 현재 위치로 주소 변환 요청
            requestAddressConversion(location)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10000,
                10f,
                locationListener,
                Looper.getMainLooper()
            )
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            lastKnownLocation?.let {
                currentLocation = it
                addCurrentLocationLabel(it.latitude, it.longitude)
                updateCameraToCurrentLocation()
            }
        }
    }


    private fun fetchAndDisplayRoute(inOperationId: Long) {
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
    private fun requestOptimizedRoute(
        currentAddress: String,
        hostDestAddress: String,
        guestDestAddress: String,
        hostId: Int,
        guestId: Int
    ) {
        Log.d("MapActivity", "requestOptimizedRoute 호출됨: $currentAddress")
        // 주소 변환 결과를 사용하여 경로 최적화 요청
        mapViewModel.getOptimizedRoute(
            currentAddress,
            hostDestAddress,
            guestDestAddress,
            hostId.toLong(),
            guestId.toLong()
        )
    }

    private fun updateCameraToCurrentLocation() {
        currentLocation?.let { location ->
            kakaoMap?.let { map ->
                val cameraUpdate =
                    CameraUpdateFactory.newCenterPosition(LatLng.from(location.latitude, location.longitude))
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
            val labelStyles = LabelStyles.from(
                "myCurrentLocationStyle",
                LabelStyle.from(R.drawable.icon_car_location).setTextStyles(70, Color.BLACK, 1, Color.WHITE)
                    .setZoomLevel(8),
                LabelStyle.from(R.drawable.icon_car_location).setTextStyles(70, Color.BLACK, 1, Color.WHITE)
                    .setZoomLevel(11),
                LabelStyle.from(R.drawable.icon_car_location).setTextStyles(70, Color.BLACK, 1, Color.WHITE)
                    .setZoomLevel(15)
            )

            // 스타일 추가
            val styles = labelManager?.addLabelStyles(labelStyles)

            // 라벨 옵션 설정
            val options = LabelOptions.from(LatLng.from(lat, lon))
                .setStyles(styles)
                .setTexts("\uD83D\uDE04")

            // 새 라벨 추가 및 참조 저장
            currentLocationLabel = labelLayer?.addLabel(options)
            BottomSheetBehavior.from(binding.destinationLayout).isDraggable
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
                val cameraUpdate =
                    CameraUpdateFactory.newCenterPosition(LatLng.from(location.latitude, location.longitude))
                // 카메라를 새 위치로 이동시키며 애니메이션 효과 적용
                kakaoMap?.moveCamera(cameraUpdate, CameraAnimation.from(500, true, true))
            }
        }
    }

    private lateinit var movingLabel: Label
    private val handler = Handler(Looper.getMainLooper())

    // 경로 데이터를 동적으로 받아오기 위해 route 변수 선언을 제거합니다.
    private fun convertCoordinatesToLatLng(coordinates: List<org.softeer.robocar.data.dto.route.response.Coordinate>): List<LatLng> {
        return coordinates.map { LatLng.from(it.latitude, it.longitude) } // Coordinate의 y를 latitude로, x를 longitude로 사용
    }

    // 경로 데이터를 받아와서 라벨 이동 시작하는 함수
    private fun fetchAndStartMovingLabel() {
        mapViewModel.routeSolo.observe(this, Observer { routeSolo ->
            routeSolo?.let {
                val latLngList = convertCoordinatesToLatLng(it.nodes) // Coordinate -> LatLng 변환
                startMovingLabel(latLngList) // 변환된 리스트를 사용하여 라벨 이동 시작
            }
        })

        mapViewModel.getOptimizedRouteSolo("논현동 40", "서울특별시 강남구 학동로 171")
    }

    private var routeIndex = 0 // 클래스의 멤버 변수로 선언
    private fun startMovingLabel(route: List<LatLng>) {
        kakaoMap?.let { map ->
            val labelManager = map.getLabelManager()
            val labelLayer = labelManager?.getLayer()

            // 기존에 움직이는 라벨이 있으면 제거
            if (routeIndex == 0 && ::movingLabel.isInitialized) {
                labelLayer?.remove(movingLabel)
            }

            val labelStyles = LabelStyles.from(
                "myMovingLabelStyle",
                LabelStyle.from(R.drawable.icon_car_location).setTextStyles(70, Color.BLACK, 1, Color.WHITE)
                    .setZoomLevel(8),
                LabelStyle.from(R.drawable.icon_car_location).setTextStyles(70, Color.BLACK, 1, Color.WHITE)
                    .setZoomLevel(11),
                LabelStyle.from(R.drawable.icon_car_location).setTextStyles(70, Color.BLACK, 1, Color.WHITE)
                    .setZoomLevel(15)
            )

            val styles = labelManager?.addLabelStyles(labelStyles)

            // 라벨 옵션 설정
            val options = LabelOptions.from(LatLng.from(route[0]))
                .setStyles(styles)
                .setTexts("\uD83D\uDE98")

            // 움직이는 라벨 생성
            movingLabel = labelLayer?.addLabel(options)!!
            // 위치 업데이트 함수를 호출하여 경로에 따라 라벨 이동
            var routeIndex = 0 // 경로 인덱스 초기화
            updateLabelPosition(route, routeIndex)
        }
    }

    private fun updateLabelPosition(route: List<LatLng>, index: Int) {
        handler.postDelayed({
            if (index < route.size) {
                // 라벨 위치 업데이트
                movingLabel?.moveTo(route[index])

                // 다음 위치로 업데이트
                updateLabelPosition(route, index + 1)
            } else {
                // 최종 목적지에 도착했을 때 라벨 제거
                kakaoMap?.getLabelManager()?.getLayer()?.remove(movingLabel)
                routeIndex = 0 // routeIndex를 다시 초기화
            }
        }, 100) // 0.3초마다 위치 업데이트
    }
    private fun setCarPoolANDTaxiType() {
        val taxiType = TaxiType.getSize(intent.getStringExtra("taxiType") ?: "SMALL")
        val carPoolType = CarPoolType.getType(intent.getStringExtra("carPoolType") ?: "ALONE")
        mapViewModel.setTaxiType(taxiType)
        mapViewModel.setCarPoolType(carPoolType)
        mapViewModel.setPassengerType(taxiT = taxiType, carPoolT = carPoolType)

        }
    }