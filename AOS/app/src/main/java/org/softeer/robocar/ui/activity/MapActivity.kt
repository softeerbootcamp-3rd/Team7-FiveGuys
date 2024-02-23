package org.softeer.robocar.ui.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.kakao.vectormap.*
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.route.*
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivityMapBinding
import org.softeer.robocar.ui.fragment.HeadcountDialogFragment
import java.util.*


@AndroidEntryPoint
class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    private lateinit var mapView: MapView
    private lateinit var locationManager: LocationManager
    private var kakaoMap: KakaoMap? = null // kakaoMap 객체 저장을 위한 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)

        // 위치 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
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

                // 1. RouteLineLayer 가져오기
                val layer = kakaoMap.routeLineManager!!.layer

                val stylesSet = RouteLineStylesSet.from(
                    "blueStyles",
                    RouteLineStyles.from(RouteLineStyle.from(16f, Color.BLUE))
                )

                val segment = RouteLineSegment.from(
                    Arrays.asList(
                        LatLng.from(37.514770, 127.033240),
                        LatLng.from(38.514770, 127.033240),
                    )
                ).setStyles(stylesSet.getStyles(0))

                val options = RouteLineOptions.from(segment).setStylesSet(stylesSet)
                val routeLine = layer.addRouteLine(options)

                // 여기서 위치 업데이트에 따라 카메라를 이동하지 않고, 초기 위치로 고정
            }
        })

        HeadcountDialogFragment().show(supportFragmentManager, "headCount")
    }

    private fun setupLocationUpdates() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = LocationListener { location ->
            // 받은 위치 정보로 지도의 카메라 업데이트
            val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(location.latitude, location.longitude))
            kakaoMap?.moveCamera(cameraUpdate, CameraAnimation.from(500, true, true))
        }

        // 위치 업데이트 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10f, locationListener, Looper.getMainLooper())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupLocationUpdates()
        }
    }
}
