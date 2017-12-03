package com.ddowney.vehilytics.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ddowney.vehilytics.R
import com.ddowney.vehilytics.godObject.User
import com.ddowney.vehilytics.models.UserDetails
import com.ddowney.vehilytics.services.ServiceManager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    companion object {
        private val LOG_TAG = "LoginActivityLog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {

            ServiceManager.userService.login(email_field.text.toString(), password_field.text.toString())
                    .enqueue(object : Callback<UserDetails> {
                        override fun onFailure(call: Call<UserDetails>?, t: Throwable?) {
                            //FIXME: This might not display correctly due to it being an anonymous class
                            Log.e(LOG_TAG, "Error : ${t?.message}")
                        }

                        override fun onResponse(call: Call<UserDetails>?, response: Response<UserDetails>?) {
                            if (response?.code() == 200) {
                                User.login(response.body())
                                val intent = Intent(baseContext, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(baseContext, "Invalid credentials", Toast.LENGTH_SHORT).show()
                            }
                        }

                    })

        }

        register_button.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
