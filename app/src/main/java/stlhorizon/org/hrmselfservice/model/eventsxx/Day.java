//package stlhorizon.org.hrmselfservice.model.eventsxx;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
///**
// * Created by wairegimurakaru on 9/20/17.
// */
//
//public class Day implements Parcelable {
//    private static final DateFormat DAY_DATE_FORMAT = DateUtils.withEastAfricaTimeZone(new SimpleDateFormat("EEEE", Locale.US));
//
//    private int index;
//    private Date date;
//
//    public Day() {
//    }
//
//    public int getIndex() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public String getName() {
//        return String.format(Locale.US, "Day %1$d (%2$s)", index, DAY_DATE_FORMAT.format(date));
//    }
//
//    public String getShortName() {
//        return DAY_DATE_FORMAT.format(date);
//    }
//
//    @Override
//    public String toString() {
//        return getName();
//    }
//
//    @Override
//    public int hashCode() {
//        return index;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        Day other = (Day) obj;
//        return (index == other.index);
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
//        dest.writeInt(this.index);
//        dest.writeLong(this.date != null ? this.date.getTime() : -1);
//    }
//
//    protected Day(Parcel in) {
//        this.index = in.readInt();
//        long tmpDate = in.readLong();
//        this.date = tmpDate == -1 ? null : new Date(tmpDate);
//    }
//
//    public static final Creator<Day> CREATOR = new Creator<Day>() {
//        @Override
//        public Day createFromParcel(Parcel source) {
//            return new Day(source);
//        }
//
//        @Override
//        public Day[] newArray(int size) {
//            return new Day[size];
//        }
//    };
//}
