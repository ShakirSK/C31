package main.master.c31.ArtWork;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import com.bumptech.glide.Glide;
import gun0912.tedbottompicker.TedBottomPicker;
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
import retrofit2.Response;

public class Artwork_MainActivity extends AppCompatActivity {

    TextView date1,date2,save;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    private EditText name, size, content,vanue,description;
    String datetosend;
    UserService userService;
    String sname,ssize,scontent,svanue,sdescription,psid;
    private List<Uri> selectedUriList;
    ImageView selectedimage;
    String urlString;
    MultipartBody.Part body;
    ImageView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork__main);

        SharedPreferences sh
                = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        psid= sh.getString("id", "");

        userService = ApiUtils.getUserService();


        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.statusbarcolor));
        }

        backbutton = (ImageView) findViewById(R.id.close);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        selectedimage = (ImageView)findViewById(R.id.selectedimage);
        name = findViewById(R.id.name);
        size = findViewById(R.id.size);
        content = findViewById(R.id.content);
        vanue = findViewById(R.id.vanue);
        description = findViewById(R.id.description);

        date1 = (TextView)findViewById(R.id.date1);

        save = (TextView)findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //checking if internet available
                if (!ConnectionDetector.networkStatus(getApplicationContext())) {

                    //   Toast.makeText(getApplicationContext(),"tre",Toast.LENGTH_SHORT).show();
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Artwork_MainActivity.this).create();
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


                sname = name.getText().toString().trim();
                  ssize = size.getText().toString().trim();
                 scontent = content.getText().toString().trim();
                 svanue = vanue.getText().toString().trim();
                 sdescription = description.getText().toString().trim();

                if (sname.isEmpty()) {
                    name.setError("Name required");
                    name.requestFocus();
                    return;
                }

                if (ssize.isEmpty()) {
                    size.setError("Size required");
                    size.requestFocus();
                    return;
                }

             /*   if (scontent.isEmpty()) {
                    content.setError("Content required");
                    content.requestFocus();
                    return;
                }

                if (svanue.isEmpty()) {
                    vanue.setError("Venue required");
                    vanue.requestFocus();
                    return;
                }*/
                if (sdescription.isEmpty()) {
                    description.setError("Description required");
                    description.requestFocus();
                    return;
                }



                AlertDialog.Builder builder = new AlertDialog.Builder(Artwork_MainActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Submit_Artwork();

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

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Artwork_MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date1.setText(day + "/" + (month + 1) + "/" + year);
                                datetosend=day + "-" + (month + 1) + "-" + year;
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


      /*  date2 = (TextView)findViewById(R.id.date2);

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Artwork_MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date2.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
*/
    }
    private void Submit_Artwork() {

        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

/*
        final ArtworkModel artwork = new ArtworkModel(
                psid,  sname,
                 ssize,  datetosend,  svanue,
                 scontent,  sdescription, psid);*/

        if(selectedUriList!=null){
            Log.d( "urlp1: ", String.valueOf(selectedUriList));
            String ere = removeUriFromPath(selectedUriList.get(0).toString());
            urlString = Uri.decode(ere);
            File imgFile = new File(urlString);

            RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), imgFile);
            body = MultipartBody.Part.createFormData("file", imgFile.getName(), reqFile);
        }



        String spreschool_id = psid;
        RequestBody preschool_id =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, spreschool_id);

        String sfb_post_name = sname ;
        RequestBody fb_post_name =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sfb_post_name);


        String sfb_ssize = ssize ;
        RequestBody fb_ssize =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sfb_ssize);

        String sfb_datetosend = datetosend ;
        RequestBody fb_datetosend =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sfb_datetosend);


        String sfb_svanue = svanue ;
        RequestBody fb_svanue =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sfb_svanue);


        String sfb_scontent = scontent ;
        RequestBody fb_scontent =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sfb_scontent);

        String sfb_sdescription = sdescription ;
        RequestBody fb_sdescription =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sfb_sdescription);


        String screated_by = psid;
        RequestBody created_by =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, screated_by);

        Call<ResponseBody> call = userService.Artwork(preschool_id,
                fb_post_name,fb_ssize,fb_datetosend,
                fb_svanue,fb_scontent,fb_sdescription,created_by,body);

            call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                Log.d("onResponse: ", response.toString());
                if(response.isSuccessful()){

                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    //   Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();


                        Toast.makeText(Artwork_MainActivity.this, "ArtWork Request is successfully submited", Toast.LENGTH_SHORT).show();
                        Intent splashLoginm = new Intent(Artwork_MainActivity.this, MainActivity.class);
                        splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(splashLoginm);
                        finish();

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
                    Toast.makeText(Artwork_MainActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                Toast.makeText(Artwork_MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void SelectImage(View view){
        TedBottomPicker.with(Artwork_MainActivity.this)
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


}
