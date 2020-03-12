package main.master.c31.FacebookpostRequest;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import main.master.c31.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacebookInfoFragment extends Fragment {

    LinearLayout one,two;
    CheckBox cbone,cbtwo;
    EditText changeinmakein;
    public FacebookInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_facebook_info, container, false);

        one = (LinearLayout)view.findViewById(R.id.one);
        two = (LinearLayout)view.findViewById(R.id.two);

        cbone = (CheckBox)view.findViewById(R.id.checkbox1);
        cbtwo = (CheckBox)view.findViewById(R.id.checkbox2);
        changeinmakein = (EditText)view.findViewById(R.id.changeinmakein);


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cbtwo.setChecked(false);
                cbone.setChecked(true);
                changeinmakein.setVisibility(View.VISIBLE);



            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cbtwo.setChecked(true);
                cbone.setChecked(false);
                changeinmakein.setVisibility(View.GONE);

            }
        });
        return view ;
    }

}
