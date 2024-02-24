package org.softeer.robocar.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.softeer.robocar.R
import org.softeer.robocar.data.dto.login.request.LoginRequest
import org.softeer.robocar.data.service.firebase.RoboCarFireBaseMessagingService
import org.softeer.robocar.databinding.ActivityLoginBinding
import org.softeer.robocar.di.RoboCarApplication
import org.softeer.robocar.ui.viewmodel.LoginViewModel
import org.softeer.robocar.utils.interceptor.ResponseLoggingInterceptor.Companion.TAG
import org.softeer.robocar.utils.setStatusBarTransparent

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setStatusBarTransparent(window)

        with(binding) {
            loginButton.setOnClickListener { login() }
            signUpButton.setOnClickListener { goToSignUp() }
        }

        //requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        askNotificationPermission()
        lifecycleScope.launch {
            RoboCarFireBaseMessagingService().onNewToken("")
        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            RoboCarApplication.FCMToken = task.result
        })

    }

    private fun login() {
        val loginRequest = LoginRequest(
            userId = getUserIdInput(),
            password = getUserPasswordInput(),
            FCMToken = RoboCarApplication.FCMToken!!
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

    private fun goToSignUp() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
