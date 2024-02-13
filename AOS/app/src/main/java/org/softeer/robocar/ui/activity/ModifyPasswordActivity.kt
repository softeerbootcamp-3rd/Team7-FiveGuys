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
        // 여기에서 R.layout.activity_modify_password로 변경해야 합니다.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_password)

        // 초기 경고 메시지 숨김
        hideWarningMessages()
    }

    private fun hideWarningMessages() {
        // 모든 경고 메시지를 숨기기
        binding.passwordWarningMessage.visibility = View.GONE
        // 필요에 따라 추가된 다른 경고 메시지도 숨김 처리
    }
}
