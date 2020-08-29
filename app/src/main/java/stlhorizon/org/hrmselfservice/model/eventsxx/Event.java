//package stlhorizon.org.hrmselfservice.model.eventsxx;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//import android.text.format.DateUtils;
//
//import java.util.Date;
//
//public class Event implements Parcelable {
//    private long id;
//    private Day day;
//    private String title;
//    private Date startTime;
//    private Date endTime;
//    private Track track;
//
//    public Event() {
//    }
//
//    public Event(Day day, String title, Date startTime, Date endTime) {
//        this.day = day;
//        this.title = title;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public Day getDay() {
//        return day;
//    }
//
//    public void setDay(Day day) {
//        this.day = day;
//    }
//
//    public Date getStartTime() {
//        return startTime;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setStartTime(Date startTime) {
//        this.startTime = startTime;
//    }
//
//    public Date getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(Date endTime) {
//        this.endTime = endTime;
//    }
//
//    public Track getTrack() {
//        return track;
//    }
//
//    public void setTrack(Track track) {
//        this.track = track;
//    }
//
//    public boolean isRunningAtTime(long time) {
//        return (startTime != null) && (endTime != null) && (startTime.getTime() < time) && (time < endTime.getTime());
//    }
//
//    /**
//     * @return The event duration in minutes
//     */
//    public int getDuration() {
//        if ((startTime == null) || (endTime == null)) {
//            return 0;
//        }
//        return (int) ((this.endTime.getTime() - this.startTime.getTime()) / DateUtils.MINUTE_IN_MILLIS);
//    }
//
//    @Override
//    public String toString() {
//        return title;
//    }
//
//    @Override
//    public int hashCode() {
//        return (int) (id ^ (id >>> 32));
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        Event other = (Event) obj;
//        return id == other.id;
//    }
//
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeLong(this.id);
//        dest.writeParcelable(this.day, flags);
//        dest.writeString(this.title);
//        dest.writeLong(this.startTime != null ? this.startTime.getTime() : -1);
//        dest.writeLong(this.endTime != null ? this.endTime.getTime() : -1);
//        dest.writeParcelable(this.track, flags);
//    }
//
//    protected Event(Parcel in) {
//        this.id = in.readLong();
//        day = Day.CREATOR.createFromParcel(in);
//        this.title = in.readString();
//        /*long tmpStartTime = in.readLong();
//        this.startTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
//        long tmpEndTime = in.readLong();
//        this.endTime = tmpEndTime == -1 ? null : new Date(tmpEndTime);*/
//        long time = in.readLong();
//        if (time != 0L) {
//            startTime = new Date(time);
//        }
//        time = in.readLong();
//        if (time != 0L) {
//            endTime = new Date(time);
//        }
//        this.track = Track.CREATOR.createFromParcel(in);
//    }
//
//}
