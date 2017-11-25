package com.ddowney.vehilytics.models

import android.graphics.drawable.Drawable

/**
 * Created by Dan on 25/11/2017.
 */
data class ReminderDetails(val reminderName: String, val delay: ReminderDelay,
                           val image: Drawable, val recurring: Boolean) {
    //Nullable types so the user can have 1 or the other, or both
    //Simplified to simply months for the prototype
    data class ReminderDelay(val durationMonths: Int?, val distanceKm: Int?)
}