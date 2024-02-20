package org.softeer.robocar.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivityRequestCarPoolBinding
import org.softeer.robocar.utils.setStatusBarTransparent

@AndroidEntryPoint
class RequestCarPoolActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestCarPoolBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_request_car_pool)
        setStatusBarTransparent(window)

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}