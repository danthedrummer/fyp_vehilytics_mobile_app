package com.ddowney.vehilytics.services

import com.ddowney.vehilytics.models.RegistrationDetails
import com.ddowney.vehilytics.models.UserDetails
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Dan on 03/12/2017.
 * Retrofit service for accessing the users endpoint of the api
 */
interface UserService {

    @POST("/users")
    fun register(@Body userDetails: RegistrationDetails) : Call<UserDetails>

    //Don't judge me, I'm on a deadline :(
    @GET("/users/0")
    fun login(@Query("email") email: String, @Query("password") password: String) : Call<UserDetails>

    @DELETE("/users/{email}")
    fun deleteAccount(@Path("email") email: String) : Call<Void>
}