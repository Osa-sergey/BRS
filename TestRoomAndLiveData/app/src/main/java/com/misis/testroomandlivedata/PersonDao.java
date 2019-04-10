package com.misis.testroomandlivedata;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface PersonDao {

    @Query("SELECT first_name FROM person WHERE id BETWEEN :firstId AND :lastId")
    PersonName[] getPersons(long firstId, long lastId);

    @Insert
    void insert(Person person);

}
