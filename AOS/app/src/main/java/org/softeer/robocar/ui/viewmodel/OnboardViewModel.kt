package org.softeer.robocar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.softeer.robocar.data.dto.operation.OnboardData
import org.softeer.robocar.data.dto.operation.OnboardResponse
import org.softeer.robocar.domain.usecase.OnboardDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val onboardDetailsUseCase: OnboardDetailsUseCase
) : ViewModel() {

    private val _onboardDetails = MutableLiveData<OnboardData>()
    val onboardDetails: LiveData<OnboardData> = _onboardDetails

    fun fetchOnboardDetails(inOperationId: Int) {
        viewModelScope.launch {
            onboardDetailsUseCase(inOperationId).let { result ->
                result.onSuccess { onboard ->
                    _onboardDetails.value = onboard
                }.onFailure { error ->
                    Log.d("Response", "운행정보 조회 오류")
                }
            }
        }
    }
}
