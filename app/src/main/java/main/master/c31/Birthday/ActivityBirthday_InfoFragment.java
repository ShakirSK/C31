package main.master.c31.Birthday;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.*;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.*;
import java.net.URLConnection;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import main.master.c31.ArtWork.ArtworkResponse;
import main.master.c31.EventDetails.EventDetailsModel;
import main.master.c31.EventDetails.EventDetails_MainActivity;
import main.master.c31.LauncherMainActivity.MainActivity;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.RetrofitClient;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static android.app.Activity.RESULT_OK;
import static gun0912.tedbottompicker.util.RealPathUtil.isExternalStorageDocument;
import static main.master.c31.Birthday.pickimagefragment.ActivityBirthday_PickImageFragment.birthdayimage;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityBirthday_InfoFragment extends Fragment  {

    String URL ="http://creative31minds.com/creative-web/api/TestAPI/testing";
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
    ArrayAdapter<Integer> dataAdapter;
    UserService userService;
    String datesubmit;
    String imageString;
    Button button;

    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
    private Bitmap bitmap;
    private String filePath;
    private File drImageFile = null;

    public ActivityBirthday_InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_activity_birthday__info, container, false);

        activityname = (EditText) view.findViewById(R.id.activityname);

        userService = ApiUtils.getUserService();

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
                                datesubmit = day + "-" + (month + 1) + "-" + year;
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);

           //     datePickerDialog.getDatePicker().setMinDate(calendar.getFirstDayOfWeek());


           //    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
               datePickerDialog.show();


              }
        });


        save = (TextView)view.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sactivityname = activityname.getText().toString().trim();


                if (sactivityname.isEmpty()) {
                    activityname.setError("Name required");
                    activityname.requestFocus();
                    return;
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(birthdayimage==null)
                        {
                            Toast.makeText(getContext(),"Please Select Student Image",Toast.LENGTH_SHORT).show();
                        }
                        else{
                           Submit_Birthday();
                        }
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


        final ArrayList<Integer> Daylist = new ArrayList<Integer>(30);



        for (int i = 1; i <=30; i++)
        {
            Daylist.add(i);
        }



        // Spinner element
        spinner = (Spinner) view.findViewById(R.id.spinner1);


        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, Daylist);

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
                        Toast.makeText(parent.getContext(), "Selected: " + days, Toast.LENGTH_LONG).show();


                    }
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });


        ArrayList<String> monthsList = new ArrayList<String>();
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length; i++) {
            String month = months[i];
            monthsList .add(months[i]);
        }


        // Spinner element
        final Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,monthsList);

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
                        Toast.makeText(parent.getContext(), "Selected: " + monthsubmit, Toast.LENGTH_LONG).show();

                    }
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });



        final ArrayList<Integer> Yearlist = new ArrayList<Integer>();
        Yearlist.add(2020);
        Yearlist.add(2021);


        // Spinner element
        final Spinner spinner3 = (Spinner) view.findViewById(R.id.spinner3);


        // Creating adapter for spinner
        ArrayAdapter<Integer> dataAdapter3 = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, Yearlist);

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
                        Toast.makeText(parent.getContext(), "Selected: " + years, Toast.LENGTH_LONG).show();

                    }
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });




        return view;
    }

    public void Submit_Birthday() {


        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        Log.d( "urlp1: ", String.valueOf(birthdayimage));


        String ere = removeUriFromPath(birthdayimage.toString());
        String urlString = Uri.decode(ere);
       // String  urlString = ere.replaceAll(" ", "%20");
        Log.d( "urlp2: ", String.valueOf(ere));

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



        File imgFile = new File(urlString);
        RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), imgFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imgFile.getName(), reqFile);



        String spreschool_id = "19";
        RequestBody preschool_id =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, spreschool_id);

        String sstudent_name = sactivityname ;
        RequestBody student_name =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sstudent_name);

        String sdob = dateformsubmit;
        RequestBody dob =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sdob);

        String screated_by = "19";
        RequestBody created_by =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, screated_by);


        Call<ResponseBody> call = userService.Birthday(preschool_id,
                student_name,dob,body, created_by);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.v("Upload", "success");
               // Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();

                Toast.makeText(getContext(), "Your Birthday is successfully submited", Toast.LENGTH_SHORT).show();
                Intent splashLoginm = new Intent(getContext(), MainActivity.class);
                splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(splashLoginm);
               getActivity().finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("Upload error:", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });








    }

    public static String removeUriFromPath(String uri)
    {
        return  uri.substring(7, uri.length());
    }


}
