package main.master.c31.LauncherMainActivity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import main.master.c31.R;

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
            }
        });
    }
}
