package main.master.c31.UploadActivity.UploadActivityList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import main.master.c31.utils.ConnectionDetector;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static main.master.c31.UploadActivity.UploadActivityList.ActivityUploadedList.checkActivityCategory;


public class ActivityUploadedListAdapter extends RecyclerView.Adapter<ActivityUploadedListAdapter.MyViewHolder>
{
    List<ActivityModel> loginResponse;

    Context context;
    UserService userService;
    String delete_activityid,delete_activityname;
    ProgressDialog mProgressDialog;

    public ActivityUploadedListAdapter(Context context, List<ActivityModel> loginResponse ) {
        this.loginResponse = loginResponse;
        this.context = context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_activity_uploaded_list,parent,false);
        return new ActivityUploadedListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        /*ListItem listItem = name.get(position);
        holder.text1.setText(listItem.getText());*/
        userService = ApiUtils.getUserService();

        ActivityModel datum = loginResponse.get(position);



             if (datum.getStudentName()==null){
                 if (datum.getActivityName()==null) {
                       if (datum.getEventname()==null){
                            if (datum.getArtworkname()==null){
                                if (datum.getFbpostname()==null){
                                      if (datum.getFbrequirements()==null){

                                         }
                                         else{
                                             holder.activityname.setText(datum.getFbrequirements());
                                             holder.nofp.setVisibility(View.GONE);
                                         }
                             }
                             else{
                                 holder.activityname.setText(datum.getFbpostname());
                                    holder.nofp.setVisibility(View.GONE);

                                    if(datum.getSocialMediaManagerStatus().equals("1")){
                                        holder.status.setText("Post Uploaded");
                                    }
                                    else if(datum.getGraphicsdesignerstatus().equals("1")){
                                        holder.status.setText("Request Accepted");
                                    }
                                    else {
                                        holder.status.setText("Pending");
                                    }
                             }
                         }
                         else{
                             holder.activityname.setText(datum.getArtworkname());
                                //holder.nofp.setText(datum.getDate());
                                holder.nofp.setVisibility(View.GONE);

                                if(datum.getSocialMediaManagerStatus().equals("0")&&datum.getGraphicsdesignerstatus().equals("0")){
                                    holder.status.setText("Pending");
                                }
                                else if(datum.getSocialMediaManagerStatus().equals("0")&&datum.getGraphicsdesignerstatus().equals("1")){
                                    holder.status.setText("Sent for Upload");
                                }
                                else if(datum.getSocialMediaManagerStatus().equals("1")&&datum.getGraphicsdesignerstatus().equals("1")){
                                    holder.status.setText("Scheduled/Uploaded");
                                }
                                else if(datum.getSocialMediaManagerStatus().equals("0")&&datum.getGraphicsdesignerstatus().equals("2")){
                                    holder.status.setText("Completed");
                                }
                              /*  if(datum.getSocialMediaManagerStatus().equals("1")){
                                    holder.status.setText("Scheduled/Uploaded");
                                }
                                else if(datum.getGraphicsdesignerstatus().equals("1")){
                                    holder.status.setText("Sent for Upload");
                                }
                                else {
                                    holder.status.setText("Pending");
                                }*/
                         }
                     }
                     else{
                         holder.activityname.setText(datum.getEventname());
                          // holder.nofp.setText(datum.getEventfromdate());
                           holder.nofp.setVisibility(View.GONE);
                           if(datum.getSocialMediaManagerStatus().equals("1")){
                               holder.status.setText("Event Created");
                           }
                           else if(datum.getGraphicsdesignerstatus().equals("1")){
                               holder.status.setText("Template Created");
                           }
                           else {
                               holder.status.setText("Pending");
                           }
                     }
                 }
                 //for activity
                 else{
                     if(checkActivityCategory.equals("activityvideo"))
                     {
                         if (datum.getIs_video().equals("1")){
                             holder.nofp.setVisibility(View.GONE);
                             holder.activityname.setText(datum.getActivityName());
                             //    holder.nofp.setVisibility(View.GONE);
                             holder.nofp.setText(datum.getPicturecount());
                             if(datum.getSocialMediaManagerStatus().equals("1")){
                                 holder.status.setText("Uploaded On FB");
                             }
                             else {
                                 holder.status.setText("Pending");
                             }
                         }
                         else{
                             holder.itemView.setVisibility(View.GONE);
                             holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

                         }
                     }
                     else
                     {
                         if (datum.getIs_video().equals("0")){
                             holder.activityname.setText(datum.getActivityName());
                             //    holder.nofp.setVisibility(View.GONE);
                             holder.nofp.setText(datum.getPicturecount());
                             if(datum.getSocialMediaManagerStatus().equals("1")){
                                 holder.status.setText("Uploaded On FB");
                             }
                             else {
                                 holder.status.setText("Pending");
                             }
                         }
                         else {
                             holder.itemView.setVisibility(View.GONE);
                             holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

                         }

                     }

                 }
            }
             else{
                 holder.activityname.setText(datum.getStudentName());
                 holder.nofp.setText(datum.getDob());
                 if(datum.getSocialMediaManagerStatus().equals("1")){
                     holder.status.setText("Scheduled/Uploaded");
                 }
                 else if(datum.getGraphicsdesignerstatus().equals("1")){
                     holder.status.setText("Template Created");
                 }
                 else {
                     holder.status.setText("Pending");
                 }
             }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkActivityCategory.equals("activityvideo") || checkActivityCategory.equals("activity"))
                {
                    if (holder.status.getText().toString().equals("Pending")) {

                        delete_activityname = datum.getActivityName();
                        delete_activityid = datum.getActivityId();

                        Toast.makeText(context, datum.getActivityId()+datum.getActivityName()+"Pending", Toast.LENGTH_SHORT).show();

                        //checking if internet available
                        if (!ConnectionDetector.networkStatus(context)) {

                            //   Toast.makeText(getApplicationContext(),"tre",Toast.LENGTH_SHORT).show();
                            android.app.AlertDialog alertDialog = new AlertDialog.Builder(view.getRootView().getContext()).create();
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Are you sure?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                mProgressDialog = new ProgressDialog(view.getRootView().getContext());
                                mProgressDialog.setIndeterminate(true);
                                mProgressDialog.setMessage("Loading...");
                                mProgressDialog.setCanceledOnTouchOutside(false);
                                mProgressDialog.show();


                                Delete_Activity();

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
                    else{
                        Toast.makeText(context, "Activity Is Uploaded On Fb , Can't Delete", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return loginResponse.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public TextView activityname,nofp,status;
        public ImageView image1,image2;
        LinearLayout fullbody;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            activityname=(TextView)itemView.findViewById(R.id.activityname);
            nofp=(TextView)itemView.findViewById(R.id.nofp);
            status =(TextView)itemView.findViewById(R.id.status);


        }


        @Override
        public void onClick(View view) {

        }
    }



    private void Delete_Activity() {



        String sdelete_activityid = delete_activityid;
        RequestBody rdelete_activityid =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sdelete_activityid);

        Call<ResponseBody> call = userService.DeleteActivity(rdelete_activityid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("onResponse: ", response.toString());
                if(response.isSuccessful()){

                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                     Toast.makeText(context, delete_activityname+" Successfully Deleted", Toast.LENGTH_SHORT).show();


                 } else {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Toast.makeText(context, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.d("onResponseup: ",t.getMessage());

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}