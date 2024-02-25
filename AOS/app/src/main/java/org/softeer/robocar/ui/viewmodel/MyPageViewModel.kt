package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.softeer.robocar.data.repository.auth.AuthRepository
import org.softeer.robocar.data.repository.user.UserRepository
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
):ViewModel() {

    private var _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickname

    init {
        viewModelScope.launch{
            _nickname.value = authRepository.getUserInfo().nickname
        }
    }
}