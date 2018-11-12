package com.misis.brs;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);

        if (!pref.contains("semester"))
        {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("semester", 0);
            editor.apply();
        }

        final int semester = pref.getInt("semester", 0);

        final TextView scoreLabel = view.findViewById(R.id.score_label);

        Spinner semesterPicker = view.findViewById(R.id.semester_picker);
        semesterPicker.setSelection(semester);
        semesterPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("semester", position);
                editor.apply();

                updateScore(view, semester, scoreLabel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updateScore(view, semester, scoreLabel);
        return view;
    }

    private void updateScore(View view, int semester, TextView scoreLabel)
    {
        Vector<Mark> marks = new Vector<>();
        for (int type = 0; type < 7; ++type)
            marks.addAll(((MainActivity) getActivity()).databaseHandler.selectMark(semester, type));

        int sum = 0;
        for (Mark m : marks) {
            sum += m.getMark();
        }

        scoreLabel.setText(String.format(Locale.US, "TOTAL:\n%d / 100", sum));
    }
}
