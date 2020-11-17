package com.example.freenow.domain.models

import com.example.freenow.common.EFleetType

data class PoiModel(
    val coordinate: CoordinateModel,
    val fleetType: EFleetType,
    val heading: Double,
    val id: Int
)
