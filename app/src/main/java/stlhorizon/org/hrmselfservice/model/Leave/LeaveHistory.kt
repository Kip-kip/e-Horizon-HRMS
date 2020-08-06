package stlhorizon.org.hrmselfservice.model.Leave

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import stlhorizon.org.hrmselfservice.model.highordermodels.Response


class LeaveHistory : Response() {
    @Expose
    private var data: List<LeaveHistoryModel>? =
        null

    var leaveHistoryData: List<LeaveHistoryModel>?
        get() = data
        set(data) {
            this.data = data
        }

    inner class LeaveHistoryModel {
        @Expose
        var leave_request_id: String? = null

        @Expose
        var record_type: String? = null

        @Expose
        var request_type: String? = null

        @Expose
        var request_on: String? = null

        @Expose
        var reference_no: String? = null

        @Expose
        var order_reference_no: String? = null

        @Expose
        var order_reference_date: String? = null

        @Expose
        var party_id: String? = null

        @Expose
        var applied_from: String? = null

        @Expose
        var applied_to: String? = null

        @Expose
        var unit_on_end: String? = null

        @Expose
        var approved_from: String? = null

        @Expose
        var approved_to: String? = null

        @Expose
        var used_from: String? = null

        @Expose
        var used_to: String? = null

        @Expose
        var reason: String? = null

        @Expose
        var reason_type: String? = null

        @Expose
        var contact_id: String? = null

        @Expose
        var specific_contact: String? = null

        @Expose
        var work_handover_to: String? = null

        @Expose
        var employee_to_notified: String? = null

        @Expose
        var allowance_claim_id: String? = null

        @Expose
        var how_to_adjust_excess_leave: String? = null

        @Expose
        var lr_id: String? = null


        @Expose
        var approver_opted: String? = null

        @Expose
        var approver_id: String? = null

        @Expose
        var approver_email: String? = null

        @Expose
        var unit_on_all_days: String? = null

        @Expose
        var leave_id: String? = null

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
        fun createLeaveHistoryFrom(newsResponseString: String?): LeaveHistory? {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(newsResponseString, LeaveHistory::class.java)
        }

        fun createLeaveHistoryModelFrom(newsResponseString: String?): LeaveHistoryModel {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(
                newsResponseString,
                LeaveHistoryModel::class.java
            )
        }
    }
}