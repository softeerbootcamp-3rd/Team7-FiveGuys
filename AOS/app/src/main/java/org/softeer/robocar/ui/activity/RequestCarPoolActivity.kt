package org.softeer.robocar.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.softeer.robocar.R
import org.softeer.robocar.utils.setStatusBarTransparent

class RequestCarPoolActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_car_pool)
        setStatusBarTransparent(window)
    }
}