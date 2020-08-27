package stlhorizon.org.hrmselfservice.activities.Profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_personaldetails.*
import kotlinx.android.synthetic.main.activity_servicerecord.*

import org.json.JSONException
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Payslip.EditProfileActivity
import stlhorizon.org.hrmselfservice.helper.SessionManager
import stlhorizon.org.hrmselfservice.model.user.Profile
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException

class ServiceRecordActivity : AppCompatActivity() {
    private var session: SessionManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicerecord)

        val toeditpersonalinfo = findViewById<ImageView>(R.id.editpersonalinfo)
        // Session manager
        session = SessionManager(this)
        toeditpersonalinfo.setOnClickListener {

            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        loadUserProfile()

    }


    fun loadUserProfile() {

        val token =session!!.token;

      NetworkConnection.makeAGetRequest(
            "https://hrms5.stl-horizon.com/api/web/api/user-profile?token=$token",
            null,
            object : OnReceivingResult {
                override fun onErrorResult(e: IOException) {
                    e.printStackTrace()
                    Log.e("error", e.message)
                }

                override fun onReceiving100SeriesResponse(remoteResponse: RemoteResponse?) {}
                override fun onReceiving200SeriesResponse(remoteResponse: RemoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse)
                    val response = remoteResponse.messangeAsJSON
                    try {
                        if (response.getString("success").equals("1", ignoreCase = true)) {
                            val profile: Profile? =
                                Profile.createProfileFrom(remoteResponse.message)

                            department.setText(profile!!.profileData!!.department)
                            currentposition.setText(profile!!.profileData!!.current_position)
                            grade.setText(profile!!.profileData!!.grade)
                            doj.setText(profile!!.profileData!!.date_of_join)
                            noe.setText(profile!!.profileData!!.nature_of_employment)
                            dor.setText(profile!!.profileData!!.estimated_retirement_date)

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
