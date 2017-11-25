package com.ddowney.vehilytics.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ddowney.vehilytics.R
import com.ddowney.vehilytics.models.ReminderDetails
import com.ddowney.vehilytics.utils.StringHelper
import kotlinx.android.synthetic.main.reminder_detail_row.view.*

/**
 * Created by Dan on 25/11/2017.
 */
class ReminderListAdapter(internal val context: Context, private val reminders : List<ReminderDetails>)
    : ArrayAdapter<ReminderDetails>(context, R.layout.symbol_info_row, reminders) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val reminder = reminders[position]

        val newView = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            inflater.inflate(R.layout.reminder_detail_row, parent, false)
        } else {
            convertView
        }

        newView.reminder_name.text = reminder.reminderName
        newView.reminder_image.setImageDrawable(reminder.image)

        newView.reminder_recurring.text =
                if (reminder.recurring) {
                    "Recurring"
                } else {
                    "One Time"
                }

        val freqString = StringBuilder()
        reminder.delay.let { delay ->
            if (delay.durationMonths != null) {
                freqString.append(" ${delay.durationMonths}")
                if (delay.durationMonths > 1) {
                    freqString.append(" months")
                } else {
                    freqString.append(" month")
                }
                if (delay.distanceKm != null) {
                    freqString.append(" /")
                }
            }
            if (delay.distanceKm != null) {
                val formatter = StringHelper()
                freqString.append(" ${formatter.getReadableInt(delay.distanceKm)}km")
            }
        }
        newView.reminder_delay_frequency.text = freqString.toString()

        return newView
    }
}