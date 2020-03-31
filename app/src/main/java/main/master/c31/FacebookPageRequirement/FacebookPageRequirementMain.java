package main.master.c31.FacebookPageRequirement;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.*;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import main.master.c31.FacebookPageRequirement.change_in_activity.FBRequirementMenuActivity;
import main.master.c31.FacebookPageRequirement.pick3topicimages.PickFBRequirementMainActivity;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import main.master.c31.UploadActivity.UploadActivityList.ActivityModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacebookPageRequirementMain extends AppCompatActivity {

    RecyclerView listView;
    MyListAdapter adapter ;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    /*  ArrayList arrayList = new ArrayList<>(Arrays.asList("FIVE PICTURE OF KIDS","STAFF PICTURES","INFRASTRUCTURE PICTURES"));
  */
 // ArrayList arrayList = new ArrayList<>();
  public static HashMap<Integer,String> arrayList=new HashMap<Integer,String>();
    List<String> list;

    Button button;
    UserService userService;
    String psid;
    
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_page_requirement_main);


        SharedPreferences sh
                = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        psid= sh.getString("id", "");


        listView=(RecyclerView)findViewById(R.id.listviewforfbpagelist);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(listView.getContext(), linearLayoutManager.getOrientation());



        button = (Button)findViewById(R.id.button);

        userService = ApiUtils.getUserService();



        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.statusbarcolor));
        }
  /*      button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PickFBRequirementMainActivity.class);
                intent.putStringArrayListExtra("list", (ArrayList<String>) arrayList);
                startActivity(intent);
            }
        });*/


    /*    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getApplicationContext(), FBRequirementMenuActivity.class);
                it.putExtra("YourKeyHere", (String) list.get(position));
                startActivity(it);
            }
        });*/
        Get_Requirement();
                
    }
    public void Get_Requirement(){
        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();


        Call<List<ActivityModel>> call = userService.posturdata("FbcreativeAPI","fbcreative",psid);
        call.enqueue(new Callback<List<ActivityModel>>() {
            @Override
            public void onResponse(Call<List<ActivityModel>>call, Response<List<ActivityModel>> response) {
                Log.d("onResponse: ", response.toString());
                if(response.message().equals("Not Found"))
                {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Toast.makeText(FacebookPageRequirementMain.this, "No Uploaded Activities", Toast.LENGTH_SHORT).show();
                }
                else if(response.isSuccessful()){

                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    //   Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    List<ActivityModel> loginResponse = response.body();

                    Log.e("keshav", "loginResponse 1 --> " + loginResponse);

                    if (loginResponse != null) {

                        String fbrequirementString = null;
                        for (int i = 0; i < loginResponse.size(); i++) {
                            fbrequirementString = loginResponse.get(i).getFbrequirements();
                            Log.e("keshav", "getUserId          -->  " + fbrequirementString);

                        }


                        String listoffbR =  fbrequirementString.replaceAll("[\\[\\]\\(\\)]", "");
                        String[] psachild = listoffbR.split("\\s*,\\s*");

                    /*    String[] animal1 = psachild[0].split("\\[");
                        String[] animal2 = psachild[psachild.length-1].split("]");

                    */    for (int i=0;i<psachild.length;i++){
                                arrayList.put(i,psachild[i].replaceAll("^\"|\"$", ""));
                                Log.d( "onPostpsanh",psachild[i]);
                            }


                /*        if(fbrequirementString.contains(","))
                        {
                            String[] psachild = fbrequirementString.split("\\s*,\\s*");

                            String[] animal1 = psachild[0].split("\\[");
                            String[] animal2 = psachild[psachild.length-1].split("]");

                            for (int i=0;i<psachild.length;i++){
                                if( psachild[i].contains("[")||psachild[i].contains("]"))
                                {

                                    if(i==0){
                                        arrayList.put(i,animal1[1].replaceAll("^\"|\"$", ""));
                                    }

                                    if(i==psachild.length-1)
                                    {
                                        arrayList.put(i,animal2[0].replaceAll("^\"|\"$", ""));
                                    }

                                    Log.d( "onPostpsahave ",psachild[i]);
                                }
                                else
                                {
                                    arrayList.put(i,psachild[i].replaceAll("^\"|\"$", ""));
                                    Log.d( "onPostpsanh",psachild[i]);
                                }
                            }


                        }
                        else{

                           String listoffbR =  fbrequirementString.replaceAll("[\\[\\]\\(\\)]", "");
                            arrayList.put(0,listoffbR.replaceAll("^\"|\"$", ""));
                            Toast.makeText(getApplicationContext(),"NOT",Toast.LENGTH_SHORT).show();
                        }

*/



                        Log.d( "onPostarray",arrayList.toString());

                    }
                  //  list = new ArrayList<String>(arrayList.values());


                  /*  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, android.R.id.text1, list);*/

                    adapter = new MyListAdapter(getApplicationContext(),arrayList);
                    listView.setHasFixedSize(true);
                    listView.setLayoutManager(linearLayoutManager);
                    listView.addItemDecoration(dividerItemDecoration);
                    listView.setAdapter(adapter);



                }
                else {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Toast.makeText(FacebookPageRequirementMain.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ActivityModel>> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.d("onResponse: ",  t.getMessage());
                Toast.makeText(FacebookPageRequirementMain.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
