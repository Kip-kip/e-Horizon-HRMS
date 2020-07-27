package stlhorizon.org.hrmselfservice.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import stlhorizon.org.hrmselfservice.fragments.dashboard.FragmentApplication
import stlhorizon.org.hrmselfservice.fragments.dashboard.FragmentHistory
import stlhorizon.org.hrmselfservice.fragments.dashboard.FragmentEncashment

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentApplication()
            }
            1 -> FragmentEncashment()
            else -> {
                return FragmentHistory()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Leave Application"
            1 -> "Leave Encashment"
            else -> {
                return "Leave History"
            }
        }
    }
}