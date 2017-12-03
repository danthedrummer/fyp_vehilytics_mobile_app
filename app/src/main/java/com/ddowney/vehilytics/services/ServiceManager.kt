package com.ddowney.vehilytics.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Dan on 03/12/2017.
 * Handles the retrofit instance and makes it available for use throughout the app
 */
object ServiceManager {
    private val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://vehilytics-proto-v2.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val userService : UserService = retrofit.create(UserService::class.java)
    val readingsService : DiagnosticReadingsService = retrofit.create(DiagnosticReadingsService::class.java)
}