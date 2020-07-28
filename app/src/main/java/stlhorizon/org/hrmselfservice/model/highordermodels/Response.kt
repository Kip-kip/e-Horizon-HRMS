package stlhorizon.org.hrmselfservice.model.highordermodels

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose

open class Response {
    @Expose
    var success: String? = null

    override fun toString(): String {
        val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        return gsonBuilder.create().toJson(this)
    }

    val isAResponseASuccess: Boolean
        get() {
            if (success == null) {
                try {
                    throw Exception("Response not initialized")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return success.equals("1", ignoreCase = true)
        }

    companion object {
        fun createResponseFrom(newsResponseString: String?): Response {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(
                newsResponseString,
                Response::class.java
            )
        }
    }
}