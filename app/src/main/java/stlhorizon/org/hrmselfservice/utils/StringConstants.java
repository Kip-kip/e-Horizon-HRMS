package stlhorizon.org.hrmselfservice.utils;

public class StringConstants {
    public static String DATE_PICKER_FLAG = "date_picker_flag";
    public static String IS_FIRST_TIME = "is_first_time";
    public static String TOKEN = "token";
    public static String SESSION = "session";
    public static String COMPANY_CODE = "company_code";

    // for training
    public static String COURSE_ID = "course_id";
    public static String COURSE_NAME = "course_name";
    public static String START_DATE = "start_date";
    public static String END_DATE = "end_date";
    public static String TYPE = "type";

    // for leave
    public static String LEAVE_RQST_MXM_DAYS = "mxm_days";
    public static String LEAVE_RQST_START_DATE = "start_date";
    public static String LEAVE_ID = "leave_id";
    public static String LEAVE_FROM = "frm";
    public static String LEAVE_TYPE = "type";
    public static String LEAVE_START_DATE = "s_date";
    public static String LEAVE_END_DATE = "e_date";
    public static String LEAVE_REASON = "reason";
    public static String LEAVE_TITLE = "title";
    public static String LEAVE_BODY = "body";
    public static String LEAVE_TO = "to";
    public static String LEAVE_NOTIFICATION = "notification";
    public static String LEAVE_DATA = "data";
    public static String LEAVE_DATE_APPLIED = "date_applied";
    public static String LEAVE_DURATION = "duration";
    public static String LEAVE_MSG_ID = "message_id";
    public static String LEAVE_USER_NAME = "user_name";
    public static String LEAVE_STATUS = "status";
    public static String LEAVE_MINE = "mine";
    public static String LEAVE_STATUS_CODE = "status_code";
    public static String LEAVE_HISTORY_ACTIVITY = "lh_activity";
    public static String SAVED_STATE = "savedState";
    public static String LEAVE_NAME = "leave_name";

    // for tasks
    public final static String TASK_ID = "TASK_ID";
    public final static String TASK_PROJECT_ID = "TASK_PROJECT_ID";
    public final static String TASK_CREATED_BY = "CREATED_BY";
    public final static String TASK_TITLE = "TASK_TITLE";
    public final static String TASK_DESCRIPTION = "TASK_DESCRIPTION";
    public final static String TASK_START_DATE = "TASK_START_DATE";
    public final static String TASK_END_DATE = "TASK_END_DATE";
    public final static String TASK_URGENCY = "TASK_URGENCY";
    public final static String TASK_IS_COMPLETED = "TASK_IS_COMPLETED";
    public final static String TASK_IS_FAVORITE = "TASK_IS_FAVORITE";
    public final static String TASK_ATTACHMENTS = "TASK_ATTACHMENTS";
    public final static String TASK_SUB_TASKS = "TASK_SUB_TASKS";
    public final static String TASK_COMMENTS= "TASK_COMMENTS";
    public final static String TASK_TEAM= "TASK_TEAM";
    public final static String TASK_CREATED_AT = "TASK_CREATED_AT";

    // for project
    public final static String TAG_PROJECT_ID = "ProjectID";
    public final static String TAG_PROJECT_TITLE = "ProjectName";
    public final static String TAG_CREATED_BY = "CreatedBy";
    public final static String TAG_CREATED_AT = "CreatedAt";
    public final static String TAG_COLOR = "color";
    public final static String TAG_ICON = "ProjectIcon";

    // for shared preferences
    public final static String SCREEN_LOCK_MODE = "screenlockmode";
    public final static String SCREEN_LOCK_KEY = "screenlockkey";
    public final static String READ_USERS = "read_users";
    public final static String AUTH_TOKEN = "auth_token";
    public final static String INITIALS = "initials";
    public final static String LOGGED_IN = "logged_in";
    public final static String EVENTS_INSERTED = "events_inserted";
    public final static String WELCOME_SHOWN = "welcome_shown";

    //for date formatters
    public static final String DATE_FORMAT_1 = "MMM d, yyyy";
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_3 = "MMMM yyyy";
    public static final String DATE_FORMAT_4 = "dd-MM-yyyy";
    public static final String DATE_FORMAT_5 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_6 = "yyyy-MM-dd hh:mm:ss";
    public static final String TIME_FORMAT_1 = "hh:mm a";

    // Urls
    public static final String FACEBOOK = "https://www.facebook.com/stllimited";
    public static final String TWITTER = "http://twitter.com/STLHorizon";
    public static final String LINKEDIN = "https://www.linkedin.com/in/software-technologies-limited-68a27354/";

    // for socket IO
    public static final String STATIC_TOKEN = "eyJpYXQiOjE1MjYyODE5NDEsImlzcyI6Indhc2lsaWFuYS5zdGwtaG9yaXpvbi5jb20iLCJuYmYiOjE1MjYyODE5NDEsImV4cCI6MTUyNjI4MTk1MSwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYiLCJraWQiOiI1OTMyOTNlYTQzYjFkODJmMThkNDI3OWU2MGNhZGFlMCJ9.eyJ1c2VyX2lkIjoiMTAxIiwiaXNfc3VwZXIiOiJOIiwidXNlcm5hbWUiOiJlaG9yaXpvbi1ocm1zNSIsImZ1bGxfbmFtZSI6IkhybXMgSW5mbyIsInVzZXJfcm9sZSI6W3sicm9sZV9pZCI6IjEiLCJpZF9wYXJlbnQiOm51bGwsImNvZGUiOiJBTSIsInJvbGVfbmFtZSI6ImFkbWluaXN0cmF0b3IiLCJwZXJtYW5lbnQiOiJZIiwiYWN0aXZlIjoiWSJ9XSwiaWNvbiI6Imh0dHA6XC9cL3dhc2lsaWFuYS5zdGwtaG9yaXpvbi5jb21cL2FwaVwvcGVyc29uLWltYWdlc1wvZG93bmxvYWRcLzExNj90PTE1MjYyODE5NDEmYWNjZXNzX3Rva2VuPSIsImZpcnN0X25hbWUiOiJIcm1zIiwibGFzdF9uYW1lIjoiSW5mbyIsInBob25lIjoiOTQ5NjgzNTM4OCIsInByaW1hcnlfZW1haWwiOiJpbmZvQHN0bC1ob3Jpem9uLmNvbSIsImRvYiI6bnVsbCwiZ2VuZGVyIjoiMSIsImluaXRpYWxzIjpudWxsLCJjaXR5X3Rvd24iOm51bGwsImNvdW50cnkiOiJLRSIsInJvbGUiOiIxIiwiZGFzaGJvYXJkIjp7Im9iamVjdF9pZCI6IjM3IiwidHlwZV9pZCI6IjQiLCJpZF9wYXJlbnQiOm51bGwsInRpdGxlIjoiRGFzaGJvYXJkIiwiY29kZSI6IkFEQiIsImRlc2NyaXB0aW9uIjoiQWRtaW4gRGFzaGJvYXJkIiwicGF0aF9yZWYiOiJcL3Byb2Nlc3Nlc1wvbGlzdCIsInN0YXRlX3JlZiI6InByb2Nlc3Nlcy5saXN0IiwiaWNvbiI6ImZhIGZhLWRhc2hib2FyZCIsInBvc2l0aW9uIjoiMCJ9LCJwZXJzb25faWQiOiIxMTYiLCJvcmdhbmlzYXRpb24iOnsib3JnYW5pc2F0aW9uX2lkIjoiMSIsImlkX3BhcmVudCI6bnVsbCwiZnVsbF9uYW1lIjoiSHJtcyBPcmdhbmlzYXRpb24iLCJuaWNrX25hbWUiOiJLVkwiLCJyZWdpc3RyYXRpb25fbm8iOiJDUFItMTkzM1wvMTI1MzMxIiwia3JhX3BpbiI6IlAwNTE0NTI5MTdUIiwidmF0X25vIjoiNDU2Njc3ODg4OSIsInBob25lIjoiNzI1NDczMzMyIiwibGFzdF91cGRhdGVkIjoiMTQ4NzI1NDYyOCIsInByaW1hcnlfZW1haWwiOiJpbmZvQGt2aXNpb25sdGQuY29tIiwiY2l0eV90b3duIjoiTmFpcm9iaSIsImNvdW50cnkiOiJLRSIsImlzX293bmVyIjoiWSIsInByaW1hcnlfbG9jYXRpb24iOiJOYWlyb2JpIiwicHJpbWFyeV9hZGRyZXNzIjoiQm94IDMyMS00MDMwMyBOYWlyb2JpIiwicHJpbWFyeV93ZWJzaXRlIjoid3d3Lmt2aXNpb25sdGQuY29tIiwiaWNvbiI6Imh0dHA6XC9cL3dhc2lsaWFuYS5zdGwtaG9yaXpvbi5jb21cL2FwaVwvb3JnYW5pc2F0aW9uLWltYWdlc1wvZG93bmxvYWRcLzE_YWNjZXNzX3Rva2VuPWV5SjBlWEFpT2lKS1YxUWlMQ0poYkdjaU9pSklVekkxTmlKOS5leUpqYjIxd1lXNTVYMk52WkdVaU9pSklVazFUSW4wLkpJcFVPUWpLNHF5MmNWNmRDaUpEZGVNNU91dm04YXlDcHFVU1NZWnNMcVUiLCJwZXJzb24iOnsicGVyc29uX2lkIjoiMSIsImZpcnN0X25hbWUiOiJKYXN3aW5kZXIiLCJsYXN0X25hbWUiOiJHYWhpciIsIm90aGVyX25hbWUiOiJNIiwiaW5pdGlhbHMiOiJTTyIsImRvYiI6IjE5OTAtMDQtMjIiLCJnZW5kZXIiOiIyIiwicGhvbmUiOiIyNTQ3MjU4MzQ1MjAiLCJwcmltYXJ5X2VtYWlsIjoib2dldG9Ac3RsLWhvcml6b24uY29tIiwiY2l0eV90b3duIjoiTkFJUk9CSSIsImNvdW50cnkiOiJLRSIsInByaW1hcnlfbG9jYXRpb24iOm51bGwsInJlbWFya3MiOiJTZW5pb3IgSW1wbGVtZW5hdGlvbiBDb25zdWx0YW50IEAgICAgICAgICAgICAgXG5Tb2Z0d2FyZSBUZWNobm9sb2dpZXMgTHRkIiwibGFzdF91cGRhdGVkIjoiMTUyMDIzNjI4OCJ9fSwib3duZXIiOnsib3JnYW5pc2F0aW9uX2lkIjoiMSIsImlkX3BhcmVudCI6bnVsbCwiZnVsbF9uYW1lIjoiSHJtcyBPcmdhbmlzYXRpb24iLCJuaWNrX25hbWUiOiJLVkwiLCJyZWdpc3RyYXRpb25fbm8iOiJDUFItMTkzM1wvMTI1MzMxIiwia3JhX3BpbiI6IlAwNTE0NTI5MTdUIiwidmF0X25vIjoiNDU2Njc3ODg4OSIsInBob25lIjoiNzI1NDczMzMyIiwibGFzdF91cGRhdGVkIjoiMTQ4NzI1NDYyOCIsInByaW1hcnlfZW1haWwiOiJpbmZvQGt2aXNpb25sdGQuY29tIiwiY2l0eV90b3duIjoiTmFpcm9iaSIsImNvdW50cnkiOiJLRSIsImlzX293bmVyIjoiWSIsInByaW1hcnlfbG9jYXRpb24iOiJOYWlyb2JpIiwicHJpbWFyeV9hZGRyZXNzIjoiQm94IDMyMS00MDMwMyBOYWlyb2JpIiwicHJpbWFyeV93ZWJzaXRlIjoid3d3Lmt2aXNpb25sdGQuY29tIiwiaWNvbiI6Imh0dHA6XC9cL3dhc2lsaWFuYS5zdGwtaG9yaXpvbi5jb21cL2FwaVwvb3JnYW5pc2F0aW9uLWltYWdlc1wvZG93bmxvYWRcLzE_YWNjZXNzX3Rva2VuPWV5SjBlWEFpT2lKS1YxUWlMQ0poYkdjaU9pSklVekkxTmlKOS5leUpqYjIxd1lXNTVYMk52WkdVaU9pSklVazFUSW4wLkpJcFVPUWpLNHF5MmNWNmRDaUpEZGVNNU91dm04YXlDcHFVU1NZWnNMcVUifSwiY29tcGFueV9jb2RlIjoiSFJNUyJ9.f_RWBcYVIm1n3ABzbbjeovPyRKTbFndBlqX8ahci0e0";
    public static final String CHAT_SERVER_URL = "http://wasiliana.stl-horizon.com/chats/authenticate?access_token=" + STATIC_TOKEN;
    public static final String HRMS_USERS_API = "http://wasiliana.stl-horizon.com/api/users/list?access_token=";
    //http://wasiliana.stl-horizon.com/chats/authenticate?access_token=

    //Others
    public static final String SELECTED_DATE = "selected_date";
    public static final String TIME = "time";

}
