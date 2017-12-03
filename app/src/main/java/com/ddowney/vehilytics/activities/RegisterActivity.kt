package com.ddowney.vehilytics.activities

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.ddowney.vehilytics.R
import com.ddowney.vehilytics.godObject.User
import com.ddowney.vehilytics.models.RegistrationDetails
import com.ddowney.vehilytics.models.UserDetails
import com.ddowney.vehilytics.services.ServiceManager
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    companion object {
        private val LOG_TAG = "RegisterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(login_toolbar)

        register_button.setOnClickListener {
             if (isRegistrationValid()) {
                 val registrationDetails = RegistrationDetails(name_field.text.toString(),
                         email_field.text.toString(),
                         password_field.text.toString(),
                         device_id_field.text.toString(),
                         vehicle_manufacturer_field.text.toString())
                 ServiceManager.userService.register(registrationDetails)
                         .enqueue(object: Callback<UserDetails> {
                             override fun onFailure(call: Call<UserDetails>?, t: Throwable?) {
                                 Log.e(LOG_TAG, "Error : ${t?.message}")
                             }

                             override fun onResponse(call: Call<UserDetails>?, response: Response<UserDetails>?) {
                                 Log.d(LOG_TAG, "Response code = ${response?.code()}")
                                 if (response?.code() == 201) {
                                     val userDetail = response.body()
                                     User.login(userDetail)
                                     val intent = Intent(baseContext, HomeActivity::class.java)
                                     startActivity(intent)
                                     finish()
                                 }
                             }
                         })
             }
        }
    }

    private fun isRegistrationValid(): Boolean {
        var valid = true

        val placeholderColor = ContextCompat.getColor(this, R.color.colorSecondaryText)
        val errorColor = ContextCompat.getColor(this, R.color.colorErrorPlaceholder)

        name_field.setHintTextColor(placeholderColor)
        email_field.setHintTextColor(placeholderColor)
        device_id_field.setHintTextColor(placeholderColor)
        vehicle_manufacturer_field.setHintTextColor(placeholderColor)
        password_field.setHintTextColor(placeholderColor)
        confirm_password_field.setHintTextColor(placeholderColor)


        if (name_field.text.isNullOrEmpty()) {
            name_field.setHintTextColor(errorColor)
            valid = false
        }

        if (email_field.text.isNullOrEmpty()) {
            email_field.setHintTextColor(errorColor)
            valid = false
        }

        if (device_id_field.text.isNullOrEmpty()) {
            device_id_field.setHintTextColor(errorColor)
            valid = false
        }

        if (vehicle_manufacturer_field.text.isNullOrEmpty()) {
            vehicle_manufacturer_field.setHintTextColor(errorColor)
            valid = false
        }

        if (password_field.text.isNullOrEmpty()) {
            password_field.setHintTextColor(errorColor)
            valid = false
        }

        if (confirm_password_field.text.isNullOrEmpty()) {
            confirm_password_field.setHintTextColor(errorColor)
            valid = false
        }

        if (password_field.text.toString() != confirm_password_field.text.toString()) {
            Toast.makeText(this, "Passwords must match", Toast.LENGTH_SHORT).show()
            password_field.text.clear()
            confirm_password_field.text.clear()
            password_field.setHintTextColor(errorColor)
            confirm_password_field.setHintTextColor(errorColor)
            valid = false

        }

        return valid
    }
}
