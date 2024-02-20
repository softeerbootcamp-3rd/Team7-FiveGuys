package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.softeer.robocar.data.model.CarPool
import javax.inject.Inject

class CarPoolDetailViewModel @Inject constructor(
    // 동승 신청 useCase 추가
) : ViewModel() {

    private var _carPool = MutableLiveData<CarPool>()
    val carPool: LiveData<CarPool> = _carPool

    private var _originalCharge = MutableLiveData<Int>()
    val originalCharge: LiveData<Int> = _originalCharge

    fun setCarPoolDetail(currentCarPool: CarPool, originalCharge: Int) {
        _carPool.value = currentCarPool
        _originalCharge.value = originalCharge
    }

}