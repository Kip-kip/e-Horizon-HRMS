package stlhorizon.org.hrmselfservice.fragments.dashboard

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_leave.*
import kotlinx.android.synthetic.main.leavehistory_list_item.view.*
import org.json.JSONException
import org.json.JSONObject
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Leave.LeaveItemActivity
import stlhorizon.org.hrmselfservice.adapter.LeaveTypeAdapter
import stlhorizon.org.hrmselfservice.model.Leave.LeaveHistory
import stlhorizon.org.hrmselfservice.model.Leave.LeaveTypes
import stlhorizon.org.hrmselfservice.model.login.LeaveApplicationSuccessResponse
import stlhorizon.org.hrmselfservice.model.login.LoginErrorResponse
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException
import java.util.*


class LeaveFragment : Fragment() {

    private var leaveTypeRecyclerView: RecyclerView? = null
    private var leaveTypeAdapter: LeaveTypeAdapter? = null
    private var txtTask: TextView? = null
    private var txtSDate: TextView? = null
    private var txtEDate: TextView? = null

    private var txtLeaveTo: EditText? = null
    private var txtReason: TextView? = null
    var myGlobalString = "initial_value"

    val c = Calendar.getInstance()
    var hour: Int = c.get(Calendar.HOUR_OF_DAY)
    var minute: Int = c.get(Calendar.MINUTE)
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mYear: Int = 0

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
        val btnapply = root.findViewById<Button>(R.id.btnApply)
        val imgLeaveFrom = root.findViewById<ImageView>(R.id.imgLeaveFrom)
        val imgLeaveTo = root.findViewById<ImageView>(R.id.imgLeaveTo)

        val llapplication = root.findViewById<LinearLayout>(R.id.llapplication)
        val llencashment = root.findViewById<LinearLayout>(R.id.llencashment)
        val llhistory = root.findViewById<LinearLayout>(R.id.llhistory)

        val txtLeaveFrom = root.findViewById<EditText>(R.id.txtLeaveFrom)
        val txtLeaveTo = root.findViewById<EditText>(R.id.txtLeaveTo)
        txtReason = root.findViewById(R.id.txtReason)

        //val gotohistoryitem = root.findViewById<TableRow>(R.id.gotohistoryitem)

        leaveTypeRecyclerView = root.findViewById<RecyclerView>(R.id.leavetyperecyclerView)

        //application button clicked--hide history and encashment
        btnapplication.setOnClickListener {
            llapplication.visibility = View.VISIBLE
            llhistory.visibility = View.GONE
            llencashment.visibility = View.GONE
        }

        //encashment button clicked--hide history and application
        btnencashment.setOnClickListener {
            llapplication.visibility = View.GONE
            llhistory.visibility = View.GONE
            llencashment.visibility = View.VISIBLE
        }

        //history button clicked--hide application and encashment
        btnhistory.setOnClickListener {
            llapplication.visibility = View.GONE
            llhistory.visibility = View.VISIBLE
            llencashment.visibility = View.GONE
        }

        //Apply for leave
        btnapply.setOnClickListener {
            applyForLeave()
        }

//choose From
        imgLeaveFrom.setOnClickListener(View.OnClickListener { // Get Current Date
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(
                activity!!,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val monthSelected = (monthOfYear + 1).toString()
                    val daySelected = dayOfMonth.toString()
                    val month =
                        if (monthSelected.length == 1) "0$monthSelected" else monthSelected
                    val day =
                        if (daySelected.length == 1) "0$daySelected" else daySelected
                    val date = "$year-$month-$day"



                           txtLeaveFrom.setText(date)



                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        })


//choose To
        imgLeaveTo.setOnClickListener(View.OnClickListener { // Get Current Date
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(
                activity!!,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val monthSelected = (monthOfYear + 1).toString()
                    val daySelected = dayOfMonth.toString()
                    val month =
                        if (monthSelected.length == 1) "0$monthSelected" else monthSelected
                    val day =
                        if (daySelected.length == 1) "0$daySelected" else daySelected
                    val date = "$year-$month-$day"



                    txtLeaveTo.setText(date)



                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        })


        loadLeaveType()

        loadLeaveHistory()

        return root
    }


    fun loadLeaveType() {


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
                            val leaveTypes: LeaveTypes =
                                LeaveTypes.createLeaveTypesFrom(remoteResponse.message)
                            val leaveTypesModel: List<LeaveTypes.LeaveTypesModel> = leaveTypes.leaveTypesData!!



                            leaveTypeAdapter =
                                context?.let { LeaveTypeAdapter(it, leaveTypesModel) }
                            leaveTypeRecyclerView?.setAdapter(leaveTypeAdapter)
                            leaveTypeRecyclerView?.setLayoutManager(
                                LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            )

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
//
//
//    fun loadLeaveHistory() {
//        Toast.makeText(context, "wabe", Toast.LENGTH_LONG).show()
//
//        val token =
//            "eyJpYXQiOjE1OTY0NDU1MzUsImlzcyI6ImhybXM1LnN0bC1ob3Jpem9uLmNvbSIsIm5iZiI6MTU5NjQ0NTUzNSwiZXhwIjoxNTk2NDQ1NTQ1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImtpZCI6Ijg1ZjFjNTQ4Y2VlNWI2ODNmYWE0MGNjNjJhYTA1YWJjIn0.eyJ1c2VyX2lkIjoyMzEsInVzZXJuYW1lIjoiQ3lydXMiLCJmdWxsX25hbWUiOiJDeXJ1cyAgS2lwcm90aWNoIiwicGFydHlfaWQiOiIxNDg4MDgxIiwiZGF0ZV9vZl9iaXJ0aCI6IjE5OTQtMDktMTkiLCJnZW5kZXIiOiJNQUxFIiwiY2l0eSI6Ik5BSVJPQkkiLCJjb3VudHJ5IjoiS0UiLCJhcHBvaW50X2lkIjoiMTQ4ODA4NSIsImVudGl0eV9pZCI6IjEwMCIsImVudGl0eV9uYW1lIjoiU09GVFdBUkUgVEVDSE5PTE9HSUVTIExJTUlURUQiLCJwZXJubyI6IlNUTDEzNCIsImNvZGUiOiJIUjUwMDEiLCJpbWFnZSI6bnVsbH0.rDnJfGiTVFSjNtTGqTIw9iv-XI64_yg2PrHnrzRyGGo"
//
//        NetworkConnection.makeAGetRequest("https://hrms5.stl-horizon.com/api/web/api/leave-history?token=$token", null, object : OnReceivingResult {
//                override fun onErrorResult(e: IOException) {
//                    e.printStackTrace()
//                    Log.e("error", e.message)
//                }
//
//                override fun onReceiving100SeriesResponse(remoteResponse: RemoteResponse?) {}
//                override fun onReceiving200SeriesResponse(remoteResponse: RemoteResponse) {
//                    NetworkConnection.remoteResponseLogger(remoteResponse)
//                    val response = remoteResponse.messangeAsJSON
//                    try {
//                        if (response.getString("success").equals("1", ignoreCase = true)) {
//
//                            Toast.makeText(context, "wabe", Toast.LENGTH_SHORT).show()
//
//
//
//                            return
//                        } else {
//                        }
//                    } catch (e: JSONException) {
//                        e.printStackTrace()
//                    }
//                }
//
//                override fun onReceiving300SeriesResponse(remoteResponse: RemoteResponse?) {}
//                override fun onReceiving400SeriesResponse(remoteResponse: RemoteResponse?) {}
//                override fun onReceiving500SeriesResponse(remoteResponse: RemoteResponse?) {}
//                override fun onAnyEvent() {}
//            })
//    }


    fun loadLeaveHistory() {
        val token =
            "eyJpYXQiOjE1OTY0NDU1MzUsImlzcyI6ImhybXM1LnN0bC1ob3Jpem9uLmNvbSIsIm5iZiI6MTU5NjQ0NTUzNSwiZXhwIjoxNTk2NDQ1NTQ1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImtpZCI6Ijg1ZjFjNTQ4Y2VlNWI2ODNmYWE0MGNjNjJhYTA1YWJjIn0.eyJ1c2VyX2lkIjoyMzEsInVzZXJuYW1lIjoiQ3lydXMiLCJmdWxsX25hbWUiOiJDeXJ1cyAgS2lwcm90aWNoIiwicGFydHlfaWQiOiIxNDg4MDgxIiwiZGF0ZV9vZl9iaXJ0aCI6IjE5OTQtMDktMTkiLCJnZW5kZXIiOiJNQUxFIiwiY2l0eSI6Ik5BSVJPQkkiLCJjb3VudHJ5IjoiS0UiLCJhcHBvaW50X2lkIjoiMTQ4ODA4NSIsImVudGl0eV9pZCI6IjEwMCIsImVudGl0eV9uYW1lIjoiU09GVFdBUkUgVEVDSE5PTE9HSUVTIExJTUlURUQiLCJwZXJubyI6IlNUTDEzNCIsImNvZGUiOiJIUjUwMDEiLCJpbWFnZSI6bnVsbH0.rDnJfGiTVFSjNtTGqTIw9iv-XI64_yg2PrHnrzRyGGo"

        NetworkConnection.makeAGetRequest(
            "https://hrms5.stl-horizon.com/api/web/api/leave-history?token=$token",
            null,
            object :
                OnReceivingResult {
                override fun onErrorResult(e: IOException) {
                    e.printStackTrace()
                }

                override fun onReceiving100SeriesResponse(remoteResponse: RemoteResponse?) {
                    NetworkConnection.remoteResponseLogger(remoteResponse)
                }

                override fun onReceiving200SeriesResponse(remoteResponse: RemoteResponse) {
                    //  NetworkConnection.remoteResponseLogger(remoteResponse);
                    val response = remoteResponse.messangeAsJSON
                    try {
                        if (response.getString("success").equals("1", ignoreCase = true)) {
                            val leavehistory: LeaveHistory? =
                                LeaveHistory.createLeaveHistoryFrom(remoteResponse.message)
                            if (leavehistory != null) {
                                if (leavehistory.isAResponseASuccess) {
                                    for (leavehistorymodel in leavehistory.leaveHistoryData!!) {
                                        tab_layout.addView(
                                            this@LeaveFragment.populateTableLeaveHistory(
                                                leavehistorymodel
                                            )
                                        )
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "" + leavehistory.success,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }



                            return
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onReceiving300SeriesResponse(remoteResponse: RemoteResponse?) {
                    NetworkConnection.remoteResponseLogger(remoteResponse)
                }

                override fun onReceiving400SeriesResponse(remoteResponse: RemoteResponse?) {
                    NetworkConnection.remoteResponseLogger(remoteResponse)
                }

                override fun onReceiving500SeriesResponse(remoteResponse: RemoteResponse?) {
                    NetworkConnection.remoteResponseLogger(remoteResponse)
                }

                override fun onAnyEvent() {}
            })
    }


    private fun populateTableLeaveHistory(leaveHistoryModel: LeaveHistory.LeaveHistoryModel): TableRow? {
        val view = view
        val tableRow = LayoutInflater.from(context)
            .inflate(R.layout.leavehistory_list_item, null, false) as TableRow
        // txtTask = tableRow.findViewById<TextView>(R.id.txtTask)
        txtSDate = tableRow.findViewById<TextView>(R.id.txtSDate)
        txtEDate = tableRow.findViewById<TextView>(R.id.txtEDate)
        tableRow.txtTask.setText(leaveHistoryModel.leave_name)


        tableRow.setOnClickListener(
            HistoryClickEvent(
                leaveHistoryModel
            )
        )
        return tableRow

    }


    internal class HistoryClickEvent(leaveHistoryModel: LeaveHistory.LeaveHistoryModel) :
        View.OnClickListener {
        private var leaveHistoryModel: LeaveHistory.LeaveHistoryModel? = null


        fun HistoryClickEvent(leaveHistoryModel: LeaveHistory.LeaveHistoryModel?) {
            this.leaveHistoryModel = leaveHistoryModel

        }

        override fun onClick(view: View) {


            val intent = Intent(view.context, LeaveItemActivity::class.java)
            intent.putExtra("DETAIL", leaveHistoryModel.toString())
            view.context.startActivity(intent)

        }




        init {
            this.leaveHistoryModel = leaveHistoryModel
        }
    }


    /** APPLY FOR LEAVE**/


    fun applyForLeave() {

        //Get selected leave ID
        val M_SHARED_PREFERENCES = "CodeRequestPref"
        val mPreferences: SharedPreferences = activity!!.getSharedPreferences(
            M_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val code_request_status = mPreferences.getString("SKIP_CODE_REQUEST", "0")


        val token =
            "eyJpYXQiOjE1OTY0NDU1MzUsImlzcyI6ImhybXM1LnN0bC1ob3Jpem9uLmNvbSIsIm5iZiI6MTU5NjQ0NTUzNSwiZXhwIjoxNTk2NDQ1NTQ1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImtpZCI6Ijg1ZjFjNTQ4Y2VlNWI2ODNmYWE0MGNjNjJhYTA1YWJjIn0.eyJ1c2VyX2lkIjoyMzEsInVzZXJuYW1lIjoiQ3lydXMiLCJmdWxsX25hbWUiOiJDeXJ1cyAgS2lwcm90aWNoIiwicGFydHlfaWQiOiIxNDg4MDgxIiwiZGF0ZV9vZl9iaXJ0aCI6IjE5OTQtMDktMTkiLCJnZW5kZXIiOiJNQUxFIiwiY2l0eSI6Ik5BSVJPQkkiLCJjb3VudHJ5IjoiS0UiLCJhcHBvaW50X2lkIjoiMTQ4ODA4NSIsImVudGl0eV9pZCI6IjEwMCIsImVudGl0eV9uYW1lIjoiU09GVFdBUkUgVEVDSE5PTE9HSUVTIExJTUlURUQiLCJwZXJubyI6IlNUTDEzNCIsImNvZGUiOiJIUjUwMDEiLCJpbWFnZSI6bnVsbH0.rDnJfGiTVFSjNtTGqTIw9iv-XI64_yg2PrHnrzRyGGo"


        val jsonObject = JSONObject()
        val headers = JSONObject()
        try {
            headers.put("Content-Type", "multipart/form-data")
            jsonObject.put("leave_id", code_request_status.toString())
            jsonObject.put("applied_from", txtLeaveFrom?.text.toString())
            jsonObject.put("applied_to", txtLeaveTo?.text.toString())
            jsonObject.put("reason", txtReason?.text.toString())
            NetworkConnection.makeAPostRequestFormData("https://hrms5.stl-horizon.com/api/web/api/leave-application?token=$token",
                jsonObject,
                headers,
                object : OnReceivingResult {
                    override fun onErrorResult(e: IOException) {
                        Log.e("error", e.message)

                    }

                    override fun onReceiving100SeriesResponse(remoteResponse: RemoteResponse?) {
                        NetworkConnection.remoteResponseLogger(remoteResponse)
                    }

                    override fun onReceiving200SeriesResponse(remoteResponse: RemoteResponse?) {
                        NetworkConnection.remoteResponseLogger(remoteResponse)
                        val response: JSONObject = remoteResponse!!.messangeAsJSON
                        try {

                            /**
                             * Success 1
                             * **/

                            if (response.getString("success").equals("1", ignoreCase = true)) {
                                val leaveApplicationSuccessResponse: LeaveApplicationSuccessResponse =
                                    LeaveApplicationSuccessResponse.createLeaveApplicationSuccessResponseFrom(
                                        remoteResponse.message
                                    )


                                Toast.makeText(
                                    activity,
                                    leaveApplicationSuccessResponse.message,
                                    Toast.LENGTH_LONG
                                ).show()


                                return
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }


                        /**
                         * Success 0
                         * **/

                        val loginErrorResponse =
                            LoginErrorResponse.createLoginErrorResponseFrom(remoteResponse.message)

                        if (loginErrorResponse.success.equals("0", ignoreCase = true)) {


                        } else {

                        }


                    }

                    override fun onReceiving300SeriesResponse(remoteResponse: RemoteResponse?) {
                        NetworkConnection.remoteResponseLogger(remoteResponse)
                    }

                    override fun onReceiving400SeriesResponse(remoteResponse: RemoteResponse?) {
                        NetworkConnection.remoteResponseLogger(remoteResponse)
                    }

                    override fun onReceiving500SeriesResponse(remoteResponse: RemoteResponse?) {
                        NetworkConnection.remoteResponseLogger(remoteResponse)
                    }

                    override fun onAnyEvent() {

                    }
                })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


}