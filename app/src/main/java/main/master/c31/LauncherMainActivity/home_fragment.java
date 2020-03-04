package main.master.c31.LauncherMainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import main.master.c31.R;


public class home_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView schoolname;
    RecyclerView recyclerView;

    public home_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static home_fragment newInstance(String param1, String param2) {
        home_fragment fragment = new home_fragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList text5 = new ArrayList<>(Arrays.asList("Upload Activity Pictures","Upload Birthdays","Add Event Details",
                "Facebook Post Request","Facebook Page Requirement","Artwork Request"));

        ArrayList activitydescription = new ArrayList<>(Arrays.asList(
                "Upload activity pictures along with activity name and a short description.",
                "Upload birthday details of the child along with the child's name and date of birth.",
                "Add details of the events you plan to conduct along with name, date, time and venue for the event.",
                "Upload Facebook post to be uploaded on your page.\n",
                "Upload pictures as per the mentioned requirement in this section.",
                "Add details of your artwork requirement."));

        schoolname = (TextView)view.findViewById(R.id.schoolname);
      //  schoolname.setTextColor(Color.parseColor("#ec98b9"));
        recyclerView = (RecyclerView)view.findViewById(R.id.allcontest);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager horizontalLayoutManager  = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(horizontalLayoutManager);

        home_fragmentAdapter customAdapter = new home_fragmentAdapter(getActivity(), text5,activitydescription);
        recyclerView.setAdapter(customAdapter);

        return view;
    }


}
