package main.master.c31.UploadActivity.UploadActivityList;

import android.content.Context;
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

import main.master.c31.R;


public class ActivityUploadedListAdapter extends RecyclerView.Adapter<ActivityUploadedListAdapter.MyViewHolder>
{
    List<ActivityModel> loginResponse;

    Context context;

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
                                if(datum.getSocialMediaManagerStatus().equals("1")){
                                    holder.status.setText("Scheduled/Uploaded");
                                }
                                else if(datum.getGraphicsdesignerstatus().equals("1")){
                                    holder.status.setText("Sent for Upload");
                                }
                                else {
                                    holder.status.setText("Pending");
                                }
                         }
                     }
                     else{
                         holder.activityname.setText(datum.getEventname());
                           holder.nofp.setText(datum.getEventfromdate());

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
                 else{
                     holder.activityname.setText(datum.getActivityName());
                     holder.nofp.setVisibility(View.GONE);

                     if(datum.getSocialMediaManagerStatus().equals("1")){
                         holder.status.setText("Uploaded On FB");
                     }
                     else {
                         holder.status.setText("Pending");
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
}