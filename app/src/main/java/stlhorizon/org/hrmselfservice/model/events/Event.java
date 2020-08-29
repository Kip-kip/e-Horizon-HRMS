package stlhorizon.org.hrmselfservice.model.events;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;



public class Event  {
    @Expose
    private String message;
    @Expose
    private List<EventModel> data;

    public List<Long> getEventTimeStamp() {
        List<Long> eventstimestamps = new ArrayList<>();
        if (this.data != null) {
            for (EventModel eventModel : data) {

                DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime parsedDateTimeUsingFormatter = DateTime.parse(eventModel.getStart_time(), dateTimeFormatter);

                eventstimestamps.add(parsedDateTimeUsingFormatter.getMillis());
            }
        }
        return eventstimestamps;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<EventModel> getEventData() {
        return data;
    }

    public void setEventData(List<EventModel> data) {
        this.data = data;
    }


    public static Event createEventFrom(String eventResponseString) {

        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create().fromJson(eventResponseString, Event.class);
    }


    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create().toJson(this);

    }
}