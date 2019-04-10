package com.misis.testroomandlivedata;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<PersonName> adapter;
    private PersonName[] persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);

        DBHelper.getInstance(getApplicationContext());
        persons =DBHelper.getSomePerson(1,10);

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,persons);
        listView.setAdapter(adapter);
    }
}
