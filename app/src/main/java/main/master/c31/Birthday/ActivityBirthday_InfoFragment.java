package main.master.c31.Birthday;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.master.c31.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityBirthday_InfoFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    TextView date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    LinearLayout daybody,monthbody,yearbody;

    public ActivityBirthday_InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_activity_birthday__info, container, false);

        daybody = (LinearLayout)view.findViewById(R.id.daybody);
        monthbody = (LinearLayout)view.findViewById(R.id.monthbody);
        yearbody = (LinearLayout)view.findViewById(R.id.yearbody);

        date = (TextView)view.findViewById(R.id.date);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = 1;
                datePickerDialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);

           //     datePickerDialog.getDatePicker().setMinDate(calendar.getFirstDayOfWeek());


           //    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
               datePickerDialog.show();


              }
        });


        // Spinner element
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.days_array));

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        daybody.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              /*  if(spinner.getSelectedItem() == null) { // user selected nothing...
                    spinner.performClick();
                }*/
                spinner.performClick();
            }
        });


        // Spinner element
        final Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.months_array));

        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);


        monthbody.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              /*  if(spinner.getSelectedItem() == null) { // user selected nothing...
                    spinner.performClick();
                }*/
                spinner2.performClick();
            }
        });



        // Spinner element
        final Spinner spinner3 = (Spinner) view.findViewById(R.id.spinner3);


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.years_array));

        // Drop down layout style - list view with radio button
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner3.setAdapter(dataAdapter3);
        yearbody.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              /*  if(spinner.getSelectedItem() == null) { // user selected nothing...
                    spinner.performClick();
                }*/
                spinner3.performClick();
            }
        });


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
