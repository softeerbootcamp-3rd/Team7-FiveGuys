package org.softeer.robocar.ui.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.softeer.robocar.BuildConfig
import org.softeer.robocar.data.dto.placesearch.Place
import org.softeer.robocar.data.model.PlaceItem
import org.softeer.robocar.domain.usecase.AddressSearchUseCase
import org.softeer.robocar.domain.usecase.SearchPlaceUseCase
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val addressSearchUseCase: AddressSearchUseCase,
    private val searchPlaceUseCase: SearchPlaceUseCase
): ViewModel() {

    private var _countMale = MutableLiveData<String>()
    val countMale: LiveData<String> = _countMale
    private var _countFemale = MutableLiveData<String>()
    val countFemale: LiveData<String> = _countFemale
    var keyWord = MutableLiveData<String>()
    private var _placeList = MutableLiveData<List<Place>>()
    val placeList: LiveData<List<Place>> = _placeList
    private var _destName = MutableLiveData<String>()
    val destName: LiveData<String> = _destName
    private var _destRoadAddress = MutableLiveData<String>()
    val destRoadAddress: LiveData<String> = _destRoadAddress
    // 주소 검색 결과를 저장할 LiveData
    private val _addressResult = MutableLiveData<String>()
    val addressResult: LiveData<String> = _addressResult


    init {
        _countMale.value = "0"
        _countFemale.value = "0"
        keyWord.value = ""
        _placeList.value = listOf()
        _destName.value = ""
        _destRoadAddress.value = ""
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
            "test",
            "test",
            countMale.value!!,
            countFemale.value!!,
            "출발지",
            "출발지 도로명",
            destName.value!!,
            destRoadAddress.value!!
            )
    }
    // 주소 검색을 수행하는 함수
    // 주소 변환 로직을 suspend 함수로 구현
    suspend fun convertLocationToAddress(location: Location): String {
        return try {
            val apiKey = BuildConfig.kakao_rest_api_key // API 키 설정
            val latitude = location.latitude
            val longitude = location.longitude

            // 주소 검색 수행하고 결과 반환
            val result = addressSearchUseCase(apiKey, latitude, longitude).getOrThrow()
            result
        } catch (e: Exception) {
            "주소를 찾을 수 없습니다." // 예외 발생 시 기본 문자열 반환
        }
    }
}