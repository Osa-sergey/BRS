package com.misis.brs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

public class TaskViewAdapter extends BaseAdapter {

    private Context context;
    private int selectedTask = -1;
    private boolean started = false;

    public Vector<Homework> tasks;

    public TaskViewAdapter(Context context, Vector<Homework> tasks)
    {
        this.context = context;
        this.tasks = tasks;
    }

    public void setSelectedTask(int selectedTask)
    {
        this.selectedTask = selectedTask;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.fragment_task_view, null);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

        final Homework task = tasks.get(position);

        // ((TextView) view.findViewById(R.id.task_name)).setText(tasks.get(position).name);
        final TextView taskDeadline = view.findViewById(R.id.task_deadline);
        taskDeadline.setText(df.format(new Date(task.getDeadline())));
        final TextView taskDescription = view.findViewById(R.id.task_description);
        taskDescription.setText(task.getHometask());
        final CheckBox taskState = view.findViewById(R.id.task_completion_checkbox);
        final LinearLayout taskLayout = view.findViewById(R.id.task_layout);

        started = true;
        taskState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setCheck(isChecked);

                if (position == selectedTask)
                {
                    taskDeadline.setTextColor(Color.parseColor("#ffffff"));
                    taskDescription.setTextColor(Color.parseColor("#ffffff"));
                    view.getBackground().setColorFilter(Color.parseColor("#335599"), PorterDuff.Mode.DARKEN);
                }
                else {
                    taskDeadline.setTextColor(Color.parseColor("#ffffff"));
                    // taskDescription.setPaintFlags(taskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    taskDescription.setTextColor(Color.parseColor("#ffffff"));
                    taskLayout.setBackgroundColor(context.getResources().getColor(R.color.checkedTaskLayout));
                    taskState.setBackgroundColor(context.getResources().getColor(R.color.checkedTaskState));
                    view.getBackground().setColorFilter(Color.parseColor("#339955"), PorterDuff.Mode.DARKEN);
                }

                if (!started)
                {
                    notifyDataSetInvalidated();
                    ((MainActivity) context).databaseHandler.updateHometaskByDate(task.getDeadline(), task.getHometask(), task.getCheck());
                }
            }
        });

        if (position == selectedTask) {
            taskState.setBackgroundColor(context.getResources().getColor(R.color.deleteTask));
            if (task.getCheck())
                taskLayout.setBackground(context.getResources().getDrawable(R.drawable.gradient_checked_task));
            else
                taskLayout.setBackground(context.getResources().getDrawable(R.drawable.gradient_unchecked_task));
        }

        taskState.setChecked(task.getCheck());
        started = false;

        return view;
    }
}
