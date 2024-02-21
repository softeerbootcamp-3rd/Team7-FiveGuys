package org.softeer.robocar.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.overlay.ArrowheadPathOverlay
import org.softeer.robocar.R

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        // 경로선 추가
        val path = PathOverlay()
        path.coords = listOf(
            LatLng(37.57152, 126.97714),
            LatLng(37.56607, 126.98268),
            LatLng(37.56445, 126.97707),
            LatLng(37.55855, 126.97822)
        )
        path.map = naverMap

        // 화살표 추가
        val arrowheadPath = ArrowheadPathOverlay()
        arrowheadPath.coords = listOf(
            LatLng(37.568003, 126.9772503),
            LatLng(37.5701573, 126.9772503),
            LatLng(37.5701573, 126.9793745)
        )
        arrowheadPath.width = 20 // 화살표 두께 설정
        arrowheadPath.color = Color.GREEN // 화살표 색상 설정
        arrowheadPath.map = naverMap
    }
}
