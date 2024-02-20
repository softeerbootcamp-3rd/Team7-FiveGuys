package org.softeer.robocar.ui.adapter

import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import org.softeer.robocar.R

@BindingAdapter("activateButtonIfInputNotEmpty")
fun activateButtonIfInputNotEmpty(button: AppCompatButton, input: String) {
    if (input.isNotEmpty()) {
        button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_hyundai_blue_radius_12)
        button.isClickable = true
    } else {
        button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_gray_600_radius_12)
        button.isClickable = false
    }
}

@BindingAdapter(
    "checkIdInputValid",
    "checkPasswordInputValid",
    "checkConfirmedPasswordInputValid",
    "checkNicknameInputValid",
    "checkAvailableId",
    "checkAvailableNickname",
)
fun activeButtonIfAllInputsComplete(
    button: AppCompatButton,
    id: String,
    password: String,
    confirmedPassword: String,
    nickname: String,
    isAvailableId: Boolean,
    isAvailableNickname: Boolean,
) {
    if (id.isNotEmpty() && password.isNotEmpty() && confirmedPassword.isNotEmpty() && nickname.isNotEmpty()
        && isPasswordMatching(password, confirmedPassword) && isAvailableId && isAvailableNickname
    ) {
        button.isClickable = true
        button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_hyundai_blue_radius_12)
    } else {
        button.background = ContextCompat.getDrawable(button.context, R.drawable.rectangle_gray_600_radius_12)
        button.isClickable = false
    }
}

@BindingAdapter("showIdAvailabilityMessage")
fun showIdAvailabilityMessage(textView: AppCompatTextView, isAvailable: Boolean?) {
    if (isAvailable == null)
        return

    if (isAvailable) {
        textView.text = "사용 가능한 아이디입니다."
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.active_blue_pms_312C))
    } else {
        textView.text = "이미 존재하는 아이디입니다."
        textView.setTextColor(Color.RED)
    }
    textView.visibility = View.VISIBLE
}

@BindingAdapter("showNicknameAvailabilityMessage")
fun showNicknameAvailabilityMessage(textView: AppCompatTextView, isAvailable: Boolean?) {
    if (isAvailable == null)
        return

    if (isAvailable) {
        textView.text = "사용 가능한 닉네임입니다."
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.active_blue_pms_312C))
    } else {
        textView.text = "이미 존재하는 닉네임입니다."
        textView.setTextColor(Color.RED)
    }
    textView.visibility = View.VISIBLE
}

@BindingAdapter("passwordMatching", "confirmedPasswordMatching")
fun showPasswordMatchingMessage(textView: AppCompatTextView, password: String, confirmedPassword: String) {
    if (confirmedPassword.isEmpty())
        return

    if (!isPasswordMatching(password, confirmedPassword)) {
        textView.visibility = View.VISIBLE
    } else {
        textView.visibility = View.GONE
    }
}

private fun isPasswordMatching(password: String, confirmedPassword: String): Boolean {
    return password.equals(confirmedPassword)
}