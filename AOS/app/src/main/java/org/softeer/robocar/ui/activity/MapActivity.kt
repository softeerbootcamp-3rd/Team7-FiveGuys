
package org.softeer.robocar.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kakao.vectormap.*
import com.kakao.vectormap.route.*
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivityMapBinding
import org.softeer.robocar.ui.fragment.HeadcountDialogFragment
import java.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)

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

                // 1. RouteLineLayer 가져오기
                val layer = kakaoMap.routeLineManager!!.layer


                val stylesSet = RouteLineStylesSet.from(
                    "blueStyles",
                    RouteLineStyles.from(RouteLineStyle.from(16f, Color.BLUE))
                )

                val segment = RouteLineSegment.from(
                    Arrays.asList(
                        LatLng.from(37.3597049, 127.1059979),
                        LatLng.from(37.3597031, 127.1059979),
                    )
                )
                    .setStyles(stylesSet.getStyles(0))

                val options = RouteLineOptions.from(segment)
                    .setStylesSet(stylesSet)

                val routeLine = layer.addRouteLine(options)
            }
        })

        HeadcountDialogFragment().show(supportFragmentManager, "headCount")
    }
}