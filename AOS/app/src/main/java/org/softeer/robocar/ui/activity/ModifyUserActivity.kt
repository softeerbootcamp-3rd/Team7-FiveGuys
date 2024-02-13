package org.softeer.robocar.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivityModifyUserBinding

class ModifyUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModifyUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_user)
        hideWarningMessages()
        val commonTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트 변경 전에 호출됨
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // nickNameInputText의 텍스트가 변경될 때
                updateButtonBackground(binding.nickNameInputText, binding.nickNameCheckButton)
            }

            override fun afterTextChanged(s: Editable?) {
                // 텍스트 변경 후에 호출됨
            }
        }

        binding.nickNameInputText.addTextChangedListener(commonTextWatcher)
        // 로그인된 사용자의 아이디를 설정, 실제 앱에서는 사용자 데이터를 관리하는 로직에 따라 변경필요
        val userId = "userid" // 사용자 아이디
        binding.idText.text = getString(R.string.user_id, userId)
    }
    private fun updateButtonBackground(editText: EditText, button: Button) {
        val backgroundResource = if (editText.text.isNotEmpty()) {
            R.drawable.rectangle_hyundai_blue_radius_12 // 텍스트가 있으면 파란색 배경
        } else {
            R.drawable.rectangle_gray_600_radius_12 // 텍스트가 없으면 회색 배경
        }
        button.setBackgroundResource(backgroundResource)
    }
    private fun hideWarningMessages() {
        binding.nicknameWarningMessage.visibility = View.GONE
    }
}
