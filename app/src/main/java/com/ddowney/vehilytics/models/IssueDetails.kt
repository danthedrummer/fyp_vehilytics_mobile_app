package com.ddowney.vehilytics.models

import android.graphics.drawable.Drawable

/**
 * Created by Dan on 25/11/2017.
 * Model describing a detected vehicle issue
 */
data class IssueDetails (val issueHeader: String, val issueDescription: String,
                         val image: Drawable, val reading: List<DiagnosticReading>)