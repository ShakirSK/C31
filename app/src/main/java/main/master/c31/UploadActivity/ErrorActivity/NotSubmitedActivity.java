package main.master.c31.UploadActivity.ErrorActivity;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import main.master.c31.Database.DatabaseClient;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import main.master.c31.Room.ActivityTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotSubmitedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    ImageView backbutton;
    List<ActivityTable> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_submited);

        recyclerView = (RecyclerView)findViewById(R.id.recycleview);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());





        backbutton = (ImageView) findViewById(R.id.close);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        //room operation in backround thread
        AsyncTask.execute(new Runnable()
        {
            @Override
            public void run()
            {
                //checing if tasklist in roomdb is empty or not
                taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .activityTableDao()
                        .getcheck();

                //if it is empty
                if(taskList.isEmpty()){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "You dont have any pending activity", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //if it has something
                else{
                    //getting the data from room db and show in recycleview
                    GetData gt = new GetData();
                    gt.execute();

                }
            }
        });

    }



    //retrive from Room DB
    class GetData extends AsyncTask<Void, Void, List<ActivityTable>> {

        @Override
        protected List<ActivityTable> doInBackground(Void... voids) {
            //getting all data from room db in list
            List<ActivityTable> taskList = DatabaseClient
                    .getInstance(getApplicationContext())
                    .getAppDatabase()
                    .activityTableDao()
                    .getAll();
            return taskList;
        }

        @Override
        protected void onPostExecute(List<ActivityTable> tasks) {
            super.onPostExecute(tasks);

         /*   ActivityTable activityTable = new ActivityTable();
            //List task data set to Record model  to show in recycleview
            for (int j = 0; j < tasks.size(); j++) {
                 activityTable = tasks.get(j);

            }
*/

            NotSubmitedActivityAdapter notSubmitedActivity = new NotSubmitedActivityAdapter(NotSubmitedActivity.this,tasks);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setAdapter(notSubmitedActivity);

        }
    }


}
