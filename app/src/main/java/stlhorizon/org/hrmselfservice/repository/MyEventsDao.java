package stlhorizon.org.hrmselfservice.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import stlhorizon.org.hrmselfservice.model.events.EventModel;


@Dao
public interface MyEventsDao {

    @Query("SELECT * FROM myevents")
    List<EventModel> getEventModels();


    @Query("SELECT * FROM myevents WHERE start_time LIKE:month")
    List<EventModel> getEventModelsForTheMonth(String month);

    @Query("SELECT * FROM myevents WHERE start_time=:date")
    List<EventModel> getSelectedDaysEvent(String date);


    @Insert
    long insertEventModel(EventModel eventModel);
    @Insert
    void insertAllEventModels(List<EventModel> eventModels);


    @Update
    void updateEventModel(EventModel repos);


    @Delete
    void deleteEventModel(EventModel eventModel);
    @Query("DELETE  FROM myevents")
    void deleteAllEvents();

    //delete events based on their sources
    @Query("DELETE  FROM myevents where eventSourceType=1")
    void deleteAllOnline();

    //delete individual my events
    @Query("DELETE  FROM myevents where event_id=:event_id")
    void deleteOneOfMyEvents(String event_id);

    @Delete
    void deleteEventModels(EventModel... eventModel);

    @Query("DELETE  FROM myevents where eventSourceType=2")
    void deleteAllCalenderEvent();

}
