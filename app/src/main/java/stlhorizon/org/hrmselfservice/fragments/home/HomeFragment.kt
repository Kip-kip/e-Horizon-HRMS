package stlhorizon.org.hrmselfservice.fragments.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_home.*
import noman.weekcalendar.WeekCalendar
import org.json.JSONException
import stlhorizon.org.hrmselfservice.FRAGMENT
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Calendar.CalendarActivity
import stlhorizon.org.hrmselfservice.activities.MainActivity
import stlhorizon.org.hrmselfservice.fragments.dashboard.ApprovalsFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.LeaveFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.PayslipFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.ProfileFragment
import stlhorizon.org.hrmselfservice.fragments.loan.DocsFragment
import stlhorizon.org.hrmselfservice.fragments.loan.LoanFragment
import stlhorizon.org.hrmselfservice.fragments.training.TrainingFragment
import stlhorizon.org.hrmselfservice.helper.SessionManager
import stlhorizon.org.hrmselfservice.model.user.Profile
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException

class HomeFragment : Fragment() {
    private var session: SessionManager? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val profilesection = root.findViewById<LinearLayout>(R.id.toprofile)
        val leaveapplicationcard = root.findViewById<LinearLayout>(R.id.toleave)
        val payslipcard = root.findViewById<LinearLayout>(R.id.topayslip)
        val approvalscard = root.findViewById<LinearLayout>(R.id.toapprovals)
        val loanscard = root.findViewById<LinearLayout>(R.id.toloan)
        val docscard = root.findViewById<LinearLayout>(R.id.todocs)
        val trainingcard = root.findViewById<LinearLayout>(R.id.totraining)
        val calendar = root.findViewById<View>(R.id.toCalendar)
        val userimage = root.findViewById<CircleImageView>(R.id.homeuserimage)
        // Session manager
        session = SessionManager(context)
        //to profile
        profilesection.setOnClickListener {
            replaceFragment(ProfileFragment())
           MainActivity.Foo.lastFragment = FRAGMENT.PROFILE
        }

        leaveapplicationcard.setOnClickListener {
            replaceFragment(LeaveFragment())
            MainActivity.Foo.lastFragment = FRAGMENT.LEAVE
        }

        payslipcard.setOnClickListener {
            replaceFragment(PayslipFragment())
            MainActivity.Foo.lastFragment = FRAGMENT.PAYSPLIP
        }

        approvalscard.setOnClickListener {
            replaceFragment(ApprovalsFragment())
            MainActivity.Foo.lastFragment = FRAGMENT.APPROVALS
        }
        loanscard.setOnClickListener {
            replaceFragment(LoanFragment())
            MainActivity.Foo.lastFragment = FRAGMENT.LOAN
        }
        docscard.setOnClickListener {
            replaceFragment(DocsFragment())
            MainActivity.Foo.lastFragment = FRAGMENT.DOCS
        }
        trainingcard.setOnClickListener {
            replaceFragment(TrainingFragment())
            MainActivity.Foo.lastFragment = FRAGMENT.TRAINING
        }
        calendar.setOnClickListener {
            val intent = Intent(activity, CalendarActivity::class.java)

            startActivity(intent)
        }

        loadUserProfile()
        loadAvatar(context, userimage)

        return root
    }

    private fun replaceFragment(fragment: Fragment) {
        var fr = getFragmentManager()?.beginTransaction()
        fr?.replace(R.id.nav_host_fragment, fragment)
        fr?.commit()
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

                            names.setText(profile!!.profileData!!.first_name + " " + profile.profileData!!.last_name)

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
