package com.ddowney.vehilytics.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Dan on 03/12/2017.
 * Model describing a user to be registered
 */
data class RegistrationDetails(
        val name: String, val email: String, val password: String,
        @SerializedName("device_id") val deviceId: String,
        @SerializedName("vehicle_manufacturer") val vehicleManufacturer: String)