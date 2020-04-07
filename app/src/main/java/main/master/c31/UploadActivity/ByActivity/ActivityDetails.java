package main.master.c31.UploadActivity.ByActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import main.master.c31.FacebookPageRequirement.change_in_activity.FBRequirementMenuActivity;
import main.master.c31.R;

import java.io.File;
import java.util.Calendar;

public class ActivityDetails extends AppCompatActivity {

    TextView date,save;
    EditText activityname,activitydescription;
    String sactivityname,sactivitydescription,datesubmit;

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        activityname = (EditText) findViewById(R.id.activityname);
        activitydescription = (EditText) findViewById(R.id.activitydescription);

        date = (TextView)findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(ActivityDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);

                                datesubmit = day + "-" + (month + 1) + "-" + year;
                            }
                        }, year, month, dayOfMonth);
                //   datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        save = (TextView)findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sactivityname = activityname.getText().toString().trim();

                sactivitydescription = activitydescription.getText().toString().trim();


                if (sactivityname.isEmpty()) {
                    activityname.setError("Name required");
                    activityname.requestFocus();
                    return;
                }


                if (sactivitydescription.isEmpty()) {
                    activitydescription.setError("Description required");
                    activitydescription.requestFocus();
                    return;
                }

                if (datesubmit==null) {
                    Toast.makeText(getApplicationContext(),"Date required",Toast.LENGTH_SHORT).show();
                    return;
                }



                Intent it = new Intent(getApplicationContext(), ActivityPickImage.class);
                it.putExtra("activityname", sactivityname);
                it.putExtra("activitydescription", sactivitydescription);
                it.putExtra("date", datesubmit);
                startActivity(it);

            }
        });






    }
}
