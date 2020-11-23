package com.example.freenow.data.repository

import com.example.freenow.common.ResultError
import com.example.freenow.common.ResultSuccess
import com.example.freenow.common.ResultWrapper
import com.example.freenow.data.mappers.PoiDataToDomainMapper
import com.example.freenow.data.remote.ApiService
import com.example.freenow.domain.models.BoundsModel
import com.example.freenow.domain.models.PoiModel
import com.example.freenow.domain.repositories.PoiRepository
import retrofit2.HttpException
import javax.inject.Inject

class PoiRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val poiDataToDomainMapper: PoiDataToDomainMapper
) : PoiRepository {

    override suspend fun getPoiListInBounds(bounds: BoundsModel): ResultWrapper<List<PoiModel>> {
        return try {
            val resp = apiService.getPoiListInBounds(
                bounds.p1Latitude,
                bounds.p1Longitude,
                bounds.p2Latitude,
                bounds.p2Longitude
            )
            when {
                resp.poiList != null -> ResultSuccess(resp.poiList.map {
                    poiDataToDomainMapper.toPoi(it)
                })
                else -> ResultError("Expected object is null")
            }
        } catch (e: HttpException) {
            ResultError(e.response()!!.errorBody()?.string())
        } catch (e: Exception) {
            ResultError(message = e.localizedMessage)
        }
    }


}