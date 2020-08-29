package stlhorizon.org.hrmselfservice.model.eventsxx;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {

    public enum Type{
        LEAVE("Leave", 0),
        TASK("Task", 1),
        TRAINING("Training", 2),
        EVENT("Event", 3);

        private String stringValue;
        private int intValue;
        private Type(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    private String name;
    private Type type;

    public Track() {
    }

    public Track(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + type.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        Track other = (Track) obj;
        return name.equals(other.name) && (type == other.type);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    protected Track(Parcel in) {
        name = in.readString();
        type = Type.values()[in.readInt()];
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
