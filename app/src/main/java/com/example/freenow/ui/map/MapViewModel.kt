package com.example.freenow.ui.map

import androidx.hilt.lifecycle.ViewModelInject
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


    val poiList = MutableLiveData<List<PoiModel>>()
    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun getPoiListInBounds(p1Lat: Double, p1Lon: Double, p2Lat: Double, p2Lon: Double) {
        isLoading.value = true
        viewModelScope.launch {
            val response = getAvailablePoiList.execute(BoundsModel(p1Lat, p1Lon, p2Lat, p2Lon))
            isLoading.value = false
            when (response) {
                is ResultError -> errorMessage.value = response.message
                is ResultSuccess -> poiList.value = response.value
            }.exhaustive
        }
    }

}