package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReceivedCarPoolRequestDialogViewModel @Inject constructor(

): ViewModel() {

    private var _guestId = MutableLiveData<Long>()
    val guestId: LiveData<Long> = _guestId

    private var _maleCount = MutableLiveData<Int>()
    val maleCount: LiveData<Int> = _maleCount

    private var _femaleCount = MutableLiveData<Int>()
    val femaleCount: LiveData<Int> = _femaleCount

    private var _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickname

    private var _guestAddress = MutableLiveData<String>()
    val guestAddress: LiveData<String> = _guestAddress


    fun setRequestCarPoolInfo(
        guestId: Long,
        maleCount: Int,
        femaleCount: Int,
        nickname: String,
        guestAddress: String,
    ){
        _guestId.value = guestId
        _maleCount.value = maleCount
        _femaleCount.value = femaleCount
        _nickname.value = nickname
        _guestAddress.value = guestAddress
    }
}