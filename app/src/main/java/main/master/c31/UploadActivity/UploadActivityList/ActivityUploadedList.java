package main.master.c31.UploadActivity.UploadActivityList;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import main.master.c31.ArtWork.Artwork_MainActivity;
import main.master.c31.Birthday.BirthdayMainActivity;
import main.master.c31.EventDetails.EventDetails_MainActivity;
import main.master.c31.FacebookPageRequirement.FacebookPageRequirementMain;
import main.master.c31.FacebookpostRequest.FacebookPageRequestMain;
import main.master.c31.R;
import main.master.c31.UploadActivity.UploadActivityMain;

public class ActivityUploadedList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList activityname = new ArrayList<>(Arrays.asList("Pink Day Celebration","Sports Day |","Sports Day ||","Red Day Celebration"));
    ArrayList status = new ArrayList<>(Arrays.asList("Uploaded On FB","Pending","Pending","Uploaded On FB"));
    ArrayList noofp = new ArrayList<>(Arrays.asList("52","102","80","22"));


    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    TextView newupload,titlename;
    ImageView backbutton;
    String gettingfromintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_list);


        Intent intent = getIntent();
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


        if(intent.getStringExtra("fromactivity").equals("activity")){
            titlename.setText("Uploaded Activities");
            newupload.setText("Upload New Activity");
        }
        else if(intent.getStringExtra("fromactivity").equals("facebookrequest")){
            titlename.setText("Requests Sent");
            newupload.setText("New Post Request");
        }
        else if(intent.getStringExtra("fromactivity").equals("facebookrequire")){
            titlename.setText("Uploaded Images");
            newupload.setText("Upload New Image");
        }
        else if(intent.getStringExtra("fromactivity").equals("birthday_activity")){
            titlename.setText("Uploaded Birthdays");
            newupload.setText("Upload New Birthday");
        }
        else if(intent.getStringExtra("fromactivity").equals("Artwork")){
            titlename.setText("Artwork Requests");
            newupload.setText("Request New Artwork");
        }
        else if(intent.getStringExtra("fromactivity").equals("EventDetails")){
            titlename.setText("Uploaded Events");
            newupload.setText("Upload New Event");
        }



        newupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(intent.getStringExtra("fromactivity").equals("activity")){
                  Intent intent = new Intent(getApplicationContext(), UploadActivityMain.class);
                  startActivity(intent);
              }
           else if(intent.getStringExtra("fromactivity").equals("facebookrequest")){

                  Intent intent = new Intent(getApplicationContext(), FacebookPageRequestMain.class);
                  startActivity(intent);
              }
              else if(intent.getStringExtra("fromactivity").equals("facebookrequire")){

                  Intent intent = new Intent(getApplicationContext(), FacebookPageRequirementMain.class);
                  startActivity(intent);
              }
              else if(intent.getStringExtra("fromactivity").equals("birthday_activity")){

                  Intent intent = new Intent(getApplicationContext(), BirthdayMainActivity.class);
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

        ActivityUploadedListAdapter adapter = new ActivityUploadedListAdapter(getApplicationContext(), activityname,noofp,status);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);


    }
}
