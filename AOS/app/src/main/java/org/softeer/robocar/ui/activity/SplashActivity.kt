package org.softeer.robocar.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivitySplashBinding
import org.softeer.robocar.ui.viewmodel.SplashViewModel

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity: AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        lifecycleScope.launch {
            viewModel.verifyUserToken()
                .onSuccess {
                    goToHome()
                }
                .onFailure {
                    goToLogin()
                }
        }
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}