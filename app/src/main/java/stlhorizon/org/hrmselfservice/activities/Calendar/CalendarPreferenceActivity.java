//package stlhorizon.org.hrmselfservice.activities.calendar;
//
//import android.content.AsyncQueryHandler;
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.provider.CalendarContract;
//import android.support.annotation.Nullable;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.CursorLoader;
//import android.support.v4.content.Loader;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.MenuItem;
//
//import java.util.HashSet;
//
//import stlhorizon.org.hrmselfservice.BuildConfig;
//import stlhorizon.org.hrmselfservice.R;
//import stlhorizon.org.hrmselfservice.content.CalendarCursor;
//import stlhorizon.org.hrmselfservice.widget.CalendarSelectionView;
//
//
//public class CalendarPreferenceActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
//    private static final int LOADER_CALENDARS = 0;
//    private static final int LOADER_LOCAL_CALENDAR = 1;
//    private CalendarSelectionView mCalendarSelectionView;
//    private final HashSet<String> mExcludedCalendarIds = new HashSet<>();
//
//    Toolbar mToolbar;
//    private final CalendarSelectionView.OnSelectionChangeListener mCalendarSelectionListener
//            = new CalendarSelectionView.OnSelectionChangeListener() {
//        @Override
//        public void onSelectionChange(long id, boolean enabled) {
//            if (!enabled) {
//                mExcludedCalendarIds.add(String.valueOf(id));
//            } else {
//                mExcludedCalendarIds.remove(String.valueOf(id));
//            }
//            //mCalendarView.invalidateData();
//            //mAgendaView.invalidateData();
//        }
//    };
//
//    @Override
//    protected void onCreate(@Nullable final Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_calendar_preferences);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
//        ab.setDisplayShowHomeEnabled(true);
//        mCalendarSelectionView = (CalendarSelectionView) findViewById(R.id.list_view_calendar);
//        mCalendarSelectionView.setOnSelectionChangeListener(mCalendarSelectionListener);
//
//        getSupportLoaderManager().initLoader(LOADER_CALENDARS, null, this);
//        getSupportLoaderManager().initLoader(LOADER_LOCAL_CALENDAR, null, this);
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
//                    mCalendarSelectionView.swapCursor(new CalendarCursor(data), mExcludedCalendarIds);
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
//        mCalendarSelectionView.swapCursor(null, null);
//
//    }
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
//    static class CalendarQueryHandler extends AsyncQueryHandler {
//
//        public CalendarQueryHandler(ContentResolver cr) {
//            super(cr);
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id==android.R.id.home){
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//
//    }
//}
