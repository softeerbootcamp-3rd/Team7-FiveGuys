package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.softeer.robocar.data.dto.user.request.SignUpRequest
import org.softeer.robocar.domain.usecase.CheckDuplicatedNicknameUseCase
import org.softeer.robocar.domain.usecase.CheckDuplicatedUserIdUseCase
import org.softeer.robocar.domain.usecase.SignUpUseCase
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val checkDuplicatedUserIdUseCase: CheckDuplicatedUserIdUseCase,
    private val checkDuplicatedNicknameUseCase: CheckDuplicatedNicknameUseCase,
) : ViewModel() {

    var userId = MutableLiveData<String>()

    var password = MutableLiveData<String>()

    var passwordConfirmation = MutableLiveData<String>()

    var nickname = MutableLiveData<String>()

    private var _isIdAvailable = MutableLiveData<Boolean?>()
    val isIdAvailable: LiveData<Boolean?> = _isIdAvailable

    private var _isNicknameAvailable = MutableLiveData<Boolean?>()
    val isNicknameAvailable: LiveData<Boolean?> = _isNicknameAvailable

    init {
        userId.value = ""
        password.value = ""
        passwordConfirmation.value = ""
        nickname.value = ""
    }

    suspend fun signUp(): Boolean {
        return viewModelScope.async {
            signUpUseCase(
                SignUpRequest(
                    userId = userId.value!!,
                    password = password.value!!,
                    nickname = nickname.value!!,
                )
            )
        }.await()
    }

    fun checkDuplicatedUserId(userId: String) {
        viewModelScope.launch {
            checkDuplicatedUserIdUseCase(userId)
                .onSuccess {
                    _isIdAvailable.value = it.isAvailable
                }
                .onFailure { }
        }
    }

    fun checkDuplicatedNickname(nickname: String) {
        viewModelScope.launch {
            checkDuplicatedNicknameUseCase(nickname)
                .onSuccess {
                    _isNicknameAvailable.value = it.isAvailable
                }
                .onFailure { }
        }
    }
}