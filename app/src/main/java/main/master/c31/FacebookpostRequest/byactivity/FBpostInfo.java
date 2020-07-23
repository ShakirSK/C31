package main.master.c31.FacebookpostRequest.byactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import main.master.c31.R;
import main.master.c31.UploadActivity.ByActivity.ActivityDetails;
import main.master.c31.utils.ConnectionDetector;

public class FBpostInfo extends AppCompatActivity {
    LinearLayout one,two;
    CheckBox cbone,cbtwo;
    EditText changeinmakein;
    TextView save;
    EditText activityname,description,link;
    String sactivityname,sdescription,slink,schangeinmakein,psid;
    int checkboxselect_status=10;
    ImageView backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbpost_info);


        backbutton = (ImageView) findViewById(R.id.close);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        one = (LinearLayout)findViewById(R.id.one);
        two = (LinearLayout)findViewById(R.id.two);

        cbone = (CheckBox)findViewById(R.id.checkbox1);
        cbtwo = (CheckBox)findViewById(R.id.checkbox2);
        changeinmakein = (EditText)findViewById(R.id.changeinmakein);
        activityname = (EditText)findViewById(R.id.activityname);
        description = (EditText)findViewById(R.id.description);
        link = (EditText)findViewById(R.id.link);


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cbtwo.setChecked(false);
                cbone.setChecked(true);
                changeinmakein.setVisibility(View.VISIBLE);
                checkboxselect_status=1;


            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cbtwo.setChecked(true);
                cbone.setChecked(false);
                changeinmakein.setVisibility(View.GONE);
                checkboxselect_status=0;
            }
        });

        save = (TextView)findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking if internet available
                if (!ConnectionDetector.networkStatus(getApplicationContext())) {

                    //   Toast.makeText(getApplicationContext(),"tre",Toast.LENGTH_SHORT).show();
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(FBpostInfo.this).create();
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



                sactivityname = activityname.getText().toString().trim();

                sdescription = description.getText().toString().trim();

                slink = link.getText().toString().trim();

                if (sactivityname.isEmpty()) {
                    activityname.setError("Name required");
                    activityname.requestFocus();
                    return;
                }

                if (sdescription.isEmpty()) {
                    description.setError("Description required");
                    description.requestFocus();
                    return;
                }
                if (slink.isEmpty()) {
                    slink = null;
                }

                if(checkboxselect_status==10)
                {
                    Toast.makeText(getApplicationContext(),"Please select one of the checkbox",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(checkboxselect_status==1)
                {
                    schangeinmakein = changeinmakein.getText().toString().trim();

                    if (schangeinmakein.isEmpty()) {
                        changeinmakein.setError("Changes required");
                        changeinmakein.requestFocus();
                        return;
                    }
                }
                else if(checkboxselect_status==0)
                {
                    schangeinmakein="notanychanges";
                }



                Intent it = new Intent(getApplicationContext(), FBpostPickImage.class);
                it.putExtra("activityname", sactivityname);
                it.putExtra("activitydescription", sdescription);
                it.putExtra("link", slink);
                it.putExtra("schangeinmakein", schangeinmakein);
                it.putExtra("checkboxselect_status", checkboxselect_status);
                startActivity(it);

            }
        });
    }
}
