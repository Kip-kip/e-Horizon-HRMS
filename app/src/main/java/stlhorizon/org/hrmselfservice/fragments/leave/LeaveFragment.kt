package stlhorizon.org.hrmselfservice.fragments.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.fragment.app.Fragment
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Leave.LeaveItemActivity
import stlhorizon.org.hrmselfservice.activities.MainActivity
import stlhorizon.org.hrmselfservice.activities.Payslip.PayslipItemActivity

class LeaveFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_leave, container, false)
//        val tabLayout = root.findViewById(R.id.tabs_main) as TabLayout
//        val viewPager = root.findViewById(R.id.viewpager_main) as ViewPager
//        viewPager.setAdapter(fragmentManager?.let { MyPagerAdapter(it) })
//        tabLayout!!.post(Runnable { tabLayout!!.setupWithViewPager(viewPager) })

        val btnapplication = root.findViewById<Button>(R.id.btnapplication)
        val btnencashment = root.findViewById<Button>(R.id.btnencashment)
        val btnhistory = root.findViewById<Button>(R.id.btnhistory)

        val llapplication = root.findViewById<LinearLayout>(R.id.llapplication)
        val llencashment = root.findViewById<LinearLayout>(R.id.llencashment)
        val llhistory = root.findViewById<LinearLayout>(R.id.llhistory)

        val gotohistoryitem = root.findViewById<TableRow>(R.id.gotohistoryitem)

        //application button clicked--hide history and encashment
        btnapplication.setOnClickListener {
            llapplication.visibility = View.VISIBLE
            llhistory.visibility = View.GONE
            llencashment.visibility=View.GONE
        }

        //encashment button clicked--hide history and application
        btnencashment.setOnClickListener {
            llapplication.visibility = View.GONE
            llhistory.visibility = View.GONE
            llencashment.visibility=View.VISIBLE
        }

        //history button clicked--hide application and encashment
        btnhistory.setOnClickListener {
            llapplication.visibility = View.GONE
            llhistory.visibility = View.VISIBLE
            llencashment.visibility=View.GONE
        }

        //go to history item
        gotohistoryitem.setOnClickListener {
            val intent = Intent(context, LeaveItemActivity::class.java)
            startActivity(intent)
        }

        return root
    }
}