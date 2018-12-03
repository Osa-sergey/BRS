package com.misis.brs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TaskViewDialog extends AppCompatActivity {
    public Homework task;
    public DatabaseHandler databaseHandler;


    public TaskViewDialog() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_task_view_dialog);

        Intent intent = getIntent();
        task = new Homework(
                intent.getIntExtra("semester", 0),
                intent.getLongExtra("deadline", 0),
                intent.getStringExtra("description"),
                intent.getBooleanExtra("is_done", false)
        );

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

        TextView deadline = findViewById(R.id.task_view_dialog_deadline);
        deadline.setText(df.format(new Date(task.getDeadline())));

        databaseHandler = new DatabaseHandler(this);

        showViewer();
    }

    public void showViewer()
    {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.task_view_dialog_frame, DialogTaskViewerFragment.newInstance(task.getHometask()));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showEditor()
    {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.task_view_dialog_frame, DialogTaskEditorFragment.newInstance(task));
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
