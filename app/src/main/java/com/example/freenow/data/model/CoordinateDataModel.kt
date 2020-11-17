package com.example.freenow.data.model

import com.google.gson.annotations.SerializedName

data class CoordinateDataModel(
    @SerializedName("latitude")   val latitude: Double? = null,
    @SerializedName("longitude")   val longitude: Double? = null
)