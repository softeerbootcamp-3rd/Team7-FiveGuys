package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.softeer.robocar.data.repository.CarPool.CarPoolRepository
import org.softeer.robocar.data.repository.auth.AuthRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val carPoolRepository: CarPoolRepository
) : ViewModel() {
    suspend fun verifyUserToken(): Result<Unit> {
        return authRepository.verifyUserToken()
    }
    suspend fun checkOperationStatus(): Result<Unit> {
        return carPoolRepository.checkOperationStatus()
    }

    suspend fun getCarPoolId(): Long{
        return carPoolRepository.getCarPoolId()
    }
}