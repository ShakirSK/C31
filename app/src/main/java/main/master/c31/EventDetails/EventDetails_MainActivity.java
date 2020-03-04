package main.master.c31.EventDetails;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import main.master.c31.R;

public class EventDetails_MainActivity extends AppCompatActivity {

    TextView date,time;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

     int  mHour, mMinute;

    int year1,year2;
    String day1,day2;
    String month1,month2;

    String timeperiod1,timeperiod2,defaultdate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details__main);
        date = (TextView)findViewById(R.id.date);

        time = (TextView)findViewById(R.id.time);


        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.statusbarcolor));
        }



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showcalender();
                /*calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(EventDetails_MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();*/


            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showtime();
           /*     // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(EventDetails_MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
*/
            }
        });



    }

    private void showcalender(){
     /*   ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_date, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();*/

        AlertDialog.Builder builder;
        AlertDialog alertDialog;

        LayoutInflater inflater = (LayoutInflater)
                getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_date,
                (ViewGroup) findViewById(R.id.tabhost));

        TabHost tabs = (TabHost) layout.findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec tabpage1 = tabs.newTabSpec("FROM");
        tabpage1.setContent(R.id.ScrollView01);
        tabpage1.setIndicator("FROM");
        TabHost.TabSpec tabpage2 = tabs.newTabSpec("TO");
        tabpage2.setContent(R.id.ScrollView02);
        tabpage2.setIndicator("TO");
        tabs.addTab(tabpage1);
        tabs.addTab(tabpage2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  final DatePicker datePicker = (DatePicker) layout.findViewById(R.id.datePicker);


        Calendar currentDate = Calendar.getInstance();
        currentDate.set(currentDate.get(Calendar.YEAR)-1,Calendar.APRIL,1);
        datePicker.setMinDate(currentDate.getTimeInMillis());

        Calendar currentDate2 = Calendar.getInstance();
        currentDate2.set(currentDate2.get(Calendar.YEAR),Calendar.MARCH,31);
        datePicker.setMaxDate(currentDate2.getTimeInMillis());
/*
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

            }
        });
*/

        final DatePicker datePicker2 = (DatePicker) layout.findViewById(R.id.datePicker2);


        datePicker2.setMinDate(currentDate.getTimeInMillis());
        datePicker2.setMaxDate(currentDate2.getTimeInMillis());

        /*datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {


            }
        });*/
        builder = new AlertDialog.Builder(EventDetails_MainActivity.this);

        builder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                day1 = String.valueOf(datePicker.getDayOfMonth());
                                month1 = String.valueOf(datePicker.getMonth() +1);
                                year1 = datePicker.getYear();


                                day2 = String.valueOf(datePicker2.getDayOfMonth());
                                month2 = String.valueOf(datePicker2.getMonth()+1);
                                year2 = datePicker2.getYear();



                                timeperiod1=year1+"-"+month1+"-"+day1;

                                timeperiod2=year2+"-"+month2+"-"+day2;

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date date1 = null,date2 = null;
                                try {

                                    date1 = sdf.parse(timeperiod1);
                                    date2 = sdf.parse(timeperiod2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                timeperiod1 = sdf.format(date1);
                                timeperiod2 = sdf.format(date2);
                                date.setText(timeperiod1+" - "+timeperiod2);

                            //    party_ledger_arrayDate("preference","amountsort",timeperiod1,timeperiod2);

                               /* dateendandstart.setText(sdf.format(date1)+" - "+sdf.format(date2));
                                party_ledger_arrayDate("preference","amountsort",sdf.format(date1),sdf.format(date2));*/


                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        /* builder.setTitle("Dialog with tabs");*/
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();

    }


    public void showtime(){
     /*   ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_date, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();*/

        AlertDialog.Builder builder;
        AlertDialog alertDialog;

        LayoutInflater inflater = (LayoutInflater)
                getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_time,
                (ViewGroup) findViewById(R.id.tabhost));

        TabHost tabs = (TabHost) layout.findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec tabpage1 = tabs.newTabSpec("FROM");
        tabpage1.setContent(R.id.ScrollView01);
        tabpage1.setIndicator("FROM");
        TabHost.TabSpec tabpage2 = tabs.newTabSpec("TO");
        tabpage2.setContent(R.id.ScrollView02);
        tabpage2.setIndicator("TO");
        tabs.addTab(tabpage1);
        tabs.addTab(tabpage2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        final TimePicker datePicker = (TimePicker) layout.findViewById(R.id.datePicker);




        final TimePicker datePicker2 = (TimePicker) layout.findViewById(R.id.datePicker2);


        builder = new AlertDialog.Builder(EventDetails_MainActivity.this);

        builder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                time.setText(datePicker.getCurrentHour() + ":" + datePicker.getCurrentMinute()+"  -  "+datePicker2.getCurrentHour() + ":" + datePicker2.getCurrentMinute());



                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        /* builder.setTitle("Dialog with tabs");*/
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();

    }
}
