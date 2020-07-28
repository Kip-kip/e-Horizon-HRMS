package stlhorizon.org.hrmselfservice.model.login


import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import stlhorizon.org.hrmselfservice.model.highordermodels.Response


class LoginSuccessResponse : Response() {
    @Expose
    var message: String? = null
    @Expose
    var token: String? = null
    @Expose
    var title: String? = null

    companion object {
        fun createLoginSuccessResponseFrom(message: String?): LoginSuccessResponse {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(message, LoginSuccessResponse::class.java)
        }
    }
}