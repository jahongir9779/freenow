package com.example.freenow.data.model

import com.example.freenow.common.EFleetType
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Field

data class PoiDataModel(
    @SerializedName("coordinate") val coordinate: CoordinateDataModel? = null,
    @SerializedName("fleetType") val fleetType: EFleetType? = null,
    @SerializedName("heading") val heading: Double? = null,
    @SerializedName("id") val id: Int? = null
){


//method copied from stackoverflow
    fun anyUnset(): Boolean {
        val fields: Array<Field> = this.javaClass.declaredFields
        for (f in fields) {
            try {
                val value: Any = f.get(this)
                if (value != null) {
                    return false
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
        return true
    }

}
