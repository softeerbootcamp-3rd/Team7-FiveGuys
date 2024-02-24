package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.model.TaxiType
import org.softeer.robocar.domain.usecase.RegisterCarPoolUseCase
import javax.inject.Inject

@HiltViewModel
class CarPoolMatchingViewModel @Inject constructor(
    private val registerCarPoolUseCase: RegisterCarPoolUseCase
) : ViewModel() {

    private var _startLocation = MutableLiveData<String>()
    val startLocation: LiveData<String> = _startLocation

    private var _destinationLocation = MutableLiveData<String>()
    val destinationLocation: LiveData<String> = _destinationLocation

    private var _countOfMen = MutableLiveData<Int>()
    val countOfMen: LiveData<Int> = _countOfMen

    private var _countOfWomen = MutableLiveData<Int>()
    val countOfWomen: LiveData<Int> = _countOfWomen

    private var _carType = MutableLiveData<TaxiType>()
    val carType: LiveData<TaxiType> = _carType

    // TODO 값 받아오게 되면 init 없애기
    init {
        _startLocation.value = "서울 강남구 학동로 180"
        _destinationLocation.value = "서울 강서구 하늘길 111 국내선 주차대기실"
        _countOfMen.value = 1
        _countOfWomen.value = 0
        _carType.value = TaxiType.COMPACT_TAXI
    }

    fun registerCarPool() {
        viewModelScope.launch {
            registerCarPoolUseCase(
                registerCarPoolRequest(
                    departAddress = startLocation.value!!,
                    destAddress = destinationLocation.value!!,
                    carType = carType.value!!.size,
                    countOfWomen = countOfWomen.value!!,
                    countOfMen = countOfMen.value!!
                )
            )
        }
    }
}