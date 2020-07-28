package stlhorizon.org.hrmselfservice.activities.Authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.model.login.LoginErrorResponse
import stlhorizon.org.hrmselfservice.model.login.LoginSuccessResponse
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login = findViewById<Button>(R.id.btnLogin)

        //move to next activity
        login.setOnClickListener{

           LoginUser()
        }

    }


    fun LoginUser(){

        val jsonObject = JSONObject()
        try {
            jsonObject.put("username", "Cyrus")
            jsonObject.put("password", "*6320ABcd")
            jsonObject.put("code", "HR5001")
        } catch (e: JSONException) {
        }

        NetworkConnection.makeAPostRequest(
            "https://hrms5.stl-horizon.com/api/web/api/login",
            jsonObject.toString(),
            null,
            object : OnReceivingResult {
                override fun onErrorResult(e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@LoginActivity,
                        "Check Internet Connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onReceiving100SeriesResponse(remoteResponse: RemoteResponse?) {

                }
                override fun onReceiving200SeriesResponse(remoteResponse: RemoteResponse) {

                    NetworkConnection.remoteResponseLogger(remoteResponse)
                    val response: JSONObject = remoteResponse.messangeAsJSON
                    try {

                        if (response.getString("success").equals("1", ignoreCase = true)) {
                            val loginSuccessResponse: LoginSuccessResponse =
                                LoginSuccessResponse.createLoginSuccessResponseFrom(remoteResponse.message)

                            Toast.makeText(
                                applicationContext,
                                "success",
                                Toast.LENGTH_LONG
                            ).show()

                            return
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    val loginErrorResponse: LoginErrorResponse =
                        LoginErrorResponse.createLoginErrorResponseFrom(remoteResponse.message)
                    if (loginErrorResponse.success.equals("0",true)) {


                        Toast.makeText(
                            applicationContext,loginErrorResponse.message
                            ,
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
                        Toast.makeText(
                            applicationContext,
                            "An Error Occurred, please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onReceiving300SeriesResponse(remoteResponse: RemoteResponse?) {
                    Toast.makeText(
                        this@LoginActivity,
                        "300",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onReceiving400SeriesResponse(remoteResponse: RemoteResponse?) {
                    Toast.makeText(
                        this@LoginActivity,
                        "400",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onReceiving500SeriesResponse(remoteResponse: RemoteResponse?) {
                    Toast.makeText(
                        this@LoginActivity,
                        "500",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onAnyEvent() {}
            })


    }

}
