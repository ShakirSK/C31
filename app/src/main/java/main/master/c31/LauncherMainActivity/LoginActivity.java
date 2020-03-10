package main.master.c31.LauncherMainActivity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.Datum;
import main.master.c31.Network.Example;
import main.master.c31.Network.UserService;
import main.master.c31.Network.loginmodel;
import main.master.c31.R;
import retrofit2.Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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



                Intent splashLoginm = new Intent(LoginActivity.this, MainActivity.class);
                splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(splashLoginm);
                finish();
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

        final loginmodel login = new loginmodel(username, password);

        Call<Example> call = userService.login(login);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                Log.d("onResponse: ", response.toString());
                if(response.isSuccessful()){

                 //   Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    Example loginResponse = response.body();

                    Log.e("keshav", "loginResponse 1 --> " + loginResponse);
                    if (loginResponse != null) {

                        List<Datum> items = response.body().getData();

                        for (int i = 0;i<items.size();i++){
                            Datum datum= items.get(i);
                            Log.e("keshav", "getUserId          -->  " + datum.getPsName());

                        }



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
                    Toast.makeText(LoginActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
