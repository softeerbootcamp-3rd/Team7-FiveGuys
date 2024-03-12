package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.softeer.robocar.data.dto.user.request.SignUpRequest
import org.softeer.robocar.data.network.ApiResult
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

    private var _idCheckState = MutableLiveData<ApiResult>(ApiResult.Loading)
    val idCheckState: LiveData<ApiResult> = _idCheckState

    private var _nicknameCheckState = MutableLiveData<ApiResult>(ApiResult.Loading)
    val nicknameCheckState: LiveData<ApiResult> = _nicknameCheckState

    private var _signUpState = MutableLiveData<ApiResult>(ApiResult.Loading)
    val signUpState: LiveData<ApiResult> = _signUpState

    init {
        userId.value = ""
        password.value = ""
        passwordConfirmation.value = ""
        nickname.value = ""
    }

    suspend fun signUp() {
        viewModelScope.launch {
            val result = signUpUseCase(
                SignUpRequest(
                    userId = userId.value!!,
                    password = password.value!!,
                    nickname = nickname.value!!,
                )
            )

            when(result){
                is ApiResult.Loading -> {

                }
                is ApiResult.Success<*> -> {
                    _signUpState.value = ApiResult.Success(result.data)
                }
                is ApiResult.Failure -> {
                    _signUpState.value = ApiResult.Failure(
                        code = result.code,
                        message = result.message
                    )
                }
                is ApiResult.Exception -> {
                    _signUpState.value = ApiResult.Exception(
                        e = result.e
                    )
                }
            }

        }
    }

    fun checkDuplicatedUserId(userId: String) {
        viewModelScope.launch {
            when(val result = checkDuplicatedUserIdUseCase(userId)){
                is ApiResult.Loading -> {

                }
                is ApiResult.Success<*> -> {
                    _idCheckState.value = ApiResult.Success(result.data)
                }
                is ApiResult.Failure -> {
                    _idCheckState.value = ApiResult.Failure(
                        code = result.code,
                        message = result.message
                    )
                }
                is ApiResult.Exception -> {
                    _idCheckState.value = ApiResult.Exception(
                        e = result.e
                    )
                }
            }
        }
    }

    fun checkDuplicatedNickname(nickname: String) {
        viewModelScope.launch {
            when(val result = checkDuplicatedNicknameUseCase(nickname)){
                is ApiResult.Loading -> {

                }
                is ApiResult.Success<*> -> {
                    _nicknameCheckState.value = ApiResult.Success(result.data)
                }
                is ApiResult.Failure -> {
                    _nicknameCheckState.value = ApiResult.Failure(
                        code = result.code,
                        message = result.message
                    )
                }
                is ApiResult.Exception -> {
                    _nicknameCheckState.value = ApiResult.Exception(
                        e = result.e
                    )
                }
            }
        }
    }
}