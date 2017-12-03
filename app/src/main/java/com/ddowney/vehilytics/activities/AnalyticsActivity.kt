package com.ddowney.vehilytics.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ddowney.vehilytics.R
import com.ddowney.vehilytics.godObject.User
import com.ddowney.vehilytics.models.DiagnosticReading
import com.ddowney.vehilytics.services.ServiceManager
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_analytics.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnalyticsActivity : AppCompatActivity() {

    companion object {
        private val LOG_TAG = "AnalyticsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)
        setSupportActionBar(analytics_toolbar)

        retrieveLatestReadings()

    }

    override fun onResume() {
        super.onResume()
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
                                populateGraphs(readings)
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

    private fun populateGraphs(diagnosticReadings: List<DiagnosticReading>) {
        val optimumUpperEntries = mutableListOf<Entry>()
        val optimumLowerEntries = mutableListOf<Entry>()
        val batteryEntries = mutableListOf<Entry>()
        diagnosticReadings.forEach { reading ->
            if (reading.sensorName == "Battery Voltage") {
                optimumUpperEntries.add(Entry(batteryEntries.size.toFloat(), reading.rangeMax.toFloat()))
                optimumLowerEntries.add(Entry(batteryEntries.size.toFloat(), reading.rangeMin.toFloat()))
                batteryEntries.add(Entry(batteryEntries.size.toFloat(), reading.value.toFloat()))
            }
        }

        val setUpper = LineDataSet(optimumUpperEntries, "Upper Optimum")
        setUpper.axisDependency = YAxis.AxisDependency.LEFT
        setUpper.color = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        setUpper.circleColors = listOf(ContextCompat.getColor(this, R.color.colorIcons))
        setUpper.lineWidth = 2f
        setUpper.circleRadius = 3f
        setUpper.fillAlpha = 65
        setUpper.setDrawValues(false)

        val setLower = LineDataSet(optimumLowerEntries, "Lower Optimum")
        setLower.axisDependency = YAxis.AxisDependency.LEFT
        setLower.color = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        setLower.circleColors = listOf(ContextCompat.getColor(this, R.color.colorIcons))
        setLower.lineWidth = 2f
        setLower.circleRadius = 3f
        setLower.fillAlpha = 65
        setLower.setDrawValues(false)

        val setActual = LineDataSet(batteryEntries, "Actual")
        setActual.axisDependency = YAxis.AxisDependency.LEFT
        setActual.color = ContextCompat.getColor(this, R.color.colorAccent)
        setActual.circleColors = listOf(ContextCompat.getColor(this, R.color.colorDivider))
        setActual.lineWidth = 2f
        setActual.circleRadius = 3f
        setActual.fillAlpha = 65

        val data = LineData(setUpper, setLower, setActual)
        data.setValueTextColor(R.color.colorDivider)
        data.setValueTextSize(9f)

        test_chart.clear()
        test_chart.data = data
        val d = Description()
        d.text = "Battery Voltage"
        test_chart.description = d

        var min = Float.MAX_VALUE
        var max = Float.MIN_VALUE
        batteryEntries.forEach { entry ->
            min = Math.min(min, entry.y)
            max = Math.max(max, entry.y)
        }

        test_chart.axisLeft.axisMinimum = min - ((max - min)/2)
        test_chart.axisRight.setDrawLabels(false)
        test_chart.xAxis.setDrawLabels(false)
    }


}
