package com.ddowney.vehilytics.utils

/**
 * Created by Dan on 25/11/2017.
 */
class StringHelper {

    /**
     * Accepts an integer value and returns a readable string version of the number
     */
    fun getReadableInt(number: Int): String {
        val str = StringBuilder()
        val num = number.toString()
        for (i in num.length-1 downTo 0) {
            str.append(num[i])
            if ((i + 1) % 3 == 0) {
                str.append(",")
            }
        }
        return str.reverse().toString()
    }
}