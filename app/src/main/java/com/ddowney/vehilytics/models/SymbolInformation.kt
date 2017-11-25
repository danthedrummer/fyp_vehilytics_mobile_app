package com.ddowney.vehilytics.models

import android.graphics.drawable.Drawable

/**
 * Model describing vehicle symbols.
 *
 * For the prototype this uses a drawable for the image but
 * in the final build it will be provided by the web service
 */
data class SymbolInformation(val symbolName: String, val symbolDescription: String, val image: Drawable)