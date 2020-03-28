package main.master.c31.LauncherMainActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import main.master.c31.Database.DatabaseClient;
import main.master.c31.Database.User;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.Datum;
import main.master.c31.Network.Example;
import main.master.c31.Network.UserService;
import main.master.c31.Network.loginmodel;
import main.master.c31.R;
import main.master.c31.Session.SaveSharedPreference;
import retrofit2.Response;

import retrofit2.Call;
import retrofit2.Callback;


public class LoginActivity extends AppCompatActivity {
    EditText edtUsername;
    EditText edtPassword;
    ImageView submit;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = (EditText) findViewById(R.id.emailid);
        edtPassword = (EditText) findViewById(R.id.pass);

        submit = (ImageView)findViewById(R.id.submit);

        userService = ApiUtils.getUserService();



        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        }



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                //validate form
                if(validateLogin(username, password)){
                    //do login
                    doLogin(username, password);
                }




            }
        });
    }

    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(final String username,final String password){

        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        final loginmodel login = new loginmodel(username, password);

        Call<Example> call = userService.login(login);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                Log.d("onResponse: ", response.toString());
                if(response.isSuccessful()){

                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                 //   Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    Example loginResponse = response.body();

                    Log.e("keshav", "loginResponse 1 --> " + loginResponse);
                    if (loginResponse != null) {

                        List<Datum> items = response.body().getData();


                        new  AsyncTaskFunding(items).execute();





                   /*     Log.e("keshav", "getFirstName       -->  " + loginResponse.getFirstName());
                        Log.e("keshav", "getLastName        -->  " + loginResponse.getLastName());
                        Log.e("keshav", "getProfilePicture  -->  " + loginResponse.getProfilePicture());
*/
                    /*    String responseCode = loginResponse.getResponseCode();
                        Log.e("keshav", "getResponseCode  -->  " + loginResponse.getResponseCode());
                        Log.e("keshav", "getResponseMessage  -->  " + loginResponse.getResponseMessage());
                        if (responseCode != null && responseCode.equals("404")) {
                            Toast.makeText(MainActivity.this, "Invalid Login Details \n Please try again", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Welcome " + loginResponse.getFirstName(), Toast.LENGTH_SHORT).show();
                        }*/
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
                    Toast.makeText(LoginActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //insert in to Room DB
    public class AsyncTaskFunding extends AsyncTask<String,String,String> {

        List<Datum> record;

        AsyncTaskFunding(List<Datum> record){
            this.record = record;
        }
        @Override
        protected String doInBackground(String... params) {

            Log.d( "doInBackgrounsize", String.valueOf(record.size()));
            //setting my  List<Record> to Room Model Funding
            for (int i = 0;i<record.size();i++){
                Datum datum= record.get(i);
                Log.e("keshav", "getUserId          -->  " + datum.getPsName());

                //creating a task
                User user = new User();
                user.setPreschool_id(datum.getPreschoolId());
                user.setPs_name(datum.getPsName());
                user.setPs_activities(datum.getPsActivities());
                user.setPs_email(datum.getPsEmail());
                user.setPs_mobile(datum.getPsMobile());


                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .userDao()
                        .insert(user);
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            // Set Logged In statue to 'true'
            SaveSharedPreference.setLoggedIn(getApplicationContext(), true);

            Intent splashLoginm = new Intent(LoginActivity.this, MainActivity.class);
            splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(splashLoginm);
            finish();

            //   Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
            //logout
            //https://medium.com/viithiisys/android-manage-user-session-using-shared-preferences-1187cb9c5cd8
        }
    }


}
