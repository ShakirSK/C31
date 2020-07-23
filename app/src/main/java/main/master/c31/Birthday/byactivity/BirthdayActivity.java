package main.master.c31.Birthday.byactivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.ViewCompat;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import gun0912.tedbottompicker.TedBottomPicker;
import main.master.c31.Birthday.BirthdayModel;
import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import main.master.c31.utils.ConnectionDetector;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import java.io.File;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

import static main.master.c31.UploadActivity.UploadActivityList.ActivityUploadedList.map;

public class BirthdayActivity extends AppCompatActivity {
    TextView date,save;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    LinearLayout daybody,monthbody,yearbody;
    EditText activityname;
    String sactivityname,days,monthsubmit,years;
    Spinner spinner;
    ArrayAdapter<String> dataAdapter;
    UserService userService;
    String datesubmit;
    private String psid;
    ImageView selectedimage;
    private List<Uri> selectedUriList;

    private Animation fabOpenAnimation;
    private Animation fabCloseAnimation;
    private boolean isFabMenuOpen = false;
    FloatingActionButton baseFloatingActionButton,shareFab,createFab;
    LinearLayout createLayout,shareLayout;
    ProgressDialog mProgressDialog;
    //hashmap randomkey
    int notificationid;
    ImageView backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);


        backbutton = (ImageView) findViewById(R.id.close);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences sh
                = getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        psid= sh.getString("id", "");

        activityname = (EditText)findViewById(R.id.activityname);
        baseFloatingActionButton = (FloatingActionButton)findViewById(R.id.baseFloatingActionButton);
        shareFab = (FloatingActionButton)findViewById(R.id.shareFab);
        createFab = (FloatingActionButton)findViewById(R.id.createFab);

        userService = ApiUtils.getUserService();

        daybody = (LinearLayout)findViewById(R.id.daybody);
        monthbody = (LinearLayout)findViewById(R.id.monthbody);
        yearbody = (LinearLayout)findViewById(R.id.yearbody);
        selectedimage = (ImageView)findViewById(R.id.selectedimage);
        date = (TextView)findViewById(R.id.date);

        createLayout = (LinearLayout)findViewById(R.id.createLayout);
        shareLayout = (LinearLayout)findViewById(R.id.shareLayout);

        getAnimations();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = 1;
                datePickerDialog = new DatePickerDialog(BirthdayActivity.this,android.R.style.Theme_Holo_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                                datesubmit = day + "-" + (month + 1) + "-" + year;
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);

                //     datePickerDialog.getDatePicker().setMinDate(calendar.getFirstDayOfWeek());


                //    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();


            }
        });


        save = (TextView)findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking if internet available
                if (!ConnectionDetector.networkStatus(getApplicationContext())) {

                    //   Toast.makeText(getApplicationContext(),"tre",Toast.LENGTH_SHORT).show();
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BirthdayActivity.this).create();
                    alertDialog.setTitle("No Internet Connection");
                    alertDialog.setMessage("Please check your internet connection  and try again");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();

                        }
                    });
                    alertDialog.show();
                    return;
                }

                sactivityname = activityname.getText().toString().trim();


                if (sactivityname.isEmpty()) {
                    activityname.setError("Name required");
                    activityname.requestFocus();
                    return;
                }

                if(selectedUriList==null || selectedUriList.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Select Student Image",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(days.equals("Select Day") || monthsubmit.equals("Select Month")|| years.equals("Select Year") )
                {
                    Toast.makeText(getApplicationContext(),"Please Select Proper Date",Toast.LENGTH_SHORT).show();
                    return;
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(BirthdayActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                            Submit_Birthday();

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


        final ArrayList<String> Daylist = new ArrayList<String>(31);



        Daylist.add("Select Day");
        for (int i = 1; i <=31; i++)
        {
            Daylist.add(String.valueOf(i));
        }



        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner1);


        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(BirthdayActivity.this, android.R.layout.simple_spinner_item, Daylist);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
    /*   days = (String) spinner.getSelectedItem().toString();
       Toast.makeText(getContext(),days,Toast.LENGTH_SHORT).show();

*/


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // selectedyear = parent.getSelectedItemPosition();
                days      = spinner.getSelectedItem().toString();
                //     Toast.makeText(parent.getContext(), "Selected: " + days, Toast.LENGTH_LONG).show();


            }
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        ArrayList<String> monthsList = new ArrayList<String>();

        monthsList.add("Select Month");

        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length; i++) {
            String month = months[i];
            monthsList .add(months[i]);
        }


        // Spinner element
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(BirthdayActivity.this, android.R.layout.simple_spinner_item,monthsList);

        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);


        //    monthsubmit = (String) spinner2.getSelectedItem().toString();
        //  Toast.makeText(getContext(),ss2,Toast.LENGTH_SHORT).show();



        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                monthsubmit      = spinner2.getSelectedItem().toString();
                // Toast.makeText(parent.getContext(), "Selected: " + monthsubmit, Toast.LENGTH_LONG).show();

            }
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });



        final ArrayList<String> Yearlist = new ArrayList<String>();
        Yearlist.add("Select Year");
        Yearlist.add(String.valueOf(2020));
        Yearlist.add(String.valueOf(2021));


        // Spinner element
        final Spinner spinner3 = (Spinner)findViewById(R.id.spinner3);


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(BirthdayActivity.this, android.R.layout.simple_spinner_item, Yearlist);

        // Drop down layout style - list view with radio button
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner3.setAdapter(dataAdapter3);

        //   years = (String) spinner3.getSelectedItem().toString();
        // Toast.makeText(getContext(),ss3,Toast.LENGTH_SHORT).show();


        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                years      = spinner3.getSelectedItem().toString();
                //  Toast.makeText(parent.getContext(), "Selected: " + years, Toast.LENGTH_LONG).show();

            }
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


    }

    public void Submit_Birthday() {

        Set<Map.Entry<Integer, BirthdayModel>> set = map.entrySet();

        for (Map.Entry<Integer, BirthdayModel> me : set) {
            // System.out.println("Key :"+me.getKey() +" Name : "+ me.getValue().getName()+"Age :"+me.getValue().getAge());
            Log.d("hasgmapbm: ", "Key :" + me.getKey() + " Name : " + me.getValue().getStudent_name() );

            Log.d("hasgmapbm: ", "Key :" + me.getKey() +"Age :" + me.getValue().getDob());

            Log.d("hasgmapbm: ", "Size :" + set.size() + " "+map.lastKey());

            Log.d( "urlp1: ", String.valueOf(me.getValue().getFile()));


            String ere = removeUriFromPath(me.getValue().getFile());
            String urlString = Uri.decode(ere);
            // String  urlString = ere.replaceAll(" ", "%20");
            Log.d( "urlp2: ", String.valueOf(ere));
///



///
            File imgFile = new File(urlString);
            RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), imgFile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", imgFile.getName(), reqFile);



            String spreschool_id = me.getValue().getPreschool_id();
            RequestBody preschool_id =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, spreschool_id);

            String sstudent_name = me.getValue().getStudent_name() ;
            RequestBody student_name =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, sstudent_name);

            String sdob = me.getValue().getDob();
            RequestBody dob =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, sdob);

            String screated_by = me.getValue().getCreated_by();
            RequestBody created_by =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, screated_by);


            Call<ResponseBody> call = userService.Birthday(preschool_id,
                    student_name,dob,body, created_by);
            call.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {


                    if(me.getKey()==map.lastKey()){
                        Log.d("hasgmapbm: ", "finish :" );
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        Log.v("Upload", "success");
                        // Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();

                        Toast.makeText(getApplicationContext(), "Your Birthday is successfully submited", Toast.LENGTH_SHORT).show();
                        Intent splashLoginm = new Intent(getApplicationContext(), MainActivity.class);
                        splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(splashLoginm);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Log.e("Upload error:", t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });


        }












    }

    public void SelectImage(View view){
        TedBottomPicker.with(BirthdayActivity.this)
                //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                .setPeekHeight(1600)
                .showTitle(true)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("Nothing Selected")
                .setPreviewMaxCount(60)
                .setSelectMaxCount(1)
                .setSelectedUriList(selectedUriList)
                .showMultiImage(uriList -> {
                    selectedUriList = uriList;
                    //  showUriList(uriList);
                    if(selectedUriList!=null&&!selectedUriList.isEmpty()) {
                        Glide.with(getApplicationContext())
                                .load(selectedUriList.get(0))
                                .into(selectedimage);
                    }
                    else{
                        Glide.with(getApplicationContext())
                                .load(R.drawable.newuploadimageicondocconnect)
                                .into(selectedimage);
                    }
                });
    }


    public static String removeUriFromPath(String uri)
    {
        return  uri.substring(7, uri.length());
    }

    private void getAnimations() {

        fabOpenAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_open);

        fabCloseAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_close);

    }

    private void expandFabMenu() {

        ViewCompat.animate(baseFloatingActionButton).rotation(45.0F).withLayer().setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        createLayout.startAnimation(fabOpenAnimation);
        shareLayout.startAnimation(fabOpenAnimation);
        createFab.setClickable(true);
        shareFab.setClickable(true);
        isFabMenuOpen = true;


    }

    private void collapseFabMenu() {

        ViewCompat.animate(baseFloatingActionButton).rotation(0.0F).withLayer().setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        createLayout.startAnimation(fabCloseAnimation);
        shareLayout.startAnimation(fabCloseAnimation);
        createFab.setClickable(false);
        shareFab.setClickable(false);
        isFabMenuOpen = false;

    }

    public void onBaseFabClick(View view) {

        if (isFabMenuOpen)
            collapseFabMenu();
        else
            expandFabMenu();


    }

    public void onImageSelectClick(View view) {

        sactivityname = activityname.getText().toString().trim();


        if (sactivityname.isEmpty()) {
            activityname.setError("Name required");
            activityname.requestFocus();
            return;
        }

        if(selectedUriList==null || selectedUriList.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Select Student Image",Toast.LENGTH_SHORT).show();
            return;
        }

        if(days.equals("Select Day") || monthsubmit.equals("Select Month")|| years.equals("Select Year") )
        {
            Toast.makeText(getApplicationContext(),"Please Select Proper Date",Toast.LENGTH_SHORT).show();
            return;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(BirthdayActivity.this);
        builder.setMessage("Make sure , The Birthday Information Is Correct , Your Information will be Added");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String dfdnf = null;

                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM");
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(inputFormat.parse(monthsubmit));
                    SimpleDateFormat outputFormat = new SimpleDateFormat("MM"); // 01-12

                    dfdnf = outputFormat.format(cal.getTime());
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String dateformsubmit = days+"-"+dfdnf+"-"+years;

                Log.d( "urlp3: ", String.valueOf(dateformsubmit));

                notificationid = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                BirthdayModel birthdayModel =  new BirthdayModel(psid,sactivityname,dateformsubmit,selectedUriList.get(0).toString(),psid);
                map.put(notificationid, birthdayModel);

                Set<Map.Entry<Integer, BirthdayModel>> set = map.entrySet();

                for (Map.Entry<Integer, BirthdayModel> me : set) {
                   // System.out.println("Key :"+me.getKey() +" Name : "+ me.getValue().getName()+"Age :"+me.getValue().getAge());
                    Log.d( "hasgmapbm: ","Key :"+me.getKey() +" Name : "+ me.getValue().getStudent_name()+"Age :"+me.getValue().getDob());

                }
           /*     for (BirthdayModel key : map.values()) {
                   // System.out.println(key + " : " + map.get(key).toString());
                    Log.d( "hasgmapbm: ", key + " : "+key.getStudent_name()+ " : " + map.get(key).toString());

                }*/

                Toast.makeText(getApplicationContext(),"Your Birthday Added",Toast.LENGTH_SHORT).show();


                //   Snackbar.make(binding.coordinatorLayout, "Create FAB tapped", Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),BirthdayActivity.class);
                startActivity(intent);


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

    public void onVideoSelectClick(View view) {

        //checking if internet available
        if (!ConnectionDetector.networkStatus(getApplicationContext())) {

            //   Toast.makeText(getApplicationContext(),"tre",Toast.LENGTH_SHORT).show();
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(BirthdayActivity.this).create();
            alertDialog.setTitle("No Internet Connection");
            alertDialog.setMessage("Please check your internet connection  and try again");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();

                }
            });
            alertDialog.show();
            return;
        }

        sactivityname = activityname.getText().toString().trim();



        if (!map.isEmpty()&&(sactivityname.isEmpty()|| (selectedUriList==null || selectedUriList.isEmpty()) || (days.equals("Select Day") || monthsubmit.equals("Select Month")|| years.equals("Select Year") ))){
                            AlertDialog.Builder builder = new AlertDialog.Builder(BirthdayActivity.this);
                            builder.setTitle("Are you sure?");
                            builder.setMessage("Your Previos "+map.size()+" Birthday Request will be Added");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    mProgressDialog = new ProgressDialog(BirthdayActivity.this);
                                    mProgressDialog.setIndeterminate(true);
                                    mProgressDialog.setMessage("Loading...");
                                    mProgressDialog.setCanceledOnTouchOutside(false);
                                    mProgressDialog.show();

                                    Submit_Birthday();


                                    //       Submit_Birthdayexa();
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


        }else{

                        if (sactivityname.isEmpty()) {
                            activityname.setError("Name required");
                            activityname.requestFocus();
                            return;
                        }

                        if(selectedUriList==null || selectedUriList.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"Please Select Student Image",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(days.equals("Select Day") || monthsubmit.equals("Select Month")|| years.equals("Select Year") )
                        {
                            Toast.makeText(getApplicationContext(),"Please Select Proper Date",Toast.LENGTH_SHORT).show();
                            return;
                        }


                        AlertDialog.Builder builder = new AlertDialog.Builder(BirthdayActivity.this);
                        builder.setTitle("Are you sure?");
                        builder.setMessage(" Birthday Request will be Added");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                String dfdnf = null;

                                try {
                                    SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM");
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(inputFormat.parse(monthsubmit));
                                    SimpleDateFormat outputFormat = new SimpleDateFormat("MM"); // 01-12

                                    dfdnf = outputFormat.format(cal.getTime());
                                } catch (java.text.ParseException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                String dateformsubmit = days+"-"+dfdnf+"-"+years;

                                Log.d( "urlp3: ", String.valueOf(dateformsubmit));

                                notificationid = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                                BirthdayModel birthdayModel =  new BirthdayModel(psid,sactivityname,dateformsubmit,selectedUriList.get(0).toString(),psid);
                                map.put(notificationid, birthdayModel);


                                mProgressDialog = new ProgressDialog(BirthdayActivity.this);
                                mProgressDialog.setIndeterminate(true);
                                mProgressDialog.setMessage("Loading...");
                                mProgressDialog.setCanceledOnTouchOutside(false);
                                mProgressDialog.show();

                                Submit_Birthday();

                                //       Submit_Birthdayexa();
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

    }


    @Override
    public void onBackPressed() {
        sactivityname = activityname.getText().toString().trim();
        if (map.isEmpty()&&sactivityname.isEmpty()&&(selectedUriList==null || selectedUriList.isEmpty()))
        {
            Intent splashLoginm = new Intent(getApplicationContext(), MainActivity.class);
            splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(splashLoginm);
            finish();
        }
        else{
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit? YOur Information Will Get Erase")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent splashLoginm = new Intent(getApplicationContext(), MainActivity.class);
                            splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(splashLoginm);
                            finish();
                        }
                    }).create().show();
        }

    }
    }
