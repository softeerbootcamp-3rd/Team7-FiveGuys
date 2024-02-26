package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.softeer.robocar.data.dto.carpool.request.registerCarPoolRequest
import org.softeer.robocar.data.model.PlaceItem
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

    fun setCarPoolInfo(
        startLocation: String,
        destinationLocation: String,
        countOfMen: Int,
        countOfWomen: Int,
        carType: TaxiType
    ) {
        _startLocation.value = startLocation
        _destinationLocation.value = destinationLocation
        _countOfMen.value = countOfMen
        _countOfWomen.value = countOfWomen
        _carType.value = carType
    }

}