package com.ddowney.vehilytics.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Dan on 03/12/2017.
 * Model describing the logged in user
 */
data class UserDetails(
        val name: String, val email: String,
        @SerializedName("device_id") val deviceId: String,
        @SerializedName("vehicle_manufacturer") val vehicleManufacturer: String)