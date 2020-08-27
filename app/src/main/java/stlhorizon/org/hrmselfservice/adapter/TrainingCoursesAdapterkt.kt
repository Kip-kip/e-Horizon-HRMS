package stlhorizon.org.hrmselfservice.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.model.training.TrainingCourses


public class TrainingCoursesAdapterkt(
    var context: Context,


    dataModelArrayList: List<TrainingCourses.TrainingCoursesModel>
) :
    RecyclerView.Adapter<TrainingCoursesAdapterkt.MyViewHolder>() {
    private val inflater: LayoutInflater
    private val dataModelArrayListUsertypes: List<TrainingCourses.TrainingCoursesModel>
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = inflater.inflate(R.layout.trainingcourses_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {

        val trainingCoursesModel: TrainingCourses.TrainingCoursesModel = dataModelArrayListUsertypes[position]
        holder.txtCourseName.setText(trainingCoursesModel.short_name)
        holder.txtDesc.setText(trainingCoursesModel.description)


        //style TextView
        holder.txtCourseName.setTextSize(13F);


        holder.itemView.setOnClickListener {

            // skip code request if already requested
            val preferences: SharedPreferences
            val MY_SHARED_PREFERENCES = "CourseID"
            preferences = context.getSharedPreferences(
                MY_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
            val editor = preferences.edit()
            editor.putString("COURSE_ID", trainingCoursesModel.id)
            editor.commit()


        }


    }

    override fun getItemCount(): Int {
        return dataModelArrayListUsertypes.size
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var txtCourseName: TextView
        var txtDesc: TextView
        var llCardBody: LinearLayout

        init {
            txtCourseName = itemView.findViewById(R.id.txtCourseName)
            txtDesc= itemView.findViewById(R.id.txtDesc)
            llCardBody = itemView.findViewById(R.id.cardbody)

        }
    }

    init {
        inflater = LayoutInflater.from(context)
        dataModelArrayListUsertypes = dataModelArrayList
    }
}