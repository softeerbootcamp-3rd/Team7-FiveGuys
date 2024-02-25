package org.softeer.robocar.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivityRequestCarPoolBinding
import org.softeer.robocar.ui.adapter.DataPassListener
import org.softeer.robocar.ui.viewmodel.CarPoolRequestViewModel
import org.softeer.robocar.utils.setStatusBarTransparent

@AndroidEntryPoint
class RequestCarPoolActivity : AppCompatActivity(), DataPassListener {

    lateinit var binding: ActivityRequestCarPoolBinding
    private val viewModel: CarPoolRequestViewModel  by viewModels()
    private val args: RequestCarPoolActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_request_car_pool)
        setStatusBarTransparent(window)
        viewModel.setCarPoolInfo(args.CarPoolInformation)

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDataPassed(data: Long) {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("carPoolId", data)
        startActivity(intent)
    }
}