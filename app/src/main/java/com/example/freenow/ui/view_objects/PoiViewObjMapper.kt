package com.example.freenow.ui.view_objects

import com.example.freenow.domain.models.PoiModel

object PoiViewObjMapper {

    fun toPoiViewObject(poiModel: PoiModel): PoiViewObj {
        val coordinateViewObj =
            CoordinateViewObj(poiModel.coordinate.latitude, poiModel.coordinate.longitude)
        return PoiViewObj(coordinateViewObj, poiModel.fleetType, poiModel.heading, poiModel.id)
    }

}