package main.master.c31.ArtWork;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import main.master.c31.LauncherMainActivity.MainActivity;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Artwork_MainActivity extends AppCompatActivity {

    TextView date1,date2,save;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    private EditText name, size, content,vanue,description;
    String datetosend;
    UserService userService;
    String sname,ssize,scontent,svanue,sdescription,psid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork__main);

        SharedPreferences sh
                = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        psid= sh.getString("id", "");

        userService = ApiUtils.getUserService();


        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.statusbarcolor));
        }


        name = findViewById(R.id.name);
        size = findViewById(R.id.size);
        content = findViewById(R.id.content);
        vanue = findViewById(R.id.vanue);
        description = findViewById(R.id.description);

        date1 = (TextView)findViewById(R.id.date1);

        save = (TextView)findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 sname = name.getText().toString().trim();
                  ssize = size.getText().toString().trim();
                 scontent = content.getText().toString().trim();
                 svanue = vanue.getText().toString().trim();
                 sdescription = description.getText().toString().trim();

                if (sname.isEmpty()) {
                    name.setError("Name required");
                    name.requestFocus();
                    return;
                }

                if (ssize.isEmpty()) {
                    size.setError("Size required");
                    size.requestFocus();
                    return;
                }

                if (scontent.isEmpty()) {
                    content.setError("Content required");
                    content.requestFocus();
                    return;
                }

                if (svanue.isEmpty()) {
                    vanue.setError("Venue required");
                    vanue.requestFocus();
                    return;
                }
                if (sdescription.isEmpty()) {
                    description.setError("Description required");
                    description.requestFocus();
                    return;
                }



                AlertDialog.Builder builder = new AlertDialog.Builder(Artwork_MainActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Submit_Artwork();

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

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Artwork_MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date1.setText(day + "/" + (month + 1) + "/" + year);
                                datetosend=day + "-" + (month + 1) + "-" + year;
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


      /*  date2 = (TextView)findViewById(R.id.date2);

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Artwork_MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date2.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
*/
    }
    private void Submit_Artwork() {

        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();


        final ArtworkModel artwork = new ArtworkModel(
                psid,  sname,
                 ssize,  datetosend,  svanue,
                 scontent,  sdescription, psid);

            Call<ArtworkResponse> call = userService.Artwork(artwork);
            call.enqueue(new Callback<ArtworkResponse>() {
            @Override
            public void onResponse(Call<ArtworkResponse> call, Response<ArtworkResponse> response) {

                Log.d("onResponse: ", response.toString());
                if(response.isSuccessful()){

                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    //   Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    ArtworkResponse loginResponse = response.body();

                    Log.e("keshav", "loginResponse 1 --> " + loginResponse);
                    if (loginResponse != null) {
                        Toast.makeText(Artwork_MainActivity.this, "ArtWork Request is successfully submited", Toast.LENGTH_SHORT).show();
                        Intent splashLoginm = new Intent(Artwork_MainActivity.this, MainActivity.class);
                        splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(splashLoginm);
                        finish();
                    }

              /*      loginObj resObj = response.body();
                    if(resObj.getMessage().equals("true")){
                        //login start main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);

                    } else {
                       Toast.makeText(LoginActivity.this, "The username or password is incorrect", Toast.LENGTH_SHORT).show();
                     }*/
                } else {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Toast.makeText(Artwork_MainActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArtworkResponse> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                Toast.makeText(Artwork_MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
