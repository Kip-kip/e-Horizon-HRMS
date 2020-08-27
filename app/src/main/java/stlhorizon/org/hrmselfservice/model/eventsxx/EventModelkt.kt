package stlhorizon.org.hrmselfservice.model.eventsxx

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.io.Serializable

class EventModelkt : Serializable {

    @Expose
    var event_id: Int? = null

    @Expose
    var event_title: String? = null

    @Expose
    var start_time: String? = null

    @Expose
    var end_time: String? = null

    @Expose
    var event_type: String? = null

    @Expose
    var color_code: String? = null

    @Expose
    var venue: String? = null

    @Expose
    var created_date: String? = null

    @Expose
    var created_by: String? = null





    override fun toString(): String {
        val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        return gsonBuilder.create().toJson(this)
    }

    companion object {
        fun createEventModelFrom(eventResponseString: String?): EventModelkt {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(eventResponseString, EventModelkt::class.java)
        }
    }
}