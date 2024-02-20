package org.softeer.robocar.ui.adapter

import androidx.appcompat.widget.AppCompatButton
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

private fun isPasswordMatching(password: String, confirmedPassword: String): Boolean {
    return password.equals(confirmedPassword)
}