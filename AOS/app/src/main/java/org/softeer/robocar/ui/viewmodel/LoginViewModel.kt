package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import org.softeer.robocar.data.dto.login.request.LoginRequest
import org.softeer.robocar.domain.usecase.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    suspend fun login(loginRequest: LoginRequest): Boolean {
        return viewModelScope.async {
            loginUseCase(loginRequest)
        }.await()
    }

}