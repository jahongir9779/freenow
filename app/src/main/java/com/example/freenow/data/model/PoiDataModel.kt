package com.example.freenow.data.model

import com.example.freenow.common.EFleetType
import com.google.gson.annotations.SerializedName

data class PoiDataModel(
    @SerializedName("coordinate") val coordinate: CoordinateDataModel? = null,
    @SerializedName("fleetType") val fleetType: EFleetType? = null,
    @SerializedName("heading") val heading: Double? = null,
    @SerializedName("id") val id: Int? = null
)
