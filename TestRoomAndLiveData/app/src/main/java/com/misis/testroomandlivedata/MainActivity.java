package com.misis.testroomandlivedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView textView;
    private Button btn;
    private ArrayAdapter<PersonName> adapter;
    private PersonName[] persons;
    private LiveData<String> liveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.text);
        btn = (Button) findViewById(R.id.button);

        liveData = LiveDataController.getInstance("ку").getData();
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveDataController.getInstance("ку").setText("не ку");
            }
        });

        DBHelper.getInstance(getApplicationContext());
        persons =DBHelper.getSomePerson(1,10);

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,persons);
        listView.setAdapter(adapter);
    }
}
