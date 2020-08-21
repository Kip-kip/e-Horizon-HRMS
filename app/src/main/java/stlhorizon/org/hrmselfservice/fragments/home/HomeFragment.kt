package stlhorizon.org.hrmselfservice.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import noman.weekcalendar.WeekCalendar
import org.json.JSONException
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.CalendarActivity
import stlhorizon.org.hrmselfservice.activities.Leave.LoanItemActivity
import stlhorizon.org.hrmselfservice.adapter.LeaveTypeAdapter
import stlhorizon.org.hrmselfservice.fragments.dashboard.ApprovalsFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.LeaveFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.PayslipFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.ProfileFragment
import stlhorizon.org.hrmselfservice.fragments.loan.DocsFragment
import stlhorizon.org.hrmselfservice.fragments.loan.LoanFragment
import stlhorizon.org.hrmselfservice.fragments.training.TrainingFragment
import stlhorizon.org.hrmselfservice.model.Leave.LeaveTypes
import stlhorizon.org.hrmselfservice.model.user.Profile
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException

class HomeFragment : Fragment() {

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
        val calendar =root.findViewById<TextView>(R.id.toCalendar)
        //to profile
        profilesection.setOnClickListener {
            replaceFragment(ProfileFragment())
        }

        leaveapplicationcard.setOnClickListener {
           replaceFragment(LeaveFragment())
        }

        payslipcard.setOnClickListener {
            replaceFragment(PayslipFragment())
        }

        approvalscard.setOnClickListener {
            replaceFragment(ApprovalsFragment())
        }
        loanscard.setOnClickListener {
            replaceFragment(LoanFragment())
        }
        docscard.setOnClickListener {
            replaceFragment(DocsFragment())
        }
        trainingcard.setOnClickListener {
                replaceFragment(TrainingFragment())
        }
        calendar.setOnClickListener {
            val intent = Intent(activity, CalendarActivity::class.java)
            startActivity(intent)
        }

        loadUserProfile()

        return root
    }

    private fun replaceFragment(fragment: Fragment) {
        var fr = getFragmentManager()?.beginTransaction()
        fr?.replace(R.id.nav_host_fragment,fragment)
        fr?.commit()
    }

    fun loadUserProfile(){

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
                            val profile: Profile? = Profile.createProfileFrom(remoteResponse.message)

                            names.setText(profile!!.profileData!!.first_name+" "+profile.profileData!!.last_name)

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
