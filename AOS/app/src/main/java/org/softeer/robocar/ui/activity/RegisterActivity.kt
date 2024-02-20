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
import org.softeer.robocar.databinding.ActivityRegisterBinding
import org.softeer.robocar.ui.viewmodel.SignUpViewModel

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
            val isDone = viewModel.signUp()
            if (isDone) {
                goToLogin()
            } else {
                //TODO("회원가입 실패 다이얼로그 띄우기")
            }
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
