package org.softeer.robocar.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivityLoginBinding
import org.softeer.robocar.utils.setStatusBarTransparent

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setStatusBarTransparent(window)
    }
}