package com.ddowney.vehilytics.services

import com.ddowney.vehilytics.models.DiagnosticReading
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Dan on 03/12/2017.
 * Retrofit service for accessing the readings endpoint of the api
 */
interface DiagnosticReadingsService {

    @GET("/readings")
    fun getReadingsForDevice(@Query("device_id") deviceId: String) : Call<List<DiagnosticReading>>
}