package main.master.c31.UploadActivity.ByActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import gun0912.tedbottompicker.TedBottomPicker;
import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.R;
import main.master.c31.UploadActivity.PickimageFrag.ActivityPickImageFragmentAdapter;
import main.master.c31.testimage.WorkManager13;
import main.master.c31.utils.ConnectionDetector;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ActivityPickImage extends AppCompatActivity {


    GridView simpleGrid;
    private List<Uri> selectedUriList;
    TextView selectphoto,submit;
    List<Uri> activitylisturi;
    String sactivityname,sactivitydescription,datesubmit;
    List<String> targetList;
    ProgressDialog mProgressDialog;
    int notificationid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_image);


        Intent intent = getIntent();
        sactivityname = intent.getStringExtra("activityname");
        sactivitydescription = intent.getStringExtra("activitydescription");
        datesubmit = intent.getStringExtra("date");


        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        selectedUriList = new ArrayList<>();

        selectphoto = (TextView) findViewById(R.id.selectphoto);
        submit = (TextView) findViewById(R.id.submits);

        selectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking if internet available
                if (!ConnectionDetector.networkStatus(getApplicationContext())) {

                    //   Toast.makeText(getApplicationContext(),"tre",Toast.LENGTH_SHORT).show();
                    android.app.AlertDialog alertDialog = new AlertDialog.Builder(ActivityPickImage.this).create();
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



                TedBottomPicker.with(ActivityPickImage.this)
                        //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                        .setPeekHeight(1600)
                        .showTitle(true)
                        .setCompleteButtonText("Done")
                        .setEmptySelectionText("Nothing Selected")
                        .setPreviewMaxCount(200)
                        .setGalleryTileBackgroundResId(R.color.statusbarcolor)
                        .setGalleryTile(R.drawable.ic_gallery)
                        .setSelectedUriList(selectedUriList)
                        .showMultiImage(uriList -> {
                            selectedUriList = uriList;
                            showUriList(uriList);
                        });
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //checking if internet available
                if (!ConnectionDetector.networkStatus(getApplicationContext())) {

                    //   Toast.makeText(getApplicationContext(),"tre",Toast.LENGTH_SHORT).show();
                    android.app.AlertDialog alertDialog = new AlertDialog.Builder(ActivityPickImage.this).create();
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



                if(activitylisturi==null||activitylisturi.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Select Images",Toast.LENGTH_SHORT).show();
                }
                else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPickImage.this);
                    builder.setTitle("Are you sure?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            mProgressDialog = new ProgressDialog(ActivityPickImage.this);
                            mProgressDialog.setIndeterminate(true);
                            mProgressDialog.setMessage("Loading...");
                            mProgressDialog.setCanceledOnTouchOutside(false);
                            mProgressDialog.show();




                            //     new AsyncGettingBitmapFromUrl().execute();

                            startWork();

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "We will Notify you when  Activity "+sactivityname+" is uploaded", Toast.LENGTH_SHORT).show();

                                    Intent splashLoginm = new Intent(getApplicationContext(), MainActivity.class);
                                    splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(splashLoginm);

                                }
                            }, 100);



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
        });

    }

    private void showUriList(List<Uri> uriList) {
        activitylisturi = new ArrayList<>() ;
        activitylisturi = uriList;
        ActivityPickImageFragmentAdapter customAdapter = new ActivityPickImageFragmentAdapter(getApplicationContext(), uriList);
        simpleGrid.setAdapter(customAdapter);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startWork(){
        notificationid = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        targetList = new ArrayList<String>();
        selectedUriList.forEach(uri -> targetList.add(uri.toString()));
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkManager13.class)
                .setInputData(createInputData(targetList))
                .setInitialDelay(2, TimeUnit.SECONDS).build();
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
    }

    private Data createInputData(List<String> imagePath){
        Data data = new Data.Builder()
                .putStringArray("imagePath", imagePath.toArray(new String[imagePath.size()]))
                .putString("sactivityname",sactivityname)
                .putString("sactivitydescription",sactivitydescription)
                .putString("datesubmit",datesubmit)
                .putString("notificationid", String.valueOf(notificationid))
                .build();
        return data;
    }
}
