package stlhorizon.org.hrmselfservice.model.training

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import stlhorizon.org.hrmselfservice.model.highordermodels.Response


class TrainingCourses : Response() {
    @Expose
    private var data: List<TrainingCoursesModel>? =
        null

    var trainingCoursesData: List<TrainingCoursesModel>?
        get() = data
        set(data) {
            this.data = data
        }

    inner class TrainingCoursesModel {
        @Expose
        var id: String? = null

        @Expose
        var short_name: String? = null

        @Expose
        var description: String? = null

        override fun toString(): String {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().toJson(this)
        }
    }

    override fun toString(): String {
        val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        return gsonBuilder.create().toJson(this)
    }

    companion object {
        fun createTrainingCoursesFrom(newsResponseString: String?): TrainingCourses {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(newsResponseString, TrainingCourses::class.java)
        }

        fun createTrainingCoursesModelFrom(newsResponseString: String?): TrainingCoursesModel {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(
                newsResponseString,
                TrainingCoursesModel::class.java
            )
        }
    }
}