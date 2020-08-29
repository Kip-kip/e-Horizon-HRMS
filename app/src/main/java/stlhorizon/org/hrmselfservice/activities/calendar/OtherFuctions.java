package stlhorizon.org.hrmselfservice.activities.calendar;

/**
 * Created by makari on 4/26/2018.
 */

public class OtherFuctions {
    /**

     First, please check the uri address . For example 'content://calendar/calendars' , it's not used over Android 2.1. Before Android 14:

     calanderURL = "content://calendar/calendars";
     calanderEventURL = "content://calendar/events";
     calanderRemiderURL= "content://calendar/reminders";
     After:

     calanderURL = "content://com.android.calendar/calendars";
     calanderEventURL = "content://com.android.calendar/events";
     calanderRemiderURL = "content://com.android.calendar/reminders";
     However, you'd better use like this:

     private Uri calendarsUri = Calendars.CONTENT_URI;
     private Uri eventsUri = Events.CONTENT_URI;
     private Uri remindersUri = Reminders.CONTENT_URI;
     private Uri attendeesUri = Attendees.CONTENT_URI;

     Second , please check the table column name . You can print the following columns and have a look .

     // Calendars table columns
    public static final String[] CALENDARS_COLUMNS = new String[] {
            Calendars._ID,                           // 0
            Calendars.ACCOUNT_NAME,                  // 1
            Calendars.CALENDAR_DISPLAY_NAME,         // 2
            Calendars.OWNER_ACCOUNT                  // 3
    };

    // Events table columns
    public static final String[] EVENTS_COLUMNS = new String[] {
            Events._ID,
            Events.CALENDAR_ID,
            Events.TITLE,
            Events.DESCRIPTION,
            Events.EVENT_LOCATION,
            Events.DTSTART,
            Events.DTEND,
            Events.EVENT_TIMEZONE,
            Events.HAS_ALARM,
            Events.ALL_DAY,
            Events.AVAILABILITY,
            Events.ACCESS_LEVEL,
            Events.STATUS,
    };

    // Reminders table columns
    public static final String[] REMINDERS_COLUMNS = new String[] {
            Reminders._ID,
            Reminders.EVENT_ID,
            Reminders.MINUTES,
            Reminders.METHOD,
    };

    // Attendee table columns
    public static final String[] ATTENDEES_COLUMNS = new String[] {
            Attendees._ID,
            Attendees.ATTENDEE_NAME,
            Attendees.ATTENDEE_EMAIL,
            Attendees.ATTENDEE_STATUS
    };

     */
}
