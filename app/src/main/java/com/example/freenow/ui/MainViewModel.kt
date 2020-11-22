package com.example.freenow.ui

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
import com.example.freenow.domain.usecases.GetPoiListForBounds
import com.example.freenow.ui.view_objects.BoundsViewObj
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val getPoiList: GetPoiListForBounds) :
    ViewModel() {

    val defaultBounds = BoundsViewObj(53.694865, 9.757589, 53.394655, 10.099891)

    private val _poiList = MutableLiveData<List<PoiModel>>()
    val poiList: LiveData<List<PoiModel>> get() = _poiList
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getPoiListInBounds() {
        _isLoading.value = true
        viewModelScope.launch {
            val response = getPoiList.execute(
                BoundsModel(
                    defaultBounds.p1Latitude,
                    defaultBounds.p1Latitude,
                    defaultBounds.p1Latitude,
                    defaultBounds.p1Latitude
                )
            )
            _isLoading.value = false
            when (response) {
                is ResultError -> _errorMessage.value = response.message
                is ResultSuccess -> _poiList.value = response.value
            }.exhaustive
        }
    }


}