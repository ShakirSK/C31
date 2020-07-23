package main.master.c31.LauncherMainActivity.UpdateProfile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import main.master.c31.Database.DatabaseClient;
import main.master.c31.Database.User;
import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import main.master.c31.utils.ConnectionDetector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {
    TextView textDummyHintUsername;
    TextView text_dummy_hint_mobilenumber;
    EditText edit_ownername,edit_mobilenumber,edit_emailid,edit_address,edit_facebook,edit_website;
    ImageView updateprofile;
     String sownername,smobilenumber,semailid,saddress,facebook,swebsite, spreschool_id;;

    UserService userService;
    ImageView backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        backbutton = (ImageView) findViewById(R.id.close);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userService = ApiUtils.getUserService();


        updateprofile = (ImageView) findViewById(R.id.updateprofile);
        textDummyHintUsername = (TextView) findViewById(R.id.text_dummy_hint_ownername);
        text_dummy_hint_mobilenumber = (TextView) findViewById(R.id.text_dummy_hint_mobilenumber);
        edit_ownername = (EditText) findViewById(R.id.edit_ownername);
        edit_mobilenumber = (EditText) findViewById(R.id.edit_mobilenumber);
        edit_emailid = (EditText) findViewById(R.id.edit_emailid);
        edit_address = (EditText) findViewById(R.id.edit_address);
        edit_facebook = (EditText) findViewById(R.id.edit_facebook);
        edit_website = (EditText) findViewById(R.id.edit_website);

        edit_ownername.requestFocus();
        edit_mobilenumber.requestFocus();
        edit_emailid.requestFocus();
        edit_address.requestFocus();
        edit_facebook.requestFocus();
        edit_website.requestFocus();

        Intent intent =  getIntent();

        spreschool_id = intent.getStringExtra("spreschool_id");
        sownername = intent.getStringExtra("sownername");
        smobilenumber = intent.getStringExtra("smobilenumber");
        semailid = intent.getStringExtra("semailid");
        saddress = intent.getStringExtra("saddress");
        facebook = intent.getStringExtra("facebook");
        swebsite  = intent.getStringExtra("swebsite");


        edit_ownername.setText(sownername);
        edit_mobilenumber.setText(smobilenumber);
        edit_emailid.setText(semailid);
        edit_address.setText(saddress);
        edit_facebook.setText(facebook);
        edit_website.setText(swebsite);


       /* // Username
        edit_ownername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            textDummyHintUsername.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (edit_ownername.getText().length() > 0)
                        textDummyHintUsername.setVisibility(View.VISIBLE);
                    else
                        textDummyHintUsername.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Password
        edit_mobilenumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            text_dummy_hint_mobilenumber.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (edit_mobilenumber.getText().length() > 0)
                        text_dummy_hint_mobilenumber.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_mobilenumber.setVisibility(View.INVISIBLE);
                }
            }
        });*/

        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        //checking if internet available
                        if (!ConnectionDetector.networkStatus(getApplicationContext())) {

                            //   Toast.makeText(getApplicationContext(),"tre",Toast.LENGTH_SHORT).show();
                            android.app.AlertDialog alertDialog = new AlertDialog.Builder(UpdateProfileActivity.this).create();
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


                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Submit_UpdateProfile();

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

    private void Submit_UpdateProfile() {

        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        sownername = edit_ownername.getText().toString().trim();
        semailid = edit_emailid.getText().toString().trim();
        saddress = edit_address.getText().toString().trim();
        smobilenumber = edit_mobilenumber.getText().toString().trim();
        swebsite = edit_website.getText().toString().trim();
        facebook = edit_facebook.getText().toString().trim();

     /*   final UpdatePofileModel artwork = new UpdatePofileModel(
                spreschool_id, edit_ownername.getText().toString().trim(),
                edit_emailid.getText().toString().trim(),
                edit_address.getText().toString().trim(),
                edit_mobilenumber.getText().toString().trim(),
                edit_website.getText().toString().trim(),
                edit_facebook.getText().toString().trim());*/

        final UpdatePofileModel artwork = new UpdatePofileModel(
                spreschool_id, sownername,
                semailid,saddress, smobilenumber,swebsite,facebook);

     /*   final UpdatePofileModel artwork = new UpdatePofileModel(
                "4", "Sanket",
                "creativeminds@gmail.com","Office", "9819229936","www.creative31minds.com","www.facebook.com/creative31minds");

       */ Log.d("updateprofile: ", spreschool_id+" "+ sownername+" "+
                semailid+" "+saddress+" "+ smobilenumber+" "+swebsite+" "+facebook);


        Call<UpdateProfileResponse> call = userService.UpdateProfile(artwork);
        call.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {

                Log.d("onResponse: ", response.toString());
                if(response.isSuccessful()){

                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    //   Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    UpdateProfileResponse loginResponse = response.body();

                    Log.e("keshav", "loginResponse 1 --> " + loginResponse);
                    if (loginResponse != null) {

                        UpdateUSER();
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
                    Toast.makeText(UpdateProfileActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.d("onResponseup: ",t.getMessage());

                Toast.makeText(UpdateProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void UpdateUSER() {
        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                User user = new User();
                user.setPreschool_id(spreschool_id);
                user.setPs_email(semailid);
                user.setPs_mobile(smobilenumber);
                user.setCenter_address(saddress);
                user.setOwnername(sownername);
                user.setFacebooklink(facebook);
                user.setWebsite(swebsite);


                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .userDao()
                        .updateUser(spreschool_id, sownername,
                                semailid,saddress, smobilenumber,swebsite,facebook);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Toast.makeText(UpdateProfileActivity.this, "Your Profile is successfully submited", Toast.LENGTH_SHORT).show();
                Intent splashLoginm = new Intent(UpdateProfileActivity.this, MainActivity.class);
                splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(splashLoginm);
                finish();
            }
        }

        UpdateTask dt = new UpdateTask();
        dt.execute();

    }

}
