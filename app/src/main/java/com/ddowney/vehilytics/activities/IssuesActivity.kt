package com.ddowney.vehilytics.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ddowney.vehilytics.R
import com.ddowney.vehilytics.adapters.IssuesListAdapter
import com.ddowney.vehilytics.godObject.User
import com.ddowney.vehilytics.models.DiagnosticReading
import com.ddowney.vehilytics.models.IssueDetails
import com.ddowney.vehilytics.services.ServiceManager
import kotlinx.android.synthetic.main.activity_issues.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IssuesActivity : AppCompatActivity() {

    companion object {
        private val LOG_TAG = "IssuesActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issues)
        setSupportActionBar(analytics_toolbar)

        retrieveLatestReadings()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.analytics_menu, menu)
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

            R.id.refresh_menu_option -> {
                retrieveLatestReadings()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    //Currently hardcoded to only support 2 sensor types: battery charge and oil pressure
    private fun retrieveLatestReadings() {
        val deviceId = User.getUserDetails()?.deviceId
        if(deviceId != null) {
            ServiceManager.readingsService.getReadingsForDevice(deviceId)
                    .enqueue(object: Callback<List<DiagnosticReading>> {
                        override fun onFailure(call: Call<List<DiagnosticReading>>?, t: Throwable?) {
                            Log.e(LOG_TAG, "Error : ${t?.message}")
                        }

                        override fun onResponse(call: Call<List<DiagnosticReading>>?, response: Response<List<DiagnosticReading>>?) {
                            val readings = response?.body()
                            if (readings != null && !readings.isEmpty()) {

                                val batteryIssues = mutableListOf<DiagnosticReading>()
                                val oilPressureIssues = mutableListOf<DiagnosticReading>()

                                readings.forEach { reading ->
                                    when (reading.sensorName) {
                                        "Battery Voltage" -> {
                                            batteryIssues.add(reading)
                                        }
                                        "Oil Pressure" -> {
                                            oilPressureIssues.add(reading)
                                        }
                                    }
                                }

                                val sampleBatteryIssues = IssueDetails("Battery Charge Alert", "Your battery voltage " +
                                        "has been dropping over the last month. You should clean your battery " +
                                        "contacts or check your alternator.", getDrawable(R.drawable.battery_symbol),
                                        batteryIssues)

                                val sampleOilPressureIssues = IssueDetails("Oil Pressure Alert", "There was a loss " +
                                        "in oil pressure. Immediately check oil level and pressure",
                                        getDrawable(R.drawable.oil_pressure_symbol), oilPressureIssues)

                                val sampleIssues = mutableListOf<IssueDetails>()
                                if (!batteryIssues.isEmpty()) {
                                    sampleIssues.add(sampleBatteryIssues)
                                }
                                if (!oilPressureIssues.isEmpty()) {
                                    sampleIssues.add(sampleOilPressureIssues)
                                }

                                val issuesAdapter = IssuesListAdapter(baseContext, sampleIssues)

                                issues_list_view.adapter = issuesAdapter
                                issues_list_view.divider = ContextCompat.getDrawable(baseContext, R.drawable.simple_list_divider)
                                issues_list_view.dividerHeight = 2

                            } else if (readings != null && readings.isEmpty()) {
                                Log.d(LOG_TAG, "No readings received")
                            } else {
                                Log.d(LOG_TAG, "something went wrong : ${response?.code()}")
                            }
                        }
                    })
        } else {
            //display error
            Log.d(LOG_TAG, "no device id")
        }
    }


}
