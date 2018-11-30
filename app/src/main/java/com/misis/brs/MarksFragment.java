package com.misis.brs;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarksFragment extends Fragment {


    public MarksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_marks, container, false);

        final NumberPicker markMaxPicker = view.findViewById(R.id.create_mark_maximum);
        String[] numsForMarkMaxPicker = new String[20];
        for(int i=0; i < numsForMarkMaxPicker.length; i++)
            numsForMarkMaxPicker[i] = Integer.toString(i + 1);

        markMaxPicker.setMinValue(1);
        markMaxPicker.setMaxValue(20);
        markMaxPicker.setWrapSelectorWheel(false);
        markMaxPicker.setDisplayedValues(numsForMarkMaxPicker);
        markMaxPicker.setValue(5);


        final NumberPicker markValuePicker = view.findViewById(R.id.create_mark_value);
        String[] numsForMarkValuePicker = new String[21];
        for(int i=0; i < numsForMarkValuePicker.length; i++)
            numsForMarkValuePicker[i] = Integer.toString(i);

        markValuePicker.setMinValue(0);
        markValuePicker.setMaxValue(20);
        markValuePicker.setWrapSelectorWheel(false);
        markValuePicker.setDisplayedValues(numsForMarkValuePicker);
        markValuePicker.setValue(5);


        final Spinner markTypeSpinner = view.findViewById(R.id.create_mark_type);
        final EditText markDescriptionInput = view.findViewById(R.id.create_mark_description);


        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
        final int semester = pref.getInt("semester", 0);

        final DatabaseHandler db = ((MainActivity) getActivity()).databaseHandler;
        final MarkViewAdapter markViewAdapter = new MarkViewAdapter(getActivity(), new Vector<Mark>());
        ((ListView) view.findViewById(R.id.marks_list)).setAdapter(markViewAdapter);
        loadMarks(db, semester, markViewAdapter);

        Button createMarkButton = view.findViewById(R.id.create_mark_button);
        createMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (markValuePicker.getValue() > markMaxPicker.getValue())
                {
                    final Snackbar notificationSnackbar = Snackbar.make(
                            view.findViewById(R.id.marks_fragment_view),
                            "Mark can not be higher than the maximum.",
                            Snackbar.LENGTH_SHORT
                    );
                    notificationSnackbar.show();

                    return;
                }
                Mark added = new Mark(
                        semester,
                        markTypeSpinner.getSelectedItemPosition(),
                        markValuePicker.getValue(),
                        markMaxPicker.getValue(),
                        markDescriptionInput.getText().toString());
                Returns returned = db.isAbleToAdd(added);
                if (returned == Returns.DONE){
                    if (db.addMark(added) == Returns.DONE)
                    {
                        markTypeSpinner.setSelection(0);
                        markValuePicker.setValue(5);
                        markMaxPicker.setValue(5);
                        markDescriptionInput.setText("");
                    }
                }else{
                    if(returned == Returns.MARKS_SCORE_LIMIT){
                        final Snackbar notificationSnackbar = Snackbar.make(
                                view.findViewById(R.id.marks_fragment_view),
                                "Limit for the number of points for this type of assessment.",
                                Snackbar.LENGTH_LONG
                        );
                        notificationSnackbar.show();
                    }else {
                        final Snackbar notificationSnackbar = Snackbar.make(
                                view.findViewById(R.id.marks_fragment_view),
                                "Limit exceeded the number of evaluations of this type.",
                                Snackbar.LENGTH_LONG
                        );
                        notificationSnackbar.show();
                    }
                }
                loadMarks(db, semester, markViewAdapter);
            }
        });

        return view;
    }

    private void loadMarks(final DatabaseHandler databaseHandler, int semester, MarkViewAdapter markViewAdapter)
    {
        Vector<Mark> marks = new Vector<>();
        for (int type = 0; type < 7; ++type)
            marks.addAll(databaseHandler.selectMark(semester, type));

        markViewAdapter.marks = marks;

        markViewAdapter.notifyDataSetChanged();
    }

}
