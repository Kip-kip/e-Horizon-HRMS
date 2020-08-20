package stlhorizon.org.hrmselfservice.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import noman.weekcalendar.WeekCalendar
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.CalendarActivity
import stlhorizon.org.hrmselfservice.activities.Leave.LoanItemActivity
import stlhorizon.org.hrmselfservice.fragments.dashboard.ApprovalsFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.LeaveFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.PayslipFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.ProfileFragment
import stlhorizon.org.hrmselfservice.fragments.loan.DocsFragment
import stlhorizon.org.hrmselfservice.fragments.loan.LoanFragment
import stlhorizon.org.hrmselfservice.fragments.training.TrainingFragment

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

        return root
    }

    private fun replaceFragment(fragment: Fragment) {
        var fr = getFragmentManager()?.beginTransaction()
        fr?.replace(R.id.nav_host_fragment,fragment)
        fr?.commit()
    }

}
