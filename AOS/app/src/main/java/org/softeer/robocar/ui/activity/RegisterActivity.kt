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
import org.softeer.robocar.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)


        // 공통 TextWatcher 인스턴스 생성
        val commonTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트 변경 전에 호출됨
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // idInputText의 텍스트가 변경될 때
                updateButtonBackground(binding.idInputText, binding.idCheckButton)

                // nickNameInputText의 텍스트가 변경될 때
                updateButtonBackground(binding.nickNameInputText, binding.nickNameCheckButton)
            }

            override fun afterTextChanged(s: Editable?) {
                // 텍스트 변경 후에 호출됨
            }
        }

        // 공통 TextWatcher를 두 EditText에 적용
        binding.idInputText.addTextChangedListener(commonTextWatcher)
        binding.nickNameInputText.addTextChangedListener(commonTextWatcher)
    }

    // 버튼 배경을 업데이트하는 공통 함수
    private fun updateButtonBackground(editText: EditText, button: Button) {
        val backgroundResource = if (editText.text.isNotEmpty()) {
            R.drawable.rectangle_hyundai_blue_radius_12 // 텍스트가 있으면 파란색 배경
        } else {
            R.drawable.rectangle_gray_600_radius_12 // 텍스트가 없으면 회색 배경
        }
        button.setBackgroundResource(backgroundResource)
    }

    private fun hideWarningMessages() {
        // 모든 경고 메시지를 숨기기
        binding.idWarningMessage.visibility = View.GONE
        binding.passwordWarningMessage.visibility = View.GONE
        binding.nicknameWarningMessage.visibility = View.GONE
    }
}
