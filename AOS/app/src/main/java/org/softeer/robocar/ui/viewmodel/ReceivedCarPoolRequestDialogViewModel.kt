package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.softeer.robocar.domain.usecase.AcceptCarPoolRequestUseCase
import org.softeer.robocar.domain.usecase.RejectCarPoolRequestUseCase
import javax.inject.Inject

@HiltViewModel
class ReceivedCarPoolRequestDialogViewModel @Inject constructor(
    private val rejectCarPoolRequestUseCase: RejectCarPoolRequestUseCase,
    private val acceptCarPoolRequestUseCase: AcceptCarPoolRequestUseCase
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

    private var _carPoolId = MutableLiveData<Long>()
    val carPoolId: LiveData<Long> = _carPoolId


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
        _carPoolId.value = -1
    }

    fun rejectCarPoolRequest(
    ){
        viewModelScope.launch {
            rejectCarPoolRequestUseCase(
                guestId.value!!
            )
                .onSuccess {
                }
                .onFailure {
                }

        }
    }

    fun acceptCarPoolRequest(){
        viewModelScope.launch {
            acceptCarPoolRequestUseCase(
                guestId.value!!,
                guestAddress.value!!
            )
                .onSuccess {
                    _carPoolId.value = it.inOperationId
                }

                .onFailure {
                    _carPoolId.value = -1
                }
        }
    }
}