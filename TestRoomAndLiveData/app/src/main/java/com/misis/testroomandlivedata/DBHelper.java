package com.misis.testroomandlivedata;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class DBHelper {
    private static DBHelper instance;
    private AppDatabase db;

    private DBHelper(Context context){
        db = Room.databaseBuilder(context,AppDatabase.class,"database").build();
    }

    public static DBHelper getInstance(Context context){
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    public static PersonName[] getSomePerson(long firstID, long lastID){
        AsyncTask<Long, Void, PersonName[]> task = new AsyncTask<Long, Void, PersonName[]>() {
            @Override
            protected PersonName[] doInBackground(Long... longs) {
                return instance.db.personDao().getPersons(longs[0],longs[1]);
            }
        };
        task.execute(firstID,lastID);
        try {
            return task.get();
        } catch (Exception e) {
            Log.e("RoomPerson", e.toString());
        }
        return null;
    }
}
