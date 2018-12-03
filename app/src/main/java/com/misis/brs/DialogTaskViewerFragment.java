package com.misis.brs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DialogTaskViewerFragment extends Fragment {

    private String task;

    public static Fragment newInstance(String task) {
        DialogTaskViewerFragment fragment = new DialogTaskViewerFragment();

        Bundle args = new Bundle();
        args.putString("description", task);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            this.task = getArguments().getString("description");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_task_view_dialog_display, null);

        TextView description = view.findViewById(R.id.task_view_dialog_description);
        description.setText(task);

        view.findViewById(R.id.task_view_dialog_close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        view.findViewById(R.id.task_view_dialog_edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TaskViewDialog) getActivity()).showEditor();
            }
        });

        return view;
    }
}
