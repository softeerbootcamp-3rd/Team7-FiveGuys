package org.softeer.robocar.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.softeer.robocar.data.model.Route
import org.softeer.robocar.domain.usecase.GetOptimizedRouteUseCase
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(
    private val getOptimizedRouteUseCase: GetOptimizedRouteUseCase
) : ViewModel() {

    private var _route = MutableLiveData<Route>()
    val route: LiveData<Route> = _route

    fun getOptimizedRoute(
        departureAddress: String,
        hostDestAddress: String,
        guestDestAddress: String
    ) {
        viewModelScope.launch {
            val optimizedRoute = getOptimizedRouteUseCase(departureAddress, hostDestAddress, guestDestAddress)
            _route.value = optimizedRoute
        }
    }
}
