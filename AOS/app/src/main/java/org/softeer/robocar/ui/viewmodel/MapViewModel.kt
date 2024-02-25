package org.softeer.robocar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.softeer.robocar.BuildConfig
import org.softeer.robocar.data.dto.placesearch.Place
import org.softeer.robocar.data.model.CarPoolType
import org.softeer.robocar.data.model.PlaceItem
import org.softeer.robocar.data.model.Route
import org.softeer.robocar.data.model.TaxiType
import org.softeer.robocar.domain.usecase.GetOptimizedRouteUseCase
import org.softeer.robocar.domain.usecase.SearchPlaceUseCase
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val searchPlaceUseCase: SearchPlaceUseCase,
    private val getOptimizedRouteUseCase: GetOptimizedRouteUseCase
): ViewModel() {

    var keyWord = MutableLiveData<String>()
    private var _placeList = MutableLiveData<List<Place>>()
    val placeList: LiveData<List<Place>> = _placeList
    private var _route = MutableLiveData<Route>()
    val route: LiveData<Route> = _route

    private var _taxiType = MutableLiveData<TaxiType>()
    val taxiType: LiveData<TaxiType> = _taxiType
    private var _carPoolType = MutableLiveData<CarPoolType>()
    val carPoolType: LiveData<CarPoolType> = _carPoolType
    private var _countMale = MutableLiveData<String>()
    val countMale: LiveData<String> = _countMale
    private var _countFemale = MutableLiveData<String>()
    val countFemale: LiveData<String> = _countFemale
    private var _destName = MutableLiveData<String>()
    val destName: LiveData<String> = _destName
    private var _destRoadAddress = MutableLiveData<String>()
    val destRoadAddress: LiveData<String> = _destRoadAddress

    init {
        _countMale.value = "0"
        _countFemale.value = "0"
        keyWord.value = ""
        _placeList.value = listOf()
        _destName.value = ""
        _destRoadAddress.value = ""
    }

    fun getOptimizedRoute(
        departureAddress: String,
        hostDestAddress: String,
        guestDestAddress: String,
        hostId: Long,
        guestId: Long
    ) {
        viewModelScope.launch {
            val optimizedRoute = getOptimizedRouteUseCase(departureAddress, hostDestAddress, guestDestAddress, hostId, guestId)
            _route.value = optimizedRoute
        }
    }

    suspend fun getSearchResult() {
        val key = BuildConfig.kakao_rest_api_key
        viewModelScope.launch {
            searchPlaceUseCase(key, keyWord.value!!)
                .onSuccess {
                    _placeList.value = it.documents
                }
                .onFailure {
                    Log.d("Error", it.toString())
                }
        }
    }

    fun setPassengerType(taxiType: TaxiType, carPoolType: CarPoolType) {
        _taxiType.value = taxiType
        _carPoolType.value = carPoolType
    }

    fun setDestInfo(destName: String, destRoadAddress: String) {
        _destName.value = destName
        _destRoadAddress.value = destRoadAddress
    }

    fun addMale(): Int {
        val next = _countMale.value!!.toInt() + 1
        _countMale.value = next.toString()

        return next
    }

    fun subtractMale(): Int {
        val next = _countMale.value!!.toInt() - 1
        _countMale.value = next.toString()

        return next
    }

    fun addFemale(): Int {
        val next = _countFemale.value!!.toInt() + 1
        _countFemale.value = next.toString()

        return next
    }

    fun subtractFemale(): Int {
        val next = _countFemale.value!!.toInt() - 1
        _countFemale.value = next.toString()

        return next
    }

    fun emitInfo(): PlaceItem {
        return PlaceItem(
            taxiType.value!!,
            carPoolType.value!!,
            countMale.value!!,
            countFemale.value!!,
            "출발지",
            "출발지 도로명",
            destName.value!!,
            destRoadAddress.value!!
            )
    }
}