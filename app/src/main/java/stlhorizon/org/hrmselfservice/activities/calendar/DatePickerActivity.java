//package stlhorizon.org.hrmselfservice.activities.calendar;
//
//import android.Manifest;
//import android.content.AsyncQueryHandler;
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.provider.CalendarContract;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.annotation.VisibleForTesting;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.CursorLoader;
//import android.support.v4.content.Loader;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.text.format.DateUtils;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.CheckedTextView;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.HashSet;
//import java.util.Set;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//import stlhorizon.org.hrmselfservice.BuildConfig;
//import stlhorizon.org.hrmselfservice.CalendarUtils;
//import stlhorizon.org.hrmselfservice.R;
//import stlhorizon.org.hrmselfservice.content.CalendarCursor;
//import stlhorizon.org.hrmselfservice.content.EventCursor;
//import stlhorizon.org.hrmselfservice.content.EventsQueryHandler;
//import stlhorizon.org.hrmselfservice.utils.DateFormatter;
//import stlhorizon.org.hrmselfservice.utils.StringConstants;
//import stlhorizon.org.hrmselfservice.weather.WeatherSyncService;
//import stlhorizon.org.hrmselfservice.widget.AgendaAdapter;
//import stlhorizon.org.hrmselfservice.widget.AgendaView;
//import stlhorizon.org.hrmselfservice.widget.EventCalendarView;
//
//import static android.graphics.Paint.ANTI_ALIAS_FLAG;
//import static stlhorizon.org.hrmselfservice.preferences.Preferences.readInitials;
//import static stlhorizon.org.hrmselfservice.utils.StringConstants.INITIALS;
//import static stlhorizon.org.hrmselfservice.utils.StringConstants.LEAVE_NAME;
//import static stlhorizon.org.hrmselfservice.utils.Utils.loadAvatar;
//
//public class DatePickerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
//
//    private static final String STATE_TOOLBAR_TOGGLE = "state:toolbarToggle";
//    private static final int REQUEST_CODE_CALENDAR = 0;
//    private static final int REQUEST_CODE_LOCATION = 1;
//    private static final String SEPARATOR = ",";
//    private static final int LOADER_CALENDARS = 0;
//    private static final int LOADER_LOCAL_CALENDAR = 1;
//    private static Context context;
//    private static long date = System.currentTimeMillis();
//    private static TextView tv_from,tv_days,tv_error;
//    int flag = 2,days_remaining = 0;
//    long start_date = 0;
//    @VisibleForTesting
//    public static Set<Integer> dayIndexes;
//    private boolean correct_date = true;
//
//    private final SharedPreferences.OnSharedPreferenceChangeListener mWeatherChangeListener =
//            new SharedPreferences.OnSharedPreferenceChangeListener() {
//                @Override
//                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
//                                                      String key) {
//                    if (TextUtils.equals(key, WeatherSyncService.PREF_WEATHER_TODAY) ||
//                            TextUtils.equals(key, WeatherSyncService.PREF_WEATHER_TOMORROW)) {
//                        loadWeather();
//                    }
//                }
//            };
//
//    private final DatePickerActivity.Coordinator mCoordinator = new DatePickerActivity.Coordinator();
//    private View mCoordinatorLayout;
//    private CheckedTextView mToolbarToggle;
//    private EventCalendarView mCalendarView;
//    private AgendaView mAgendaView;
//    private FloatingActionButton fab;
//    private static Toolbar toolbar,toolbar_details;
//    private final HashSet<String> mExcludedCalendarIds = new HashSet<>();
//    private boolean mWeatherEnabled, mPendingWeatherEnabled;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setUpPreferences();
//        setContentView(R.layout.activity_date_picker);
//        context = this;
//        toolbar = findViewById(R.id.toolbar);
//        toolbar_details = findViewById(R.id.toolbar_details);
//
//        tv_from = toolbar_details.findViewById(R.id.tv_from);
//        tv_days = toolbar_details.findViewById(R.id.tv_days);
//        tv_error = toolbar_details.findViewById(R.id.tv_error);
//
//        // TODO initialise these preferences during first installation
//        flag = PreferenceManager.getDefaultSharedPreferences(this)
//                .getInt(StringConstants.DATE_PICKER_FLAG,0);
//        start_date = PreferenceManager.getDefaultSharedPreferences(this)
//                .getLong(StringConstants.LEAVE_RQST_START_DATE,0);
//        days_remaining = PreferenceManager.getDefaultSharedPreferences(this)
//                .getInt(StringConstants.LEAVE_RQST_MXM_DAYS,0);
//        //setSupportActionBar(toolbar);
//        //noinspection ConstantConditions
//        setUpContentView();
//        setTitle();
//        getIntentData();
//    }
//
//    private void getIntentData(){
//        if(getIntent() != null){
//            if(getIntent().hasExtra(LEAVE_NAME)){
//                mToolbarToggle.setText(getIntent().getStringExtra(LEAVE_NAME) + "(" + days_remaining + " days)");
//            } else
//                mToolbarToggle.setText("Pick date");
//        }
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mCoordinator.restoreState(savedInstanceState);
//        if (savedInstanceState.getBoolean(STATE_TOOLBAR_TOGGLE, false)) {
//            View toggleButton = findViewById(R.id.toolbar_toggle_frame);
//            if (toggleButton != null) { // can be null as disabled in landscape
//                toggleButton.performClick();
//            }
//        }
//    }
//
//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        //mDrawerToggle.syncState();
//        //mCoordinator.coordinate(mToolbarToggle, mCalendarView, mAgendaView);
//        mCoordinator.coordinate(tv_error, mCalendarView, mAgendaView);
//        if (checkCalendarPermissions()) {
//            loadEvents();
//        } else {
//            toggleEmptyView(true);
//        }
//        if (mWeatherEnabled && !checkLocationPermissions()) {
//            explainLocationPermissions();
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mCoordinator.saveState(outState);
//        outState.putBoolean(STATE_TOOLBAR_TOGGLE, mToolbarToggle.isChecked());
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mCalendarView.deactivate();
//        mAgendaView.setAdapter(null); // force detaching adapter
//        PreferenceManager.getDefaultSharedPreferences(this)
//                .edit()
//                .putString(CalendarUtils.PREF_CALENDAR_EXCLUSIONS,
//                        TextUtils.join(SEPARATOR, mExcludedCalendarIds))
//                .apply();
//        PreferenceManager.getDefaultSharedPreferences(this)
//                .unregisterOnSharedPreferenceChangeListener(mWeatherChangeListener);
//    }
//
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent();
//        intent.putExtra("date","date");
//        setResult(1,intent);
//        super.onBackPressed();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case REQUEST_CODE_CALENDAR:
//                if (checkCalendarPermissions()) {
//                    toggleEmptyView(false);
//                    loadEvents();
//                } else {
//                    toggleEmptyView(true);
//                }
//                break;
//            case REQUEST_CODE_LOCATION:
//                if (checkLocationPermissions()) {
//                    toggleWeather();
//                } else {
//                    explainLocationPermissions();
//                }
//                break;
//        }
//    }
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        String selection = null;
//        String[] selectionArgs = null;
//        if (id == LOADER_LOCAL_CALENDAR) {
//            selection = CalendarContract.Calendars.ACCOUNT_TYPE + "=?";
//            selectionArgs = new String[]{String.valueOf(CalendarContract.ACCOUNT_TYPE_LOCAL)};
//        }
//        return new CursorLoader(this,
//                CalendarContract.Calendars.CONTENT_URI,
//                CalendarCursor.PROJECTION, selection, selectionArgs,
//                CalendarContract.Calendars.DEFAULT_SORT_ORDER);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        switch (loader.getId()) {
//            case LOADER_CALENDARS:
//                if (data != null && data.moveToFirst()) {
//                }
//                break;
//            case LOADER_LOCAL_CALENDAR:
//                if (data == null || data.getCount() == 0) {
//                    createLocalCalendar();
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//    }
//
//    private void setUpPreferences() {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//        mWeatherEnabled = mPendingWeatherEnabled = sp.getBoolean(
//                WeatherSyncService.PREF_WEATHER_ENABLED, false);
//        String exclusions = PreferenceManager.getDefaultSharedPreferences(this)
//                .getString(CalendarUtils.PREF_CALENDAR_EXCLUSIONS, null);
//        if (!TextUtils.isEmpty(exclusions)) {
//            mExcludedCalendarIds.addAll(Arrays.asList(exclusions.split(SEPARATOR)));
//        }
//        CalendarUtils.sWeekStart = sp.getInt(CalendarUtils.PREF_WEEK_START, Calendar.SUNDAY);
//        PreferenceManager.getDefaultSharedPreferences(this)
//                .registerOnSharedPreferenceChangeListener(mWeatherChangeListener);
//    }
//
//    private void setUpContentView() {
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayShowCustomEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            LayoutInflater inflater = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.custom_toolbar_for_all, null);
//            getSupportActionBar().setCustomView(view);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//            Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
//            ImageView iv_test = (ImageView) findViewById(R.id.iv_test);
//
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)iv_test.getLayoutParams();
//            params.setMargins(111, 47, 0, 0); //substitute parameters for left, top, right, bottom
//            iv_test.setLayoutParams(params);
//
//            iv_test.startAnimation(startRotateAnimation);
//
//            loadAvatar(context,(CircleImageView)view.findViewById(R.id.iv_user_image));
//            TextView tv_initials = view.findViewById(R.id.tv_initials);
//            tv_initials.setText(readInitials(context, INITIALS, "-"));
//        }
//
//        mCoordinatorLayout = findViewById(R.id.coordinator_layout);
//
//        mToolbarToggle = findViewById(R.id.toolbar_toggle);
//        View toggleButton = findViewById(R.id.toolbar_toggle_frame);
//        if (toggleButton != null) { // can be null as disabled in landscape
//            toggleButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mToolbarToggle.toggle();
//                }
//            });
//        }
//        mCalendarView = findViewById(R.id.calendar_view);
//        mAgendaView = findViewById(R.id.agenda_view);
//        fab = findViewById(R.id.fab);
//        mCalendarView.setVisibility(View.VISIBLE);
//        if(flag == 0){
//            fab.setImageBitmap(textAsBitmap("To", 40, Color.WHITE));
//        }
//        else {
//            fab.setImageResource(R.drawable.ic_action_arrow_right);
//        }
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setDate();
//            }
//        });
//    }
//
//    @SuppressWarnings("ConstantConditions")
//    private void toggleEmptyView(boolean visible) {
//        if (visible) {
//            findViewById(R.id.empty).setVisibility(View.VISIBLE);
//            findViewById(R.id.empty).bringToFront();
//            findViewById(R.id.button_permission)
//                    .setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            requestCalendarPermissions();
//                        }
//                    });
//        } else {
//            findViewById(R.id.empty).setVisibility(View.GONE);
//        }
//    }
//
//    private void setDate() {
//        Intent intent = new Intent();
//        intent.putExtra("date", String.valueOf(date));
//        setResult(1,intent);
//        date = System.currentTimeMillis();
//        finish();
//    }
//
//    private void loadEvents() {
//        getSupportLoaderManager().initLoader(LOADER_CALENDARS, null, this);
//        getSupportLoaderManager().initLoader(LOADER_LOCAL_CALENDAR, null, this);
//        fab.show();
//        mCalendarView.setCalendarAdapter(new DatePickerActivity.CalendarCursorAdapter(this, mExcludedCalendarIds));
//        mAgendaView.setAdapter(new DatePickerActivity.AgendaCursorAdapter(this, mExcludedCalendarIds));
//        loadWeather();
//    }
//
//    private void toggleWeather() {
//        mWeatherEnabled = mPendingWeatherEnabled;
//        PreferenceManager.getDefaultSharedPreferences(this)
//                .edit()
//                .putBoolean(WeatherSyncService.PREF_WEATHER_ENABLED, mWeatherEnabled)
//                .apply();
//        supportInvalidateOptionsMenu();
//        loadWeather();
//    }
//
//    private void loadWeather() {
//        mAgendaView.setWeather(mWeatherEnabled ? WeatherSyncService.getSyncedWeather(this) : null);
//    }
//
//    private void createLocalCalendar() {
//        String name = getString(R.string.default_calendar_name);
//        ContentValues cv = new ContentValues();
//        cv.put(CalendarContract.Calendars.ACCOUNT_NAME, BuildConfig.APPLICATION_ID);
//        cv.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
//        cv.put(CalendarContract.Calendars.NAME, name);
//        cv.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, name);
//        cv.put(CalendarContract.Calendars.CALENDAR_COLOR, 0);
//        cv.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
//                CalendarContract.Calendars.CAL_ACCESS_OWNER);
//        cv.put(CalendarContract.Calendars.OWNER_ACCOUNT, BuildConfig.APPLICATION_ID);
//        new DatePickerActivity.CalendarQueryHandler(getContentResolver())
//                .startInsert(0, null, CalendarContract.Calendars.CONTENT_URI
//                                .buildUpon()
//                                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "1")
//                                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME,
//                                        BuildConfig.APPLICATION_ID)
//                                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE,
//                                        CalendarContract.ACCOUNT_TYPE_LOCAL)
//                                .build()
//                        , cv);
//    }
//
//    @VisibleForTesting
//    protected boolean checkCalendarPermissions() {
//        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) |
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)) ==
//                PackageManager.PERMISSION_GRANTED;
//    }
//
//    @VisibleForTesting
//    protected boolean checkLocationPermissions() {
//        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) |
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) ==
//                PackageManager.PERMISSION_GRANTED;
//    }
//
//    @VisibleForTesting
//    protected void requestCalendarPermissions() {
//        ActivityCompat.requestPermissions(this,
//                new String[]{
//                        Manifest.permission.READ_CALENDAR,
//                        Manifest.permission.WRITE_CALENDAR},
//                REQUEST_CODE_CALENDAR);
//    }
//
//    @VisibleForTesting
//    protected void requestLocationPermissions() {
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                REQUEST_CODE_LOCATION);
//    }
//
//    private void explainLocationPermissions() {
//        Snackbar.make(mCoordinatorLayout, R.string.location_permission_required,
//                Snackbar.LENGTH_LONG)
//                .setAction(R.string.grant_access, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        requestLocationPermissions();
//                    }
//                })
//                .show();
//    }
//
//    /**
//     * Coordinator utility that synchronizes widgets as selected date changes
//     */
//
//    class Coordinator {
//        private static final String STATE_SELECTED_DATE = "state:selectedDate";
//        private final EventCalendarView.OnChangeListener mCalendarListener
//                = new EventCalendarView.OnChangeListener() {
//            @Override
//            public void onSelectedDayChange(long calendarDate) {
//
//                tv_days.setVisibility(View.VISIBLE);
//                date = calendarDate;
//
//                if(flag == 0){
//                    long today = CalendarUtils.today();
//                    tv_from.setText("From:");
//                    if(date < today){
//                        correct_date = false;
//                        tv_from.setVisibility(View.GONE);
//                        fab.setVisibility(View.GONE);
//                        //tv_days.setText(days_remaining + " days left");
//                        tv_error.setText("Start date cannot be earlier than today");
//                        tv_error.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_error));
//                        tv_error.setTextColor(ContextCompat.getColor(context, R.color.isan_error));
//                        sync(calendarDate, mCalendarView);
//                    }else{
//                        correct_date = true;
//                        tv_from.setVisibility(View.VISIBLE);
//                        tv_error.setTextColor(ContextCompat.getColor(context, R.color.hrms_color_44));
//                        fab.setVisibility(View.VISIBLE);
//                        sync(calendarDate, mCalendarView);
//                    }
//                }
//                else if(flag == 1){
//                    tv_from.setText("To:");
//                    int selected_days = onDateSet(start_date,date) + 1;
//                    if(selected_days == 0){
//                        correct_date = true;
//                        sync(calendarDate, mCalendarView);
//                        tv_days.setText((days_remaining - 1) + " days left");
//                        tv_days.setVisibility(View.VISIBLE);
//                        tv_from.setVisibility(View.VISIBLE);
//                        tv_error.setTextColor(ContextCompat.getColor(context, R.color.hrms_color_44));
//                        fab.setVisibility(View.VISIBLE);
//                        showIndexes(start_date,date);
//                    }
//                    else if(selected_days == 1){
//                        correct_date = false;
//                        fab.setVisibility(View.GONE);
//                        tv_days.setText(days_remaining + " days left");
//                        tv_error.setText("End date cannot be earlier than " +
//                                DateFormatter.dateFormatter("MMM d, yyyy").format(new Date(start_date)));
//                        tv_error.setTextColor(ContextCompat.getColor(context, R.color.isan_error));
//                        tv_from.setVisibility(View.GONE);
//                        sync(calendarDate, mCalendarView);
//                    }
//                    else if(days_remaining - selected_days == 0){
//                        correct_date = true;
//                        sync(calendarDate, mCalendarView);
//                        tv_days.setText("Nill");
//                        tv_from.setVisibility(View.VISIBLE);
//                        tv_error.setTextColor(ContextCompat.getColor(context, R.color.hrms_color_44));
//                        fab.setVisibility(View.VISIBLE);
//                        showIndexes(start_date,date);
//                    }
//                    else if(days_remaining - selected_days < 1){
//                        correct_date = false;
//                        tv_days.setVisibility(View.GONE);
//                        fab.setVisibility(View.GONE);
//                        tv_from.setVisibility(View.GONE);
//                        tv_error.setText(days_remaining == 1? "More than the " + days_remaining + " day available"
//                                :"More than the " + days_remaining + " days available");
//                        tv_error.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_error));
//                        tv_error.setTextColor(ContextCompat.getColor(context, R.color.isan_error));
//                        sync(calendarDate, mCalendarView);
//                    }else {
//                        correct_date = true;
//                        sync(calendarDate, mCalendarView);
//                        tv_days.setText((days_remaining - selected_days) == 1? (days_remaining - selected_days) + " day left"
//                                :(days_remaining - selected_days) + " days left");
//                        tv_days.setVisibility(View.VISIBLE);
//                        tv_from.setVisibility(View.VISIBLE);
//                        tv_error.setTextColor(ContextCompat.getColor(context, R.color.hrms_color_44));
//                        fab.setVisibility(View.VISIBLE);
//                        showIndexes(start_date,date);
//                    }
//                }else{
//                    sync(calendarDate, mCalendarView);
//                }
//            }
//        };
//
//        private final AgendaView.OnDateChangeListener mAgendaListener
//                = new AgendaView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(long dayMillis) {
//                sync(dayMillis, mAgendaView);
//            }
//        };
//
//        private TextView mTextView;
//        private EventCalendarView mCalendarView;
//        private AgendaView mAgendaView;
//
//        private long mSelectedDayMillis = CalendarUtils.NO_TIME_MILLIS;
//
//        /**
//         * Set up widgets to be synchronized
//         * @param textView      title
//         * @param calendarView  calendar view
//         * @param agendaView    agenda view
//         */
//        public void coordinate(@NonNull TextView textView,
//                               @NonNull EventCalendarView calendarView,
//                               @NonNull AgendaView agendaView) {
//            if (mCalendarView != null) {
//                mCalendarView.setOnChangeListener(null);
//            }
//            if (mAgendaView != null) {
//                mAgendaView.setOnDateChangeListener(null);
//            }
//            mTextView = textView;
//            mCalendarView = calendarView;
//            mAgendaView = agendaView;
//
//            if (mSelectedDayMillis < 0) {
//                if(flag == 1){
//                    mSelectedDayMillis = start_date;
//
//                }else{
//                    mSelectedDayMillis = CalendarUtils.today();
//                }
//            }
//            mCalendarView.setSelectedDay(mSelectedDayMillis);
//            mAgendaView.setSelectedDay(mSelectedDayMillis);
//            updateTitle(mSelectedDayMillis);
//            calendarView.setOnChangeListener(mCalendarListener);
//            agendaView.setOnDateChangeListener(mAgendaListener);
//        }
//
//        void saveState(Bundle outState) {
//            outState.putLong(STATE_SELECTED_DATE, mSelectedDayMillis);
//        }
//
//        void restoreState(Bundle savedState) {
//            mSelectedDayMillis = savedState.getLong(STATE_SELECTED_DATE,
//                    CalendarUtils.NO_TIME_MILLIS);
//        }
//
//        void reset() {
//            mSelectedDayMillis = CalendarUtils.today();
//            if (mCalendarView != null) {
//                mCalendarView.reset();
//            }
//            if (mAgendaView != null) {
//                mAgendaView.reset();
//            }
//            updateTitle(mSelectedDayMillis);
//        }
//
//        private void sync(long dayMillis, View originator) {
//            mSelectedDayMillis = dayMillis;
//            if (originator != mCalendarView) {
//                mCalendarView.setSelectedDay(dayMillis);
//            }
//            if (originator != mAgendaView) {
//                mAgendaView.setSelectedDay(dayMillis);
//            }
//            updateTitle(dayMillis);
//        }
//
//        private void updateTitle(long dayMillis) {
//            if(correct_date) {
//                mTextView.setText(DateFormatter.dateFormatter("MMM d, yyyy").format(new Date(dayMillis)));
//                tv_days.setVisibility(View.VISIBLE);
//            } else
//                tv_days.setVisibility(View.GONE);
//        }
//    }
//
//    private int onDateSet(long from_date, long to_date) {
//        int days = 0;
//        SimpleDateFormat dateFormat = DateFormatter.dateFormatter("MM/dd/yy HH:mm a");
//        Date startDate = null,endDate = null;
//
//        try {
//            startDate = dateFormat.parse(dateFormat.format(new Date(from_date)));
//            endDate = dateFormat.parse(dateFormat.format(new Date(to_date)));
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        if (startDate != null && endDate != null) {
//            days = excludeWeekends(startDate, endDate);
//        }
//        return days;
//    }
//
//    private int excludeWeekends(Date date1, Date date2 ){
//        int numberOfDays = 0;
//        if(date1 != null && date2 != null){
//            String date_1 = DateFormatter.dateFormatter("MMM d, yyyy").format(date1),
//                    date_2 = DateFormatter.dateFormatter("MMM d, yyyy").format(date2);
//            if(date_1.equals(date_2)){
//                numberOfDays = -1;
//            }
//            if(!date_1.equals(date_2)){
//                Calendar cal1 = Calendar.getInstance(), cal2 = Calendar.getInstance();
//                cal1.setTime(date1);
//                cal2.setTime(date2);
//
//                while (cal1.before(cal2)) {
//                    if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
//                            &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
//                        numberOfDays++;
//                    }
//                    cal1.add(Calendar.DATE,1);
//                }
//            }
//        }
//        return  numberOfDays;
//    }
//
//    public void setTitle(){
//        if(flag == 0){
//            tv_from.setVisibility(View.VISIBLE);
//            tv_from.setText("From:");
//            tv_days.setText(days_remaining + " days");
//
//        } else if(flag == 1){
//            tv_from.setVisibility(View.VISIBLE);
//            tv_from.setText("To:");
//            tv_days.setText(days_remaining + " days left");
//            //getIndexes(start_date);
//        }
//    }
//
//    void showIndexes(long from_date, long to_date) {
//        SimpleDateFormat dateFormat = DateFormatter.dateFormatter("MM/dd/yy HH:mm a");
//
//        Date startDate = null, endDate = null;
//        try {
//            startDate = dateFormat.parse(dateFormat.format(new Date(from_date)));
//            endDate = dateFormat.parse(dateFormat.format(new Date(to_date)));
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if (startDate!=null && endDate!=null) {
//            getDaysBetweenDates(startDate, endDate);
//        }
//    }
//
//    void getDaysBetweenDates(Date startdate, Date enddate)
//    {
//        dayIndexes = new HashSet<>();
//        Calendar start = new GregorianCalendar();
//        start.setTime(startdate);
//
//        Calendar end = new GregorianCalendar();
//        end.setTime(enddate);
//
//        end.add(Calendar.DATE, 1);
//
//        while (start.getTime().before(end.getTime()))
//        {
//            if ((Calendar.SATURDAY != start.get(Calendar.DAY_OF_WEEK))
//                    &&(Calendar.SUNDAY != start.get(Calendar.DAY_OF_WEEK))) {
//                Date result = start.getTime();
//                long dt = result.getTime();
//                getIndexes(dt);
//            }
//            start.add(Calendar.DATE, 1);
//        }
//    }
//
//    void getIndexes(long date){
//        long mBaseTimeMillis = CalendarUtils.monthFirstDay(date);
//        int index = (int) ((date - mBaseTimeMillis) / DateUtils.DAY_IN_MILLIS);
//        dayIndexes.add(index);
//    }
//
//    static class AgendaCursorAdapter extends AgendaAdapter {
//
//        @VisibleForTesting
//        final DatePickerActivity.DayEventsQueryHandler mHandler;
//
//        public AgendaCursorAdapter(Context context, Collection<String> excludedCalendarIds) {
//            super(context);
//            mHandler = new DatePickerActivity.DayEventsQueryHandler(context.getContentResolver(), this,
//                    excludedCalendarIds);
//        }
//
//        @Override
//        protected void loadEvents(long timeMillis) {
//            mHandler.startQuery(timeMillis, timeMillis, timeMillis + DateUtils.DAY_IN_MILLIS);
//        }
//    }
//
//    static class CalendarCursorAdapter extends EventCalendarView.CalendarAdapter {
//        private final DatePickerActivity.MonthEventsQueryHandler mHandler;
//
//        public CalendarCursorAdapter(Context context, Collection<String> excludedCalendarIds) {
//            mHandler = new DatePickerActivity.MonthEventsQueryHandler(context.getContentResolver(), this,
//                    excludedCalendarIds);
//        }
//
//        @Override
//        protected void loadEvents(long monthMillis) {
//            long startTimeMillis = CalendarUtils.monthFirstDay(monthMillis),
//                    endTimeMillis = startTimeMillis + DateUtils.DAY_IN_MILLIS *
//                            CalendarUtils.monthSize(monthMillis);
//            mHandler.startQuery(monthMillis, startTimeMillis, endTimeMillis);
//        }
//    }
//
//    static class DayEventsQueryHandler extends EventsQueryHandler {
//
//        private final DatePickerActivity.AgendaCursorAdapter mAgendaCursorAdapter;
//
//        public DayEventsQueryHandler(ContentResolver cr,
//                                     DatePickerActivity.AgendaCursorAdapter agendaCursorAdapter,
//                                     @NonNull Collection<String> excludedCalendarIds) {
//            super(cr, excludedCalendarIds);
//            mAgendaCursorAdapter = agendaCursorAdapter;
//        }
//
//        @Override
//        protected void handleQueryComplete(int token, Object cookie, EventCursor cursor) {
//            mAgendaCursorAdapter.bindEvents((Long) cookie, cursor);
//        }
//    }
//
//    static class MonthEventsQueryHandler extends EventsQueryHandler {
//
//        private final DatePickerActivity.CalendarCursorAdapter mAdapter;
//
//        public MonthEventsQueryHandler(ContentResolver cr,
//                                       DatePickerActivity.CalendarCursorAdapter adapter,
//                                       @NonNull Collection<String> excludedCalendarIds) {
//            super(cr, excludedCalendarIds);
//            mAdapter = adapter;
//        }
//
//        @Override
//        protected void handleQueryComplete(int token, Object cookie, EventCursor cursor) {
//            mAdapter.bindEvents((Long) cookie, cursor);
//        }
//    }
//
//    static class CalendarQueryHandler extends AsyncQueryHandler {
//
//        public CalendarQueryHandler(ContentResolver cr) {
//            super(cr);
//        }
//    }
//
//    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
//        Paint paint = new Paint(ANTI_ALIAS_FLAG);
//        paint.setTextSize(textSize);
//        paint.setColor(textColor);
//        paint.setTextAlign(Paint.Align.LEFT);
//        float baseline = -paint.ascent(); // ascent() is negative
//        int width = (int) (paint.measureText(text) + 0.0f); // round
//        int height = (int) (baseline + paint.descent() + 0.0f);
//        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//
//        Canvas canvas = new Canvas(image);
//        canvas.drawText(text, 0, baseline, paint);
//        return image;
//    }
//}
//
