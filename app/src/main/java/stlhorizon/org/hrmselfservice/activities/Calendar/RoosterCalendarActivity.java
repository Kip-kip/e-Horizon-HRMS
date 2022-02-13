//package stlhorizon.org.hrmselfservice.activities.calendar;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.ActionBar;
//import android.app.AlertDialog;
//import android.content.AsyncQueryHandler;
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.RectF;
//import android.net.Uri;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.provider.CalendarContract;
//import android.text.TextUtils;
//import android.text.format.DateUtils;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.SearchView;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.Toolbar;
//
//import androidx.annotation.IdRes;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.VisibleForTesting;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.view.MenuItemCompat;
//import androidx.loader.app.LoaderManager;
//import androidx.loader.content.CursorLoader;
//import androidx.loader.content.Loader;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.alamkanak.weekview.DateTimeInterpreter;
//import com.alamkanak.weekview.MonthLoader;
//import com.alamkanak.weekview.WeekView;
//import com.alamkanak.weekview.WeekViewEvent;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
//
//import java.text.DateFormatSymbols;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Locale;
//
//import stlhorizon.org.hrmselfservice.BuildConfig;
//import stlhorizon.org.hrmselfservice.CalendarUtils;
//import stlhorizon.org.hrmselfservice.R;
//import stlhorizon.org.hrmselfservice.content.CalendarCursor;
//import stlhorizon.org.hrmselfservice.content.EventCursor;
//import stlhorizon.org.hrmselfservice.content.EventsQueryHandler;
//import stlhorizon.org.hrmselfservice.utils.DateFormatter;
//import stlhorizon.org.hrmselfservice.weather.WeatherSyncService;
//import stlhorizon.org.hrmselfservice.widget.AgendaAdapter;
//import stlhorizon.org.hrmselfservice.widget.AgendaView;
//import stlhorizon.org.hrmselfservice.widget.CalendarSelectionView;
//import stlhorizon.org.hrmselfservice.widget.EventCalendarView;
//
//import static stlhorizon.org.hrmselfservice.utils.StringConstants.SELECTED_DATE;
//import static stlhorizon.org.hrmselfservice.utils.Utils.randomColor;
//
//public class RoosterCalendarActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
//        WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener,
//        WeekView.EmptyViewLongPressListener, WeekView.EmptyViewClickListener {
//
//    private static final String STATE_TOOLBAR_TOGGLE = "state:toolbarToggle";
//    private static final int REQUEST_CODE_CALENDAR = 0;
//    private static final int REQUEST_CODE_LOCATION = 1;
//    private static final String SEPARATOR = ",";
//    private static final int LOADER_CALENDARS = 0;
//    private static final int LOADER_LOCAL_CALENDAR = 1;
//    private static Context context;
//    private Menu mMenu;
//    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
//    boolean calledNetwork = false;
//    private Toolbar toolbar, toolbar_details;
//    private TextView toolbar_toggle;
//    private TextView tv_month_view, tv_today;
//    public static long selected_date = 0;
//    private AlertDialog.Builder builder;
//
//    // for 3D and Week view
//    private static final int TYPE_DAY_VIEW = 1;
//    private static final int TYPE_THREE_DAY_VIEW = 2;
//    private static final int TYPE_WEEK_VIEW = 3;
//    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
//    private WeekView mWeekView;
//
//    private ImageView iv_test;
//    private Animation startRotateAnimation;
//
//    private final Coordinator mCoordinator = new Coordinator();
//    private View mCoordinatorLayout;
//    private TextView mToolbarToggle;
//    private EventCalendarView mCalendarView;
//    private AgendaView mAgendaView;
//    private FloatingActionButton mFabAdd;
//    private LinearLayout ll_activity_main, ll_3d_and_week_view;
//    private final HashSet<String> mExcludedCalendarIds = new HashSet<>();
//    private boolean mWeatherEnabled, mPendingWeatherEnabled;
//
//    private SwipeRefreshLayout srl_evt_cal_view;//, srl_weekview;
//    private InsertEvent createInsertEvent;
//    private int flag = -1;
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
//    private final CalendarSelectionView.OnSelectionChangeListener mCalendarSelectionListener
//            = new CalendarSelectionView.OnSelectionChangeListener() {
//        @Override
//        public void onSelectionChange(long id, boolean enabled) {
//            if (!enabled) {
//                mExcludedCalendarIds.add(String.valueOf(id));
//            } else {
//                mExcludedCalendarIds.remove(String.valueOf(id));
//            }
//            mCalendarView.invalidateData();
//            mAgendaView.invalidateData();
//        }
//    };
//
//    @SuppressLint("WrongConstant")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        context = this;
//
//        setUpPreferences();
//
//        setContentView(R.layout.activity_rooster_calendar);
//        toolbar = findViewById(R.id.toolbar);
//        toolbar_details = findViewById(R.id.toolbar_details);
//        //toolbar_toggle = toolbar_details.findViewById(R.id.toolbar_toggle);
//        //toolbar_toggle.setTextSize(14);
//        tv_month_view = toolbar_details.findViewById(R.id.tv_month_view);
//        tv_today = toolbar_details.findViewById(R.id.tv_today);
//
//        srl_evt_cal_view = findViewById(R.id.srl_evt_cal_view);
//        //srl_weekview = findViewById(R.id.srl_weekview);
//
//        tv_today.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ll_activity_main.isShown()) {
//                    mCoordinator.reset(CalendarUtils.today());
//                } else {
//                    mWeekView.goToToday();
//                }
//            }
//        });
//
//        //setSupportActionBar(toolbar);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//            startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
//            iv_test = (ImageView) findViewById(R.id.iv_test);
//
//            //RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)iv_test.getLayoutParams();
//            //params.setMargins(111, 47, 0, 0); //substitute parameters for left, top, right, bottom
//            //iv_test.setLayoutParams(params);
//
//            iv_test.startAnimation(startRotateAnimation);
//
//            //loadAvatar(context, (CircleImageView) findViewById(R.id.iv_user_image));
//            TextView tv_initials = findViewById(R.id.tv_initials);
//            //tv_initials.setText(readInitials(context, INITIALS, "-"));
//        }
//
//        setUpContentView();
//        init3DWeekView();
//
//        if (getIntent() != null) {
//            selected_date = getIntent().getLongExtra(SELECTED_DATE, 0);
//        }
//        fetchAllEvts();
//
//        srl_evt_cal_view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                srl_evt_cal_view.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       // refreshEvents();
//                    }
//                }, 500);
//            }
//        });
//        srl_evt_cal_view.setColorScheme(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
///*        srl_weekview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                srl_weekview.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e("srl_weekview", "refreshActivity called");
//                        refreshEvents();
//                    }
//                }, 500);
//            }
//        });
//        srl_weekview.setColorScheme(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);*/
//
//        //builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MyCheckBox));
//        createInsertEvent = new InsertEvent(getContentResolver(),context);
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
//        mCoordinator.coordinate(mToolbarToggle, mCalendarView, mAgendaView);
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
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        mMenu = menu;
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        //*** setOnQueryTextFocusChangeListener ***
//        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    toolbar_details.setVisibility(View.GONE);
//                } else {
//                    toolbar_details.setVisibility(View.VISIBLE);
//                }
//                iv_test.startAnimation(startRotateAnimation);
//            }
//        });
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                iv_test.startAnimation(startRotateAnimation);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String searchQuery) {
//                //TODO implements onsearch here
////                adapter.filter(searchQuery.toString().trim());
////                listView.invalidate();
//                return true;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        //menu.findItem(R.id.action_month_view).setVisible(false);
//        menu.findItem(R.id.action_weather).setVisible(false);
//        menu.findItem(R.id.action_week_start).setVisible(false);
//        /*menu.findItem(R.id.action_refresh_calendar).setVisible(false);
//        if(ll_3d_and_week_view.isShown()) {
//            menu.findItem(R.id.action_refresh_calendar).setVisible(true);
//        }*/
//        menu.findItem(R.id.action_weather).setChecked(mWeatherEnabled);
//        switch (CalendarUtils.sWeekStart) {
//            case Calendar.SATURDAY:
//                menu.findItem(R.id.action_week_start_saturday).setChecked(true);
//                break;
//            case Calendar.SUNDAY:
//                menu.findItem(R.id.action_week_start_sunday).setChecked(true);
//                break;
//            case Calendar.MONDAY:
//                menu.findItem(R.id.action_week_start_monday).setChecked(true);
//                break;
//        }
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        setupDateTimeInterpreter(id == R.id.action_week_view);
//
//        if (item.getItemId() == android.R.id.home) {
//            if(ll_3d_and_week_view.isShown()){
//                ll_activity_main.setVisibility(View.VISIBLE);
//                ll_3d_and_week_view.setVisibility(View.GONE);
//                invalidateOptionsMenu();
//            } else{
//                onBackPressed();
//            }
//            return true;
//
//        } if (item.getItemId() == R.id.action_3_day_view) {
//            tv_month_view.setText("3 Day View");
//            //toolbar_toggle.setText("3 Day View");
//            //toolbar_toggle.setTextSize(20);
//            item.setVisible(false);
//            setVisible(R.id.action_week_view, R.id.action_month_view);
//
//            ll_activity_main.setVisibility(View.GONE);
//            ll_3d_and_week_view.setVisibility(View.VISIBLE);
//            //invalidateOptionsMenu();
//
//            if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
//                item.setChecked(!item.isChecked());
//                mWeekViewType = TYPE_THREE_DAY_VIEW;
//                mWeekView.setNumberOfVisibleDays(3);
//
//                // Lets change some dimensions to best fit the view.
//                mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
//                mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
//                mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
//            }
//            return true;
//
//        } if (item.getItemId() == R.id.action_week_view) {
//            tv_month_view.setText("Week View");
//            //toolbar_toggle.setText("Week View");
//            //toolbar_toggle.setTextSize(20);
//            item.setVisible(false);
//            setVisible(R.id.action_3_day_view, R.id.action_month_view);
//            ll_activity_main.setVisibility(View.GONE);
//            ll_3d_and_week_view.setVisibility(View.VISIBLE);
//            //invalidateOptionsMenu();
//
//            if (mWeekViewType != TYPE_WEEK_VIEW) {
//                item.setChecked(!item.isChecked());
//                mWeekViewType = TYPE_WEEK_VIEW;
//                mWeekView.setNumberOfVisibleDays(7);
//
//                // Lets change some dimensions to best fit the view.
//                mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
//                mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
//                mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
//            }
//            return true;
//
//        } if (item.getItemId() == R.id.action_month_view) {
//            if (ll_activity_main.isShown()) {
//               // ShowToast.showInfoMessage(this, "You are in month view", Toast.LENGTH_SHORT);
//                item.setVisible(false);
//            } else {
//                //tv_month_view.setVisibility(View.VISIBLE);
//                //toolbar_toggle.setTextSize(14);
//                toolbar_toggle.setText(DateFormatter.dateFormatter("MMM d, yyyy")
//                        .format(new Date(CalendarUtils.today())));
//                item.setVisible(false);
//                setVisible(R.id.action_3_day_view, R.id.action_week_view);
//                ll_activity_main.setVisibility(View.VISIBLE);
//                ll_3d_and_week_view.setVisibility(View.GONE);
//                //invalidateOptionsMenu();
//            }
//            tv_month_view.setText("Month View");
//            return true;
//        }
///*        if (item.getItemId() == R.id.action_today) {
//            if(ll_activity_main.isShown()){
//                mCoordinator.reset(CalendarUtils.today());
//            }
//            else{
//                mWeekView.goToToday();
//            }
//
//            return true;
//        }*/
//
//        //TODO to be removed
//        if (item.getItemId() == R.id.action_weather) {
//            mPendingWeatherEnabled = !mWeatherEnabled;
//            if (!mWeatherEnabled && !checkLocationPermissions()) {
//                requestLocationPermissions();
//            } else {
//                toggleWeather();
//            }
//            return true;
//
//        } if (item.getItemId() == R.id.action_week_start_saturday ||
//                item.getItemId() == R.id.action_week_start_sunday ||
//                item.getItemId() == R.id.action_week_start_monday) {
//            if (!item.isChecked()) {
//                changeWeekStart(item.getItemId());
//            }
//            return true;
//
//        } if (item.getItemId() == R.id.action_refresh_calendar) {
//            //refreshEvents();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    void setVisible(int item1, int item2) {
//        if (mMenu != null) {
//            MenuItem item_1 = mMenu.findItem(item1),
//                    item_2 = mMenu.findItem(item2);
//            if (item_1 != null && item_2 != null) {
//                item_1.setVisible(true);
//                item_2.setVisible(true);
//            }
//        }
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mCoordinator.saveState(outState);
//        //outState.putBoolean(STATE_TOOLBAR_TOGGLE, mToolbarToggle.isChecked());
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
////        if (mDrawerLayout.isDrawerOpen(mDrawer)) {
////            mDrawerLayout.closeDrawer(mDrawer);
////        } else {
//        super.onBackPressed();
//        //}
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
//                    fetchAllEvts();
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
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        switch (loader.getId()) {
//            case LOADER_CALENDARS:
//                if (data != null && data.moveToFirst()) {
//                    //mCalendarSelectionView.swapCursor(new CalendarCursor(data), mExcludedCalendarIds);
//                }
//                break;
//            case LOADER_LOCAL_CALENDAR:
//                if (data == null || data.getCount() == 0) {
//                    //createLocalCalendar();
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        //mCalendarSelectionView.swapCursor(null, null);
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
//        if(getSupportActionBar()!=null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//        }
//
//        ll_activity_main = (LinearLayout) findViewById(R.id.ll_activity_main);
//        ll_3d_and_week_view = (LinearLayout) findViewById(R.id.ll_3d_and_week_view);
//        mCoordinatorLayout = findViewById(R.id.coordinator_layout);
//        //mToolbarToggle = (TextView) findViewById(R.id.toolbar_toggle);
//        mCalendarView = (EventCalendarView) findViewById(R.id.calendar_view);
//        mAgendaView = ( AgendaView) findViewById(R.id.agenda_view);
//      //  mFabAdd = (FloatingActionButton) findViewById(R.id.fab);
//        mCalendarView.setVisibility(View.VISIBLE);
//        mFabAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createEvent();
//            }
//        });
//
//        //noinspection ConstantConditions
//        mFabAdd.hide();
//    }
//
//    @SuppressWarnings("ConstantConditions")
//    private void toggleEmptyView(boolean visible) {
//        if (visible) {
//            findViewById(R.id.empty).setVisibility(View.VISIBLE);
//            findViewById(R.id.empty).bringToFront();
////            findViewById(R.id.button_permission)
////                    .setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            requestCalendarPermissions();
////                        }
////                    });
//        } else {
//            findViewById(R.id.empty).setVisibility(View.GONE);
//        }
//    }
//
//    private void changeWeekStart(@IdRes int selection) {
//        switch (selection) {
//            case R.id.action_week_start_saturday:
//                CalendarUtils.sWeekStart = Calendar.SATURDAY;
//                break;
//            case R.id.action_week_start_sunday:
//                CalendarUtils.sWeekStart = Calendar.SUNDAY;
//                break;
//            case R.id.action_week_start_monday:
//                CalendarUtils.sWeekStart = Calendar.MONDAY;
//                break;
//        }
//        PreferenceManager.getDefaultSharedPreferences(this)
//                .edit()
//                .putInt(CalendarUtils.PREF_WEEK_START, CalendarUtils.sWeekStart)
//                .apply();
//        supportInvalidateOptionsMenu();
//        mCoordinator.reset(CalendarUtils.today());
//    }
//
//    private void createEvent() {
//        //startActivity(new Intent(this, EditActivity.class));
//        //startActivityForResult(new Intent(this, ActivityNewEvent.class),1);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode){
//            case 1:
//                if(resultCode==1){
//                    //refreshEvents();
//                }
//                break;
//        }
//    }
//
//    private void loadEvents() {
//        getSupportLoaderManager().initLoader(LOADER_CALENDARS, null, this);
//        getSupportLoaderManager().initLoader(LOADER_LOCAL_CALENDAR, null, this);
//        mFabAdd.show();
//        mCalendarView.setCalendarAdapter(new CalendarCursorAdapter(this, mExcludedCalendarIds));
//        mAgendaView.setAdapter(new AgendaCursorAdapter(this, mExcludedCalendarIds));
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
//
//        new CalendarQueryHandler(getContentResolver())
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
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
//
//    /**
//     * Coordinator utility that synchronizes widgets as selected date changes
//     */
//    static class Coordinator {
//
//        private static final String STATE_SELECTED_DATE = "state:selectedDate";
//
//        private final EventCalendarView.OnChangeListener mCalendarListener
//                = new EventCalendarView.OnChangeListener() {
//            @Override
//            public void onSelectedDayChange(long calendarDate) {
//                sync(calendarDate, mCalendarView);
//            }
//        };
//
//        private final AgendaView.OnDateChangeListener mAgendaListener
//                = new AgendaView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(long dayMillis) {
//                sync(dayMillis, mAgendaView);
//                //TODO any on agenda scroll event change to be done here
//            }
//        };
//
//        private TextView mTextView;
//        private EventCalendarView mCalendarView;
//        private AgendaView mAgendaView;
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
//            if (mSelectedDayMillis < 0) {
//                if (RoosterCalendarActivity.selected_date == 0) {
//                    mSelectedDayMillis = CalendarUtils.today();
//                } else {
//                    mSelectedDayMillis = selected_date;
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
//        void reset(long date) {
//            mSelectedDayMillis = date;
//            if (mCalendarView != null) {
//                mCalendarView.reset();
//            }
//            if (mAgendaView != null) {
//                mAgendaView.reset();
//            }
//            updateTitle(mSelectedDayMillis);
//        }
//
//        private void sync(long dayMillis, AgendaView originator) {
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
//            mTextView.setText(DateFormatter.dateFormatter("MMM d, yyyy").format(new Date(dayMillis)));
//        }
//    }
//
//    public static class AgendaCursorAdapter extends AgendaAdapter {
//
//        @VisibleForTesting
//        final DayEventsQueryHandler mHandler;
//
//        public AgendaCursorAdapter(Context context, Collection<String> excludedCalendarIds) {
//            super(context);
//            mHandler = new DayEventsQueryHandler(context.getContentResolver(), this,
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
//        private final MonthEventsQueryHandler mHandler;
//
//        public CalendarCursorAdapter(Context context, Collection<String> excludedCalendarIds) {
//            mHandler = new MonthEventsQueryHandler(context.getContentResolver(), this,
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
//        private final AgendaCursorAdapter mAgendaCursorAdapter;
//
//        public DayEventsQueryHandler(ContentResolver cr,
//                                     AgendaCursorAdapter agendaCursorAdapter,
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
//        private final CalendarCursorAdapter mAdapter;
//
//        public MonthEventsQueryHandler(ContentResolver cr,
//                                       CalendarCursorAdapter adapter,
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
//
//    //********************************* For 3 day and Week view ***************************************//
//
//
//    void init3DWeekView() {
//        // Get a reference for the week view in the layout.
//        mWeekView = (WeekView) findViewById(R.id.weekView);
//
//        // Show a toast message about the touched event.
//        mWeekView.setOnEventClickListener(this);
//
//        // The week view has infinite scrolling horizontally. We have to provide the events of a
//        // month every time the month changes on the week view.
//        mWeekView.setMonthChangeListener(RoosterCalendarActivity.this);
//
//        // Set long press listener for events.
//        mWeekView.setEventLongPressListener(this);
//
//        // Set long press listener for empty view
//        mWeekView.setEmptyViewLongPressListener(this);
//
//        // Set up a date time interpreter to interpret how the date and time will be formatted in
//        // the week view. This is optional.
//        setupDateTimeInterpreter(false);
//    }
//
//    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
//        List<WeekViewEvent> visible_events = new ArrayList<WeekViewEvent>();
//        if (events.size() > 0) {
//            for (WeekViewEvent event : events) {
//                if (eventMatches(event, newYear, newMonth)) {
//                    visible_events.add(event);
//                }
//            }
//        }
//        mToolbarToggle.setText(getMonth(newMonth) + " " + newYear);
//        return visible_events;
//    }
//
//    public String getMonth(int month) {
//        return new DateFormatSymbols().getMonths()[month-2];
//    }
//
//    /**
//     * Checks if an event falls into a specific year and month.
//     * @param event The event to check for.
//     * @param year The year.
//     * @param month The month.
//     * @return True if the event matches the year and month.
//     */
//    private boolean eventMatches(WeekViewEvent event, int year, int month) {
//        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1)
//                || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
//    }
//
//    /**
//     * Set up a date time interpreter which will show short date values when in week view and long
//     * date values otherwise.
//     * @param shortDate True if the date values should be short.
//     */
//
//    private void setupDateTimeInterpreter(final boolean shortDate) {
//        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
//            @Override
//            public String interpretDate(Calendar date) {
//                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
//                String weekday = weekdayNameFormat.format(date.getTime());
//                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());
//
//                // All android api level do not have a standard way of getting the first letter of
//                // the week day name. Hence we get the first char programmatically.
//                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
//                if (shortDate)
//                    weekday = String.valueOf(weekday.charAt(0));
//                return weekday.toUpperCase() + format.format(date.getTime());
//            }
//
//            @Override
//            public String interpretTime(int hour) {
//                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
//            }
//        });
//    }
//
//    @Override
//    public void onEventClick(WeekViewEvent event, RectF eventRect) {
//        //showEventInfo(context,event);
//    }
//
//    @Override
//    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
//       // showEventInfo(context,event);
//    }
//
//    @Override
//    public void onEmptyViewClicked(Calendar time) {
//        if(time.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()){
//           // ShowToast.showErrorMessage(context, getString(R.string.start_date_err), Toast.LENGTH_SHORT);
//        } else {
////            Intent intent = new Intent(RoosterCalendarActivity.this, ActivityNewEvent.class);
////            intent.putExtra(TIME, time.getTimeInMillis());
////            startActivityForResult(intent, 1);
//        }
//    }
//
//    @Override
//    public void onEmptyViewLongPress(Calendar time) {
//        if(time.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()){
//           // ShowToast.showErrorMessage(context, getString(R.string.start_date_err), Toast.LENGTH_SHORT);
//        } else {
//            //Intent intent = new Intent(RoosterCalendarActivity.this, ActivityNewEvent.class);
//            //intent.putExtra(TIME, time.getTimeInMillis());
//            //startActivityForResult(intent, 1);
//        }
//    }
//
//    /**
//     * Show more info about the event
//     */
////    private void showEventInfo(Context context, WeekViewEvent event){
////        DialogPlus dialog = DialogPlus.newDialog(context)
////                .setContentHolder(new ViewHolder(R.layout.layout_show_event))
////                .setExpanded(true, 400)  // This will enable the expand feature,
////                // (similar to android L share dialog)
////                .create();
////
////        View view_dialog = dialog.getHolderView();
////        String start_time = DateFormatter.dateFormatter(DATE_FORMAT_1).format(event.getStartTime().getTimeInMillis()) + " at " +
////                DateFormatter.dateFormatter(TIME_FORMAT_1).format(event.getStartTime().getTimeInMillis());
////        String end_time = DateFormatter.dateFormatter(DATE_FORMAT_1).format(event.getEndTime().getTimeInMillis()) + " at " +
////                DateFormatter.dateFormatter(TIME_FORMAT_1).format(event.getEndTime().getTimeInMillis());
////
////        TextView tv_name = view_dialog.findViewById(R.id.tv_name);
////        TextView tv_id = view_dialog.findViewById(R.id.tv_id);
////        TextView tv_start = view_dialog.findViewById(R.id.tv_start);
////        TextView tv_end = view_dialog.findViewById(R.id.tv_end);
////        TextView tv_location = view_dialog.findViewById(R.id.tv_location);
////        TextView tv_all_day = view_dialog.findViewById(R.id.tv_all_day);
////        TextView tv_calendar = view_dialog.findViewById(R.id.tv_calendar);
////
////        tv_name.setText(event.getName());
////        tv_id.setText(String.valueOf(event.getId()));
////        tv_start.setText(start_time);
////        tv_end.setText(end_time);
////        tv_location.setText("Location");
////        tv_all_day.setText(event.getLocation());
////        tv_calendar.setText("HRMS");
////
////        dialog.show();
////    }
//
//    public WeekView getWeekView() {
//        return mWeekView;
//    }
//
//    /**
//     * Fetch all events within five years after and before
//     */
//    private void fetchAllEvts() {
//        int year = Calendar.getInstance().get(Calendar.YEAR);
//        String[] projection = new String[]{CalendarContract.Events.CALENDAR_ID,
//                CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION,
//                CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND,
//                CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION};
//
//        // 0 = January, 1 = February, ...
//        Calendar startTime = Calendar.getInstance();
//        startTime.set(year - 5, 00, 01, 00, 00);
//
//        Calendar endTime = Calendar.getInstance();
//        endTime.set(year + 5, 00, 01, 00, 00);
//
//        // the range is all data five years after and before
//        String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + startTime.getTimeInMillis() + " ) " +
//                "AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() + " ))";
//
//        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) |
//                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR)) ==
//                PackageManager.PERMISSION_GRANTED) {
//            Cursor cursor = this.getBaseContext().getContentResolver().query(CalendarContract.Events.CONTENT_URI,
//                    projection, selection, null, null);
//
//            // output the events
//            if(cursor != null && cursor.getCount()>0) {
//                while (cursor.moveToNext()) {
//                    WeekViewEvent event = new WeekViewEvent();
//
//                    Calendar start_calendar = Calendar.getInstance();
//                    Calendar end_calendar = Calendar.getInstance();
//
//                    start_calendar.setTimeInMillis(cursor.getLong(3));
//                    end_calendar.setTimeInMillis(cursor.getLong(4));
//
//                    event.setName(cursor.getString(1));
//                    event.setId((long) cursor.getPosition());
//                    event.setStartTime(start_calendar);
//                    event.setEndTime(end_calendar);
//                    event.setColor(randomColor(context));
//                    events.add(event);
//                }
//            } if (cursor != null) {
//                cursor.close();
//            }
//        } else {
//            explainCalendarPermissions();
//        }
//    }
//
//    /**
//     * Request calendar permissions
//     */
//    private void explainCalendarPermissions() {
//        Snackbar.make(mCoordinatorLayout, R.string.calendar_permission_required,
//                Snackbar.LENGTH_LONG)
//                .setAction(R.string.grant_access, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        requestCalendarPermissions();
//                    }
//                })
//                .show();
//    }
//
//    /**
//     * Refresh leaves
//     */
////    private void refreshEvents(){
////        final String TAG = "refreshEvents";
////        try {
////            ShowToast.showInfoMessage(context,"Refreshing events...", Toast.LENGTH_SHORT);
////            final HRMSDBHandler dbHandler = new HRMSDBHandler(context);
////            String token_value = readAuthToken(context, AUTH_TOKEN, "null");
////            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
////                    LIST_EVENT_API + TOKEN_KEY + token_value, null,
////                    new Response.Listener<JSONObject>() {
////                        @Override
////                        public void onResponse(JSONObject response) {
////                            try {
////                                if (response.getString("success").equals("1")
////                                        && response.has("data") && dbHandler.refreshEvents()>0) {
////                                    JSONArray eventsArray = response.getJSONArray("data");
////                                    for (int i = 0; i < eventsArray.length(); i++) {
////                                        JSONObject eventsObject = eventsArray.getJSONObject(i);
////                                        Events event = new Events();
////                                        event.setEvent_id(eventsObject.getString("event_id"));
////                                        event.setEvent_title(eventsObject.getString("event_title"));
////                                        event.setStart_time(eventsObject.getString("start_time"));
////                                        event.setEnd_time(eventsObject.getString("end_time"));
////                                        event.setVenue(eventsObject.getString("venue"));
////                                        event.setVenue(eventsObject.getString("event_type"));
////                                        event.setColor_code(eventsObject.getString("color_code"));
////                                        event.setCreated_date(eventsObject.getString("created_date"));
////                                        event.setCreated_by(eventsObject.getString("created_by"));
////                                        //Log.e(TAG, "update event: " + dbHandler.insertEvent(event));
////
////                                        if(i==(eventsArray.length()-1)) {
////                                            deleteLocalCalendar();
////                                        }
////                                    }
////                                } else {
////                                    if(srl_evt_cal_view.isRefreshing())
////                                        srl_evt_cal_view.setRefreshing(false);
////                                    Toast.makeText(RoosterCalendarActivity.this, "No record was found.", Toast.LENGTH_SHORT).show();
////                                }
////
////                            } catch (JSONException e) {
////                                showBasicBuilder("Request encountered a technical error.");
////                                Log.e(TAG, "JSONException: "+ e);
////                            }
////                        }
////                    },
////                    new Response.ErrorListener() {
////                        @Override
////                        public void onErrorResponse(VolleyError error) {
////                            String msg = "Unable to establish internet connectivity.";
////                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
////                                Log.e(TAG, "TimeoutError: " + error);
////                                msg = "Request timeout.";
////                            } else if (error instanceof AuthFailureError) {
////                                Log.e(TAG, "AuthFailureError: " + error);
////                                msg = "Unable to authenticate request.";
////                            } else if (error instanceof ServerError) {
////                                Log.e(TAG, "ServerError: " + error);
////                                msg = "Request encountered a technical error.";
////                            } else if (error instanceof NetworkError) {
////                                Log.e(TAG, "NetworkError: " + error);
////                                msg = "Unable to establish network.";
////                            } else if (error instanceof ParseError) {
////                                Log.e(TAG, "ParseError: " + error);
////                                msg = "Unable to fetch content.";
////                            }
////                            showBasicBuilder(msg);
////                        }
////                    });
////
////            request.setRetryPolicy(getRetryPolicy());
////            MySingleton.getInstance(this).addToRequestQueue(request);
////        } catch (Exception error) {
////            showBasicBuilder("Unable to complete request.");
////            Log.e(TAG, "Exception: "+ error);
////        }
////    }
//
//    /**
//     * Show a general dialog message
//     * @param msg the message to be shown
//     */
//    private void showBasicBuilder(String msg) {
//        if(srl_evt_cal_view.isRefreshing())
//            srl_evt_cal_view.setRefreshing(false);
//        builder.setMessage(msg)
//                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        //refreshEvents();
//                    }
//                })
//                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                })
//                .show();
//    }
//
//    /**
//     * Delete local created calendar
//     */
//    private void deleteLocalCalendar() {
//        try {
//            Uri calUri = CalendarContract.Calendars.CONTENT_URI;
//            calUri = calUri.buildUpon()
//                    .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
//                    .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, BuildConfig.APPLICATION_ID)
//                    .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
//                    .build();
//            createInsertEvent.startDelete(0, 0, calUri, null, null);
//        } catch (Exception e){
//            if(srl_evt_cal_view.isRefreshing())
//                srl_evt_cal_view.setRefreshing(false);
//            Toast.makeText(context, "Unable to refresh events.", Toast.LENGTH_SHORT).show();
//            Log.e("deleteLocalCalendar", "" + e);
//        }
//    }
//
//    private class InsertEvent extends AsyncQueryHandler {
//
//        Context context;
//        InsertEvent(ContentResolver cr, Context context) {
//            super(cr);
//            this.context = context;
//        }
//
//        @Override
//        protected void onInsertComplete(int token, Object cookie, Uri uri) {
//            super.onInsertComplete(token, cookie, uri);
//            if(token == -2){
//                fetchAllCalendars();
//            } if(token == flag){
//                if(srl_evt_cal_view.isRefreshing())
//                    srl_evt_cal_view.setRefreshing(false);
//                Toast.makeText(context, "Events refreshed.", Toast.LENGTH_SHORT).show();
//                //refreshActivity();
//            }
//        }
//
//        @Override
//        protected void onDeleteComplete(int token, Object cookie, int result) {
//            super.onDeleteComplete(token, cookie, result);
//            if(token == 0) {
//                createNewLocalCalendar();
//            }
//        }
//    }
//
//    /**
//     * Create new local calendar for app's events
//     */
//    public void createNewLocalCalendar() {
//        Uri calUri = CalendarContract.Calendars.CONTENT_URI;
//        ContentValues cv = new ContentValues();
//        cv.put(CalendarContract.Calendars.ACCOUNT_NAME, BuildConfig.APPLICATION_ID);
//        cv.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
//        cv.put(CalendarContract.Calendars.NAME, getString(R.string.hrms_calendar));
//        cv.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, getString(R.string.hrms_calendar));
//        cv.put(CalendarContract.Calendars.CALENDAR_COLOR, R.color.colorAccent);
//        cv.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
//        cv.put(CalendarContract.Calendars.OWNER_ACCOUNT, BuildConfig.APPLICATION_ID);
//        cv.put(CalendarContract.Calendars.VISIBLE, 1);
//        cv.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
//
//        calUri = calUri.buildUpon()
//                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
//                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, BuildConfig.APPLICATION_ID)
//                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
//                .build();
//        createInsertEvent.startInsert(-2, 0,calUri,cv);
//    }
//
//    /**
//     * Checks whether the app's calendar is available
//     */
//    private void fetchAllCalendars(){
//        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) |
//                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR)) ==
//                PackageManager.PERMISSION_GRANTED) {
//            Uri calendarsUri = CalendarContract.Calendars.CONTENT_URI;
//            ContentResolver contentResolver = getContentResolver();
//
//            // Fetch a list of all calendars synced with the device, their display names and whether the
//            // user has them selected for display.
//            final Cursor cursor = contentResolver.query(calendarsUri,
//                    (new String[]{"_id", CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CalendarContract.Calendars.VISIBLE}), null, null, null);
//
//            if(cursor != null){
//                if(cursor.getCount()>0) {
//                    while (cursor.moveToNext()) {
//                        if (cursor.getString(1).equals(getString(R.string.hrms_calendar))) {
//                            //insertEvents(cursor.getLong(0), context);
//                            break;
//                        }
//                    }
//                }
//                cursor.close();
//            }
//        }else {
//            explainCalendarPermissions();
//        }
//    }
//
//    /**
//     * App calendar is available, insert events
//     * @param calendar_id the id of the app's calendar
//     * @param context the context
//     */
////    private void insertEvents(long calendar_id, Context context){
////        try {
////            HRMSDBHandler handler = new HRMSDBHandler(context);
////            ArrayList<Events> events = new ArrayList<>();
////            events.addAll(handler.getEvent());
////            if (events.size() > 0) {
////                flag = events.size();
////                int i = 0;
////                for (Events event : events) {
////                    i++;
////                    ContentValues cv = new ContentValues();
////                    String description = "Was created on " + event.getCreated_date() + " by " + event.getCreated_by();
////                    //cv.put(CalendarContract.Events.SYNC_DATA1, event.getEvent_id());
////                    Date start_date = DateFormatter.dateFormatter(DATE_FORMAT_6).parse(event.getStart_time());
////                    Date end_date = DateFormatter.dateFormatter(DATE_FORMAT_6).parse(event.getEnd_time());
////
////                    cv.put(CalendarContract.Events.TITLE, event.getEvent_title());
////                    cv.put(CalendarContract.Events.DESCRIPTION, description);
////                    cv.put(CalendarContract.Events.DTSTART, start_date.getTime());
////                    cv.put(CalendarContract.Events.DTEND, end_date.getTime());
////                    cv.put(CalendarContract.Events.ALL_DAY, false);
////                    cv.put(CalendarContract.Events.EVENT_END_TIMEZONE, Utils.getTimeZone(false));
////                    cv.put(CalendarContract.Events.EVENT_TIMEZONE, Utils.getTimeZone(false));
////                    cv.put(CalendarContract.Events.EVENT_LOCATION, event.getVenue());
////                    cv.put(CalendarContract.Events.CALENDAR_ID, calendar_id);
////
////                    if(i == flag) {
////                        createInsertEvent.startInsert(flag, 0, CalendarContract.Events.CONTENT_URI, cv);
////                    } else
////                        createInsertEvent.startInsert(-1, 0, CalendarContract.Events.CONTENT_URI, cv);
////                }
////            }
////        } catch (Exception e){
////            Log.e("insertEvents", "" + e);
////        }
////    }
//
//    /**
//     * Refresh this activity
//     */
//    private void refreshActivity(){
//        Intent refresh = new Intent(getApplicationContext(), RoosterCalendarActivity.class);
//        refresh.putExtra(SELECTED_DATE, selected_date);
//        startActivity(refresh);//Start the same Activity
//        finish(); //finish Activity.
//    }
//}
