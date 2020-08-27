package stlhorizon.org.hrmselfservice.activities.Payslip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_personaldetails.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONException
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.helper.SessionManager
import stlhorizon.org.hrmselfservice.model.user.Profile
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException

class PersonalDetailsActivity : AppCompatActivity() {

    private var session: SessionManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaldetails)

        val toeditpersonalinfo = findViewById<ImageView>(R.id.editpersonalinfo)
        // Session manager
        session = SessionManager(this)

        toeditpersonalinfo.setOnClickListener {

            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
        val userimage =findViewById<CircleImageView>(R.id.personaldetailsimage)
        loadAvatar(this, userimage)

        loadUserProfile()

    }

    fun loadAvatar(
        context: Context?,
        imageView: ImageView?
    ) {
        val token =session!!.token;
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

                            namepersonaldetails.setText(profile!!.profileData!!.first_name + " " + profile.profileData!!.last_name)
                            emailpersonaldetails.setText(profile!!.profileData!!.email)
                            phonepersonaldetails.setText(profile!!.profileData!!.phone_no)
                            alternatename.setText(profile!!.profileData!!.first_name + " " + profile.profileData!!.last_name)
                            dob.setText(profile!!.profileData!!.dob)
                            religion.setText(profile!!.profileData!!.religion)
                            bloodgroup.setText(profile!!.profileData!!.blood_group)
                            maritalstatus.setText(profile!!.profileData!!.married)
                            phonepersonaldetailss.setText(profile!!.profileData!!.phone_no)
                            emailpersonaldetailss.setText(profile!!.profileData!!.email)
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
