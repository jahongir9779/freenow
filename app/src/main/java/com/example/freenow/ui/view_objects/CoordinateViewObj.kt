package com.example.freenow.ui.view_objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CoordinateViewObj(
    val latitude: Double,
    val longitude: Double
):Parcelable