package main.master.c31.LauncherMainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rollbar.android.Rollbar;
import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.R;
import main.master.c31.Session.SaveSharedPreference;

public class Splash_screen extends AppCompatActivity {

    Rollbar rollbar;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Rollbar.init(this);
         rollbar  = Rollbar.instance();
       rollbar.error(new Exception("This is a test error")); //remove this after initial testing

        int currentVer = Build.VERSION.SDK_INT;
        String currentVer2 = Build.VERSION.RELEASE;

        rollbar.debug(String.valueOf(currentVer)+" "+currentVer2+" "+ Build.MODEL);

        try {
       FirebaseMessaging.getInstance().subscribeToTopic("creative");
       Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
       rollbar.debug("Success");
   }catch (Exception e){
       Log.d("Exception", e.toString());
       Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
       rollbar.error(e.toString());
   }


      /*  FirebaseMessaging.getInstance().subscribeToTopic("creative").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
            }
        });*/


      //  Toast.makeText(this, "Subscribe Topic: creative" , Toast.LENGTH_SHORT).show();
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
