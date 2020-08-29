package stlhorizon.org.hrmselfservice.model.events;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

@Entity(tableName = "myevents")
public class EventModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    @Expose
    private Integer event_id;
    @ColumnInfo
    @Expose
    private String event_title;
    @ColumnInfo
    @Expose
    private String venue;
    @ColumnInfo
    @Expose
    private String start_time;
    @ColumnInfo
    @Expose
    private String end_time;
    @ColumnInfo
    @Expose
    private String event_type;
    @ColumnInfo
    @Expose
    private String color_code;
    @ColumnInfo
    @Expose
    private Long created_date;
    @ColumnInfo
    @Expose
    private Integer created_by;




    @ColumnInfo
    //1 means online eventsSource and 0 offline
    private Integer eventSourceType=1;

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

    public Long getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Long created_date) {
        this.created_date = created_date;
    }

    public Integer getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }

    public Integer getEventSourceType() {
        return eventSourceType;
    }

    public void setEventSourceType(Integer eventSourceType) {
        this.eventSourceType = eventSourceType;
    }

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create().toJson(this);

    }

    public static EventModel createEventModelFrom(String eventResponseString) {

        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create().fromJson(eventResponseString, EventModel.class);
    }


}

