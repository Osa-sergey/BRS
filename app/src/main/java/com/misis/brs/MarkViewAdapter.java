package com.misis.brs;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Vector;

public class MarkViewAdapter extends BaseAdapter {

    private Context context;
    private int selectedMark = -1;
    public static Vector<Mark> marks;

    public MarkViewAdapter(Context context, Vector<Mark> marks)
    {
        this.context = context;
        this.marks = marks;

    }

    public void setSelectedMark(int selectedMark)
    {
        this.selectedMark = selectedMark;
    }

    @Override
    public int getCount() {
        return marks.size();
    }

    @Override
    public Object getItem(int position) {
        return marks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_mark_view, null);

        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("Prefs", 0);
        final int semester = pref.getInt("semester", 0);
        String[] markTypes = view.getResources().getStringArray(R.array.mark_types_list_odd);
        if (semester % 2 != 0) {
            markTypes = view.getResources().getStringArray(R.array.mark_types_list_even);;
        }

        ((TextView) view.findViewById(R.id.mark_type)).setText(markTypes[marks.get(position).getType()]);
        ((TextView) view.findViewById(R.id.mark_value)).setText(marks.get(position).getMark() + " (" + marks.get(position).getMaxMark() + ")");
        ((TextView) view.findViewById(R.id.mark_description)).setText(marks.get(position).getDescription());

        return view;
    }
}
