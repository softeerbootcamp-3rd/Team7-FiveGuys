package org.softeer.robocar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.softeer.robocar.data.model.RouteSolo
import org.softeer.robocar.domain.usecase.GetOptimizedRouteSoloUseCase
import javax.inject.Inject

@HiltViewModel
class RouteSoloViewModel @Inject constructor(
    private val getOptimizedRouteSoloUseCase: GetOptimizedRouteSoloUseCase
) : ViewModel() {

    private var _routeSolo = MutableLiveData<RouteSolo>()
    val routeSolo: LiveData<RouteSolo> = _routeSolo

    fun getOptimizedRouteSolo(
        departureAddress: String,
        destAddress: String
    ) {
        viewModelScope.launch {
            runCatching {
                val optimizedRouteSolo = getOptimizedRouteSoloUseCase(departureAddress, destAddress)
                _routeSolo.value = optimizedRouteSolo
            }
                .onSuccess {
                    Log.d("Response", "테스트! $it")
                }
                .onFailure {
                    Log.d("Response", "테스트! $it")
                }
        }
    }
}
