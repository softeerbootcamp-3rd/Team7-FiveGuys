package org.softeer.robocar.ui.viewmodel

import org.softeer.robocar.domain.usecase.CarDetailsUseCase
import org.softeer.robocar.domain.usecase.OnboardDetailsUseCase
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.softeer.robocar.BuildConfig
import org.softeer.robocar.data.dto.operation.OnboardData
import org.softeer.robocar.data.dto.placesearch.Place
import org.softeer.robocar.data.model.*
import org.softeer.robocar.data.repository.auth.AuthLocalDataSource
import org.softeer.robocar.domain.usecase.*
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val addressSearchUseCase: AddressSearchUseCase,
    private val searchPlaceUseCase: SearchPlaceUseCase,
    private val getOptimizedRouteUseCase: GetOptimizedRouteUseCase,
    private val getOptimizedRouteSoloUseCase: GetOptimizedRouteSoloUseCase,
    private val onboardDetailsUseCase: OnboardDetailsUseCase,
    private val carDetailsUseCase: CarDetailsUseCase,
    private val authLocalDataSource: AuthLocalDataSource
) : ViewModel() {
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

    private var _inOperationId = MutableLiveData<Long>()
    val inOperationId: LiveData<Long> = _inOperationId

    private val _onboardDetails = MutableLiveData<OnboardData>()
    val onboardDetails: LiveData<OnboardData> = _onboardDetails

    private val _carDetails = MutableLiveData<CarDetails>()
    val carDetails: LiveData<CarDetails> = _carDetails

    private val _userId = MutableLiveData<Long>()
    val userId: LiveData<Long> = _userId

    private val _addressTaxiResult = MutableLiveData<String>()
    val addressTaxiResult: LiveData<String> = _addressTaxiResult

    private val _latestRoute = MutableLiveData<Route?>()
    val latestRoute: LiveData<Route?> = _latestRoute

    init {
        _countMale.value = 0
        _countFemale.value = 0
        _passenger.value = 0
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
    fun convertLocationToAddress(location: Location) {
        viewModelScope.launch {
            try {
                val apiKey = BuildConfig.kakao_rest_api_key // API 키 설정
                val longitude = location.longitude
                val latitude = location.latitude

                // 주소 검색 수행하고 결과 반환
                val result = addressSearchUseCase(apiKey, longitude, latitude).getOrThrow()
                // 로그로 변환된 주소 출력 및 LiveData에 세팅
                Log.d("MapViewModel", "주소 변환 결과: $result")
                _addressResult.value = result
            } catch (e: Exception) {
                Log.e("MapViewModel", "주소 변환 실패", e)
                _addressResult.postValue("주소를 찾을 수 없습니다.")
            }
        }
    }

    suspend fun convertLocationToAddressString(latitude: Double, longitude: Double): String {
        return try {
            val apiKey = BuildConfig.kakao_rest_api_key
            // 주소 검색 수행하고 결과 반환
            val result = addressSearchUseCase(apiKey, longitude, latitude).getOrThrow()
            Log.d("MapViewModel", "주소 변환 결과: $result")
            result // 변환된 주소 반환
        } catch (e: Exception) {
            Log.e("MapViewModel", "주소 변환 실패", e)
            "주소를 찾을 수 없습니다." // 예외 처리
        }
    }


    fun convertCoordinateToAddress(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val apiKey = BuildConfig.kakao_rest_api_key // API 키 설정

                // 주소 검색 수행하고 결과 반환
                val result = addressSearchUseCase(apiKey, longitude, latitude).getOrThrow()

                // 로그로 변환된 주소 출력 및 LiveData에 세팅
                Log.d("MapViewModel", "주소 변환 결과: $result")
                _addressTaxiResult.postValue(result)
            } catch (e: Exception) {
                Log.e("MapViewModel", "주소 변환 실패", e)
                _addressTaxiResult.postValue("주소를 찾을 수 없습니다.")
            }
        }
    }

    suspend fun convertCurrentLocationToAddress(location: Location) {
        viewModelScope.launch {
            try {
                val apiKey = BuildConfig.kakao_rest_api_key // API 키 설정
                val longitude = location.longitude
                val latitude = location.latitude

                // 주소 검색 수행하고 결과 반환
                _startLocation.value = addressSearchUseCase(apiKey, longitude, latitude).getOrThrow()

                // 로그로 변환된 주소 출력 및 LiveData에 세팅
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

    fun setInOperationId(id: Long) {
        _inOperationId.value = id
        if (id > 0) {
            fetchOnboardDetails(id)
        }
    }

    fun fetchOnboardDetails(inOperationId: Long) {
        viewModelScope.launch {
            val result = onboardDetailsUseCase(inOperationId) // useCase 호출
            result.onSuccess { onboardData ->
                _onboardDetails.postValue(onboardData)
            }.onFailure { exception ->
                // 오류 처리. 예: 로그 출력, 사용자에게 오류 알림, 오류 상태 LiveData 업데이트 등
                Log.e("MapViewModel", "Error fetching onboard details", exception)
            }
        }
    }


    fun fetchCarDetails(carId: Long) {
        viewModelScope.launch {
            carDetailsUseCase(carId).onSuccess { carDetails ->
                _carDetails.value = carDetails
            }.onFailure { exception ->
                Log.e("MapViewModel", "Failed to fetch car details", exception)
            }
        }
    }

    suspend fun fetchUserId(): Long {
        return authLocalDataSource.getUserInfo().first().userId // Flow에서 첫 번째 User 객체의 userId 반환
    }

}