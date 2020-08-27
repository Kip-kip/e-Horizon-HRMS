package stlhorizon.org.hrmselfservice.activities.Payslip

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_payslipitem.*
import org.json.JSONException
import org.json.JSONObject
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.helper.SessionManager
import stlhorizon.org.hrmselfservice.model.login.Payslip
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException


class PayslipItemActivity : AppCompatActivity() {
    private var session: SessionManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payslipitem)

        val progressbar = findViewById(R.id.progressbar) as ProgressBar
        val txtprogress= findViewById(R.id.txtprogress) as TextView
        // Session manager
        session = SessionManager(this@PayslipItemActivity)

        fetchPayslip()

    }


    fun fetchPayslip() {

        val from_date=intent.getStringExtra("from_date")
        val to_date=intent.getStringExtra("to_date")

        val token =session!!.token;

        val jsonObject = JSONObject()
        val headers = JSONObject()
        try {
            headers.put("Content-Type", "multipart/form-data")
            jsonObject.put("from_date", from_date)
            jsonObject.put("to_date", to_date)
            NetworkConnection.makeAPostRequestFormData("https://hrms5.stl-horizon.com/api/web/api/payslip?token=$token",
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
                                val payslip: Payslip = Payslip.createPayslipFrom(remoteResponse.message)

                                progressbar.visibility = View.GONE
                                txtprogress.visibility = View.GONE
                                val browser = findViewById(R.id.wV) as WebView
                                browser.visibility=View.VISIBLE
                                browser.settings.javaScriptEnabled = true
                                browser.loadData(payslip.data.toString(), "text/html", "UTF-8")

                                return
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }


                        /**
                         * Success 0
                         * **/

                        val payslip = Payslip.createPayslipFrom(remoteResponse.message)

                        if (payslip.success.equals("0", ignoreCase = true)) {
                            progressbar.visibility = View.GONE
                            txtprogress.visibility = View.GONE
                            Toast.makeText(
                                this@PayslipItemActivity, payslip.message, Toast.LENGTH_LONG
                            ).show()

                        } else {

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
