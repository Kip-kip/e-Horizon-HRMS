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
import stlhorizon.org.hrmselfservice.helper.SessionManager
import stlhorizon.org.hrmselfservice.model.user.Profile
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException

class ProfileFragment : Fragment() {
    private var session: SessionManager? = null
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

        // Session manager
        session = SessionManager(context)

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