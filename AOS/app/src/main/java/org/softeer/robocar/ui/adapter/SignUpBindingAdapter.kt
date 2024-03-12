package org.softeer.robocar.ui.adapter

import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import org.softeer.robocar.R
import org.softeer.robocar.data.dto.user.response.CheckAvailableNicknameResponse
import org.softeer.robocar.data.dto.user.response.CheckAvailableUserIdResponse
import org.softeer.robocar.data.network.ApiResult
import org.softeer.robocar.utils.showToast

@BindingAdapter("activateButtonIfInputNotEmpty")
fun activateButtonIfInputNotEmpty(button: AppCompatButton, input: String) {
    if (input.isNotEmpty()) {
        button.background =
            ContextCompat.getDrawable(button.context, R.drawable.rectangle_hyundai_blue_radius_12)
        button.isClickable = true
    } else {
        button.background =
            ContextCompat.getDrawable(button.context, R.drawable.rectangle_gray_600_radius_12)
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
    isAvailableId: ApiResult,
    isAvailableNickname: ApiResult,
) {
    if (id.isNotEmpty() && password.isNotEmpty() && confirmedPassword.isNotEmpty() && nickname.isNotEmpty()
        && isPasswordMatching(
            password,
            confirmedPassword
        ) && (isAvailableId is ApiResult.Success<*> && (isAvailableId.data as CheckAvailableUserIdResponse).isAvailable)
        && isAvailableNickname is ApiResult.Success<*> && (isAvailableNickname.data as CheckAvailableNicknameResponse).isAvailable
    ) {
        button.isClickable = true
        button.background =
            ContextCompat.getDrawable(button.context, R.drawable.rectangle_hyundai_blue_radius_12)
    } else {
        button.background =
            ContextCompat.getDrawable(button.context, R.drawable.rectangle_gray_600_radius_12)
        button.isClickable = false
    }
}

@BindingAdapter("showIdAvailabilityMessage")
fun showIdAvailabilityMessage(textView: AppCompatTextView, idCheckState: ApiResult) {
    when (idCheckState) {
        is ApiResult.Loading -> {}
        is ApiResult.Success<*> -> {
            val isAvailable = (idCheckState.data as CheckAvailableUserIdResponse).isAvailable
            showUserIdAvailabilityMessage(textView, isAvailable)
        }

        is ApiResult.Failure -> {
            textView.context.showToast(
                textView.context.getString(R.string.internal_server_error_toast_message)
            )
        }

        is ApiResult.Exception -> {
            textView.context.showToast(
                textView.context.getString(R.string.network_error_toast_message)
            )
            textView.visibility = View.INVISIBLE
        }
    }
}

@BindingAdapter("showNicknameAvailabilityMessage")
fun showNicknameAvailabilityMessage(textView: AppCompatTextView, nicknameCheckState: ApiResult) {
    when (nicknameCheckState) {
        is ApiResult.Loading -> {}
        is ApiResult.Success<*> -> {
            val isAvailable =
                (nicknameCheckState.data as CheckAvailableNicknameResponse).isAvailable
            showUserNicknameAvailabilityMessage(textView, isAvailable)
        }

        is ApiResult.Failure -> {
            textView.context.showToast(
                textView.context.getString(R.string.internal_server_error_toast_message)
            )
        }

        is ApiResult.Exception -> {
            textView.context.showToast(
                textView.context.getString(R.string.network_error_toast_message)
            )
            textView.visibility = View.INVISIBLE
        }
    }
}

@BindingAdapter("passwordMatching", "confirmedPasswordMatching")
fun showPasswordMatchingMessage(
    textView: AppCompatTextView,
    password: String,
    confirmedPassword: String
) {
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

private fun AppCompatTextView.showDuplicateCheckMessage(message: String, textColor: Int) {
    text = message
    setTextColor(textColor)
    visibility = View.VISIBLE
}

private fun showUserIdAvailabilityMessage(textView: AppCompatTextView, isAvailableId: Boolean) {
    if (isAvailableId) {
        textView.showDuplicateCheckMessage(
            textView.context.getString(R.string.is_available_id),
            ContextCompat.getColor(textView.context, R.color.active_blue_pms_312C)
        )
    } else {
        textView.showDuplicateCheckMessage(
            textView.context.getString(R.string.is_already_exist_id),
            Color.RED
        )
    }
}

private fun showUserNicknameAvailabilityMessage(
    textView: AppCompatTextView,
    isAvailableId: Boolean
) {
    if (isAvailableId) {
        textView.showDuplicateCheckMessage(
            textView.context.getString(R.string.is_available_nickname),
            ContextCompat.getColor(textView.context, R.color.active_blue_pms_312C)
        )
    } else {
        textView.showDuplicateCheckMessage(
            textView.context.getString(R.string.is_already_exist_nickname),
            Color.RED
        )
    }
}