package org.softeer.robocar.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.softeer.robocar.R
import org.softeer.robocar.data.network.ApiResult
import org.softeer.robocar.databinding.ActivityRegisterBinding
import org.softeer.robocar.ui.viewmodel.SignUpViewModel
import org.softeer.robocar.utils.showToast

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: SignUpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        with(binding) {
            lifecycleOwner = this@RegisterActivity
            signUpViewModel = viewModel
            registerButton.setOnClickListener {
                signUp()
            }
        }

    }

    private fun signUp() {
        lifecycleScope.launch {
            viewModel.signUp()
            viewModel.signUpState.observe(this@RegisterActivity){result ->
                when (result) {
                    is ApiResult.Success<*> -> {
                        goToLogin()
                    }
                    is ApiResult.Loading -> {

                    }
                    is ApiResult.Failure -> {
                        showSignUpFailure(result.code)
                    }
                    is ApiResult.Exception -> {
                        showToast(getString(R.string.network_error_toast_message))
                    }
                }
            }
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showSignUpFailure(code: Int){
        when(code){
            409 -> {
                showToast(getString(R.string.is_already_exist_id_or_nickname))
            }
            500 -> {
                showToast(getString(R.string.internal_server_error_toast_message))
            }
        }
    }
}
