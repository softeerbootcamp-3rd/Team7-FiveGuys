package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.softeer.robocar.data.model.PlaceItem
import javax.inject.Inject

@HiltViewModel
class CarPoolRequestViewModel @Inject constructor(

) : ViewModel() {

    private val _carPoolInfo = MutableLiveData<PlaceItem>()
    var carPoolInfo: LiveData<PlaceItem> = _carPoolInfo

    fun setCarPoolInfo(carPoolInfo: PlaceItem) {
        _carPoolInfo.value = carPoolInfo
    }
}