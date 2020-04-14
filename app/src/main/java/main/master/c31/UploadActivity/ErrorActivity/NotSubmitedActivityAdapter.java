package main.master.c31.UploadActivity.ErrorActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.R;
import main.master.c31.Room.ActivityTable;
import main.master.c31.testimage.WorkManager13;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class NotSubmitedActivityAdapter extends RecyclerView.Adapter<NotSubmitedActivityAdapter.MyViewHolder>
{
    List<ActivityTable> loginResponse;

    Context context;
    int notificationid;
    String sactivityname,sactivitydescription,datesubmit;

    public NotSubmitedActivityAdapter(Context context, List<ActivityTable> loginResponse ) {
        this.loginResponse = loginResponse;
        this.context = context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_adapter,parent,false);
        return new NotSubmitedActivityAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        /*ListItem listItem = name.get(position);
        holder.text1.setText(listItem.getText());*/

        ActivityTable datum = loginResponse.get(position);
        holder.activityname.setText(datum.getActivityname());

        sactivityname = datum.getActivityname();
        sactivitydescription =datum.getDescription();
        datesubmit = datum.getActivitydate();

        holder.image1.setImageResource(R.drawable.ic_settings_backup_restore_black_24dp);

        holder.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                       startWork();


                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {

                                Toast.makeText(context, "We will Notify you when  Activity "+sactivityname+" is uploaded", Toast.LENGTH_SHORT).show();

                                Intent splashLoginm = new Intent(context, MainActivity.class);
                                splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(splashLoginm);

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




        });


    }

    @Override
    public int getItemCount() {
        return loginResponse.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public TextView activityname;
        public ImageView image1;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            activityname=(TextView)itemView.findViewById(R.id.reportname);
            image1=(ImageView)itemView.findViewById(R.id.image);
        }


        @Override
        public void onClick(View view) {

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startWork(){
        notificationid = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
       String targetList = "first";
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkManagerPending.class)
                .setInputData(createInputData(sactivityname))
                .setInitialDelay(2, TimeUnit.SECONDS).build();
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
    }

    private Data createInputData(String sactivityname){
        Data data = new Data.Builder()
                .putString("sactivityname",sactivityname)
                .putString("sactivitydescription",sactivitydescription)
                .putString("datesubmit",datesubmit)
                .putString("notificationid", String.valueOf(notificationid))
                .build();
        return data;
    }
}