package com.example.freenow.domain.models

data class PoiModel(
    val coordinate: CoordinateModel,
    val fleetType: EFleetType,
    val heading: Double,
    val id: Int
)
