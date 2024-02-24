package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.softeer.robocar.data.model.Route
import org.softeer.robocar.data.model.RouteSolo
import org.softeer.robocar.domain.usecase.GetOptimizedRouteSoloUseCase
import org.softeer.robocar.domain.usecase.GetOptimizedRouteUseCase
import javax.inject.Inject

class RouteSoloViewModel @Inject constructor(
    private val getOptimizedRouteSoloUseCase: GetOptimizedRouteSoloUseCase
) : ViewModel() {

    private var _routeSolo = MutableLiveData<RouteSolo>()
    val routeSolo: LiveData<RouteSolo> = _routeSolo

    fun getOptimizedRouteSolo(
        departureAddress: String,
        destAddress: String
    ) {
        viewModelScope.launch {
            val optimizedRouteSolo = getOptimizedRouteSoloUseCase(departureAddress, destAddress)
            _routeSolo.value = optimizedRouteSolo
        }
    }
}
