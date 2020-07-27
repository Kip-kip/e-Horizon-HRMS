package stlhorizon.org.hrmselfservice.fragments.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Payslip.ProfileItemActivity

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val gotoprofileitem = root.findViewById<LinearLayout>(R.id.toprofileitem)

        gotoprofileitem.setOnClickListener {
            val intent = Intent(context, ProfileItemActivity::class.java)
            startActivity(intent)
        }
        return root
    }
}