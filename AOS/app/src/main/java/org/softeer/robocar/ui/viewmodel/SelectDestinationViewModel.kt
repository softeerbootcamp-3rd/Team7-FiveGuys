package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectDestinationViewModel: ViewModel() {
    private var _destName = MutableLiveData<String>()
    val destName: LiveData<String> = _destName
    private var _destRoadAddress = MutableLiveData<String>()
    val destRoadAddress: LiveData<String> = _destRoadAddress

    init {
        _destName.value = ""
        _destRoadAddress.value = ""
    }

    fun setDestInfo(destName: String, destRoadAddress: String) {
        _destName.value = destName
        _destRoadAddress.value = destRoadAddress
    }

    //TODO 쌓인 데이터 POST, Loading Activity로 이동
}