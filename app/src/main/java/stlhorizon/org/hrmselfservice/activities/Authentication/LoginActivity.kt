package stlhorizon.org.hrmselfservice.activities.Authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.MainActivity
import stlhorizon.org.hrmselfservice.model.login.LoginErrorResponse
import stlhorizon.org.hrmselfservice.model.login.LoginSuccessResponse
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private var username: EditText? = null
    private var password: EditText? = null
    private var code: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.txtUsername)
        password = findViewById(R.id.txtPassword)
        code = findViewById(R.id.txtCode)

        val login = findViewById<Button>(R.id.btnLogin)

        //move to next activity
        login.setOnClickListener {

            loginUser()
            val intent = Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
        }

    }


    fun loginUser() {

        val jsonObject = JSONObject()
        val headers = JSONObject()
        try {
            headers.put("Content-Type", "multipart/form-data")
            jsonObject.put("username", username?.text.toString())
            jsonObject.put("code", code?.text.toString())
            jsonObject.put("password", password?.text.toString())
            NetworkConnection.makeAPostRequestFormData(
                "https://hrms5.stl-horizon.com/api/web/api/login",
                jsonObject,
                headers,
                object : OnReceivingResult {
                    override fun onErrorResult(e: IOException) {
                        Log.e("error", e.message)

                    }

                    override fun onReceiving100SeriesResponse(remoteResponse: RemoteResponse?) {
                        NetworkConnection.remoteResponseLogger(remoteResponse)
                    }

                    override fun onReceiving200SeriesResponse(remoteResponse: RemoteResponse?) {
                        NetworkConnection.remoteResponseLogger(remoteResponse)
                        val response: JSONObject = remoteResponse!!.messangeAsJSON
                        try {

                            /**
                             * Success 1
                             * **/

                            if (response.getString("success").equals("1", ignoreCase = true)) {
                                val loginSuccessResponse: LoginSuccessResponse = LoginSuccessResponse.createLoginSuccessResponseFrom(remoteResponse.message
                                    )

                                //redirect to Home  page
                                val it = Intent(applicationContext, MainActivity::class.java)
                                startActivity(it)
                                overridePendingTransition(R.anim.slide_in_right, R.anim.nothing)
                                finish()

                                return
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }


                        /**
                         * Success 0
                         * **/

                        val loginErrorResponse =
                            LoginErrorResponse.createLoginErrorResponseFrom(remoteResponse.message)

                        if (loginErrorResponse.success.equals("0", ignoreCase = true)) {
                            val loginErrorResponse: LoginErrorResponse = LoginErrorResponse.createLoginErrorResponseFrom(remoteResponse.message
                            )

                            Toast.makeText(
                                applicationContext,
                                loginErrorResponse.message,
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
                        NetworkConnection.remoteResponseLogger(remoteResponse)
                    }

                    override fun onReceiving400SeriesResponse(remoteResponse: RemoteResponse?) {
                        NetworkConnection.remoteResponseLogger(remoteResponse)
                    }

                    override fun onReceiving500SeriesResponse(remoteResponse: RemoteResponse?) {
                        NetworkConnection.remoteResponseLogger(remoteResponse)
                    }

                    override fun onAnyEvent() {

                    }
                })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


}
