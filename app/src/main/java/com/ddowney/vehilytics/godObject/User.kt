package com.ddowney.vehilytics.godObject

import com.ddowney.vehilytics.models.UserDetails

/**
 * Created by Dan on 03/12/2017.
 *
 * A means of accessing user details throughout the app without
 * needing to juggle the information in intents or storage
 */
object User {

    private var userDetails: UserDetails? = null

    fun login(userDetails: UserDetails?) {
        this.userDetails = userDetails
    }

    fun logout() {
        this.userDetails = null
    }

    fun getUserDetails(): UserDetails? {
        return userDetails
    }
}