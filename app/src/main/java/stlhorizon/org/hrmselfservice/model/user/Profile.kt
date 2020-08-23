package stlhorizon.org.hrmselfservice.model.user

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import stlhorizon.org.hrmselfservice.model.highordermodels.Response


class Profile : Response() {

    @Expose
    private var data: ProfileModel? = null


    var profileData: ProfileModel?
        get() = data
        set(data) {
            this.data = data
        }

    inner class ProfileModel {
        @Expose
        var party_id: String? = null

        @Expose
        var salutation: String? = null

        @Expose
        var first_name: String? = null

        @Expose
        var middle_name: String? = null

        @Expose
        var last_name: String? = null

        @Expose
        var preferred_name: String? = null

        @Expose
        var gender: String? = null

        @Expose
        var contact_person: String? = null

        @Expose
        var dob: String? = null

        @Expose
        var national_id: String? = null

        @Expose
        var company_type: String? = null

        @Expose
        var entity_id: String? = null

        @Expose
        var personal_id: String? = null

        @Expose
        var alternate_name: String? = null

        @Expose
        var religion: String? = null

        @Expose
        var blood_group: String? = null

        @Expose
        var subcaste: String? = null

        @Expose
        var married: String? = null

        @Expose
        var secondment: String? = null

        @Expose
        var secondment_org_id: String? = null

        @Expose
        var service_initiation_id: String? = null

        @Expose
        var record_type: String? = null

        @Expose
        var service_type: String? = null

        @Expose
        var order_no: String? = null

        @Expose
        var order_date: String? = null

        @Expose
        var order_source: String? = null

        @Expose
        var order_sent_on: String? = null

        @Expose
        var order_accepted_on: String? = null

        @Expose
        var acknowledge_received_on: String? = null

        @Expose
        var document_no: String? = null

        @Expose
        var service_initiation_appoint_id: String? = null

        @Expose
        var order_reason: String? = null

        @Expose
        var order_reason_type: String? = null

        @Expose
        var service_effected_on: String? = null

        @Expose
        var service_end_on: String? = null

        @Expose
        var service_status: String? = null

        @Expose
        var approval_process_id: String? = null

        @Expose
        var approval_by: String? = null

        @Expose
        var approval_on: String? = null

        @Expose
        var approval_reference: String? = null

        @Expose
        var wef: String? = null

        @Expose
        var wet: String? = null

        @Expose
        var appoint_id: String? = null

        @Expose
        var appointment_type: String? = null

        @Expose
        var nature_of_employment: String? = null

        @Expose
        var proposed_joining_date: String? = null

        @Expose
        var date_of_join: String? = null

        @Expose
        var proposed_probation_end_date: String? = null

        @Expose
        var revised_probation_end_date: String? = null

        @Expose
        var probation_end_date: String? = null

        @Expose
        var confirmation_on: String? = null

        @Expose
        var estimated_retirement_date: String? = null


        @Expose
        var revised_retirement_date: String? = null

        @Expose
        var estimated_date_of_exit: String? = null

        @Expose
        var date_of_exit: String? = null

        @Expose
        var exit_type: String? = null

        @Expose
        var exit_reason: String? = null

        @Expose
        var joining_position: String? = null

        @Expose
        var joining_entity_id: String? = null

        @Expose
        var joining_entity_structure_id: String? = null

        @Expose
        var joining_sub_grade: String? = null

        @Expose
        var current_position: String? = null

        @Expose
        var current_entity_structure_id: String? = null

        @Expose
        var current_sub_grade: String? = null

        @Expose
        var recruitment_id: String? = null
        @Expose
        var final_settlement_status: String? = null
        @Expose
        var remark: String? = null
        @Expose
        var reappoint_id: String? = null
        @Expose
        var service_id: String? = null
        @Expose
        var email: String? = null
        @Expose
        var phone_no: String? = null
        @Expose
        var grade: String? = null
        @Expose
        var department: String? = null
        @Expose
        var nssf: String? = null
        @Expose
        var nhif: String? = null


        @Expose
        var dependantsdata: List<Dependants>? = null

        @Expose
        var qualificationsdata: List<Qualifications>? = null



        override fun toString(): String {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().toJson(this)
        }
    }


//   inner class DependantsModel {
//       private val success: String? = null
//       private val message: String? = null
//       var data: List<Dependants>? = null
//    }

    override fun toString(): String {
        val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        return gsonBuilder.create().toJson(this)
    }

    companion object {
        fun createProfileFrom(newsResponseString: String?): Profile? {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(newsResponseString, Profile::class.java)
        }

        fun createProfileModelFrom(newsResponseString: String?): ProfileModel {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(
                newsResponseString,
                ProfileModel::class.java
            )
        }
    }
}