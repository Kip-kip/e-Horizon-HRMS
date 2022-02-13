//package stlhorizon.org.hrmselfservice.activities.calendar;
//
//import android.app.AlertDialog;
//import android.app.DatePickerDialog;
//import android.app.ProgressDialog;
//import android.app.TimePickerDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.ContextThemeWrapper;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.Calendar;
//import java.util.Date;
//
//import stlhorizon.org.hrmselfservice.R;
//import stlhorizon.org.hrmselfservice.utils.DateFormatter;
//
///**
// * Created by makari on 4/30/2018.
// */
//
//public class ActivityNewEvent extends AppCompatActivity implements View.OnClickListener,
//        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
//
//    private EditText et_title, et_venue;
//
//    private LinearLayout ll_start_date,
//            ll_start_date1,
//            ll_start_time,
//            ll_end_date,
//            ll_end_date1,
//            ll_end_time,
//            ll_pick_color,
//            ll_pick_color1;
//
//    private TextView tv_start_date,
//            tv_start_time,
//            tv_end_date,
//            tv_end_time,
//            tv_pick_color;
//
//    private Calendar calendar;
//    private FloatingActionButton fab;
//    private DatePickerDialog datePickerDialog;
//    private TimePickerDialog timePickerDialog;
//    private Context context;
//
//    private Calendar start;
//    private Calendar end;
//    private boolean is_end;
//    private TextView view;
//
//    private AlertDialog.Builder builder;
//    private ProgressDialog pDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new_event);
//
//        initComponents();
//
//        if (savedInstanceState != null) {
//            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
//            if (dpd != null) {
//                dpd.setOnDateSetListener(this);
//            }
//
//            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
//            if (tpd != null) {
//                tpd.setOnTimeSetListener(this);
//            }
//        }
//    }
//
//
//
//    /**
//     * Initialise views, variables and objects
//     */
//    private void initComponents(){
//        context = this;
//
//        et_title = findViewById(R.id.et_title);
//        et_venue = findViewById(R.id.et_venue);
//
//        ll_start_date = findViewById(R.id.ll_start_date);
//        ll_start_date1 = findViewById(R.id.ll_start_date1);
//        ll_start_time = findViewById(R.id.ll_start_time);
//        ll_end_date1 = findViewById(R.id.ll_end_date1);
//        ll_end_date = findViewById(R.id.ll_end_date);
//        ll_end_time = findViewById(R.id.ll_end_time);
//        ll_pick_color = findViewById(R.id.ll_pick_color);
//        ll_pick_color1 = findViewById(R.id.ll_pick_color1);
//
//        tv_start_date = findViewById(R.id.tv_start_date);
//        tv_start_time = findViewById(R.id.tv_start_time);
//        tv_end_date = findViewById(R.id.tv_end_date);
//        tv_end_time = findViewById(R.id.tv_end_time);
//        tv_pick_color = findViewById(R.id.tv_pick_color);
//
//        fab = findViewById(R.id.fab);
//        calendar = Calendar.getInstance();
//        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
//        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY),
//                calendar.get(Calendar.MINUTE), false, false);
//
//        start = Calendar.getInstance();
//        end = Calendar.getInstance();
//        is_end = false;
//
//        pDialog = new ProgressDialog(ActivityNewEvent.this);
//        builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MyCheckBox));
//
//        addEvts();
//    }
//
//    /**
//     * Add events to views
//     */
//    private void addEvts(){
//        ll_start_date.setOnClickListener(this);
//        ll_start_date1.setOnClickListener(this);
//        ll_start_time.setOnClickListener(this);
//        ll_end_date.setOnClickListener(this);
//        ll_end_date1.setOnClickListener(this);
//        ll_end_time.setOnClickListener(this);
//        ll_pick_color.setOnClickListener(this);
//        ll_pick_color1.setOnClickListener(this);
//
//        fab.setOnClickListener(this);
//
//        pDialog.setMessage("Please wait...");
//        getIntentData();
//    }
//
//    // get data from the intent
//    private void getIntentData(){
//        Intent intent = getIntent();
//        if(intent!=null){
//            if(intent.hasExtra(TIME) && intent.getLongExtra(TIME, 0)>0) {
//                //start_date = intent.getLongExtra("time", 0);
//                long time = intent.getLongExtra(TIME, 0);
//                start.setTimeInMillis(intent.getLongExtra(TIME, 0));
//                tv_start_date.setText(DateFormatter.dateFormatter(DATE_FORMAT_1).format(new Date(time)));
//                tv_start_time.setText(DateFormatter.dateFormatter(TIME_FORMAT_1).format(new Date(time)));
//            }
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(v==ll_start_date || v==ll_start_date1){
//            is_end = false;
//            view = tv_start_date;
//            view.setError(null);
//            showDatePicker();
//
//        } if(v==ll_start_time){
//            is_end = false;
//            view = tv_start_time;
//            view.setError(null);
//            showTimePicker();
//
//        } if(v==ll_end_date || v==ll_end_date1){
//            is_end = true;
//            view = tv_end_date;
//            view.setError(null);
//            showDatePicker();
//
//        } if(v==ll_end_time){
//            is_end = true;
//            view = tv_end_time;
//            view.setError(null);
//            showTimePicker();
//
//        } if(v==ll_pick_color || v==ll_pick_color1) {
//            showColorPicker();
//
//        } if(v==fab){
//            if(validateForm()){
//                submitEvent();
//            }
//        }
//
//    }
//
//    // show date picker
//    private void showDatePicker() {
//        int current_year = Calendar.getInstance().get(Calendar.YEAR);
//        datePickerDialog.setVibrate(false);
//        datePickerDialog.setYearRange(current_year-30, current_year+18);
//        datePickerDialog.setCloseOnSingleTapDay(true);
//        datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
//    }
//
//    // show time picker
//    private void showTimePicker() {
//        timePickerDialog.setVibrate(false);
//        timePickerDialog.setCloseOnSingleTapMinute(false);
//        timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
//    }
//
//    // show color picker
//    private void showColorPicker(){
//        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, 0xff21425d, new AmbilWarnaDialog.OnAmbilWarnaListener() {
//            @Override
//            public void onOk(AmbilWarnaDialog dialog, int color) {
//                // color is the color selected by the user.
//                tv_pick_color.setBackgroundColor(color);
//                String hexColor = String.format("#%06X", (0xFFFFFF & color));
//                tv_pick_color.setError(null);
//                tv_pick_color.setText(hexColor);
//            }
//
//            @Override
//            public void onCancel(AmbilWarnaDialog dialog) {
//                // cancel was selected by the user
//            }
//        });
//        dialog.show();
//    }
//
//    @Override
//    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
//        if(is_end){
//            end.set(year, month, day);
//            validateDateForm(end);
//            return;
//        }
//        start.set(year, month, day);
//        validateDateForm(start);
//    }
//
//    @Override
//    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//        if(is_end){
//            end.set(Calendar.HOUR_OF_DAY, hourOfDay);
//            end.set(Calendar.MINUTE, minute);
//            validateDateForm(end);
//            return;
//        }
//        start.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        start.set(Calendar.MINUTE, minute);
//        validateDateForm(start);
//    }
//
//    /**
//     * Validate date and time once set
//     */
//    private void validateDateForm(Calendar calendar) {
//        if(view == tv_start_date || view == tv_end_date){
//            if (view == tv_start_date && start.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
//                ShowToast.showErrorMessage(context, getString(R.string.start_date_err), Toast.LENGTH_SHORT);
//            } else if(view == tv_end_date && end.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()){
//                ShowToast.showErrorMessage(context, getString(R.string.end_date_err), Toast.LENGTH_SHORT);
//            } else {
//                view.setText(DateFormatter.dateFormatter(DATE_FORMAT_1).format(calendar.getTime()));
//                tv_start_date.setError(null);
//                tv_end_date.setError(null);
//                validateDateAndTime();
//            }
//        } else {
//            if(view == tv_start_time && start.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()){
//                ShowToast.showErrorMessage(context, getString(R.string.start_time_err), Toast.LENGTH_SHORT);
//            } else if(view == tv_end_time && end.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()){
//                ShowToast.showErrorMessage(context, getString(R.string.end_time_err), Toast.LENGTH_SHORT);
//            }  else {
//                view.setText(DateFormatter.dateFormatter(TIME_FORMAT_1).format(calendar.getTime()));
//                tv_start_time.setError(null);
//                tv_end_time.setError(null);
//                validateDateAndTime();
//            }
//        }
//    }
//
//    /**
//     * Again validate date and time once set
//     * @return true if valid, else false
//     */
//    private boolean validateDateAndTime(){
//        if(view==tv_start_date){
//            if(!tv_start_time.getText().toString().equals(getString(R.string.time)) &&
//                    !tv_end_date.getText().toString().equals(getString(R.string.date)) &&
//                    !tv_end_time.getText().toString().equals(getString(R.string.time))){
//                if(start.getTimeInMillis() > end.getTimeInMillis()){
//                    showErrorMessage(getString(R.string.start_date_err1), tv_start_date, null);
//                    return false;
//                } else
//                    setDuration();
//            }
//        } if(view==tv_start_time){
//            if(!tv_start_date.getText().toString().equals(getString(R.string.date)) &&
//                    !tv_end_date.getText().toString().equals(getString(R.string.date)) &&
//                    !tv_end_time.getText().toString().equals(getString(R.string.time))){
//                if(start.getTimeInMillis() > end.getTimeInMillis()){
//                    showErrorMessage(getString(R.string.start_time_err1), tv_start_time, null);
//                    return false;
//                } else
//                    setDuration();
//            }
//        } if(view==tv_end_date){
//            if(!tv_start_time.getText().toString().equals(getString(R.string.time)) &&
//                    !tv_start_date.getText().toString().equals(getString(R.string.date))){
//                if(start.getTimeInMillis() > end.getTimeInMillis()){
//                    showErrorMessage(getString(R.string.end_date_err1), tv_end_date, null);
//                    return false;
//                } else
//                    setDuration();
//            }
//        } if(view==tv_end_time){
//            if(!tv_start_date.getText().toString().equals(getString(R.string.date)) &&
//                    !tv_end_date.getText().toString().equals(getString(R.string.date)) &&
//                    !tv_start_time.getText().toString().equals(getString(R.string.time))){
//                if(start.getTimeInMillis() > end.getTimeInMillis()){
//                    showErrorMessage(getString(R.string.end_time_err1), tv_end_time, null);
//                    return false;
//                } else
//                    setDuration();
//            }
//        }
//        return true;
//    }
//
//    /**
//     * Validate the whole form
//     */
//    private boolean validateForm() {
//        if (et_title.getText().toString().equals("")) {
//            et_title.requestFocus();
//            et_title.setError("Enter event's title.");
//            return false;
//        } if (tv_start_date.getText().toString().equals(getString(R.string.date))) {
//            showErrorMessage(getString(R.string.start_date_err2), tv_start_date, null);
//            return false;
//        } if (tv_start_time.getText().toString().equals(getString(R.string.time))) {
//            showErrorMessage(getString(R.string.start_time_err2), tv_start_time, null);
//            return false;
//        } if (tv_end_date.getText().toString().equals(getString(R.string.date))) {
//            showErrorMessage(getString(R.string.end_date_err2), tv_end_date, null);
//            return false;
//        } if (tv_end_time.getText().toString().equals(getString(R.string.time))) {
//            showErrorMessage(getString(R.string.end_time_err2), tv_end_time, null);
//            return false;
//        } if (et_venue.getText().toString().equals("")) {
//            et_venue.setError("Enter event's venue.");
//            et_venue.requestFocus();
//            return false;
//        } if (tv_pick_color.getText().toString().equals(getString(R.string.pick_color))) {
//            showErrorMessage("Pick color", tv_pick_color, null);
//            return false;
//        }
//        return validateDateAndTime();
//    }
//
//    /**
//     * Set TextView in Secondary Unit spinner to be in error then shake control it
//     * @param message the error message to show
//     * @param tv_error the view to show @param message
//     * @param spinner the spinner with error
//     */
//    public void showErrorMessage(String message, TextView tv_error, Spinner spinner) {
//        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_error);
//        if(spinner != null) {
//            TextView tvListItem = (TextView)spinner.getSelectedView();
//            tvListItem.setError(message);
//            tvListItem.requestFocus();
//
//            // Shake the spinner to highlight that current selection is invalid
//            spinner.startAnimation(shake);
//
//            tv_error.requestFocus();
//            tv_error.setError(message);
//        } else {
//            tv_error.requestFocus();
//            tv_error.setError(message);
//            tv_error.startAnimation(shake);
//        }
//    }
//
//    /**
//     * Set duaration
//     */
//    private void setDuration(){
//        TextView tv_duration = (TextView) findViewById(R.id.tv_duration);
//        tv_duration.setText(getDuration(start.getTimeInMillis(), end.getTimeInMillis()));
//    }
//
//    /**
//     * Submit an event
//     */
//    private void submitEvent(){
//        final String TAG = "submitEvent";
//        if (!pDialog.isShowing())
//            pDialog.show();
//        try {
//            String token_value = readAuthToken(context, AUTH_TOKEN, "null");
//            if(token_value.equals("null"))
//                return;
//            JSONObject paramData = new JSONObject();
//            paramData.put(CE_EVENT_TITLE,et_title.getText().toString());
//            paramData.put(CE_START_TIME,DateFormatter.dateFormatter(DATE_FORMAT_6).format(start.getTime()));
//            paramData.put(CE_END_TIME,DateFormatter.dateFormatter(DATE_FORMAT_6).format(end.getTime()));
//            paramData.put(CE_CREATED_DATE,DateFormatter.dateFormatter(DATE_FORMAT_5).format(new Date()));
//            paramData.put(CE_VENUE,et_venue.getText().toString());
//            paramData.put(CE_EVENT_TYPE,et_title.getText().toString());
//            paramData.put(CE_COLOR_CODE,tv_pick_color.getText().toString());
//
//            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
//                    CREATE_EVENT_API + TOKEN_KEY + token_value, paramData,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                if (response.getString("success").equals("1") ) {
//                                    if (pDialog.isShowing())
//                                        pDialog.dismiss();
//                                    ShowToast.showSuccessMessage(context,
//                                            response.getString("message"), Toast.LENGTH_SHORT);
//                                    Intent intent = new Intent();
//                                    setResult(1,intent);// event submitted
//                                    finish();
//                                } else {
//                                    if (pDialog.isShowing())
//                                        pDialog.dismiss();
//                                    showGeneralDialog(context,response.getString("title"),
//                                            response.getString("message"));
//                                }
//                            } catch (JSONException e) {
//                                showBasicBuilder("Request encountered a technical error.");
//                                Log.e(TAG, "JSONException: "+ e);
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            String msg = "Unable to establish internet connectivity.";
//                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                                Log.e(TAG, "TimeoutError: " + error);
//                                msg = "Request timeout.";
//                            } else if (error instanceof AuthFailureError) {
//                                Log.e(TAG, "AuthFailureError: " + error);
//                                msg = "Unable to authenticate request.";
//                            } else if (error instanceof ServerError) {
//                                Log.e(TAG, "ServerError: " + error);
//                                msg = "Server not responding.";
//                            } else if (error instanceof NetworkError) {
//                                Log.e(TAG, "NetworkError: " + error);
//                                msg = "Unable to establish network.";
//                            } else if (error instanceof ParseError) {
//                                Log.e(TAG, "ParseError: " + error);
//                                msg = "Unable to fetch content.";
//                            }
//                            showBasicBuilder(msg);
//                        }
//                    });
//
//            request.setRetryPolicy(getRetryPolicy());
//            MySingleton.getInstance(this).addToRequestQueue(request);
//        } catch (Exception error) {
//            showBasicBuilder("Unable to complete request.");
//            Log.e(TAG, "Exception: "+ error);
//        }
//    }
//
//    /**
//     * Show a general dialog message
//     * @param msg the message to be shown
//     */
//    private void showBasicBuilder(String msg) {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//        builder.setMessage(msg)
//                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        if (!pDialog.isShowing())
//                            pDialog.show();
//                        submitEvent();
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
//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent();
//        setResult(0,intent);// user pressed back
//        super.onBackPressed();
//    }
//}
