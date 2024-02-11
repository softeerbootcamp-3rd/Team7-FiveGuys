package org.softeer.robocar.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivityLoadingBinding
import org.softeer.robocar.utils.setStatusBarTransparent

class LoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loading)
        setStatusBarTransparent(window)
    }
}