package com.example.freenow.data.mappers

import com.example.freenow.data.model.PoiDataModel
import com.example.freenow.domain.models.CoordinateModel
import com.example.freenow.domain.models.PoiModel
import javax.inject.Inject

class PoiMapper @Inject constructor() {

    fun toPoi(poiNetwork: PoiDataModel): PoiModel {
        val coordinateModel =
            CoordinateModel(poiNetwork.coordinate!!.latitude!!, poiNetwork.coordinate.longitude!!)

        return PoiModel(
            coordinateModel,
            poiNetwork.fleetType!!,
            poiNetwork.heading!!,
            poiNetwork.id!!
        )
    }

}