package stlhorizon.org.hrmselfservice.model.login


import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import stlhorizon.org.hrmselfservice.model.highordermodels.Response


class Payslip : Response() {
    @Expose
    var message: String? = null
    @Expose
    var data: String? = null
    @Expose
    var title: String? = null

    override fun toString(): String {
        val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        return gsonBuilder.create().toJson(this)
    }

    companion object {
        fun createPayslipFrom(message: String?): Payslip {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(message, Payslip::class.java)
        }
    }
}