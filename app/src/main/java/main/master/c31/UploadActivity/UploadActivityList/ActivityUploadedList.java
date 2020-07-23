package main.master.c31.UploadActivity.UploadActivityList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

import main.master.c31.ArtWork.Artwork_MainActivity;
import main.master.c31.Birthday.BirthdayModel;
import main.master.c31.Birthday.byactivity.BirthdayActivity;
import main.master.c31.EventDetails.EventDetails_MainActivity;
import main.master.c31.FacebookPageRequirement.FacebookPageRequirementMain;
import main.master.c31.FacebookpostRequest.byactivity.FBpostInfo;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import main.master.c31.UploadActivity.ByActivity.ActivityDetails;
import main.master.c31.UploadActivity.ErrorActivity.NotSubmitedActivity;
import main.master.c31.utils.ConnectionDetector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityUploadedList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList activityname = new ArrayList<>(Arrays.asList("Pink Day Celebration","Sports Day |","Sports Day ||","Red Day Celebration"));
    ArrayList status = new ArrayList<>(Arrays.asList("Uploaded On FB","Pending","Pending","Uploaded On FB"));
    ArrayList noofp = new ArrayList<>(Arrays.asList("52","102","80","22"));


    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    TextView newupload,titlename;
    ImageView backbutton,pendingactivity;
    String gettingfromintent;
    UserService userService;
    String psid;
    public static String checkActivityCategory;

    //hashmap for multiple birthday
   public static TreeMap<Integer, BirthdayModel> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_list);
        userService = ApiUtils.getUserService();

        SharedPreferences sh
                = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        psid= sh.getString("id", "");

        Intent intent = getIntent();
        checkActivityCategory = intent.getStringExtra("fromactivity2");
        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.statusbarcolor));
        }
        recyclerView = (RecyclerView)findViewById(R.id.recycleview);
        newupload = (TextView)findViewById(R.id.newupload);
        titlename = (TextView)findViewById(R.id.titlename);

        backbutton = (ImageView) findViewById(R.id.close);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        pendingactivity = (ImageView) findViewById(R.id.pendingactivity);
        pendingactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotSubmitedActivity.class);
                startActivity(intent);
            }
        });



            if(intent.getStringExtra("fromactivity").equals("activity")){
                pendingactivity.setVisibility(View.VISIBLE);
                titlename.setText("Uploaded Activities");
                newupload.setText("Upload New Activity");

                getuploadedData("Uploaded Activities","ActivityAPI","activity",psid);
            }
            else if(intent.getStringExtra("fromactivity").equals("facebookrequest")){
                titlename.setText("Requests Sent");
                newupload.setText("New Post Request");

                getuploadedData("Requests Sent","FbpostAPI","fbpost",psid);

            }
                      /*  else if(intent.getStringExtra("fromactivity").equals("facebookrequire")){
                            titlename.setText("Uploaded Images");
                            newupload.setText("Upload New Image");

                            getuploadedData("FbcreativeAPI","fbcreative","3");
                        }*/
            else if(intent.getStringExtra("fromactivity").equals("birthday_activity")){
                titlename.setText("Uploaded Birthdays");
                newupload.setText("Upload New Birthday");

                getuploadedData("Uploaded Birthdays","BirthdayAPI","birthday",psid);
            }
            else if(intent.getStringExtra("fromactivity").equals("Artwork")){
                titlename.setText("Artwork Requests");
                newupload.setText("Request New Artwork");

                getuploadedData("Artwork Requests Found","ArtworksAPI","artwork",psid);
            }
            else if(intent.getStringExtra("fromactivity").equals("EventDetails")){
                titlename.setText("Uploaded Events");
                newupload.setText("Upload New Event");

                getuploadedData("Uploaded Events","EventAPI","event",psid);
            }





        newupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(intent.getStringExtra("fromactivity2").equals("activityvideo")){
                    //Intent intent = new Intent(getApplicationContext(), UploadActivityMain.class);
                    Intent intent = new Intent(getApplicationContext(), ActivityDetails.class);
                    intent.putExtra("fromactivity","activityvideo");
                    startActivity(intent);
                }
              else if(intent.getStringExtra("fromactivity").equals("activity")){
                  //Intent intent = new Intent(getApplicationContext(), UploadActivityMain.class);
                  Intent intent = new Intent(getApplicationContext(), ActivityDetails.class);
                  intent.putExtra("fromactivity","activity");
                  startActivity(intent);
              }

                else if(intent.getStringExtra("fromactivity").equals("facebookrequest")){

                  Intent intent = new Intent(getApplicationContext(), FBpostInfo.class);
                  startActivity(intent);
              }
              else if(intent.getStringExtra("fromactivity").equals("facebookrequire")){

                  Intent intent = new Intent(getApplicationContext(), FacebookPageRequirementMain.class);
                  startActivity(intent);
              }
              else if(intent.getStringExtra("fromactivity").equals("birthday_activity")){
                   map = new TreeMap<Integer, BirthdayModel>();

                  Intent intent = new Intent(getApplicationContext(), BirthdayActivity.class);
                  startActivity(intent);
              }
              else if(intent.getStringExtra("fromactivity").equals("Artwork")){

                  Intent intent = new Intent(getApplicationContext(), Artwork_MainActivity.class);
                  startActivity(intent);
              }
              else if(intent.getStringExtra("fromactivity").equals("EventDetails")){

                  Intent intent = new Intent(getApplicationContext(), EventDetails_MainActivity.class);
                  startActivity(intent);
              }

            }
        });

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());






    }

    public void getuploadedData(String stringtoast,String Actapi,String Act,String pid){



        //checking if internet available
        if (!ConnectionDetector.networkStatus(getApplicationContext())) {

            //   Toast.makeText(getApplicationContext(),"tre",Toast.LENGTH_SHORT).show();
            AlertDialog alertDialog = new AlertDialog.Builder(ActivityUploadedList.this).create();
            alertDialog.setTitle("No Internet Connection");
            alertDialog.setMessage("Please check your internet connection  and try again");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
        // if internet connection is available
        else {
            ProgressDialog mProgressDialog;
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();


            Call<List<ActivityModel>> call = userService.posturdata(Actapi, Act, pid);
            call.enqueue(new Callback<List<ActivityModel>>() {
                @Override
                public void onResponse(Call<List<ActivityModel>> call, Response<List<ActivityModel>> response) {
                    Log.d("onResponse: ", response.toString());
                    if (response.message().equals("Not Found")) {
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        Toast.makeText(ActivityUploadedList.this, "No "+stringtoast, Toast.LENGTH_SHORT).show();
                    } else if (response.isSuccessful()) {

                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        //   Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        List<ActivityModel> loginResponse = response.body();

                        Log.e("keshav", "loginResponse 1 --> " + loginResponse);

                        if (loginResponse != null) {


                            for (int i = 0; i < loginResponse.size(); i++) {
                                ActivityModel datum = loginResponse.get(i);
                                Log.e("keshav", "getUserId          -->  " + datum.getActivityName());

                            }
                        }

                        ActivityUploadedListAdapter adapter = new ActivityUploadedListAdapter(getApplicationContext(), loginResponse);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.addItemDecoration(dividerItemDecoration);
                        recyclerView.setAdapter(adapter);

                    } else {
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        Toast.makeText(ActivityUploadedList.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ActivityModel>> call, Throwable t) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Log.d("onResponse: ", t.getMessage());
                    Toast.makeText(ActivityUploadedList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}
