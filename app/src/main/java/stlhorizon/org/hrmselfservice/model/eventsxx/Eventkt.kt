package stlhorizon.org.hrmselfservice.model.eventsxx

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import stlhorizon.org.hrmselfservice.model.highordermodels.Response
import java.util.*


class Eventkt : Response() {
    @Expose
    var message: String? = null

    @Expose
    var eventData: List<EventModelkt>? = null
    val eventTimeStamp: List<Long>
        get() {
            val eventstimestamps: MutableList<Long> =
                ArrayList()
            if (eventData != null) {
                for (eventModel in eventData!!) {
                    val dateTimeFormatter =
                        DateTimeFormat.forPattern("yyyy-MM-dd")
                    val parsedDateTimeUsingFormatter =
                        DateTime.parse(eventModel.start_time, dateTimeFormatter)
                    eventstimestamps.add(parsedDateTimeUsingFormatter.millis)
                }
            }
            return eventstimestamps
        }

    override fun toString(): String {
        val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        return gsonBuilder.create().toJson(this)
    }

    companion object {
        fun createEventFrom(eventResponseString: String?): Eventkt {
            val gsonBuilder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            return gsonBuilder.create().fromJson(
                eventResponseString,
                Eventkt::class.java
            )
        }
    }
}