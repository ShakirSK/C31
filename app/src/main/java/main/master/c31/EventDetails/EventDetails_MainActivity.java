package main.master.c31.EventDetails;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.*;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import main.master.c31.ArtWork.ArtworkResponse;
import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetails_MainActivity extends AppCompatActivity {

    TextView date,time,save;
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

    String psid,timeperiod1,timeperiod2, dateperiod1,dateperiod2,defaultdate,svanue,sactivityname,sactivitydescription;
    EditText activityname, activitydescription,vanue;

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details__main);

        SharedPreferences sh
                = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        psid= sh.getString("id", "");

        activityname = findViewById(R.id.activityname);
        activitydescription = findViewById(R.id.activitydescription);

        userService = ApiUtils.getUserService();

        date = (TextView)findViewById(R.id.date);

        time = (TextView)findViewById(R.id.time);
        vanue = (EditText)findViewById(R.id.vanue);


        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.statusbarcolor));
        }


        save = (TextView)findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sactivityname = activityname.getText().toString().trim();
                sactivitydescription = activitydescription.getText().toString().trim();
                svanue = vanue.getText().toString().trim();

                if (sactivityname.isEmpty()) {
                    activityname.setError("Name required");
                    activityname.requestFocus();
                    return;
                }

                if (sactivitydescription.isEmpty()) {
                    activitydescription.setError("Description required");
                    activitydescription.requestFocus();
                    return;
                }

                if (svanue.isEmpty()) {
                    vanue.setError("Venue required");
                    vanue.requestFocus();
                    return;
                }



                if (dateperiod1==null||dateperiod2==null) {
                    Toast.makeText(getApplicationContext(),"Date required",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (timeperiod1==null||timeperiod2==null) {
                    Toast.makeText(getApplicationContext(),"Time required",Toast.LENGTH_SHORT).show();
                    return;
                }



                AlertDialog.Builder builder = new AlertDialog.Builder(EventDetails_MainActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Submit_EventDetails();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //   Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_LONG).show();

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();


            }
        });


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
      //  datePicker.setMaxDate(currentDate2.getTimeInMillis());
/*
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

            }
        });
*/

        final DatePicker datePicker2 = (DatePicker) layout.findViewById(R.id.datePicker2);


        datePicker2.setMinDate(currentDate.getTimeInMillis());
      //  datePicker2.setMaxDate(currentDate2.getTimeInMillis());

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



                                dateperiod1=year1+"-"+month1+"-"+day1;

                                dateperiod2=year2+"-"+month2+"-"+day2;

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date date1 = null,date2 = null;
                                try {

                                    date1 = sdf.parse(dateperiod1);
                                    date2 = sdf.parse(dateperiod2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                dateperiod1 = sdf.format(date1);
                                dateperiod2 = sdf.format(date2);
                                date.setText(dateperiod1+" - "+dateperiod2);

                            //    party_ledger_arrayDate("preference","amountsort",dateperiod1,dateperiod2);

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

                                timeperiod1 = datePicker.getCurrentHour() + ":" + datePicker.getCurrentMinute();
                                timeperiod2 = datePicker2.getCurrentHour() + ":" + datePicker2.getCurrentMinute();
                                time.setText(datePicker.getCurrentHour() + ":" + datePicker.getCurrentMinute()
                                        +"  -  "+
                                        datePicker2.getCurrentHour() + ":" + datePicker2.getCurrentMinute());



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

    private void Submit_EventDetails() {

        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        final EventDetailsModel artwork = new EventDetailsModel(
                psid, sactivityname,
                dateperiod1,  dateperiod2,  timeperiod1,timeperiod2,
                svanue,  sactivitydescription, psid);



        Call<ArtworkResponse> call = userService.EventDetails(artwork);
        call.enqueue(new Callback<ArtworkResponse>() {
            @Override
            public void onResponse(Call<ArtworkResponse> call, Response<ArtworkResponse> response) {

                Log.d("onResponse: ", response.toString());
                if(response.isSuccessful()){

                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    //   Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    ArtworkResponse loginResponse = response.body();

                    Log.e("keshav", "loginResponse 1 --> " + loginResponse);
                    if (loginResponse != null) {
                        Toast.makeText(EventDetails_MainActivity.this, "Event Request is successfully submited", Toast.LENGTH_SHORT).show();
                        Intent splashLoginm = new Intent(EventDetails_MainActivity.this, MainActivity.class);
                        splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(splashLoginm);
                        finish();
                    }


              /*      loginObj resObj = response.body();
                    if(resObj.getMessage().equals("true")){
                        //login start main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);

                    } else {
                       Toast.makeText(LoginActivity.this, "The username or password is incorrect", Toast.LENGTH_SHORT).show();
                     }*/
                } else {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Toast.makeText(EventDetails_MainActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArtworkResponse> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                Toast.makeText(EventDetails_MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
