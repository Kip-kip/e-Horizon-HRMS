package stlhorizon.org.hrmselfservice.activities.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.applikeysolutions.cosmocalendar.listeners.OnMonthChangeListener;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.model.Month;
import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import org.json.JSONException;
import org.json.JSONObject;

import stlhorizon.org.hrmselfservice.R;
import stlhorizon.org.hrmselfservice.activities.Loan.LoanRequestActivity;
import stlhorizon.org.hrmselfservice.adapter.CalenderEventsAdapter;
import stlhorizon.org.hrmselfservice.app.EventSourceType;
import stlhorizon.org.hrmselfservice.customviews.CustomCalenderView;
import stlhorizon.org.hrmselfservice.helper.SessionManager;
import stlhorizon.org.hrmselfservice.interfaces.SelectedDayListener;
import stlhorizon.org.hrmselfservice.model.events.Event;
import stlhorizon.org.hrmselfservice.model.events.EventModel;
import stlhorizon.org.hrmselfservice.model.loan.LoanApplication;
import stlhorizon.org.hrmselfservice.model.spinners.LoanCategory;
import stlhorizon.org.hrmselfservice.repository.MyEventsDao;
import stlhorizon.org.hrmselfservice.repository.UniscooDataBase;
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection;
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult;
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import static stlhorizon.org.hrmselfservice.app.Config.ROOTURL;


public class CalendarActivity extends AppCompatActivity implements OnMonthChangeListener {
    private ImageView back, refresh;
    private Calendar calendar;
    private RecyclerView calendereventsRecyclerView;
    private CalenderEventsAdapter calendereventsAdapter;
    private FloatingActionButton addevent;
    Context context;
    private SessionManager session;
    final int callbackId = 42;
    Event event;
    List<DataLoaded> dataLoaded = new ArrayList<>();
    CalendarView calendarView;
    Set<Long> days = new TreeSet<>();

    private int mYear, mMonth, mDay;


    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    String startend = "";

    private void calendarSetter(CalendarView calendarView) {
        //Set days you want to connect

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        this.calendarView = (CalendarView) findViewById(R.id.calendarView);
        CustomCalenderView customCalenderView = (CustomCalenderView) this.calendarView;
//        customCalenderView.setSelectionManager(new NoneSelectionManager());
//        customCalenderView.getSelectionManager().toggleDay(new Day(new Date(2020,02,12)));
//        customCalenderView.setCalendarOrientation(0);

        customCalenderView.setSelectedDayListenerList(new SelectedDayListener() {
            @Override
            public void daySelected(List<Day> day) {
                Day day1 = day.size() > 0 ? day.get(0) : null;
                if (day1 != null) {
                    Calendar calendar = day1.getCalendar();
                    Date date = calendar.getTime();
                    android.text.format.DateFormat df = new android.text.format.DateFormat();
                    CharSequence time = df.format("yyyy-MM-dd HH:mm:ss", date);

                    new EventDaySelected(getApplicationContext()).execute(time.toString());
                }

            }
        });


        //INSTANTIATION
        back = findViewById(R.id.back);
        refresh = findViewById(R.id.refresh);
        addevent = findViewById(R.id.addevent);
        this.calendarSetter(calendarView);
        calendereventsRecyclerView = findViewById(R.id.calendereventsrecyclerView);
        // session manager
        session = new SessionManager(getApplicationContext());


        //change notification statusbar
        updateStatusBarColor("#546E7A");

        //redirect
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent it = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(it);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.nothing);
//                finish();


            }
        });

        //refresh calendar
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(it);
                overridePendingTransition(R.anim.slide_in_left, R.anim.nothing);
                finish();
            }
        });

        //add event
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddEventDialog();


            }
        });


        //LOAD EVENTS FROM UNISCOO API
        loadCalenderEvents();

        //load events from device
        //Get calendar sync status from shared preferences
        final String MY_SHARED_PREFERENCES = "CalendarSyncPref";
        SharedPreferences myPreferences = getApplicationContext().getSharedPreferences(MY_SHARED_PREFERENCES, MODE_PRIVATE);
        String check_status = myPreferences.getString("CAL_CHECK_STATUS", "0");
        if (check_status.equalsIgnoreCase("1")) {
            new GetDeviceCalendarEvents(getApplicationContext()).execute();
        } else {

        }


        //load todays events only
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        new EventDaySelected(getApplicationContext()).execute(formattedDate);


        //load all events for all months FROM ROOM DB
        loadEventsFromRepository(null);

    }

    @Override
    public void onMonthChanged(Month month) {

    }


    class CustomMonth {

        public String parseThisMonth() {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            String month_name = month_date.format(cal.getTime()).toLowerCase();
            month_name = month_name.substring(0, 1).toUpperCase() + month_name.substring(1);
            SimpleDateFormat year_date = new SimpleDateFormat("YYYY");
            String yearDate = year_date.format(cal.getTime()).toLowerCase();
            return this.parse(month_name + " " + yearDate);

        }

        public String parse(String month) {
            String[] currentMonth = month.split(" ");
            MonthName monthName = MonthName.valueOf(currentMonth[0]);
            String trail = "-%";
            switch (monthName) {

                case January:
                    return currentMonth[1] + "-01" + trail;

                case February:
                    return currentMonth[1] + "-02" + trail;
                case March:
                    return currentMonth[1] + "-03" + trail;
                case April:
                    return currentMonth[1] + "-04" + trail;

                case May:
                    return currentMonth[1] + "-05" + trail;
                case June:
                    return currentMonth[1] + "-06" + trail;
                case July:
                    return currentMonth[1] + "-07" + trail;
                case August:
                    return currentMonth[1] + "-08" + trail;
                case September:
                    return currentMonth[1] + "-09" + trail;
                case October:
                    return currentMonth[1] + "-10" + trail;
                case November:
                    return currentMonth[1] + "-11" + trail;
                case December:
                    return currentMonth[1] + "-12" + trail;


                default:
                    return "1970-01-15";
            }

        }

    }

    enum MonthName {
        January("January"),
        February("February"),
        March("March"),
        April("April"),
        May("May"),
        June("June"),
        July("July"),
        August("August"),
        September("September"),
        October("October"),
        November("November"),
        December("December");


        String month;

        MonthName(String month) {
            this.month = month;
        }

    }


    //load events from room database while offline
    private void loadEventsFromRepository(String month) {
        Log.e("compare", month + "with" + "2019-12-%");
        new CalendarActivity.MyEventsAsync(getApplicationContext(), Operation.READ, EventSourceType.LOCAL, month).execute();

    }


    enum Operation {
        INSERT,
        DELETE,
        READ,
    }


    //Async for EVENTS --ROOM
    class MyEventsAsync extends AsyncTask<List<EventModel>, List<EventModel>, List<EventModel>> {

        private Context context;
        private Operation operation;
        private String month;
        private EventSourceType eventSourceType;
        protected UniscooDataBase uniscooDataBase;
        protected MyEventsDao myEventsDao;

        public MyEventsAsync(Context context, Operation operation, EventSourceType eventSourceType) {
            this.context = context;
            this.operation = operation;
            this.eventSourceType = eventSourceType;
        }

        public MyEventsAsync(Context context, Operation operation, EventSourceType eventSourceType, String month) {
            this.context = context;
            this.operation = operation;
            this.month = month;
            this.eventSourceType = eventSourceType;
        }

        public MyEventsAsync setEventSourceType(EventSourceType eventSourceType) {
            this.eventSourceType = eventSourceType;
            return this;
        }

        @Override
        protected List<EventModel> doInBackground(List<EventModel>... eventModels) {

            List<EventModel> eventModelsretrieved = new ArrayList<>();
            uniscooDataBase = UniscooDataBase.getInstance(this.context);
            myEventsDao = uniscooDataBase.getMyEventsDao();
            List<EventModel> eventModel = eventModels.length > 0 ? eventModels[0] : null;
            switch (operation) {
                case INSERT:
                    switch (this.eventSourceType) {
                        case ONLINE:
                            myEventsDao.deleteAllOnline();
                            break;
                        case CALENDAR:
                            myEventsDao.deleteAllCalenderEvent();
                            break;
                    }
                    myEventsDao.insertAllEventModels(eventModel);
                    eventModelsretrieved.addAll(myEventsDao.getEventModels());

                    return eventModelsretrieved;


                case READ:
                    if (month != null) {
                        return myEventsDao.getEventModelsForTheMonth(month).size() > 0 ? myEventsDao.getEventModelsForTheMonth(month) : null;
                    }
                    return myEventsDao.getEventModels().size() > 0 ? myEventsDao.getEventModels() : null;

                default:
                    return null;
            }

        }

        protected void onPostExecute(List<EventModel> eventModels) {

            switch (eventSourceType) {
                case LOCAL:
                    event = new Event();
                    event.setEventData(eventModels);
                    for (DataLoaded dataloade : dataLoaded
                    ) {
                        dataloade.onDataLoaded(event);

                    }
            }

        }
    }


    class EventDaySelected extends AsyncTask<String, Void, List<EventModel>> {
        protected UniscooDataBase uniscooDataBase;
        protected MyEventsDao myEventsDao;
        private Context context;

        public EventDaySelected(Context context) {
            this.context = context;
            uniscooDataBase = UniscooDataBase.getInstance(this.context);
            myEventsDao = uniscooDataBase.getMyEventsDao();
        }


        @Override
        protected List<EventModel> doInBackground(String... eventDate) {
            List<EventModel> eventModels1 = myEventsDao.getSelectedDaysEvent(eventDate[0]);
            return eventModels1;

        }

        @Override
        protected void onPostExecute(List<EventModel> eventModels) {
            super.onPostExecute(eventModels);
            calendereventsAdapter = new CalenderEventsAdapter(getApplicationContext(), eventModels);
            calendereventsRecyclerView.setAdapter(calendereventsAdapter);
            calendereventsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        }
    }

    private void loadCalenderEvents() {

        String token = session.getToken();

        NetworkConnection.makeAGetRequest("https://hrms5.stl-horizon.com/api/web/api/event-list?token=" + token, null, new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {

            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {

            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);
                JSONObject response = remoteResponse.getMessangeAsJSON();
                try {
                    if (response.getString("success").equalsIgnoreCase("1")) {
                        event = Event.createEventFrom(remoteResponse.getMessage());
                        List<EventModel> myEvent = event.getEventData();
//                        calendereventsAdapter = new CalenderEventsAdapter(getApplicationContext(), myEvent);
//                        calendereventsRecyclerView.setAdapter(calendereventsAdapter);
//                        calendereventsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

                        //save data to ROOM
                        new MyEventsAsync(getApplicationContext(), Operation.INSERT, EventSourceType.ONLINE).execute(myEvent);


                        for (DataLoaded dataloade : dataLoaded
                        ) {
                            dataloade.onDataLoaded(event);
                        }
                        return;


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {

            }

            @Override
            public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {

            }

            @Override
            public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {

            }

            @Override
            public void onAnyEvent() {

            }
        });
    }


    private void showAddEventDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_createevent);
        dialog.setCancelable(true);

        //declare and instantiate dialog Edit Texts
        final EditText txtETitle, txtEVenue, txtEType, txtEStartDate, txtEEndDate;
        final Button btnCancel, btnSubmit;
        txtETitle = dialog.findViewById(R.id.txtETitle);
        txtEVenue = dialog.findViewById(R.id.txtEVenue);
        txtEType = dialog.findViewById(R.id.txtEType);
        txtEStartDate = dialog.findViewById(R.id.txtEStartDate);
        txtEEndDate = dialog.findViewById(R.id.txtEEndDate);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnSubmit = dialog.findViewById(R.id.btnSubmit);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtEStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    txtEStartDate.setText("Tap to enter start date");
                } else {

                }

            }
        });

        txtEEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    txtEEndDate.setText("Tap to enter end date");
                } else {

                }

            }
        });

        txtEStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CalendarActivity.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String monthSelected = String.valueOf(monthOfYear + 1);
                                String daySelected = String.valueOf(dayOfMonth);
                                String month = monthSelected.length() == 1 ? "0" + monthSelected : monthSelected;
                                String day = daySelected.length() == 1 ? "0" + daySelected : daySelected;

                                final String date = year + "-" + month + "-" + day;

                                txtEStartDate.setText(date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        txtEEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CalendarActivity.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                String monthSelected = String.valueOf(monthOfYear + 1);
                                String daySelected = String.valueOf(dayOfMonth);
                                String month = monthSelected.length() == 1 ? "0" + monthSelected : monthSelected;
                                String day = daySelected.length() == 1 ? "0" + daySelected : daySelected;

                                final String date = year + "-" + month + "-" + day;


                                txtEEndDate.setText(date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String token = session.getToken();

                JSONObject jsonObject = new JSONObject();
                JSONObject headers = new JSONObject();
                try {
                    headers.put("Content-Type", "multipart/form-data");
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                    jsonObject.put("event_title", txtETitle.getText().toString());
                    jsonObject.put("start_time", txtEStartDate.getText().toString());
                    jsonObject.put("end_time", txtEEndDate.getText().toString());
                    jsonObject.put("created_date", date);
                    jsonObject.put("venue", txtEVenue.getText().toString());
                    jsonObject.put("event_type", txtEType.getText().toString());
                    jsonObject.put("color_code", "");


                } catch (JSONException e) {

                }
                NetworkConnection.makeAPostRequestFormData("https://hrms5.stl-horizon.com/api/web/api/create-event?token=" + token, jsonObject, headers, new OnReceivingResult() {
                    @Override
                    public void onErrorResult(IOException e) {
                        e.printStackTrace();


                    }

                    @Override
                    public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                    }

                    @Override
                    public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                        NetworkConnection.remoteResponseLogger(remoteResponse);
                        JSONObject response = remoteResponse.getMessangeAsJSON();
                        Event event=Event.createEventFrom(remoteResponse.getMessage());

                        try {
                            if (response.getString("success").equalsIgnoreCase("1")) {

                                Toast.makeText(CalendarActivity.this, event.getMessage(), Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                                return;


                            } else {
                                Toast.makeText(CalendarActivity.this, event.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                    }

                    @Override
                    public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                    }

                    @Override
                    public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                    }

                    @Override
                    public void onAnyEvent() {

                    }
                });



            }
        });
        dialog.show();
    }




    class GetDeviceCalendarEvents extends AsyncTask<Void, Void, Void> {


        public GetDeviceCalendarEvents(Context context) {

        }


        @Override
        protected Void doInBackground(Void... val) {
            ContentResolver contentResolver = getApplicationContext().getContentResolver();
            Cursor cursor = contentResolver.query(Uri.parse("content://com.android.calendar/events"), (new String[]{"_id", "title", "description", "dtstart", "dtend"}), null, null, null);


            List<EventModel> googleCalenderEventModel = new ArrayList<>();
            try {


                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {

                        // GoogleCalendar googleCalendar = new GoogleCalendar();
                        // event_ID: ID of tabel Event
                        int event_ID = cursor.getInt(0);

                        // title of Event
                        String title = cursor.getString(1);

                        String description = cursor.getString(2);

                        // Date start of Event
                        String dtStart = cursor.getString(3);

                        // Date end of Event
                        String dtEnd = cursor.getString(4);

                        //df.format(Long.parseLong(dtStart))

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        EventModel eventModel = new EventModel();
                        eventModel.setEvent_title(title);
                        eventModel.setEvent_type(description);
                        eventModel.setStart_time(df.format(Long.parseLong(dtStart)));
                        eventModel.setEnd_time(df.format(Long.parseLong(dtStart)));
                        eventModel.setEventSourceType(2);
                        googleCalenderEventModel.add(eventModel);

                    }
                    new MyEventsAsync(getApplicationContext(), Operation.INSERT, EventSourceType.CALENDAR).execute(googleCalenderEventModel);

                }
            } catch (AssertionError ex) {
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    @Override
    public void onBackPressed() {
//        Intent it = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(it);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.nothing);
//        finish();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {

        super.onResume();

        //load events on the CALENDER UI
        dataLoaded.add(new DataLoaded() {
            @Override
            public void onDataLoaded(Event event) {

                List<Long> events = CalendarActivity.this.event.getEventTimeStamp();
                Calendar calendar = Calendar.getInstance();
                days.addAll(events);
                //Define colors
                int textColor = Color.parseColor("#000000");
                int selectedTextColor = Color.parseColor("#000000");
                int disabledTextColor = Color.parseColor("#000000");
                ConnectedDays connectedDays = new ConnectedDays(days, textColor, selectedTextColor, disabledTextColor);


                //Connect days to calendar
                calendarView.addConnectedDays(connectedDays);
                calendarView.setSelectedDayBackgroundColor(getResources().getColor(R.color.red_300));
                calendarView.setMonthTextColor(getResources().getColor(R.color.colorPrimaryDark));
                calendarView.setSelectionBarMonthTextColor(getResources().getColor(R.color.red_700));
                calendarView.setWeekDayTitleTextColor(getResources().getColor(R.color.red_300));
                //calendarView.setWeekendDayTextColor(getResources().getColor(R.color.red_300));


                // Check if we're running on Android 5.0 or higher in order to chose selected day icons
                if (Build.VERSION.SDK_INT >= 23) {

                    calendarView.setCurrentDayIconRes(R.drawable.ic_todayicon);
                    calendarView.setConnectedDayIconRes(R.drawable.ic_otherdayicon);
                } else {

                    calendarView.setCurrentDayIconRes(R.drawable.ic_todaylowericon);
                    calendarView.setConnectedDayIconRes(R.drawable.ic_otherdaylowericon);
                }
                calendarView.getConnectedDaysManager().applySettingsToDay(new Day(new Date(2020, 01, 15)));
                calendarView.update();
                calendarView.getSelectionManager().toggleDay(new Day(new Date(2020, 01, 15)));

            }
        });


//Hide progress and show loaded calender
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                calendarView.setVisibility(View.VISIBLE);
            }
        }, 1000);


    }

    public void updateStatusBarColor(String color) {// Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }


}

interface DataLoaded {
    public void onDataLoaded(Event event);

}





