package com.example.freenow.data.remote

import com.example.freenow.data.model.PoiDataModel
import com.example.freenow.data.model.PoiListResponse
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface ApiService {

    @GET("/")
    suspend fun getPoiListInBounds(
        @Query("p1Lat") p1Lat: Double,
        @Query("p1Lon") p1Lon: Double,
        @Query("p2Lat") p2Lat: Double,
        @Query("p2Lon") p2Lon: Double
    ): PoiListResponse

}

