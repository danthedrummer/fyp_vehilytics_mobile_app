package com.ddowney.vehilytics.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.ddowney.vehilytics.R
import com.ddowney.vehilytics.adapters.ReminderListAdapter
import com.ddowney.vehilytics.godObject.User
import com.ddowney.vehilytics.models.ReminderDetails
import kotlinx.android.synthetic.main.activity_reminders.*

class RemindersActivity : AppCompatActivity() {

    private lateinit var sampleReminders : MutableList<ReminderDetails>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)
        setSupportActionBar(main_toolbar)

        sampleReminders = mutableListOf(
                ReminderDetails("Check Oil Pressure", ReminderDetails.ReminderDelay(3, null),
                        getDrawable(R.drawable.oil_pressure_symbol), true),
                ReminderDetails("Check Tyre Pressure", ReminderDetails.ReminderDelay(1, 20_000),
                        getDrawable(R.drawable.tyre_pressure_symbol), true),
                ReminderDetails("Change Engine Coolant", ReminderDetails.ReminderDelay(null, 40_000),
                        getDrawable(R.drawable.engine_coolant_symbol), false),
                ReminderDetails("Change Oil", ReminderDetails.ReminderDelay(null, 60_000),
                        getDrawable(R.drawable.oil_pressure_symbol), true),
                ReminderDetails("Check Battery", ReminderDetails.ReminderDelay(6, null),
                        getDrawable(R.drawable.battery_symbol), true)
        )

        val reminderAdapter = ReminderListAdapter(this, sampleReminders)

        reminder_list_view.adapter = reminderAdapter
        reminder_list_view.divider = ContextCompat.getDrawable(this, R.drawable.simple_list_divider)
        reminder_list_view.dividerHeight = 2

        registerForContextMenu(reminder_list_view)

        add_reminder.setOnClickListener {
            Toast.makeText(this, "Create new reminder", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.preferences_menu_option -> {
                val intent = Intent(this, PreferencesActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.logout_menu_option -> {
                // clear user login data
                User.logout()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.reminder_list_view_options, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        val info = item?.menuInfo as AdapterView.AdapterContextMenuInfo

        return when (item.itemId) {
            R.id.reminder_edit -> {
                true
            }
            R.id.reminder_delete -> {
                sampleReminders.removeAt(info.position)
                val reminderAdapter = ReminderListAdapter(this, sampleReminders)
                reminder_list_view.adapter = reminderAdapter
                true
            }
            else -> {
                super.onContextItemSelected(item)
            }
        }
    }
}
