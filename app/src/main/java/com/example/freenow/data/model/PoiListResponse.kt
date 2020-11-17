package com.example.freenow.data.model

import com.google.gson.annotations.SerializedName

data class PoiListResponse(
 @SerializedName("poiList") val poiList: List<PoiDataModel>? = null
)