package stlhorizon.org.hrmselfservice.app;
public class Config {

    //INTERNSHIPS CATEGORY
    public static final String INTERNSHIPCATEGORY="4";

    //INTERNSHIPS CATEGORY
    public static final String SCHOLARSHIPCATEGORY="5";

    //roor URL
    public static final String ROOTURL="http://api.uniscoo.org/";


    /** push notifications **/
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
 // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";

}
