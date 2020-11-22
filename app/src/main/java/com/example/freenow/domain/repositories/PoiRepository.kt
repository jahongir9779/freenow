package com.example.freenow.domain.repositories

import com.example.freenow.common.ResultWrapper
import com.example.freenow.domain.models.BoundsModel
import com.example.freenow.domain.models.PoiModel

interface PoiRepository {

    suspend fun getPoiListInBounds(bounds: BoundsModel): ResultWrapper<List<PoiModel>>

}