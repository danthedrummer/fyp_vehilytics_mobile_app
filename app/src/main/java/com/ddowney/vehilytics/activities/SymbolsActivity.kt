package com.ddowney.vehilytics.activities

import com.ddowney.vehilytics.adapters.SymbolListAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.ddowney.vehilytics.R
import com.ddowney.vehilytics.godObject.User
import kotlinx.android.synthetic.main.activity_symbols.*
import com.ddowney.vehilytics.models.SymbolInformation

class SymbolsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symbols)
        setSupportActionBar(main_toolbar)

        //Sample data for prototype
        val symbols = listOf(
                SymbolInformation("Check Engine", "Indicates that the " +
                        "engine has a problem. Usually requires a qualified technician to diagnose " +
                        "and repair", getDrawable(R.drawable.check_engine_symbol)),
                SymbolInformation("Battery/Charging Alert", "Indicates " +
                        "voltage level is below normal level and the vehicle charging system " +
                        "is not functioning correctly. Check the battery terminals and alternator",
                        getDrawable(R.drawable.battery_symbol)),
                SymbolInformation("Tyre Pressure Warning", "Indicates " +
                        "the tire pressure monitoring system has sound a tyre with low air pressure " +
                        "or there may be a sensor malfunction. Check tyre pressure",
                        getDrawable(R.drawable.tyre_pressure_symbol)),
                SymbolInformation("Oil Pressure Warning", "If this light " +
                        "stays lit, it indicates a loss of oil pressure. Immediately check oil level " +
                        "and pressure", getDrawable(R.drawable.oil_pressure_symbol))
        )
        val symbolAdapter = SymbolListAdapter(this, symbols)

        symbols_list_view.adapter = symbolAdapter
        symbols_list_view.divider = ContextCompat.getDrawable(this, R.drawable.simple_list_divider)
        symbols_list_view.dividerHeight = 2
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
