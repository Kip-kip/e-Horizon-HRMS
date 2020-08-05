package stlhorizon.org.hrmselfservice.model.Leave

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import stlhorizon.org.hrmselfservice.model.highordermodels.Response


class LeaveTypes : Response() {
    @Expose
    private var data: List<LeaveTypesModel>? =
        null

    var leaveTypesData: List<LeaveTypesModel>?
        get() = data
        set(data) {
            this.data = data
        }

    inner class LeaveTypesModel {
        @Expose
        var leave_id: String? = null

        @Expose
        var short_name: String? = null

        @Expose
        var leave_name: String? = null

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
        fun createLeaveTypesFrom(newsResponseString: String?): LeaveTypes {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(newsResponseString, LeaveTypes::class.java)
        }

        fun createLeaveTypesModelFrom(newsResponseString: String?): LeaveTypesModel {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(
                newsResponseString,
                LeaveTypesModel::class.java
            )
        }
    }
}