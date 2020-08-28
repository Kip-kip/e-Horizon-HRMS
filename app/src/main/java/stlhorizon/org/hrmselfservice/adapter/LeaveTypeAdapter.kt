package stlhorizon.org.hrmselfservice.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
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
        holder.txtLeaveName.setText(leavetypeModel.leave_name!!.dropLast(5))
        holder.txtBalance.setText("Balance: "+leavetypeModel.balance)

        //style the card view
        if (leavetypeModel.short_name.equals("COMP")) {
            holder.llCardBody.setBackgroundResource(R.drawable.curve_alledges_pink)
            holder.imgcircle.setBackgroundResource(R.drawable.hug)
        } else if (leavetypeModel.short_name.equals("SICK")) {
            holder.llCardBody.setBackgroundResource(R.drawable.curve_alledges_red)
            holder.imgcircle.setBackgroundResource(R.drawable.headache)
        } else if (leavetypeModel.short_name.equals("PAT")) {
            holder.llCardBody.setBackgroundResource(R.drawable.curve_alledges_yellow)
            holder.imgcircle.setBackgroundResource(R.drawable.pregnancy)
        } else if (leavetypeModel.short_name.equals("MAT")) {
            holder.llCardBody.setBackgroundResource(R.drawable.curve_alledges_blue)
            holder.imgcircle.setBackgroundResource(R.drawable.pregnant)
        } else if (leavetypeModel.short_name.equals("AL")) {
            holder.llCardBody.setBackgroundResource(R.drawable.curve_alledges_green)
            holder.imgcircle.setBackgroundResource(R.drawable.summer)
        }

        //style TextView
        holder.txtLeaveName.setTextSize(11F);


        holder.itemView.setOnClickListener {


            // skip code request if already requested
            val preferences: SharedPreferences
            val MY_SHARED_PREFERENCES = "CodeRequestPref"
            preferences = context.getSharedPreferences(
                MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
            val editor = preferences.edit()
            editor.putString("SKIP_CODE_REQUEST", leavetypeModel.leave_id)
            editor.putString("SELECTED_LEAVE", leavetypeModel.leave_name)
            editor.putString("SHOW_LEAVE","1")
            editor.commit()

        }


    }

    override fun getItemCount(): Int {
        return dataModelArrayListUsertypes.size
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var txtLeaveName: TextView
        var txtBalance: TextView
        var llCardBody: LinearLayout
        var imgcircle: CircleImageView

        init {
            txtLeaveName = itemView.findViewById(R.id.txtLeaveName)
            txtBalance = itemView.findViewById(R.id.txtBalance)
            llCardBody = itemView.findViewById(R.id.cardbody)
            imgcircle = itemView.findViewById(R.id.imgcircle)

        }
    }

    init {
        inflater = LayoutInflater.from(context)
        dataModelArrayListUsertypes = dataModelArrayList
    }



}