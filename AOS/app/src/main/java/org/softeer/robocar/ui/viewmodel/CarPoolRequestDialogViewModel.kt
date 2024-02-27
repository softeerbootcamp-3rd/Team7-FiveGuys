package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.softeer.robocar.data.repository.CarPool.CarPoolRepository
import javax.inject.Inject

@HiltViewModel
class CarPoolRequestDialogViewModel @Inject constructor(
    private val carPoolRepository: CarPoolRepository
):ViewModel() {
    
    fun saveCarPoolId(carPoolId: Long){
        viewModelScope.launch {
            carPoolRepository.saveCarPoolId(carPoolId)
        }
    }
}