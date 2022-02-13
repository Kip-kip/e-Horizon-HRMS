package stlhorizon.org.hrmselfservice.fragments.chats

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import stlhorizon.org.hrmselfservice.FRAGMENT
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Authentication.LoginActivity
import stlhorizon.org.hrmselfservice.activities.MainActivity
import stlhorizon.org.hrmselfservice.repository.MyEventsDao
import stlhorizon.org.hrmselfservice.repository.UniscooDataBase

class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        val logout = root.findViewById<LinearLayout>(R.id.logout)
        val calendarsyncChecked = root.findViewById<SwitchCompat>(R.id.calendersyncChecked)
        val calendarsyncUnchecked = root.findViewById<SwitchCompat>(R.id.calendersyncUnchecked)

        //application button clicked--hide history and encashment
        logout.setOnClickListener {
            val it = Intent(context, LoginActivity::class.java)
            startActivity(it)
        }

        MainActivity.Foo.lastFragment= FRAGMENT.SETTINGS;


        //when calender unchecked is pressed


        //when calender unchecked is pressed
        calendarsyncUnchecked.setOnCheckedChangeListener { buttonView, isChecked ->
            val preferences: SharedPreferences
            val MY_SHARED_PREFERENCES = "CalendarSyncPref"
            preferences = activity!!.getSharedPreferences(
                MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
            val editor = preferences.edit()
            editor.putString("CAL_CHECK_STATUS", "1")
            editor.commit()
            calendarsyncUnchecked.visibility = View.GONE
            calendarsyncChecked.visibility = View.VISIBLE
        }

        //when calender checked is pressed

        //when calender checked is pressed
        calendarsyncChecked.setOnCheckedChangeListener { buttonView, isChecked ->
            val preferences: SharedPreferences
            val MY_SHARED_PREFERENCES = "CalendarSyncPref"
            preferences = activity!!.getSharedPreferences(
                MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
            val editor = preferences.edit()
            editor.putString("CAL_CHECK_STATUS", "0")
            editor.commit()
            calendarsyncChecked.visibility = View.GONE
            calendarsyncUnchecked.visibility = View.VISIBLE
            val uniscooDataBase: UniscooDataBase = UniscooDataBase.getInstance(context)
            val myEventsDao: MyEventsDao = uniscooDataBase.getMyEventsDao()
            myEventsDao.deleteAllCalenderEvent()
        }


        return root
    }
}