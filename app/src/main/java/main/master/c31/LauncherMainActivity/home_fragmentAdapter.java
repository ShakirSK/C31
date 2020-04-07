package main.master.c31.LauncherMainActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import main.master.c31.FacebookPageRequirement.FacebookPageRequirementMain;
import main.master.c31.R;
import main.master.c31.UploadActivity.UploadActivityList.ActivityUploadedList;
import main.master.c31.testimage.testimageActvity;

import static main.master.c31.LauncherMainActivity.home_fragment.arrayList;


public class home_fragmentAdapter extends RecyclerView.Adapter<home_fragmentAdapter.MyViewHolder> {
    ArrayList text1;
    ArrayList title;
    ArrayList activitydescription;
    Context context;

    private String token = null;


    public home_fragmentAdapter(Context context, ArrayList title, ArrayList text1,ArrayList activitydescription) {
        this.title = title;
        this.text1 = text1;
        this.activitydescription = activitydescription;
        this.context = context;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_fragment, parent, false);
        return new MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        /*ListItem listItem = name.get(position);
        holder.text1.setText(listItem.getText());*/

        if(arrayList.contains((CharSequence) title.get(position)))
        {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        else
        {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        holder.text1.setText((CharSequence) text1.get(position));
        holder.activitydescription.setText((CharSequence)activitydescription.get(position));



        if (position == 0) {
            holder.logos.setBackground(ContextCompat.getDrawable(context, R.drawable.uploadactivityicon));
          /*  holder.text3.setTextColor(Color.parseColor("#B4B4B4"));
            holder.text2.setTextColor(Color.parseColor("#B4B4B4"));
            holder.text1.setTextColor(Color.parseColor("#B4B4B4"));
      //      holder.card_view.setCardBackgroundColor(Color.parseColor("#5b5b5b"));
       */ } else if (position == 1) {
            holder.logos.setBackground(ContextCompat.getDrawable(context, R.drawable.birthdayuploads));

       /*     holder.text3.setTextColor(Color.parseColor("#5b5b5b"));
            holder.text2.setTextColor(Color.parseColor("#5b5b5b"));
            holder.text1.setTextColor(Color.parseColor("#5b5b5b"));

            holder.upload.setTextColor(Color.parseColor("#000000"));
            holder.line1.setBackgroundColor(Color.parseColor("#5b5b5b"));
      */  //  holder.card_view.setCardBackgroundColor(Color.parseColor("#c5c6c6"));
        //    holder.card_view.setBackgroundResource( R.drawable.gradient);
        }
        else if (position == 2) {
            holder.logos.setBackground(ContextCompat.getDrawable(context, R.drawable.eventupload));

            //          holder.card_view.setCardBackgroundColor(Color.parseColor("#1696e4"));
            //    holder.card_view.setBackgroundResource( R.drawable.gradient);
        }
        else if (position == 3) {
            holder.logos.setBackground(ContextCompat.getDrawable(context, R.drawable.facebook));

       /*     holder.text3.setTextColor(Color.parseColor("#B4B4B4"));
            holder.text2.setTextColor(Color.parseColor("#B4B4B4"));
            holder.text1.setTextColor(Color.parseColor("#B4B4B4"));
*/    //        holder.card_view.setCardBackgroundColor(Color.parseColor("#5b5b5b"));
            //    holder.card_view.setBackgroundResource( R.drawable.gradient);
        }
        else if (position == 4) {
            holder.logos.setBackground(ContextCompat.getDrawable(context, R.drawable.facebookpage));

          /*  holder.text3.setTextColor(Color.parseColor("#ec98b9"));
            holder.text2.setTextColor(Color.parseColor("#ec98b9"));
            holder.text1.setTextColor(Color.parseColor("#ec98b9"));
*/      //      holder.card_view.setCardBackgroundColor(Color.parseColor("#d93174"));
            //    holder.card_view.setBackgroundResource( R.drawable.gradient);
        }
        else if (position == 5) {
            holder.logos.setBackground(ContextCompat.getDrawable(context, R.drawable.artwortreq));

    /*   holder.text3.setTextColor(Color.parseColor("#F8C786"));
            holder.text2.setTextColor(Color.parseColor("#F8C786"));
            holder.text1.setTextColor(Color.parseColor("#F8C786"));
*/
           // holder.line1.setBackgroundColor(Color.parseColor("#5b5b5b"));

        //    holder.card_view.setBackgroundResource(R.color.colorPrimaryDark);
            //    holder.card_view.setBackgroundResource( R.drawable.gradient);
        }

        holder.connectsocail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==0)
                {
                    Intent intent = new Intent(context, ActivityUploadedList.class);
                    intent.putExtra("fromactivity","activity");
                    context.startActivity(intent);
                }
               else if(position==1)
                {
                    Intent intent = new Intent(context, ActivityUploadedList.class);
                    intent.putExtra("fromactivity","birthday_activity");
                    context.startActivity(intent);
                }
                else if(position==2)
                {
                    Intent intent = new Intent(context, ActivityUploadedList.class);
                    intent.putExtra("fromactivity","EventDetails");
                    context.startActivity(intent);
                }
               else if(position==3)
                {
                    Intent intent = new Intent(context, ActivityUploadedList.class);
                    intent.putExtra("fromactivity","facebookrequest");
                    context.startActivity(intent);
                }
                else if(position==4)
                {
                   /* Intent intent = new Intent(context, ActivityUploadedList.class);
                    intent.putExtra("fromactivity","facebookrequire");
                    context.startActivity(intent);*/
//                    Intent intent = new Intent(context, FacebookPageRequirementMain.class);
//                    context.startActivity(intent);

                    Intent intent = new Intent(context, testimageActvity.class);
                    context.startActivity(intent);
                }
                else if(position==5)
                {
                    Intent intent = new Intent(context, ActivityUploadedList.class);
                    intent.putExtra("fromactivity","Artwork");
                    context.startActivity(intent);
                }
                else
                {
                    Toast.makeText(view.getContext(), "faceb", Toast.LENGTH_SHORT).show();
                }
            }
        });




   /*     holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,Team_creation_page.class);
                context.startActivity(i);
            }
        });
        holder.progressBar.setProgress(30);

        holder.multiplewinnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rules_bottomsheet();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return text1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView text1, text3,upload,activitydescription;
        Button join;
        ProgressBar progressBar;
        LinearLayout multiplewinnings;
        CardView card_view;
        ImageView logos;
        LinearLayout connectsocail;
        View line1;


        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            text1 = (TextView) itemView.findViewById(R.id.title);
            activitydescription = (TextView) itemView.findViewById(R.id.activitydescription);
            text3 = (TextView) itemView.findViewById(R.id.title3);
            upload = (TextView) itemView.findViewById(R.id.upload);

            line1 = (View)itemView.findViewById(R.id.line1);
            logos = (ImageView) itemView.findViewById(R.id.logos);


            card_view = (CardView) itemView.findViewById(R.id.card_view);

            connectsocail = (LinearLayout) itemView.findViewById(R.id.connectsocail);


            //check already have access token

      /*      join=(Button)itemView.findViewById(R.id.join);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            multiplewinnings = (LinearLayout)itemView.findViewById(R.id.multiplewinnings);*/

        }

        @Override
        public void onClick(View v) {

        }

        public void setItemClickListener(ItemClickListener ic) {


        }
    }
}