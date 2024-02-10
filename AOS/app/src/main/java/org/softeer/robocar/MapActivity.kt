package org.softeer.robocar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kakao.vectormap.MapView

class MapActivity : AppCompatActivity() {
    lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MapFragment.newInstance())
                .commitNow()
        }
    }
}