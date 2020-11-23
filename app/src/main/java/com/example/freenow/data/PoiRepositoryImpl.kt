package com.example.freenow.data

import com.example.freenow.common.ResultWrapper
import com.example.freenow.data.source.PoiRemoteDataStore
import com.example.freenow.domain.models.BoundsModel
import com.example.freenow.domain.models.PoiModel
import com.example.freenow.domain.repositories.PoiRepository
import javax.inject.Inject

class PoiRepositoryImpl @Inject constructor(
    private val poiRemoteDataStore: PoiRemoteDataStore,
) : PoiRepository {

    override suspend fun getPoiListInBounds(bounds: BoundsModel): ResultWrapper<List<PoiModel>> {
        return poiRemoteDataStore.getPoiListInBounds(bounds)
    }


}