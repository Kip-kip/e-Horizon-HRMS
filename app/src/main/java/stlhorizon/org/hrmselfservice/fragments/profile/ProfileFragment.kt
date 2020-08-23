package stlhorizon.org.hrmselfservice.fragments.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONException
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Payslip.PersonalDetailsActivity
import stlhorizon.org.hrmselfservice.activities.Profile.DependantsActivity
import stlhorizon.org.hrmselfservice.activities.Profile.ImportantNumbersActivity
import stlhorizon.org.hrmselfservice.activities.Profile.QualificationsActivity
import stlhorizon.org.hrmselfservice.activities.Profile.ServiceRecordActivity
import stlhorizon.org.hrmselfservice.model.user.Profile
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val gotopersonaldetails = root.findViewById<LinearLayout>(R.id.topersonaldetails)
        val tomyservicerecord = root.findViewById<LinearLayout>(R.id.tomyservicerecord)
        val toimportantnumbers = root.findViewById<LinearLayout>(R.id.toimportantnumbers)
        val todependants = root.findViewById<LinearLayout>(R.id.todependants)
        val toqualifications = root.findViewById<LinearLayout>(R.id.toqualifications)
        val userimage =root.findViewById<CircleImageView>(R.id.userimage)
        gotopersonaldetails.setOnClickListener {
            val intent = Intent(context, PersonalDetailsActivity::class.java)
            startActivity(intent)
        }
        tomyservicerecord.setOnClickListener {

            val intent = Intent(context, ServiceRecordActivity::class.java)
            startActivity(intent)
        }
        toimportantnumbers.setOnClickListener {

            val intent = Intent(context, ImportantNumbersActivity::class.java)
            startActivity(intent)
        }
        todependants.setOnClickListener {

            val intent = Intent(context, DependantsActivity::class.java)
            startActivity(intent)
        }
        toqualifications.setOnClickListener {

            val intent = Intent(context, QualificationsActivity::class.java)
            startActivity(intent)
        }

        loadAvatar(context, userimage)


        loadUserProfile()

        return root
    }


    fun loadAvatar(
        context: Context?,
        imageView: ImageView?
    ) {
        val token ="eyJpYXQiOjE1OTc4OTkyODEsImlzcyI6ImhybXM1LnN0bC1ob3Jpem9uLmNvbSIsIm5iZiI6MTU5Nzg5OTI4MSwiZXhwIjoxNTk3ODk5MjkxLCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImtpZCI6ImVlZDNhMzkwNDI0OTJhNDI3NjM5NGU3NDA5NjgxNTUxIn0.eyJ1c2VyX2lkIjoxODYsInVzZXJuYW1lIjoidmljdG9yIiwiZnVsbF9uYW1lIjoiVmljdG9yIE11bW8gVGl0dXMiLCJwYXJ0eV9pZCI6IjEwMjYiLCJkYXRlX29mX2JpcnRoIjoiMTk4Mi0wMi0xNCIsImdlbmRlciI6Ik1BTEUiLCJjaXR5IjoiTmFpcm9iaSIsImNvdW50cnkiOiJLRSIsImFwcG9pbnRfaWQiOiI0MDI2IiwiZW50aXR5X2lkIjoiMTAwIiwiZW50aXR5X25hbWUiOiJTT0ZUV0FSRSBURUNITk9MT0dJRVMgTElNSVRFRCIsInBlcm5vIjoiU1RMMDI5IiwiY29kZSI6IkhSNTAwMSIsImltYWdlIjpudWxsfQ.Q3AqDpxM8mMI-WB-PNbkSziSXXHnncYSy367Gwil6o8";

        if (token == "null") return
        val thumbnail_ulr: String =
            "https://hrms5.stl-horizon.com/api/web/api/user-avatar?" + "token=" + token
        Glide.with(context)
            .load(thumbnail_ulr)
            .asBitmap()
            .placeholder(R.drawable.ava)
            .into(imageView)
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

                            namesprofile.setText(profile!!.profileData!!.first_name + " " + profile.profileData!!.last_name)
                            emailprofile.setText(profile!!.profileData!!.email)
                            phoneprofile.setText(profile!!.profileData!!.phone_no)

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