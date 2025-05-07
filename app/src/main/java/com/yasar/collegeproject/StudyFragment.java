package com.yasar.collegeproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CardView notes_button;
    CardView pyq_button;
    CardView lect_button;
    CardView notice_button;
    CardView calendar_button;
    CardView tt_button;


    public StudyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudyFragment newInstance(String param1, String param2) {
        StudyFragment fragment = new StudyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_study, container, false);
        notes_button= view.findViewById(R.id.notes_c);
        notes_button.setOnClickListener(this);

        notice_button=view.findViewById(R.id.notices);
        notice_button.setOnClickListener(this);

        calendar_button=view.findViewById(R.id.calendar);
        calendar_button.setOnClickListener(this);

        pyq_button=view.findViewById(R.id.pyq_c);
        pyq_button.setOnClickListener(this);

        tt_button=view.findViewById(R.id.timetable);
        tt_button.setOnClickListener(this);

        lect_button=view.findViewById(R.id.lectures_c);
        lect_button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.notes_c){
            Intent intent = new Intent(requireContext(), NotesActivity.class);
            startActivity(intent);

        } else if (id==R.id.notices) {
            Intent intent = new Intent(requireContext(), noticesActivity.class);
            startActivity(intent);
        } else if (id==R.id.calendar) {
            Intent intent = new Intent(requireContext(), calendarActivity.class);
            startActivity(intent);
        } else if (id==R.id.pyq_c) {
            Intent intent = new Intent(requireContext(), pyqActivity.class);
            startActivity(intent);
        } else if (id==R.id.lectures_c) {
            Intent intent = new Intent(requireContext(), lecturesActivity.class);
            startActivity(intent);
        } else if (id==R.id.timetable) {
            Intent intent = new Intent(requireContext(), timetableActivity.class);
            startActivity(intent);
        }
    }

}

