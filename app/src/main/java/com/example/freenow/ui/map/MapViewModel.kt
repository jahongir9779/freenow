package com.example.freenow.ui.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freenow.common.ResultError
import com.example.freenow.common.ResultSuccess
import com.example.freenow.common.exhaustive
import com.example.freenow.domain.models.BoundsModel
import com.example.freenow.domain.models.PoiModel
import com.example.freenow.domain.usecases.GetAvailablePoiListForBounds
import kotlinx.coroutines.launch

class MapViewModel @ViewModelInject constructor(private val getAvailablePoiList: GetAvailablePoiListForBounds) : ViewModel() {


    private val _poiList = MutableLiveData<List<PoiModel>>()
    val poiList: LiveData<List<PoiModel>> get() = _poiList
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getPoiListInBounds(p1Lat: Double, p1Lon: Double, p2Lat: Double, p2Lon: Double) {
        _isLoading.value = true
        viewModelScope.launch {
            val response = getAvailablePoiList.execute(BoundsModel(p1Lat, p1Lon, p2Lat, p2Lon))
            _isLoading.value = false
            when (response) {
                is ResultError -> _errorMessage.value = response.message
                is ResultSuccess -> _poiList.value = response.value
            }.exhaustive
        }
    }

}