package com.misis.brs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    private Vector<Mark> marks;
    private MarkViewAdapter markViewAdapter;
    private DatabaseHandler db;

    public MarksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("MarksFragmentDebug", "onCreateView");
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

        db = ((MainActivity) getActivity()).databaseHandler;
        markViewAdapter = new MarkViewAdapter(getActivity(), new Vector<Mark>());
        ((ListView) view.findViewById(R.id.marks_list)).setAdapter(markViewAdapter);


        int markTypes = R.array.mark_types_list_odd;
        if (semester % 2 != 0) {
            markTypes = R.array.mark_types_list_even;
        }
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(getActivity(), markTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        markTypeSpinner.setAdapter(adapter);
        markTypeSpinner.setSelection(0);

        markTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View itemSelected, int selectedItemPosition, long selectedId) {
                String[] numsForMarkMaxPicker;
                String[] numsForMarkValuePicker;

                markMaxPicker.setWrapSelectorWheel(false);
                markValuePicker.setWrapSelectorWheel(false);

                numsForMarkMaxPicker = new String[20];
                for(int i=0; i < numsForMarkMaxPicker.length; i++)
                    numsForMarkMaxPicker[i] = Integer.toString(i + 1);
                numsForMarkValuePicker = new String[21];
                for(int i=0; i < numsForMarkValuePicker.length; i++)
                    numsForMarkValuePicker[i] = Integer.toString(i);
                markMaxPicker.setDisplayedValues(numsForMarkMaxPicker);
                markValuePicker.setDisplayedValues(numsForMarkValuePicker);
                markDescriptionInput.setText("");

                switch (selectedItemPosition){
                    case 0:
                    case 1:
                        numsForMarkMaxPicker = new String[20];
                        for(int i=0; i < numsForMarkMaxPicker.length; i++)
                            numsForMarkMaxPicker[i] = Integer.toString(i + 1);

                        markMaxPicker.setMinValue(1);
                        markMaxPicker.setMaxValue(20);
                        markMaxPicker.setDisplayedValues(numsForMarkMaxPicker);
                        markMaxPicker.setValue(5);

                        numsForMarkValuePicker = new String[21];
                        for(int i=0; i < numsForMarkValuePicker.length; i++)
                            numsForMarkValuePicker[i] = Integer.toString(i);

                        markValuePicker.setMinValue(0);
                        markValuePicker.setMaxValue(20);
                        markValuePicker.setDisplayedValues(numsForMarkValuePicker);
                        markValuePicker.setValue(5);
                        break;
                    case 2:
                        numsForMarkMaxPicker = new String[1];
                        numsForMarkMaxPicker[0] = Integer.toString( 5);

                        markMaxPicker.setMinValue(5);
                        markMaxPicker.setMaxValue(5);
                        markMaxPicker.setDisplayedValues(numsForMarkMaxPicker);
                        markMaxPicker.setValue(5);

                        numsForMarkValuePicker = new String[6];
                        for(int i=0; i < numsForMarkValuePicker.length; i++)
                            numsForMarkValuePicker[i] = Integer.toString(i);

                        markValuePicker.setMinValue(0);
                        markValuePicker.setMaxValue(5);
                        markValuePicker.setDisplayedValues(numsForMarkValuePicker);
                        markValuePicker.setValue(5);
                        break;
                    case 3:
                    case 4:
                        numsForMarkMaxPicker = new String[1];
                        numsForMarkMaxPicker[0] = Integer.toString( 10);

                        markMaxPicker.setMinValue(10);
                        markMaxPicker.setMaxValue(10);
                        markMaxPicker.setDisplayedValues(numsForMarkMaxPicker);
                        markMaxPicker.setValue(10);

                        numsForMarkValuePicker = new String[11];
                        for(int i=0; i < numsForMarkValuePicker.length; i++)
                            numsForMarkValuePicker[i] = Integer.toString(i);

                        markValuePicker.setMinValue(0);
                        markValuePicker.setMaxValue(10);
                        markValuePicker.setDisplayedValues(numsForMarkValuePicker);
                        markValuePicker.setValue(10);
                        break;
                    case 5:
                    case 6:
                        numsForMarkMaxPicker = new String[1];
                        numsForMarkMaxPicker[0] = Integer.toString( 10);

                        markMaxPicker.setMinValue(10);
                        markMaxPicker.setMaxValue(10);
                        markMaxPicker.setDisplayedValues(numsForMarkMaxPicker);
                        markMaxPicker.setValue(10);

                        numsForMarkValuePicker = new String[6];
                        for(int i=0; i < numsForMarkValuePicker.length; i++)
                            numsForMarkValuePicker[i] = Integer.toString(i*2);

                        markValuePicker.setMinValue(0);
                        markValuePicker.setMaxValue(5);
                        markValuePicker.setDisplayedValues(numsForMarkValuePicker);
                        markValuePicker.setValue(5);
                        break;
                }

                Log.d("MarksFragmentDebug", "loadMarks_InSelectSemester");
                loadMarks(db, semester, markViewAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
                MarkBuilder mb = new MarkBuilder();
                mb.buildSemester(semester)
                        .buildType( markTypeSpinner.getSelectedItemPosition())
                        .buildDescription(markDescriptionInput.getText().toString())
                        .buildMaxMark( markMaxPicker.getValue());
                if( markTypeSpinner.getSelectedItemPosition() == 5 || markTypeSpinner.getSelectedItemPosition() == 6 )
                    mb.buildMark(markValuePicker.getValue()*2);
                else mb.buildMark(markValuePicker.getValue());
                Mark added = mb.build();

                Returns returned = db.isAbleToAdd(added);
                switch (returned){
                    case DONE:
                        if (db.addMark(added) == Returns.DONE)
                        {
                            markDescriptionInput.setText("");
                        }
                        break;
                    case MARKS_TYPE_LIMIT:
                        final Snackbar notificationSnackbar = Snackbar.make(
                                view.findViewById(R.id.marks_fragment_view),
                                "Limit exceeded the number of evaluations of this type.",
                                Snackbar.LENGTH_LONG
                        );
                        notificationSnackbar.show();
                        break;
                    case MARKS_SCORE_LIMIT:
                        final Snackbar notificationSnackbar1 = Snackbar.make(
                                view.findViewById(R.id.marks_fragment_view),
                                "Limit for the number of points for this type of assessment.",
                                Snackbar.LENGTH_LONG
                        );
                        notificationSnackbar1.show();
                        break;
                }

                Log.d("MarksFragmentDebug", "loadMarks_InCreateMark");
                loadMarks(db, semester, markViewAdapter);
            }
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("MarksFragmentDebug", "onResume");
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("Prefs", 0);
        final int semester = pref.getInt("semester", 0);
//Never used now        final int type = pref.getInt("type", 0);

        ListView listView = getView().findViewById(R.id.marks_list);
        Log.d("MarksFragmentDebug", "loadMarks_InResume");
        loadMarks(db, semester, markViewAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                markViewAdapter.setSelectedMark(position);
                markViewAdapter.notifyDataSetChanged();

                final Snackbar mySnackbar = Snackbar.make(
                        getView().findViewById(R.id.marks_list),
                        "Delete the selected mark?",
                        Snackbar.LENGTH_SHORT
                );

                mySnackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        markViewAdapter.setSelectedMark(-1);
                        markViewAdapter.notifyDataSetChanged();
                    }
                });

                mySnackbar.setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getActivity()).databaseHandler.deleteMarkById(markViewAdapter.marks.elementAt(position).getId());
                        Log.d("MarksFragmentDebug", "loadMarks_InDeleteMethod");
                        loadMarks(db, semester, markViewAdapter);
                        markViewAdapter.notifyDataSetChanged();
                    }
                });

                mySnackbar.show();
                return true;
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("MarksFragmentDebug", "onDetach");
        Log.d("MarksFragmentDebug", " ");
    }

    private void loadMarks(final DatabaseHandler databaseHandler, int semester, MarkViewAdapter markViewAdapter)
    {
        marks = new Vector<>();
        for (int type = 0; type < 7; ++type)
            marks.addAll(databaseHandler.selectMark(semester, type));

        markViewAdapter.marks = marks;

        markViewAdapter.notifyDataSetChanged();
        Log.d("MarksFragmentDebug", "loadMarks_successfully");
    }

}
