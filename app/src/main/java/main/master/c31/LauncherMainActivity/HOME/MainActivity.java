package main.master.c31.LauncherMainActivity.HOME;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.List;

import main.master.c31.Database.DatabaseClient;
import main.master.c31.LauncherMainActivity.HOME.ActiveStatus.ActiveStatusModel;
import main.master.c31.LauncherMainActivity.LoginActivity;
import main.master.c31.LauncherMainActivity.home_fragment;
import main.master.c31.LauncherMainActivity.more_fragment;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import main.master.c31.Session.SaveSharedPreference;
import main.master.c31.UploadActivity.UploadActivityList.ActivityUploadedList;
import main.master.c31.utils.ConnectionDetector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    static final Integer READ_EXST = 0x4;

    UserService userService;
    String psid;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(new home_fragment());
                    return true;
                case R.id.navigation_profile:
                    loadFragment(new more_fragment());
                    return true;


            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userService = ApiUtils.getUserService();

        SharedPreferences sh
                = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        psid= sh.getString("id", "");


        //checking if internet available
        if (!ConnectionDetector.networkStatus(getApplicationContext())) {

            //   Toast.makeText(getApplicationContext(),"tre",Toast.LENGTH_SHORT).show();
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("No Internet Connection");
            alertDialog.setMessage("Please check your internet connection  and try again");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();

                }
            });
            alertDialog.show();
        }
        // if internet connection is available
        else{
            //look if status of the app is active or not
            getuploadedData(psid);
        }


        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,READ_EXST);

        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.statusbarcolor));
        }



        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
     //   BottomNavigationViewHelper.disableShiftMode(navView);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new home_fragment());


    }


    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

           if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
           // Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    public static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    //    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }



    public void getuploadedData(String pid){

        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();


        Call<List<ActiveStatusModel>> call = userService.look_status_of_app(pid);
        call.enqueue(new Callback<List<ActiveStatusModel>>() {
            @Override
            public void onResponse(Call<List<ActiveStatusModel>>call, Response<List<ActiveStatusModel>> response) {
                Log.d("onResponse: ", response.toString());

                if(response.message().equals("Not Found"))
                {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    deleteTask();
                    Toast.makeText(MainActivity.this, "No Uploaded Activities", Toast.LENGTH_SHORT).show();
                }
                else if(response.isSuccessful()){

                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                  /*  List<ActiveStatusModel> loginResponse = response.body();

                    Log.e("keshav", "loginResponse 1 --> " + loginResponse);


                    if (loginResponse != null) {


                        for (int i = 0; i < loginResponse.size(); i++) {
                            ActiveStatusModel datum = loginResponse.get(i);

                            if("1".equals(datum.getStatus())){

                                 Toast.makeText(getApplicationContext(),"activee", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
*/


                }
                else {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Toast.makeText(MainActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ActiveStatusModel>> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.d("onResponse: ",  t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void deleteTask() {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .userDao()
                        .delete();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // Set LoggedIn status to false
                SaveSharedPreference.setLoggedIn(getApplicationContext(), false);

                Toast.makeText(getApplicationContext(), "Your Account is Deactivated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

}
