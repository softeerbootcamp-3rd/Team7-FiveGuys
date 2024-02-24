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
import org.softeer.robocar.domain.usecase.SearchPlaceUseCase
import javax.inject.Inject

@HiltViewModel
class PathSettingViewModel @Inject constructor(
    private val searchPlaceUseCase: SearchPlaceUseCase
): ViewModel() {

    var keyWord = MutableLiveData<String>()
    private var _placeList = MutableLiveData<List<Place>>()
    val placeList: LiveData<List<Place>> = _placeList

    init {
        keyWord.value = ""
        _placeList.value = listOf()
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

}