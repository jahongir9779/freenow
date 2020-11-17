package com.example.freenow.ui.view_objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class BoundsViewObj(
    val p1Latitude: Double,
    val p1Longitude: Double,
    val p2Latitude: Double,
    val p2Longitude: Double
) : Parcelable
