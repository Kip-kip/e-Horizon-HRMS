package stlhorizon.org.hrmselfservice.activities.Leave

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_leaveitem.*
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.model.Leave.LeaveHistory

class LeaveItemActivity : AppCompatActivity() {
    private var str: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaveitem)

        val txtLeaveItemReason = findViewById<TextView>(R.id.txtLeaveItemReason)

//get extra from news adapter
        //get extra from news adapter
        str = intent.extras!!.getString("DETAIL")
        if (str != null) {
            loadPageElements()
        }

    }


    fun loadPageElements() {
        val leavehistoryModel: LeaveHistory.LeaveHistoryModel = LeaveHistory.createLeaveHistoryModelFrom(str)
        txtLeaveItemLeaveID?.setText(leavehistoryModel.leave_id+"")
        txtLeaveItemReason.setText(leavehistoryModel.reason+"")
        txtLeaveItemFrom?.setText(leavehistoryModel.applied_from+"")
        txtLeaveItemTo?.setText(leavehistoryModel.applied_to+"")

    }


}
