package com.misis.testroomandlivedata;

import android.arch.persistence.room.ColumnInfo;

public class PersonName {

    @ColumnInfo(name = "first_name")
    public String firstName;

    @Override
    public String toString() {
        return this.firstName;
    }
}
