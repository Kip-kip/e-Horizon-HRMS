package stlhorizon.org.hrmselfservice.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.adapter.LeaveTypeAdapter.MyViewHolder
import stlhorizon.org.hrmselfservice.model.Leave.LeaveTypes


public class LeaveTypeAdapter(
    var context: Context,
    dataModelArrayList: List<LeaveTypes.LeaveTypesModel>
) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val inflater: LayoutInflater
    private val dataModelArrayListUsertypes: List<LeaveTypes.LeaveTypesModel>
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
        val leavetypeModel: LeaveTypes.LeaveTypesModel = dataModelArrayListUsertypes[position]
        holder.txtLeaveName.setText(leavetypeModel.leave_name)

        //style the card view
        if(leavetypeModel.short_name.equals("COMP")){
            holder.llCardBody.setBackgroundResource(R.drawable.curve_alledges_pink)
        }
        else if(leavetypeModel.short_name.equals("SICK")){
            holder.llCardBody.setBackgroundResource(R.drawable.curve_alledges_red)
        } else if(leavetypeModel.short_name.equals("PAT")){
            holder.llCardBody.setBackgroundResource(R.drawable.curve_alledges_yellow)
        } else if(leavetypeModel.short_name.equals("MAT")){
            holder.llCardBody.setBackgroundResource(R.drawable.curve_alledges_blue)
        } else if(leavetypeModel.short_name.equals("AL")){
            holder.llCardBody.setBackgroundResource(R.drawable.curve_alledges_green)
        }

        //style TextView
        holder.txtLeaveName.setTextSize(11F);


        holder.itemView.setOnClickListener {
          holder.edtLeaveId.setText(leavetypeModel.leave_id)
        }


    }

    override fun getItemCount(): Int {
        return dataModelArrayListUsertypes.size
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var txtLeaveName: TextView
        var edtLeaveId: EditText
        var llCardBody: LinearLayout

        init {
            txtLeaveName = itemView.findViewById(R.id.txtLeaveName)
            edtLeaveId = itemView.findViewById(R.id.txtLeaveId)
            llCardBody = itemView.findViewById(R.id.cardbody)

        }
    }

    init {
        inflater = LayoutInflater.from(context)
        dataModelArrayListUsertypes = dataModelArrayList
    }
}