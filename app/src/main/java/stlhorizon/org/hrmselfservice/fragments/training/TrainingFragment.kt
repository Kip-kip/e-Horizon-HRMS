package stlhorizon.org.hrmselfservice.fragments.training

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
import kotlinx.android.synthetic.main.fragment_leave.tab_layout
import kotlinx.android.synthetic.main.fragment_training.*
import kotlinx.android.synthetic.main.traininghistory_list_item.view.*
import org.json.JSONException
import org.json.JSONObject
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.training.TrainingItemActivity
import stlhorizon.org.hrmselfservice.adapter.TrainingCoursesAdapterkt
import stlhorizon.org.hrmselfservice.helper.SessionManager
import stlhorizon.org.hrmselfservice.model.Leave.LeaveHistory
import stlhorizon.org.hrmselfservice.model.Leave.LeaveTypes
import stlhorizon.org.hrmselfservice.model.Training.TrainingHistory
import stlhorizon.org.hrmselfservice.model.login.LeaveApplicationSuccessResponse
import stlhorizon.org.hrmselfservice.model.login.LoginErrorResponse
import stlhorizon.org.hrmselfservice.model.login.TrainingRequestSuccessResponse
import stlhorizon.org.hrmselfservice.model.training.TrainingCourses
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException
import java.util.*


class TrainingFragment : Fragment() {

    private var trainingCoursesRecyclerView: RecyclerView? = null
    private var trainingcoursesAdapter: TrainingCoursesAdapterkt? = null
    private var txtRefNo: TextView? = null
    private var txtCourseStatus: TextView? = null

    private var txtTrainingReason: TextView? = null
    private var session: SessionManager? = null

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
        val root = inflater.inflate(R.layout.fragment_training, container, false)
//        val tabLayout = root.findViewById(R.id.tabs_main) as TabLayout
//        val viewPager = root.findViewById(R.id.viewpager_main) as ViewPager
//        viewPager.setAdapter(fragmentManager?.let { MyPagerAdapter(it) })
//        tabLayout!!.post(Runnable { tabLayout!!.setupWithViewPager(viewPager) })

        val btntrainingrequest = root.findViewById<Button>(R.id.btntrainingrequest)
        val btntraininghistory = root.findViewById<Button>(R.id.btntraininghistory)
        val btnRequest = root.findViewById<Button>(R.id.btnRequest)

        val lltrainingrequest = root.findViewById<LinearLayout>(R.id.lltrainingrequest)
        val lltraininghistory = root.findViewById<LinearLayout>(R.id.lltraininghistory)

        val imgTrainingFrom = root.findViewById<ImageView>(R.id.imgTrainingFrom)
        val imgTrainingTo = root.findViewById<ImageView>(R.id.imgTrainingTo)

        val txtTrainingFrom = root.findViewById<EditText>(R.id.txtTrainingFrom)
        val txtTrainingTo = root.findViewById<EditText>(R.id.txtTrainingTo)
        txtTrainingReason = root.findViewById(R.id.txtTrainingReason)
        // Session manager
        session = SessionManager(context)

        //val gotohistoryitem = root.findViewById<TableRow>(R.id.gotohistoryitem)


        trainingCoursesRecyclerView =
            root.findViewById<RecyclerView>(R.id.trainingcourserecyclerView)

        //application button clicked--hide history and encashment
        btntrainingrequest.setOnClickListener {
            lltrainingrequest.visibility = View.VISIBLE
            lltraininghistory.visibility = View.GONE
        }
        //history button clicked--hide application and encashment
        btntraininghistory.setOnClickListener {
            lltrainingrequest.visibility = View.GONE
            lltraininghistory.visibility = View.VISIBLE
        }

        //Apply for leave
        btnRequest.setOnClickListener {
            requestForTraining()
        }


//choose From
        imgTrainingFrom.setOnClickListener(View.OnClickListener { // Get Current Date
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(
                activity!!,
                R.style.DialogTheme,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val monthSelected = (monthOfYear + 1).toString()
                    val daySelected = dayOfMonth.toString()
                    val month =
                        if (monthSelected.length == 1) "0$monthSelected" else monthSelected
                    val day =
                        if (daySelected.length == 1) "0$daySelected" else daySelected
                    val date = "$year-$month-$day"

                    txtTrainingFrom.visibility = View.VISIBLE

                    txtTrainingFrom.setText(date)


                },
                mYear,
                mMonth,
                mDay
            )
            datePickerDialog.show()
        })


//choose To
        imgTrainingTo.setOnClickListener(View.OnClickListener { // Get Current Date
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(
                activity!!,
                R.style.DialogTheme,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val monthSelected = (monthOfYear + 1).toString()
                    val daySelected = dayOfMonth.toString()
                    val month =
                        if (monthSelected.length == 1) "0$monthSelected" else monthSelected
                    val day =
                        if (daySelected.length == 1) "0$daySelected" else daySelected
                    val date = "$year-$month-$day"


                    txtTrainingTo.visibility = View.VISIBLE
                    txtTrainingTo.setText(date)


                },
                mYear,
                mMonth,
                mDay
            )
            datePickerDialog.show()
        })




        loadTrainingCourses()

        loadTrainingHistory()

        return root
    }


    fun loadTrainingCourses() {


        val token = session!!.token;
        NetworkConnection.makeAGetRequest(
            "https://hrms5.stl-horizon.com/api/web/api/training-courses?token=$token",
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
                            val trainingCourses: TrainingCourses =
                                TrainingCourses.createTrainingCoursesFrom(remoteResponse.message)
                            val trainingCoursesModel: List<TrainingCourses.TrainingCoursesModel> =
                                trainingCourses.trainingCoursesData!!



                            trainingcoursesAdapter =
                                context?.let { TrainingCoursesAdapterkt(it, trainingCoursesModel) }
                            trainingCoursesRecyclerView?.setAdapter(trainingcoursesAdapter)
                            trainingCoursesRecyclerView?.setLayoutManager(
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

    fun loadTrainingHistory() {

        val token = session!!.token;

        NetworkConnection.makeAGetRequest(
            "https://hrms5.stl-horizon.com/api/web/api/training-history?token=$token",
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
                            val traininghistory: TrainingHistory? =
                                TrainingHistory.createTrainingHistoryFrom(remoteResponse.message)
                            if (traininghistory != null) {
                                if (traininghistory.isAResponseASuccess) {
                                    for (traininghistorymodel in traininghistory.trainingHistoryData!!) {
                                        tab_layout.addView(
                                            this@TrainingFragment.populateTableTrainingHistory(
                                                traininghistorymodel
                                            )
                                        )
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "" + traininghistory.success,
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


    private fun populateTableTrainingHistory(trainingHistoryModel: TrainingHistory.TrainingHistoryModel): TableRow? {
        val view = view
        val tableRow = LayoutInflater.from(context)
            .inflate(R.layout.traininghistory_list_item, null, false) as TableRow
        txtRefNo = tableRow.findViewById<TextView>(R.id.txtRefNo)
        txtCourseStatus = tableRow.findViewById<TextView>(R.id.txtTrainingCourseName)
        tableRow.txtRefNo.setText(trainingHistoryModel.reference_no)


        tableRow.setOnClickListener(
            HistoryClickEvent(
                trainingHistoryModel
            )
        )
        return tableRow

    }


    internal class HistoryClickEvent(trainingHistoryModel: TrainingHistory.TrainingHistoryModel) :
        View.OnClickListener {
        private var trainingHistoryModel: TrainingHistory.TrainingHistoryModel? = null


        fun HistoryClickEvent(leaveHistoryModel: TrainingHistory.TrainingHistoryModel?) {
            this.trainingHistoryModel = trainingHistoryModel

        }

        override fun onClick(view: View) {

            val intent = Intent(view.context, TrainingItemActivity::class.java)
            intent.putExtra("DETAIL", trainingHistoryModel.toString())
            view.context.startActivity(intent)

        }


        init {
            this.trainingHistoryModel = trainingHistoryModel
        }
    }


    /** REQUEST FOR TRAIINING **/


    fun requestForTraining() {

        //Get selected leave ID
        val M_SHARED_PREFERENCES = "CourseID"
        val mPreferences: SharedPreferences = activity!!.getSharedPreferences(
            M_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val course_id = mPreferences.getString("COURSE_ID", "0")


        val token = session!!.token;

        val jsonObject = JSONObject()
        val headers = JSONObject()
        try {
            headers.put("Content-Type", "multipart/form-data")
            jsonObject.put("course_id", course_id.toString())
            jsonObject.put("applied_from", txtTrainingFrom?.text.toString())
            jsonObject.put("applied_to", txtTrainingTo?.text.toString())
            jsonObject.put("reason", txtTrainingReason?.text.toString())
            NetworkConnection.makeAPostRequestFormData("https://hrms5.stl-horizon.com/api/web/api/request-training?token=$token",
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
                            val trainingRequestSuccessResponse: TrainingRequestSuccessResponse =
                                TrainingRequestSuccessResponse.createTrainingRequestSuccessResponseFrom(
                                    remoteResponse.message
                                )

                            if (response.getString("success").equals("1", ignoreCase = true)) {


                                Toast.makeText(
                                    activity,
                                    trainingRequestSuccessResponse.message,
                                    Toast.LENGTH_LONG
                                ).show()


                                return
                            } else {

                                Toast.makeText(
                                    activity,
                                    trainingRequestSuccessResponse.message,
                                    Toast.LENGTH_LONG
                                ).show()

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