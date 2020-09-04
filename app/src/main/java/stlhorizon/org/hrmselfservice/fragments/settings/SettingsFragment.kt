package stlhorizon.org.hrmselfservice.fragments.chats

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import stlhorizon.org.hrmselfservice.FRAGMENT
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Authentication.LoginActivity
import stlhorizon.org.hrmselfservice.activities.MainActivity

class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        val logout = root.findViewById<LinearLayout>(R.id.logout)

        //application button clicked--hide history and encashment
        logout.setOnClickListener {
            val it = Intent(context, LoginActivity::class.java)
            startActivity(it)
        }

        MainActivity.Foo.lastFragment= FRAGMENT.SETTINGS;

        return root
    }
}