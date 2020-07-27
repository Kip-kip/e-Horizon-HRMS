package stlhorizon.org.hrmselfservice.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.adapter.MyPagerAdapter

class LeaveFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_leave, container, false)
        val tabLayout = root.findViewById(R.id.tabs_main) as TabLayout
        val viewPager = root.findViewById(R.id.viewpager_main) as ViewPager
        viewPager.setAdapter(fragmentManager?.let { MyPagerAdapter(it) })
        tabLayout!!.post(Runnable { tabLayout!!.setupWithViewPager(viewPager) })
        return root
    }
}