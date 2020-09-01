package stlhorizon.org.hrmselfservice.activities.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
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

import stlhorizon.org.hrmselfservice.R;
import stlhorizon.org.hrmselfservice.model.events.EventModel;

public class EventItemActivity extends AppCompatActivity {
    private ImageView back, deleteevent,editevent;
    private String str, string;
    private TextView ename, edesc, estartdate, eenddate, estarttime, eendtime, evenue, etype,eid;
    private Button bookevent;
    private int mYear, mMonth, mDay;
    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventitem);


        //instantiation
        back = findViewById(R.id.back);
        ename = findViewById(R.id.txtEventItemName);
        estartdate = findViewById(R.id.txtEventStartDate);
        eenddate = findViewById(R.id.txtEventEndDate);
        etype = findViewById(R.id.txtEventType);
        evenue = findViewById(R.id.txtEventVenue);

        //redirect to news in page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //redirect to calender activity
                    Intent it = new Intent(getApplicationContext(), CalendarActivity.class);
                    startActivity(it);
                    //overridePendingTransition(R.anim.slide_in_left, R.anim.nothing);
                    finish();


            }
        });



        str = getIntent().getExtras().getString("DETAIL");
        if (str != null) {
            loadPageElements();
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }




    public void loadPageElements() {

        EventModel eventModel = EventModel.createEventModelFrom(str);

        ename.setText(eventModel.getEvent_title());
        etype.setText( eventModel.getEvent_type());
        evenue.setText(eventModel.getVenue());

        //convert date to more human readable format
        String ds1 = eventModel.getStart_time();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        String ds2 = null;
        try {
            ds2 = sdf2.format(sdf1.parse(ds1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //esstartdate.setText("Start date: " + ds2);
        //convert date to more human readable format
        String ds3 = eventModel.getEnd_time();
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf4 = new SimpleDateFormat("dd-MM-yyyy");
        String ds4 = null;
        try {
            ds4 = sdf4.format(sdf3.parse(ds3));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //eenddate.setText("End date: " + ds4);

        estartdate.setText(ds2);
        eenddate.setText(ds4);



    }

}
