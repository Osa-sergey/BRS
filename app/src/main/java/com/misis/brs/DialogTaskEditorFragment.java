package com.misis.brs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class DialogTaskEditorFragment extends Fragment {
    private Homework task;


    public DialogTaskEditorFragment() {}

    public static DialogTaskEditorFragment newInstance(Homework task) {
        DialogTaskEditorFragment fragment = new DialogTaskEditorFragment();

        Bundle args = new Bundle();
        args.putInt("semester", task.getSemester());
        args.putLong("deadline", task.getDeadline());
        args.putString("description", task.getHometask());
        args.putBoolean("is_done", task.getCheck());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.task = new Homework(
                    getArguments().getInt("semester", 0),
                    getArguments().getLong("deadline", 0),
                    getArguments().getString("description"),
                    getArguments().getBoolean("is_done", false)
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog_task_editor, null);

        final EditText description = view.findViewById(R.id.task_view_dialog_description_edittext);
        description.setText(this.task.getHometask());

        view.findViewById(R.id.task_view_dialog_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TaskViewDialog) getActivity()).showViewer();
            }
        });

        view.findViewById(R.id.task_view_dialog_save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setHometask(description.getText().toString());
                ((TaskViewDialog) getActivity()).databaseHandler.updateHometaskByDate(
                        task.getDeadline(),
                        task.getHometask(),
                        task.getCheck()
                );
                ((TaskViewDialog) getActivity()).task = task;
                ((TaskViewDialog) getActivity()).showViewer();
            }
        });

        return view;
    }
}
