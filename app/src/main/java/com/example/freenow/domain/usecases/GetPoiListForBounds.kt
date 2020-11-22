package com.example.freenow.domain.usecases

import com.example.freenow.common.ResultWrapper
import com.example.freenow.domain.models.BoundsModel
import com.example.freenow.domain.models.PoiModel
import com.example.freenow.domain.repositories.PoiRepository

class GetPoiListForBounds(private val repository: PoiRepository) :
    UseCaseWithParams<BoundsModel, ResultWrapper<List<PoiModel>>> {

    override suspend fun execute(params: BoundsModel): ResultWrapper<List<PoiModel>> {
        return repository.getPoiListInBounds(params)
    }

}