package stlhorizon.org.hrmselfservice.fragments.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Leave.LeaveItemActivity
import stlhorizon.org.hrmselfservice.adapter.LeaveTypeAdapter
import stlhorizon.org.hrmselfservice.model.Leave.LeaveTypes
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException


class LeaveFragment : Fragment() {

    private var leaveTypeRecyclerView: RecyclerView? = null
    private var leaveTypeAdapter: LeaveTypeAdapter? = null

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

        leaveTypeRecyclerView = root.findViewById<RecyclerView>(R.id.leavetyperecyclerView)

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

        val horizontalLayoutManagaer =


        loadLeaveType()



        return root
    }



    fun loadLeaveType() {
        Toast.makeText(context, "wabe", Toast.LENGTH_LONG).show()

        val token =
            "eyJpYXQiOjE1OTY0NDU1MzUsImlzcyI6ImhybXM1LnN0bC1ob3Jpem9uLmNvbSIsIm5iZiI6MTU5NjQ0NTUzNSwiZXhwIjoxNTk2NDQ1NTQ1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImtpZCI6Ijg1ZjFjNTQ4Y2VlNWI2ODNmYWE0MGNjNjJhYTA1YWJjIn0.eyJ1c2VyX2lkIjoyMzEsInVzZXJuYW1lIjoiQ3lydXMiLCJmdWxsX25hbWUiOiJDeXJ1cyAgS2lwcm90aWNoIiwicGFydHlfaWQiOiIxNDg4MDgxIiwiZGF0ZV9vZl9iaXJ0aCI6IjE5OTQtMDktMTkiLCJnZW5kZXIiOiJNQUxFIiwiY2l0eSI6Ik5BSVJPQkkiLCJjb3VudHJ5IjoiS0UiLCJhcHBvaW50X2lkIjoiMTQ4ODA4NSIsImVudGl0eV9pZCI6IjEwMCIsImVudGl0eV9uYW1lIjoiU09GVFdBUkUgVEVDSE5PTE9HSUVTIExJTUlURUQiLCJwZXJubyI6IlNUTDEzNCIsImNvZGUiOiJIUjUwMDEiLCJpbWFnZSI6bnVsbH0.rDnJfGiTVFSjNtTGqTIw9iv-XI64_yg2PrHnrzRyGGo"
        NetworkConnection.makeAGetRequest(
            "https://hrms5.stl-horizon.com/api/web/api/leave-type?token=$token",
            null,
            object : OnReceivingResult {
                override fun onErrorResult(e: IOException) {
                    e.printStackTrace()
                    Log.e("error", e.message)
                }

                override fun onReceiving100SeriesResponse(remoteResponse: RemoteResponse?) {}
                override fun onReceiving200SeriesResponse(remoteResponse: RemoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse)
                    val response = remoteResponse.messangeAsJSON
                    try {
                        if (response.getString("success").equals("1", ignoreCase = true)) {
                            val leaveTypes: LeaveTypes = LeaveTypes.createLeaveTypesFrom(remoteResponse.message)
                            val leaveTypesModel: List<LeaveTypes.LeaveTypesModel> = leaveTypes.leaveTypesData!!

                            Toast.makeText(context, "wabe", Toast.LENGTH_SHORT).show()

                            leaveTypeAdapter = context?.let { LeaveTypeAdapter(it, leaveTypesModel) }
                            leaveTypeRecyclerView?.setAdapter(leaveTypeAdapter)
                            leaveTypeRecyclerView?.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))

                            return
                        } else {
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onReceiving300SeriesResponse(remoteResponse: RemoteResponse?) {}
                override fun onReceiving400SeriesResponse(remoteResponse: RemoteResponse?) {}
                override fun onReceiving500SeriesResponse(remoteResponse: RemoteResponse?) {}
                override fun onAnyEvent() {}
            })
    }



}