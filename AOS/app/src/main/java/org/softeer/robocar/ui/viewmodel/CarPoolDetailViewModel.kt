package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.domain.usecase.RequestCarPoolUseCase
import javax.inject.Inject

@HiltViewModel
class CarPoolDetailViewModel @Inject constructor(
    private val requestCarPoolUseCase: RequestCarPoolUseCase
) : ViewModel() {

    private var _carPool = MutableLiveData<CarPool>()
    val carPool: LiveData<CarPool> = _carPool

    private var _originalCharge = MutableLiveData<Int>()
    val originalCharge: LiveData<Int> = _originalCharge

    fun setCarPoolDetail(currentCarPool: CarPool, originalCharge: Int) {
        _carPool.value = currentCarPool
        _originalCharge.value = originalCharge
    }

    fun requestCarPool(
        carPool: CarPool,
        destinationLocation: String
    ) {
        viewModelScope.launch {
            requestCarPoolUseCase(
                carPool,
                destinationLocation
            )
                .onSuccess {

                }
                .onFailure {

                }
        }
    }

}