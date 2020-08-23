package stlhorizon.org.hrmselfservice.activities.Profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_importantnumbers.*
import kotlinx.android.synthetic.main.activity_personaldetails.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONException
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.model.user.Profile
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException

class ImportantNumbersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_importantnumbers)

        val toeditpersonalinfo = findViewById<ImageView>(R.id.editpersonalinfo)

        toeditpersonalinfo.setOnClickListener {

//            val intent = Intent(this, EditProfileActivity::class.java)
//            startActivity(intent)
        }



        loadUserProfile()

    }


    fun loadUserProfile() {

        val token =
            "eyJpYXQiOjE1OTY0NDU1MzUsImlzcyI6ImhybXM1LnN0bC1ob3Jpem9uLmNvbSIsIm5iZiI6MTU5NjQ0NTUzNSwiZXhwIjoxNTk2NDQ1NTQ1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImtpZCI6Ijg1ZjFjNTQ4Y2VlNWI2ODNmYWE0MGNjNjJhYTA1YWJjIn0.eyJ1c2VyX2lkIjoyMzEsInVzZXJuYW1lIjoiQ3lydXMiLCJmdWxsX25hbWUiOiJDeXJ1cyAgS2lwcm90aWNoIiwicGFydHlfaWQiOiIxNDg4MDgxIiwiZGF0ZV9vZl9iaXJ0aCI6IjE5OTQtMDktMTkiLCJnZW5kZXIiOiJNQUxFIiwiY2l0eSI6Ik5BSVJPQkkiLCJjb3VudHJ5IjoiS0UiLCJhcHBvaW50X2lkIjoiMTQ4ODA4NSIsImVudGl0eV9pZCI6IjEwMCIsImVudGl0eV9uYW1lIjoiU09GVFdBUkUgVEVDSE5PTE9HSUVTIExJTUlURUQiLCJwZXJubyI6IlNUTDEzNCIsImNvZGUiOiJIUjUwMDEiLCJpbWFnZSI6bnVsbH0.rDnJfGiTVFSjNtTGqTIw9iv-XI64_yg2PrHnrzRyGGo"
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

                            staffno.setText(profile!!.profileData!!.personal_id)
                            idno.setText(profile!!.profileData!!.national_id)
                            kra.setText("")
                            nhif.setText(profile!!.profileData!!.nhif)
                            nssf.setText(profile!!.profileData!!.nssf)

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
