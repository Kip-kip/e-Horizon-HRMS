package stlhorizon.org.hrmselfservice.activities.Leave

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.model.Leave.LeaveHistory

class LeaveItemActivity : AppCompatActivity() {
    private var str: String? = null
    private var txtReason: TextView? = null
    private  var txtFrom: TextView? = null
    private  var txtTo: TextView? = null
    private  var txtLeaveID: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaveitem)

//get extra from news adapter
        //get extra from news adapter
        str = intent.extras!!.getString("DETAIL")
        if (str != null) {
            loadPageElements()
        }

    }


    fun loadPageElements() {
        val leavehistoryModel: LeaveHistory.LeaveHistoryModel = LeaveHistory.createLeaveHistoryModelFrom(str)

        txtLeaveID?.setText(leavehistoryModel.leave_id+"")
        txtReason?.setText(leavehistoryModel.reason+"")
        txtFrom?.setText(leavehistoryModel.applied_from+"")
        txtTo?.setText(leavehistoryModel.applied_to+"")

    }


}
