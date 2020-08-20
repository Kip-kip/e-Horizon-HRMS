package stlhorizon.org.hrmselfservice.customviews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applikeysolutions.cosmocalendar.listeners.OnMonthChangeListener;
import com.applikeysolutions.cosmocalendar.model.Month;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.util.ArrayList;
import java.util.List;

import stlhorizon.org.hrmselfservice.activities.Interfaces.SelectedDayListener;


public class CustomCalenderView extends CalendarView {
    List<SelectedDayListener> selectedDayListenerList= new ArrayList<>();
    List<OnMonthChangeListener> onMonthChangeListeners= new ArrayList<>();

    public CustomCalenderView(Context context) {
        super(context);
    }

    public CustomCalenderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCalenderView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomCalenderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onDaySelected() {
        super.onDaySelected();
        for (SelectedDayListener selectedDayListener:selectedDayListenerList
             ) {
            selectedDayListener.daySelected(this.getSelectedDays());
        }

    }

    public List<SelectedDayListener> getSelectedDayListenerList() {
        return selectedDayListenerList;
    }

    public void setSelectedDayListenerList(SelectedDayListener selectedDayListener) {
        if(!this.selectedDayListenerList.contains(selectedDayListener)){
            this.selectedDayListenerList.add(selectedDayListener);
        }

    }
    public void addMothChangeListener(OnMonthChangeListener onMonthChangeListener){
        this.onMonthChangeListeners.add(onMonthChangeListener);
    }

    @Override
    public void onSnap(int position) {
      super.onSnap(position);


        for (OnMonthChangeListener on:this.onMonthChangeListeners
             ) {
            on.onMonthChanged(new Month(null,null));
        }
    }

}
