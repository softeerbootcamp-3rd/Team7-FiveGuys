package org.softeer.robocar.ui.activity

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
import org.softeer.robocar.data.dto.login.request.LoginRequest
import org.softeer.robocar.databinding.ActivityLoginBinding
import org.softeer.robocar.ui.viewmodel.LoginViewModel
import org.softeer.robocar.utils.setStatusBarTransparent

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setStatusBarTransparent(window)

        binding.loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val loginRequest = LoginRequest(
            userId = getUserIdInput(),
            password = getUserPasswordInput()
        )

        lifecycleScope.launch {
            if (viewModel.login(loginRequest)) {
                goToHome()
            } else {
                showWarningMessage()
            }
        }
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getUserIdInput(): String {
        return binding.idInputText.text.toString()
    }

    private fun getUserPasswordInput(): String {
        return binding.passwordInputText.text.toString()
    }

    private fun showWarningMessage() {
        binding.waringMessage.visibility = View.VISIBLE
    }
}