package stlhorizon.org.hrmselfservice.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import stlhorizon.org.hrmselfservice.model.events.EventModel;


@Database(entities = {EventModel.class}, version = 3, exportSchema = false)

public abstract class UniscooDataBase extends RoomDatabase {
    private static UniscooDataBase uniscooDataBase;


    public abstract MyEventsDao getMyEventsDao();

    // synchronized is use to avoid concurrent access in multithred environment
    public static UniscooDataBase getInstance(Context context) {
        if (null == uniscooDataBase) {
            uniscooDataBase = buildDatabaseInstance(context);
        }
        return uniscooDataBase;
    }

    private static UniscooDataBase buildDatabaseInstance(Context context) {


//        Migration database2_to_3=new Migration(1, 2) {
//            @Override
//            public void migrate(final SupportSQLiteDatabase db) {
//
//                List<String> tables = new ArrayList<String>();
//                Cursor cursor = db.query("SELECT * FROM sqlite_master WHERE type='table';", null);
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast()) {
//                    String tableName = cursor.getString(1);
//                    if (!tableName.equals("android_metadata") &&
//                            !tableName.equals("sqlite_sequence"))
//                        tables.add(tableName);
//                    cursor.moveToNext();
//                }
//                cursor.close();
//
//                for(String tableName:tables) {
//                    db.execSQL("DROP TABLE IF EXISTS " + tableName);
//                }
//
//            }
//        };
        return Room.databaseBuilder(context,
                UniscooDataBase.class,
                "stlhorizon.org.uniscoo").fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    public void cleanUp() {
        uniscooDataBase = null;
    }

}