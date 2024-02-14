package org.softeer.robocar.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivityModifyPasswordBinding

class ModifyPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModifyPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_password)

        // 초기 경고 메시지 숨김
    }
}
