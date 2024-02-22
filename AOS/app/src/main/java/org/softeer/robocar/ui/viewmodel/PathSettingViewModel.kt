package org.softeer.robocar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.softeer.robocar.domain.usecase.SearchPlaceUseCase
import javax.inject.Inject

class PathSettingViewModel @Inject constructor(
    private val searchPlaceUseCase: SearchPlaceUseCase
): ViewModel() {

    private var _keyWord = MutableLiveData<String>()
    val keyWord: LiveData<String> = _keyWord

    suspend fun getSearchResult(key: String, query: String) {
        viewModelScope.launch {
            searchPlaceUseCase(key, query).onSuccess {
                Log.d("Test", "Body: ${it.documents}")
            }
        }
    }

}