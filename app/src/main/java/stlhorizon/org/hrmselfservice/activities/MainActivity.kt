package stlhorizon.org.hrmselfservice.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import stlhorizon.org.hrmselfservice.FRAGMENT
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.fragments.chats.ChatListFragment
import stlhorizon.org.hrmselfservice.fragments.chats.SettingsFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.ApprovalsFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.DashboardFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.LeaveFragment
import stlhorizon.org.hrmselfservice.fragments.dashboard.ProfileFragment
import stlhorizon.org.hrmselfservice.fragments.home.HomeFragment
import stlhorizon.org.hrmselfservice.fragments.loan.DocsFragment
import stlhorizon.org.hrmselfservice.fragments.loan.LoanFragment
import stlhorizon.org.hrmselfservice.fragments.training.TrainingFragment


class MainActivity : AppCompatActivity() {

    var homeFragment: Fragment? = null
    var settingsFragment: Fragment? = null
    var profileFragment: Fragment? = null
    var loanFragment: Fragment? = null
    var leaveFragment: Fragment? = null
    var trainingFragment: Fragment? = null
    var chatsFragment: Fragment? = null
    var approvalsFragment: Fragment? = null
    var docsFragment: Fragment? = null

    object Foo {
        var lastFragment = FRAGMENT.HOME
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_settings,
                R.id.navigation_chatlist
            )
        )
        navView.setupWithNavController(navController)


    }


    /** Prevent creation of new instances of fragments every time  */
    fun getSingleInstance(fragment: FRAGMENT?): Fragment {
        when (fragment) {
            FRAGMENT.HOME -> {
                if (homeFragment == null) {
                   homeFragment = HomeFragment()
                }
                return HomeFragment()
            }
            FRAGMENT.SETTINGS -> {
                if (settingsFragment == null) {
                   settingsFragment = SettingsFragment()
                }
                return SettingsFragment()
            }
            FRAGMENT.PROFILE -> {
                if (profileFragment == null) {
                    profileFragment =ProfileFragment()
                }
                return ProfileFragment()
            }
            FRAGMENT.LOAN -> {
                if (loanFragment == null) {
                    loanFragment =LoanFragment()
                }
                return LoanFragment()
            }
            FRAGMENT.TRAINING -> {
                if (trainingFragment == null) {
                    trainingFragment = TrainingFragment()
                }
                return TrainingFragment()
            }
            FRAGMENT.LEAVE -> {
                if (leaveFragment == null) {
                    leaveFragment = LeaveFragment()
                }
                return LeaveFragment()
            }
            FRAGMENT.CHATS -> {
                if (chatsFragment == null) {
                    chatsFragment = ChatListFragment()
                }
                return ChatListFragment()
            }
            FRAGMENT.APPROVALS -> {
                if (approvalsFragment == null) {
                    approvalsFragment = ApprovalsFragment()
                }
                return ApprovalsFragment()
            }
            FRAGMENT.DOCS -> {
                if (docsFragment == null) {
                    docsFragment = DocsFragment()
                }
                return DocsFragment()
            }

        }
        return HomeFragment()
    }

    /** Fragment Transaction Function. Called during fragment transaction  */
    fun FragmentTransaction(frg: FRAGMENT?) {
        val currentFragment: Fragment =getSingleInstance(frg)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =
            fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, currentFragment)
        fragmentTransaction.commitNow()
    }

    override fun onBackPressed() {

        if (Foo.lastFragment === FRAGMENT.HOME) {
            finishAffinity()
        } else {
            Foo.lastFragment = FRAGMENT.HOME
           FragmentTransaction(FRAGMENT.HOME)
        }
    }



}


