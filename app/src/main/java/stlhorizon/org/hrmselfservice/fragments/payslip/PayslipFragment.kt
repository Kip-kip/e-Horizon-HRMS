package stlhorizon.org.hrmselfservice.fragments.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Payslip.PayslipItemActivity

class PayslipFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_payslip, container, false)
        val gotoitem = root.findViewById<LinearLayout>(R.id.gotoitem)

        gotoitem.setOnClickListener {
            val intent = Intent(context, PayslipItemActivity::class.java)
            startActivity(intent)
        }
        return root
    }
}