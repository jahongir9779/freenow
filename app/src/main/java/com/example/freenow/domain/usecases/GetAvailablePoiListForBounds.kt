package com.example.freenow.domain.usecases

import com.example.freenow.common.EFleetType
import com.example.freenow.common.ResultSuccess
import com.example.freenow.common.ResultWrapper
import com.example.freenow.domain.models.BoundsModel
import com.example.freenow.domain.models.PoiModel
import com.example.freenow.domain.repositories.RemoteRepo

class GetAvailablePoiListForBounds(private val repository: RemoteRepo) :
    UseCaseWithParams<BoundsModel, ResultWrapper<List<PoiModel>>> {

    override suspend fun execute(params: BoundsModel): ResultWrapper<List<PoiModel>> {
        val result = repository.getPoiListInBounds(params)
        return if (result is ResultSuccess) {
            ResultSuccess(result.value.filter { it.fleetType == EFleetType.TAXI })
        } else result
    }

}