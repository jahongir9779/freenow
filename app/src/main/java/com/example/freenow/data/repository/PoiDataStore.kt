package com.example.freenow.data.repository

import com.example.freenow.common.ResultWrapper
import com.example.freenow.domain.models.BoundsModel
import com.example.freenow.domain.models.PoiModel

interface PoiDataStore {

    suspend fun getPoiListInBounds(bounds: BoundsModel): ResultWrapper<List<PoiModel>>

}