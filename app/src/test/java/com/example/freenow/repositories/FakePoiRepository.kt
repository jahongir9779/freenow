package com.example.freenow.repositories

import com.example.freenow.common.ResultSuccess
import com.example.freenow.common.ResultWrapper
import com.example.freenow.domain.models.BoundsModel
import com.example.freenow.domain.models.PoiModel
import com.example.freenow.domain.repositories.PoiRepository

class FakePoiRepository : PoiRepository {

    override suspend fun getPoiListInBounds(bounds: BoundsModel): ResultWrapper<List<PoiModel>> {
        return  ResultSuccess(listOf())
    }

}
