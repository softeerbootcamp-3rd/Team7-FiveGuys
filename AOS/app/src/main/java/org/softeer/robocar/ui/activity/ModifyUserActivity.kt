package org.softeer.robocar.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import org.softeer.robocar.R
import org.softeer.robocar.databinding.ActivityModifyUserBinding

class ModifyUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModifyUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_user)

        binding.nickNameInputText.doOnTextChanged { text, _, _, _ ->
            updateButtonBackground(binding.nickNameInputText, binding.nickNameCheckButton)
        }
    }
    private fun updateButtonBackground(editText: EditText, button: Button) {
        val backgroundResource = if (editText.text.isNotEmpty()) {
            R.drawable.rectangle_hyundai_blue_radius_12 // 텍스트가 있으면 파란색 배경
        } else {
            R.drawable.rectangle_gray_600_radius_12 // 텍스트가 없으면 회색 배경
        }
        button.setBackgroundResource(backgroundResource)
    }
}
