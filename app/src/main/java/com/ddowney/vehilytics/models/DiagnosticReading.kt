package com.ddowney.vehilytics.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Dan on 03/12/2017.
 * Model describing a sensor reading
 */
data class DiagnosticReading (
        @SerializedName("sensor_name") val sensorName: String,
        @SerializedName("range_min") val rangeMin: Double,
        @SerializedName("range_max") val rangeMax: Double,
        val unit: String, val value: Double,
        @SerializedName("device_id") val deviceId: String
)