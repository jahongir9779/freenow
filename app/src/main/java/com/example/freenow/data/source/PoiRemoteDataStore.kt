package com.example.freenow.data.source

import com.example.freenow.common.ResultWrapper
import com.example.freenow.data.repository.PoiDataStore
import com.example.freenow.data.repository.PoiRemote
import com.example.freenow.domain.models.BoundsModel
import com.example.freenow.domain.models.PoiModel
import javax.inject.Inject

/**
 * Implementation of the [PoiDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class PoiRemoteDataStore @Inject constructor(private val poiRemote: PoiRemote) :
    PoiDataStore {

    override suspend fun getPoiListInBounds(bounds: BoundsModel): ResultWrapper<List<PoiModel>> {
        return poiRemote.getPoiListInBounds(bounds)
    }

}