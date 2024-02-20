package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

) : ViewModel() {

    var userId = MutableLiveData<String>()

    var password = MutableLiveData<String>()

    var passwordConfirmation = MutableLiveData<String>()

    var nickname = MutableLiveData<String>()

    private var _isIdAvailable = MutableLiveData<Boolean>()
    val isIdAvailable: LiveData<Boolean> = _isIdAvailable

    private var _isNicknameAvailable = MutableLiveData<Boolean>()
    val isNicknameAvailable: LiveData<Boolean> = _isNicknameAvailable


    init {
        userId.value = ""
        password.value = ""
        passwordConfirmation.value = ""
        nickname.value = ""
    }
}