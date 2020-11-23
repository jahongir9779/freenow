package com.example.freenow.domain.models

data class BoundsModel(
    val p1Latitude: Double,
    val p1Longitude: Double,
    val p2Latitude: Double,
    val p2Longitude: Double
){

    object Factory {
        fun create(
            p1Latitude: Double,
            p1Longitude: Double,
            p2Latitude: Double,
            p2Longitude: Double
        ): BoundsModel = BoundsModel(
            p1Latitude = p1Latitude,
            p1Longitude = p1Longitude,
            p2Latitude = p2Latitude,
            p2Longitude = p2Longitude
        )
    }


}
