package com.ddowney.vehilytics.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ddowney.vehilytics.R
import com.ddowney.vehilytics.godObject.User
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        private val LOG_TAG = "HomeActivityLog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(main_toolbar)

        vehicle_health_button.setOnClickListener {
            val intent = Intent(this, IssuesActivity::class.java)
            startActivity(intent)
        }

        analytics_button.setOnClickListener {
            val intent = Intent(this, AnalyticsActivity::class.java)
            startActivity(intent)
        }

        reminders_button.setOnClickListener {
            val intent = Intent(this, RemindersActivity::class.java)
            startActivity(intent)
        }

        symbols_button.setOnClickListener {
            val intent = Intent(this, SymbolsActivity::class.java)
            startActivity(intent)
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
}
