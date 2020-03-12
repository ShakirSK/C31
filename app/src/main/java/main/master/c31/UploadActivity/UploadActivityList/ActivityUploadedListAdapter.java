package main.master.c31.UploadActivity.UploadActivityList;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import main.master.c31.R;


public class ActivityUploadedListAdapter extends RecyclerView.Adapter<ActivityUploadedListAdapter.MyViewHolder>
{
    ArrayList text1;
    ArrayList text2;
    ArrayList text3;
    Context context;

    public ActivityUploadedListAdapter(Context context,ArrayList text1,ArrayList text2,ArrayList text3) {
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
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
        holder.activityname.setText((CharSequence) text1.get(position));
        holder.nofp.setText((CharSequence) text2.get(position));
        holder.status.setText((CharSequence) text3.get(position));

    }

    @Override
    public int getItemCount() {
        return text1.size();
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