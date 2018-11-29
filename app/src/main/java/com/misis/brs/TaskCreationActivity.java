package com.misis.brs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class TaskCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);

        Button saveTaskButton = findViewById(R.id.save_task_button);

        final EditText descriptionEditText = findViewById(R.id.task_name_edittext);
        final DatePicker deadlineDatePicker = findViewById(R.id.task_deadline_datepicker);

        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseHandler databaseHandler = new DatabaseHandler(TaskCreationActivity.this);
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("Prefs", 0);
                final int semester = pref.getInt("semester", 0);

                final Homework task = new Homework(
                        semester,
                        Calendar.dateToMillis(
                                deadlineDatePicker.getYear(),
                                deadlineDatePicker.getMonth(),
                                deadlineDatePicker.getDayOfMonth()
                        ),
                        descriptionEditText.getText().toString(),
                        false
                );

                createTask(databaseHandler, task);
            }
        });
    }

    private void createTask(final DatabaseHandler databaseHandler, final Homework task)
    {
        Returns ret = databaseHandler.addHometask(task);

        if (ret == Returns.DUPLICAT)
        {
            final Snackbar collisionResolvingSnackbar = Snackbar.make(
                    findViewById(R.id.task_creation_view),
                    "Task exists. Replace?",
                    Snackbar.LENGTH_SHORT
            );

            collisionResolvingSnackbar.setAction("YES", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateTask(databaseHandler, task);
                }
            });
            collisionResolvingSnackbar.show();
        }
        else if (ret == Returns.SQL_ERROR)
        {
            showRetrySnackbar(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createTask(databaseHandler, task);
                }
            });
        }
        else
        {
            finish();
        }
    }

    private void updateTask(final DatabaseHandler databaseHandler, final Homework task)
    {
        Returns ret = databaseHandler.updateHometaskByDate(task.getDeadline(), task.getHometask(), false);

        if (ret == Returns.SQL_ERROR)
        {
            showRetrySnackbar(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateTask(databaseHandler, task);
                }
            });
        }
        else
        {
            finish();
        }
    }

    private void showRetrySnackbar(View.OnClickListener listener)
    {
        final Snackbar retrySnackbar = Snackbar.make(
                findViewById(R.id.task_creation_view),
                "Error occurred. Try again?",
                Snackbar.LENGTH_INDEFINITE
        );

        retrySnackbar.setAction("RETRY",listener);
        retrySnackbar.show();
    }
}
