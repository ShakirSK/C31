package main.master.c31.FacebookPageRequirement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import main.master.c31.FacebookPageRequirement.change_in_activity.FBRequirementMenuActivity;
import main.master.c31.R;
import main.master.c31.testimage.testimageActvity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder>
    {
        HashMap<Integer, String> text1;
        Context context;


        public MyListAdapter(Context context, HashMap<Integer, String> text1) {
            this.text1 = text1;
            this.context = context;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_adapter,parent,false);
            return new MyListAdapter.MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
        /*ListItem listItem = name.get(position);
        holder.text1.setText(listItem.getText());*/
            holder.text1.setText((CharSequence) text1.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(view.getContext(), FBRequirementMenuActivity.class);
                 //  it.putExtra("YourKeyHere", text1.get(position));
                    view.getContext().startActivity(it);
                   // Toast.makeText(context,text1.get(position),Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return text1.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

            public TextView text1;


            public MyViewHolder(View itemView) {
                super(itemView);

                itemView.setOnClickListener(this);

                text1=(TextView)itemView.findViewById(R.id.reportname);

                //fullbody=(LinearLayout)itemView.findViewById(R.id.fullbody);

            }


            @Override
            public void onClick(View view) {

            }
        }
    }
