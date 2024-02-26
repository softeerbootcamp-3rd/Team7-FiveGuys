package org.softeer.robocar.ui.viewmodel

import android.location.Location
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
import org.softeer.robocar.data.model.*
import org.softeer.robocar.domain.usecase.AddressSearchUseCase
import org.softeer.robocar.domain.usecase.GetOptimizedRouteSoloUseCase
import org.softeer.robocar.domain.usecase.GetOptimizedRouteUseCase
import org.softeer.robocar.domain.usecase.SearchPlaceUseCase
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val addressSearchUseCase: AddressSearchUseCase,
    private val searchPlaceUseCase: SearchPlaceUseCase,
    private val getOptimizedRouteUseCase: GetOptimizedRouteUseCase,
    private val getOptimizedRouteSoloUseCase: GetOptimizedRouteSoloUseCase
): ViewModel() {
    val bottomSheetState = MutableLiveData<Int>()
    val bottomSheetDraggable = MutableLiveData<Boolean>()

    var keyWord = MutableLiveData<String>()

    private var _placeList = MutableLiveData<List<Place>>()
    val placeList: LiveData<List<Place>> = _placeList
    private var _route = MutableLiveData<Route>()
    val route: LiveData<Route> = _route
    private var _routeSolo = MutableLiveData<RouteSolo>()
    val routeSolo: LiveData<RouteSolo> = _routeSolo

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

    // 주소 검색 결과를 저장할 LiveData
    private val _addressResult = MutableLiveData<String>()
    val addressResult: LiveData<String> = _addressResult


    private var _startLocation = MutableLiveData<String>()
    val startLocation: LiveData<String> = _startLocation

    var total = if (taxiType.value == TaxiType.COMPACT_TAXI) 4 else 6
    private var _passenger = MutableLiveData<Int>()
    val passenger: LiveData<Int> = _passenger

    init {
        _countMale.value = 0
        _countFemale.value = 0
        _passenger.value = 0
        keyWord.value = ""
        _placeList.value = listOf()
        _destName.value = ""
        _destRoadAddress.value = ""
        _startLocation.value = "강남구 학동로 134"
    }

    fun getOptimizedRoute(
        departureAddress: String,
        hostDestAddress: String,
        guestDestAddress: String,
        hostId: Long,
        guestId: Long
    ) {
        viewModelScope.launch {
            val optimizedRoute =
                getOptimizedRouteUseCase(departureAddress, hostDestAddress, guestDestAddress, hostId, guestId)
            _route.value = optimizedRoute
        }

    }

    fun getOptimizedRouteSolo(
        departureAddress: String,
        destAddress: String
    ) {
        viewModelScope.launch {
            val optimizedRouteSolo = getOptimizedRouteSoloUseCase(departureAddress, destAddress)
            _routeSolo.value = optimizedRouteSolo
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
        val next = _countMale.value!! - 1
        _countMale.value = next
        _passenger.value = _passenger.value!! - 1
    }

    fun addFemale() {
        val next = _countFemale.value!! + 1
        _countFemale.value = next
        _passenger.value = _passenger.value!! + 1
    }

    fun subtractFemale() {
        val next = _countFemale.value!! - 1
        _countFemale.value = next
        _passenger.value = _passenger.value!! - 1
    }

    fun setStartLocation(location: String) {
        _startLocation.value = location
    }

    fun setTaxiType(taxiType: TaxiType) {
        _taxiType.value = taxiType
    }

    fun setCarPoolType(carPoolType: CarPoolType) {
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

    // 주소 검색을 수행하는 함수
    // 주소 변환 로직을 suspend 함수로 구현
    suspend fun convertLocationToAddress(location: Location) {
        viewModelScope.launch {
            try {
                val apiKey = BuildConfig.kakao_rest_api_key // API 키 설정
                val longitude = location.longitude
                val latitude = location.latitude

                // 주소 검색 수행하고 결과 반환
                val result = addressSearchUseCase(apiKey, longitude, latitude).getOrThrow()

                // 로그로 변환된 주소 출력 및 LiveData에 세팅
                Log.d("MapViewModel", "주소 변환 결과: $result")
                _addressResult.postValue(result)
            } catch (e: Exception) {
                Log.e("MapViewModel", "주소 변환 실패", e)
                _addressResult.postValue("주소를 찾을 수 없습니다.")
            }
        }
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