package stlhorizon.org.hrmselfservice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.model.Leave.LeaveHistory


class LeaveHistoryAdapter(
    var context: Context,
    dataModelArrayList: List<LeaveHistory.LeaveHistoryModel>
) :
    RecyclerView.Adapter<LeaveHistoryAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private val dataModelArrayListLeavehistory: List<LeaveHistory.LeaveHistoryModel>
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = inflater.inflate(R.layout.leavetype_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val leavetypeModel: LeaveHistory.LeaveHistoryModel = dataModelArrayListLeavehistory[position]
        holder.txtLeaveName.setText(leavetypeModel.leave_name)

        //style the card view

        //style TextView
        holder.txtLeaveName.setTextSize(11F);

    }

    override fun getItemCount(): Int {
        return dataModelArrayListLeavehistory.size
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var txtLeaveName: TextView
        lateinit var llCardBody: LinearLayout

        init {
            txtLeaveName = itemView.findViewById(R.id.txtLeaveName)
            llCardBody = itemView.findViewById(R.id.cardbody)

        }
    }

    init {
        inflater = LayoutInflater.from(context)
        dataModelArrayListLeavehistory = dataModelArrayList
    }
}