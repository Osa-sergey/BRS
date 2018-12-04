package com.misis.brs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Vector;

public class MarkViewAdapter extends BaseAdapter {

    private Context context;
    private int selectedMark = -1;
    public Vector<Mark> marks;

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

        String[] markTypes = view.getResources().getStringArray(R.array.mark_types_list);

        ((TextView) view.findViewById(R.id.mark_type)).setText(markTypes[marks.get(position).getType()]);
        ((TextView) view.findViewById(R.id.mark_value)).setText(marks.get(position).getMark() + " (" + marks.get(position).getMaxMark() + ")");
        ((TextView) view.findViewById(R.id.mark_description)).setText(marks.get(position).getDescription());

        return view;
    }
}
