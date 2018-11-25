package com.misis.brs;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {

    private Vector<Homework> tasks;
    private TaskViewAdapter taskViewAdapter;


    public TasksFragment() {
        tasks = new Vector<>(Arrays.asList(
                new Homework(0, Calendar.dateToMillis(2018, 10, 28), "This is a test task. Should display well.", false),
                new Homework(0, Calendar.dateToMillis(2018, 10, 29), "Тестирую русский языкъ...\n Аз бука веди\nAHdj 3r jgjhsdg jskgd jegrug3wurgu ,wjdfhg sjdfgsjdhfgj4w 43 t34t sdjg skjt 43. T J932n92 9h o485y 283h283 yf8 6fdfg6 d8fg6 d8g npeo4tg l3p4u3o42iy 58235 uiyefh y43 rby3874h f38  4u38th4u5t u54h54i t845hu4g5bb 45 5  456 3 5v 3v6j3v63456v3456bvo5 6 g dfgdh 0fg8h!", false)
        ));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        FloatingActionButton addTaskButton = view.findViewById(R.id.add_task_button);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskCreationActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
        final int semester = pref.getInt("semester", 0);

        tasks = ((MainActivity)getActivity()).databaseHandler.selectHometaskForSemester(semester);
        if (tasks == null) tasks = new Vector<>();

        taskViewAdapter = new TaskViewAdapter(getContext(), tasks);
        ListView listView = getView().findViewById(R.id.tasks_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Homework task = taskViewAdapter.tasks.elementAt(position);
                // TODO: Show full version
                Log.i("BRS", "Handling task click event");
                // task.setCheck(!task.getCheck());
                // ((MainActivity) getActivity()).databaseHandler.updateHometaskByDate(task.getDeadline(), task.getHometask(), task.getCheck());
                // taskViewAdapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                taskViewAdapter.setSelectedTask(position);
                taskViewAdapter.notifyDataSetChanged();

                final Snackbar mySnackbar = Snackbar.make(
                        getView().findViewById(R.id.tasks_coordinator_layout),
                        "Delete the selected task",
                        Snackbar.LENGTH_SHORT
                );

                mySnackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        taskViewAdapter.setSelectedTask(-1);
                        taskViewAdapter.notifyDataSetChanged();
                    }
                });

                mySnackbar.setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getActivity()).databaseHandler.deleteHometaskByDate(taskViewAdapter.tasks.elementAt(position).getDeadline());

                        tasks = ((MainActivity)getActivity()).databaseHandler.selectHometaskForSemester(semester);
                        if (tasks == null) tasks = new Vector<>();
                        taskViewAdapter.tasks = tasks;
                        taskViewAdapter.notifyDataSetChanged();
                    }
                });

                mySnackbar.show();
                return true;
            }
        });
        listView.setAdapter(taskViewAdapter);
    }

}
