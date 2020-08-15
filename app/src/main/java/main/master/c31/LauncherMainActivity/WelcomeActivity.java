package main.master.c31.LauncherMainActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import main.master.c31.Database.DatabaseClient;
import main.master.c31.R;
import main.master.c31.Session.SaveSharedPreference;

public class WelcomeActivity extends AppCompatActivity {

    TextView getstart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        getstart = (TextView)findViewById(R.id.getstart);

        getstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent splashLoginm = new Intent(WelcomeActivity.this, LoginActivity.class);
                splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(splashLoginm);
                finish();
               // Toast.makeText(this, "Subscribe Topic: creative" , Toast.LENGTH_SHORT).show()
                //Toast.makeText(getApplicationContext(), "Creative Connect", Toast.LENGTH_SHORT).show();

            }
        });
    }



}
