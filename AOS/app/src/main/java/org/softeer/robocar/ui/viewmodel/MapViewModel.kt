package org.softeer.robocar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
    val bottomSheetState = MutableLiveData<Int>()
    val bottomSheetDraggable = MutableLiveData<Boolean>()
    
    var keyWord = MutableLiveData<String>()

    private var _placeList = MutableLiveData<List<Place>>()
    val placeList: LiveData<List<Place>> = _placeList
    private var _route = MutableLiveData<Route>()
    val route: LiveData<Route> = _route

    private var _taxiType = MutableLiveData<TaxiType>()
    val taxiType: LiveData<TaxiType> = _taxiType
    private var _carPoolType = MutableLiveData<CarPoolType>()
    val carPoolType: LiveData<CarPoolType> = _carPoolType
    private var _countMale = MutableLiveData<Int>()
    val countMale: LiveData<Int> = _countMale
    private var _countFemale = MutableLiveData<Int>()
    val countFemale: LiveData<Int> = _countFemale
  
    private var _destName = MutableLiveData<String>()
    val destName: LiveData<String> = _destName

    private var _destRoadAddress = MutableLiveData<String>()
    val destRoadAddress: LiveData<String> = _destRoadAddress

    private var _startLocation = MutableLiveData<String>()
    val startLocation: LiveData<String> = _startLocation

    init {
        _countMale.value = "0"
        _countFemale.value = "0"
        keyWord.value = ""
        _placeList.value = listOf()
        _destName.value = ""
        _destRoadAddress.value = ""
        _startLocation.value = "강남구 학동로 134"
      
    var total = if (taxiType.value == TaxiType.COMPACT_TAXI) 4 else 6
    private var _passenger = MutableLiveData<Int>()
    val passenger: LiveData<Int> = _passenger

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

    fun setPassengerType(taxiT: TaxiType, carPoolT: CarPoolType) {
        _taxiType.value = taxiT
        _carPoolType.value = carPoolT
        total = if (taxiT == TaxiType.COMPACT_TAXI) 4 else 6
    }

    fun setDestInfo(destName: String, destRoadAddress: String) {
        _destName.value = destName
        _destRoadAddress.value = destRoadAddress
    }

    fun addMale() {
        val next = _countMale.value!! + 1
        _countMale.value = next
        _passenger.value = _passenger.value!! + 1
    }

    fun subtractMale() {
        val next = _countMale.value!!.toInt() - 1
        _countMale.value = next
        _passenger.value = _passenger.value!! - 1
    }

    fun addFemale() {
        val next = _countFemale.value!!.toInt() + 1
        _countFemale.value = next
        _passenger.value = _passenger.value!! + 1
    }

    fun subtractFemale() {
        val next = _countFemale.value!!.toInt() - 1
        _countFemale.value = next
        _passenger.value = _passenger.value!! - 1
    }

    fun setStartLocation(location: String){
        _startLocation.value = location
    }

    fun setTaxiType(taxiType: TaxiType){
        _taxiType.value = taxiType
    }

    fun setCarPoolType(carPoolType: CarPoolType){
        _carPoolType.value = carPoolType
    }

    fun emitInfo(): PlaceItem {
        return PlaceItem(
            taxiType.value!!,
            carPoolType.value!!,
            countMale.value!!.toString(),
            countFemale.value!!.toString(),
            startLocation.value!!,
            startLocation.value!!,
            destName.value!!,
            destRoadAddress.value!!
            )
    }

    fun sheetDown() {
        bottomSheetState.value = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun sheetUp() {
        bottomSheetState.value = BottomSheetBehavior.STATE_EXPANDED
    }

    fun setDraggable(flag: Boolean) {
        bottomSheetDraggable.value = flag
    }
}