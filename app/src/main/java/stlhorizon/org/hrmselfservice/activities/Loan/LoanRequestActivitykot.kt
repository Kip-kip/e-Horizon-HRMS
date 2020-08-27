package stlhorizon.org.hrmselfservice.activities.Leave

import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.helper.SessionManager
import stlhorizon.org.hrmselfservice.model.Leave.LoanCategorykt
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import stlhorizon.org.uniscoo.adapter.LoanCategorySpinnerAdapterkt
import java.io.IOException

class LoanRequestActivitykot : AppCompatActivity() {

    private var loanCategorySpinnerAdapter: LoanCategorySpinnerAdapterkt? = null
    private var spinner: Spinner? = null
    private var session: SessionManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loanrequest)

        // Session manager
        session = SessionManager(applicationContext)


        loadLoanCategory()

    }


    private fun loadLoanCategory() {

        val token =session!!.token;

        NetworkConnection.makeAGetRequest(
            "https://hrms5.stl-horizon.com/api/web/api/loan-category?token=" + token,
            null,
            object : OnReceivingResult {
                override fun onErrorResult(e: IOException) {
                    e.printStackTrace()
                }

                override fun onReceiving100SeriesResponse(remoteResponse: RemoteResponse?) {}
                override fun onReceiving200SeriesResponse(remoteResponse: RemoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse)
                    val response: JSONObject = remoteResponse.getMessangeAsJSON()
                    try {
                        if (response.getString("success").equals("1", ignoreCase = true)) {
                            val loanCategory: LoanCategorykt =
                                LoanCategorykt.createLoanCategoryFrom(remoteResponse.getMessage())
                            val userTypeModel: List<LoanCategorykt.LoanCategoryModel> =
                                loanCategory.loanCategoryData!!

                            spinner?.setAdapter(LoanCategorySpinnerAdapterkt(userTypeModel, applicationContext).also({ loanCategorySpinnerAdapter = it })
                            )

                            spinner?.setOnItemSelectedListener(loanCategorySpinnerAdapter)


                            return
                        } else {
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onReceiving300SeriesResponse(remoteResponse: RemoteResponse?) {}
                override fun onReceiving400SeriesResponse(remoteResponse: RemoteResponse?) {}
                override fun onReceiving500SeriesResponse(remoteResponse: RemoteResponse?) {}
                override fun onAnyEvent() {}
            })
    }

}
