package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.softeer.robocar.data.model.CarPool
import org.softeer.robocar.data.model.CarPools
import org.softeer.robocar.domain.usecase.GetCarPoolListUseCase
import org.softeer.robocar.domain.usecase.RequestCarPoolUseCase
import javax.inject.Inject

class CarPoolListViewModel @Inject constructor(
    private val getCarPoolListUseCase: GetCarPoolListUseCase,
    private val requestCarPoolUseCase: RequestCarPoolUseCase,
) : ViewModel() {

    private var _carPoolList = MutableLiveData<CarPools>()
    val carPoolList : LiveData<CarPools> = _carPoolList

    fun getCarPoolList(
        startLocation : String,
        destinationLocation : String
    ) {
        viewModelScope.launch{
           val carPools = getCarPoolListUseCase(startLocation, destinationLocation)
            _carPoolList.value = carPools
        }
    }

    fun requestCarPool(
        carPool: CarPool
    ) {
        viewModelScope.launch {
            requestCarPoolUseCase(carPool)
        }
    }
}