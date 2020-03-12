package main.master.c31.FacebookPageRequirement;

import android.content.Intent;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import main.master.c31.FacebookPageRequirement.pick3topicimages.PickFBRequirementMainActivity;
import main.master.c31.R;

public class FacebookPageRequirementMain extends AppCompatActivity {

    ListView listView;
    ArrayList arrayList = new ArrayList<>(Arrays.asList("FIVE PICTURE OF KIDS","STAFF PICTURES","INFRASTRUCTURE PICTURES"));
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_page_requirement_main);
        listView=(ListView)findViewById(R.id.listviewforfbpagelist);
        button = (Button)findViewById(R.id.button);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        listView.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.statusbarcolor));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PickFBRequirementMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
