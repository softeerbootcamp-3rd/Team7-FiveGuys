package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.softeer.robocar.data.model.CarPools
import org.softeer.robocar.domain.usecase.GetCarPoolListUseCase
import javax.inject.Inject

class CarPoolListViewModel @Inject constructor(
    private val getCarPoolListUseCase: GetCarPoolListUseCase
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
}