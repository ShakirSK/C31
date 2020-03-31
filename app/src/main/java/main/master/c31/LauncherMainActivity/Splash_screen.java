package main.master.c31.LauncherMainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.R;
import main.master.c31.Session.SaveSharedPreference;

public class Splash_screen extends AppCompatActivity {

    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences("main.society365", Context.MODE_PRIVATE);



        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        }





        handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                // Check if UserResponse is Already Logged In
                if(SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
                    Intent splashLoginm = new Intent(Splash_screen.this, MainActivity.class);
                    splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(splashLoginm);
                    finish();
                } else {
                    Intent splashLoginm = new Intent(Splash_screen.this, WelcomeActivity.class);
                    splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(splashLoginm);
                    finish();
                }







            }
        },800);
    }
}
