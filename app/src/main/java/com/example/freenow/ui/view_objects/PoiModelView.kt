package com.example.freenow.ui.view_objects

import android.os.Parcelable
import com.example.freenow.common.EFleetType
import com.example.freenow.domain.models.CoordinateModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class PoiViewObj(
    val coordinate: CoordinateViewObj,
    val fleetType: EFleetType,
    val heading: Double,
    val id: Int
):Parcelable
