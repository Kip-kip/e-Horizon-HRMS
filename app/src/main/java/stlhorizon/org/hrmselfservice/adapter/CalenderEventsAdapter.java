package stlhorizon.org.hrmselfservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import stlhorizon.org.hrmselfservice.R;
import stlhorizon.org.hrmselfservice.model.events.EventModel;

public class CalenderEventsAdapter extends RecyclerView.Adapter<CalenderEventsAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<EventModel> dataModelArrayListCalenderEvents;
    Context context;


    public CalenderEventsAdapter(Context ctx, List<EventModel> dataModelArrayList){
        this.context=ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayListCalenderEvents = dataModelArrayList;
    }

    @Override
    public CalenderEventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.calender_event_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CalenderEventsAdapter.MyViewHolder holder, final int position) {
        final EventModel eventModel= dataModelArrayListCalenderEvents.get(position);
        Picasso.get().load(R.drawable.ava);
        holder.txtEventName.setText(eventModel.getEvent_title());
        holder.txtEventDesc.setText(eventModel.getEvent_type());
        //convet date to more human readable format
        String ds1 = eventModel.getStart_time();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        String ds2 = null;
        try {
            ds2 = sdf2.format(sdf1.parse(ds1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txtEventDateTime.setText(ds2);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent intent = new Intent(CalenderEventsAdapter.this.context, EventItemActivity.class);
//
//                intent.putExtra("DETAIL",eventModel.toString());
//                //indicate where next activity is entered from
//                intent.putExtra("INDICATOR","Calender");
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);

                //DISLAY SNACK BAR

//                Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
//                snackbar.setText("Name: "+eventModel.getE_name()+"\n"+"Description: "+eventModel.getE_description()+"\n"+"Start: "+eventModel.getS_time()+"\n"+"End: "+eventModel.getE_time());
//                View snackbarView = snackbar.getView();
//                TextView snackTextView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                snackTextView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//                snackTextView.setMaxLines(10);
//                //snackbarView.setMinimumHeight(300);
//                snackbar.show();


            }
        });


    }

    @Override
    public int getItemCount() {
        return dataModelArrayListCalenderEvents.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtEventName, txtEventDesc, txtEventDateTime;


        public MyViewHolder(View itemView) {
            super(itemView);

            txtEventName = itemView.findViewById(R.id.txtEventName);
            txtEventDesc = itemView.findViewById(R.id.txtEventDesc);
            txtEventDateTime = itemView.findViewById(R.id.txtEventDateTime);


        }

    }
}