package stlhorizon.org.hrmselfservice.fragments.loan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Leave.LoanItemActivity
import stlhorizon.org.hrmselfservice.activities.Leave.LoanRequestActivitykot
import stlhorizon.org.hrmselfservice.activities.Loan.LoanRequestActivity

class LoanFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_loans, container, false)
        val gotoitem = root.findViewById<LinearLayout>(R.id.gotoitem)
        val request = root.findViewById<FloatingActionButton>(R.id.request)

        gotoitem.setOnClickListener {
            val intent = Intent(context, LoanItemActivity::class.java)
            startActivity(intent)
        }

        request.setOnClickListener {
            val intent = Intent(context, LoanRequestActivity::class.java)
            startActivity(intent)
        }

        return root
    }
}