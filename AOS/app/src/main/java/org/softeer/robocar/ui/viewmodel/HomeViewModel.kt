package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.softeer.robocar.data.model.CarPoolType
import org.softeer.robocar.data.model.TaxiType
import javax.inject.Inject

class HomeViewModel @Inject constructor(

) : ViewModel() {

    private var _taxiType = MutableLiveData<TaxiType>()
    val taxiType: LiveData<TaxiType> = _taxiType

    private var _carPoolType = MutableLiveData<CarPoolType>()
    val carPoolType: LiveData<CarPoolType> = _carPoolType

    init {
        _taxiType.value = TaxiType.COMPACT_TAXI
        _carPoolType.value = CarPoolType.ALONE
    }

    fun setTaxiType(currentTaxiType: TaxiType) {
        _taxiType.value = currentTaxiType
    }

    fun setCarPoolType(currentCarPoolType: CarPoolType) {
        _carPoolType.value = currentCarPoolType
    }
}