package stlhorizon.org.hrmselfservice.model.Training

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import stlhorizon.org.hrmselfservice.model.highordermodels.Response


class TrainingHistory : Response() {
    @Expose
    private var data: List<TrainingHistoryModel>? =
        null

    var trainingHistoryData: List<TrainingHistoryModel>?
        get() = data
        set(data) {
            this.data = data
        }

    inner class TrainingHistoryModel {
        @Expose
        var id: String? = null

        @Expose
        var entity_id: String? = null

        @Expose
        var request_type: String? = null

        @Expose
        var request_on: String? = null

        @Expose
        var reference_no: String? = null

        @Expose
        var course_id: String? = null

        @Expose
        var request_by: String? = null

        @Expose
        var group_id: String? = null

        @Expose
        var probable_period_from: String? = null

        @Expose
        var probable_period_to: String? = null

        @Expose
        var status: String? = null

        @Expose
        var is_ecomended: String? = null

        @Expose
        var course_name: String? = null




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
        fun createTrainingHistoryFrom(newsResponseString: String?): TrainingHistory? {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(newsResponseString, TrainingHistory::class.java)
        }

        fun createTrainingHistoryModelFrom(newsResponseString: String?): TrainingHistoryModel {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(
                newsResponseString,
                TrainingHistoryModel::class.java
            )
        }
    }
}