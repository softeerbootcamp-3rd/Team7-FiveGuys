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
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.kakao.vectormap.*
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.route.*
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivityMapBinding
import org.softeer.robocar.ui.fragment.HeadcountDialogFragment
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import org.softeer.robocar.ui.viewmodel.MapViewModel

@AndroidEntryPoint
class MapActivity : AppCompatActivity() {
    private val args: MapActivityArgs by navArgs()
    private lateinit var binding: ActivityMapBinding
    private lateinit var mapView: MapView
    private lateinit var locationManager: LocationManager
    private var kakaoMap: KakaoMap? = null
    private var currentLocation: Location? = null
    private val mapViewModel: MapViewModel by viewModels()
    private var currentLocationLabel: Label? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        mapViewModel.setPassengerType(args.taxiType,args.carPoolType)

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
                observeRouteData() // 경로 데이터 관찰 시작
            }
        })
        mapViewModel.getOptimizedRoute("영등포동5가 34-1", "서울특별시 강남구 학동로 180", "분당구 정자동 50-3", 1, 2)
        HeadcountDialogFragment().show(supportFragmentManager, "headCount")
        setupCurrentLocationButton()
    }

    // 경로 데이터 관찰 및 경로 그리기
    private fun observeRouteData() {
        mapViewModel.route.observe(this) { route ->
            route?.let {
                drawRoute(kakaoMap!!, it.guestNodes) // API 호출 결과에 따라 hostNodes 또는 guestNodes 사용
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
        Log.d("MapActivity", "setupLocationUpdates called")
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = LocationListener { location ->
            Log.d("MapActivity", "Location updated: Lat=${location.latitude}, Lon=${location.longitude}")
            currentLocation = location
            addCurrentLocationLabel(location.latitude, location.longitude)
            updateCameraToCurrentLocation()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10f, locationListener, Looper.getMainLooper())
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lastKnownLocation != null) {
                Log.d("MapActivity", "Last known location: Lat=${lastKnownLocation.latitude}, Lon=${lastKnownLocation.longitude}")
                currentLocation = lastKnownLocation
                addCurrentLocationLabel(lastKnownLocation.latitude, lastKnownLocation.longitude)
                updateCameraToCurrentLocation()
            } else {
                Log.d("MapActivity", "Last known location is null")
            }
        } else {
            Log.d("MapActivity", "Location permission not granted")
        }
    }

    private fun updateCameraToCurrentLocation() {
        currentLocation?.let { location ->
            kakaoMap?.let { map ->
                val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(location.latitude, location.longitude))
                map.moveCamera(cameraUpdate, CameraAnimation.from(10, true, true))
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
                kakaoMap?.moveCamera(cameraUpdate, CameraAnimation.from(10, true, true))
            }
        }
    }
}