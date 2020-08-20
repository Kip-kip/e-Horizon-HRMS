package stlhorizon.org.hrmselfservice.activities.Leave

import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.model.Leave.LoanCategorykt
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import stlhorizon.org.uniscoo.adapter.LoanCategorySpinnerAdapterkt
import java.io.IOException

class LoanRequestActivitykot : AppCompatActivity() {

    private var loanCategorySpinnerAdapter: LoanCategorySpinnerAdapterkt? = null
    private var spinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loanrequest)



        loadLoanCategory()

    }


    private fun loadLoanCategory() {

        val token =
            "eyJpYXQiOjE1OTY0NDU1MzUsImlzcyI6ImhybXM1LnN0bC1ob3Jpem9uLmNvbSIsIm5iZiI6MTU5NjQ0NTUzNSwiZXhwIjoxNTk2NDQ1NTQ1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImtpZCI6Ijg1ZjFjNTQ4Y2VlNWI2ODNmYWE0MGNjNjJhYTA1YWJjIn0.eyJ1c2VyX2lkIjoyMzEsInVzZXJuYW1lIjoiQ3lydXMiLCJmdWxsX25hbWUiOiJDeXJ1cyAgS2lwcm90aWNoIiwicGFydHlfaWQiOiIxNDg4MDgxIiwiZGF0ZV9vZl9iaXJ0aCI6IjE5OTQtMDktMTkiLCJnZW5kZXIiOiJNQUxFIiwiY2l0eSI6Ik5BSVJPQkkiLCJjb3VudHJ5IjoiS0UiLCJhcHBvaW50X2lkIjoiMTQ4ODA4NSIsImVudGl0eV9pZCI6IjEwMCIsImVudGl0eV9uYW1lIjoiU09GVFdBUkUgVEVDSE5PTE9HSUVTIExJTUlURUQiLCJwZXJubyI6IlNUTDEzNCIsImNvZGUiOiJIUjUwMDEiLCJpbWFnZSI6bnVsbH0.rDnJfGiTVFSjNtTGqTIw9iv-XI64_yg2PrHnrzRyGGo"


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
