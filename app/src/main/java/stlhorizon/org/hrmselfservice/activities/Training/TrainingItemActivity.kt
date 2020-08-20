package stlhorizon.org.hrmselfservice.activities.training

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_trainingitem.*
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.model.Training.TrainingHistory

class TrainingItemActivity : AppCompatActivity() {
    private var str: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainingitem)


//get extra from news adapter
        //get extra from news adapter
        str = intent.extras!!.getString("DETAIL")
        if (str != null) {
            loadPageElements()
        }

    }


    fun loadPageElements() {
        val traininghistoryModel: TrainingHistory.TrainingHistoryModel =  TrainingHistory.createTrainingHistoryModelFrom(str)

        txtCourseID?.setText(traininghistoryModel.course_id+"")
        txtCourseName.setText(traininghistoryModel.course_name+"")
        txtTrainingItemFrom?.setText(traininghistoryModel.probable_period_from+"")
        txtTrainingItemTo?.setText(traininghistoryModel.probable_period_to+"")

    }


}
