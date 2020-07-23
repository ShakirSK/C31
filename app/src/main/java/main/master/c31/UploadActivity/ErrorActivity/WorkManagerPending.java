package main.master.c31.UploadActivity.ErrorActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.*;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.google.gson.annotations.SerializedName;
import main.master.c31.Database.DatabaseClient;
import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.ProgressRequestBody;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import main.master.c31.Room.ActivityTable;
import me.echodev.resizer.Resizer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static android.content.Context.MODE_PRIVATE;

public class WorkManagerPending extends Worker implements ProgressRequestBody.UploadCallbacks {
    String activitytitle,activityname, activitydescription,date;
    int notificationid;
    List<String> uriwithlogo;
    String psid,psname,pslogo;
    List<String> stringList;

    Bitmap bitmapwatermark;
    Bitmap bitmap;
    ProgressManager manager;
    private File drImageFile = null;
    String sis_video ;

    public WorkManagerPending(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        manager = new ProgressManager();
        activitytitle = getInputData().getString("sactivitytitle");
        activityname = getInputData().getString("sactivityname");
        activitydescription = getInputData().getString("sactivitydescription");
        date = getInputData().getString("datesubmit");
        notificationid = Integer.parseInt(getInputData().getString("notificationid"));

//        stringList = new ArrayList<String>(Arrays.asList(imagePath));

  //      Log.d("stringList", stringList.toString()+"  "+notificationid);

        SharedPreferences sh
                = getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        psid= sh.getString("id", "");
        psname= sh.getString("name", "");
        pslogo= sh.getString("logo", "");

        getImageUri();


        return Result.success();
    }

    private void showNotification(String title, String task){
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("inducesmile", "inducesmile", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(),"inducesmile")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.appicon);
        notificationManager.notify(1, notification.build());
    }

    @Override
    public void onProgressUpdate(int percentage) {
        manager.updateProgress(percentage);
    }

    @Override
    public void onError() {
        manager.updateProgress(404);
    }

    @Override
    public void onFinish() {
        manager.updateProgress(100);
    }





    public void getImageUri() {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CreativeConnect/"+psname.replaceAll("\\s", "")+"/"+activityname);

        uriwithlogo = new ArrayList<>();
        if(activitytitle.equals("activity")){

            // for activity image  sis_video = "0"
             sis_video = "0";

            if (pictureFileDir.isDirectory())
            {
                String[] children = pictureFileDir.list();
                for (int i = 0; i < children.length; i++)
                {
                    uriwithlogo.add(pictureFileDir+"/"+children[i]);
                    //  new File(pictureFileDir, children[i]).delete();
                    Log.d("getImageUri: ",pictureFileDir+"/"+children[i]);
                }
            }

        }
        else{
            // for activity image  sis_video = "1"
            sis_video = "1";
        }



        RequestBody is_video =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sis_video);


        File pictureFileDir2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CreativeConnect/"+psname.replaceAll("\\s", ""));
        File zipFile = new File(pictureFileDir2+"/"+activityname+".zip");

        Log.d("imageuploadarray", "dssdsdsd  ");

        //    ProgressRequestBody reqFile = new ProgressRequestBody(zipFile, "*/*",this);

        RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), zipFile);
        MultipartBody.Part zipbody = MultipartBody.Part.createFormData("files_zip", zipFile.getName(), reqFile);

        String spiccount = String.valueOf(uriwithlogo.size());
        RequestBody piccount =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, spiccount);

        int count ;

        if(uriwithlogo.size()<5){
            count=uriwithlogo.size();
        }
        else {
            count=5;
        }

        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[count];

        for (int index = 0; index < count; index++) {
            Log.d("requestUploadSurvey" , index + "  " + uriwithlogo.get(index));
            File file = new File( uriwithlogo.get(index));
            //      ProgressRequestBody surveyBody = new ProgressRequestBody(file, this);

            manager.updateProgress(80+index);

            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"),
                    file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("files[]",
                    file.getName(),
                    surveyBody);
        }


        String spreschool_id = psid;
        RequestBody preschool_id =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, spreschool_id);

        String sac_name = activityname ;
        RequestBody activity_name =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sac_name);

        String sdesc_name = activitydescription ;
        RequestBody description_name =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sdesc_name);

        String sdob = date;
        RequestBody dob =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sdob);

        String screated_by = psid;
        RequestBody created_by =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, screated_by);

        manager.updateProgress(90);

        Log.v("Uploadtte",spreschool_id
                +" "+
                activityname  +" "+date  +" "+activitydescription  +" "+ psid  +" "+count  +" "+spiccount  +" "+zipFile);

        UserService userService;
        userService = ApiUtils.getUserService();
        Call<ResponseBody> call = userService.Activity(preschool_id,
                activity_name,dob,description_name, created_by,surveyImagesParts,piccount,zipbody,is_video);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.v("Upload", response.toString());
                    //deleting  images and zip file
                    try {
                        FileUtils.deleteRecursive(pictureFileDir);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                    File file = new File(pictureFileDir2+"/"+activityname+".zip");
                    file.delete();
                    if(file.exists()){
                        try {
                            file.getCanonicalFile().delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(file.exists()){
                            getApplicationContext().deleteFile(file.getName());
                        }
                    }

                       new AsyncTaskdeleteActivity(activityname).execute();

                    Toast.makeText(getApplicationContext(), "Activity " +activityname+"  Successfully Submited", Toast.LENGTH_SHORT).show();

                    manager.updateProgress(100);
                    //  showNotification("Creative Connect", "Activity " +activityname+"  Successfully Submited");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                //deleting  images and zip file
                      /*      try {
                                FileUtils.deleteRecursive(pictureFileDir);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }*/

                Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_SHORT).show();

                manager.updateProgress(404);
                Result.failure();
            }
        });

    }


    public class ProgressManager {


        // Issue the initial notification with zero progress
        int PROGRESS_MAX = 100;
        int PROGRESS_CURRENT = 0;
        int percentage;

        //  NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());


        public void updateProgress(int val) {
            Message msg = new Message();
            msg.arg1 = val;



            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                NotificationChannel notificationChannel = new NotificationChannel("inducesmile", "inducesmile", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            // Create an Intent for the activity you want to start
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(),"inducesmile")
                    .setContentIntent(resultPendingIntent)
                    .setContentTitle(activityname)
                    .setSmallIcon(R.mipmap.appicon)
                    .setOngoing(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.mipmap.appicon))
                    .setColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark))
                    .setPriority(NotificationCompat.PRIORITY_HIGH);




             /*   notification.setProgress(100, msg.arg1, true);
              //  Notification notification = notificationBuilder.build();
                notificationManager.notify(1, notification.build());
*/

            if (msg.arg1 == PROGRESS_MAX){
                notification.setOngoing(false);
                notification.setContentText("Activity " +activityname+"  Successfully Submited");
                notification.setProgress(0,0,false);
            }else if(msg.arg1 == 404){
                notification.setOngoing(false);
                notification.setContentText("Something Went Wrong Try Again");
                notification.setProgress(0,0,false);
            }else {
                // Calculate the percentage comple
                percentage = (msg.arg1*100)/PROGRESS_MAX;
                notification.setContentText(percentage+" % complete "+msg.arg1+" of "+PROGRESS_MAX);
                notification.setProgress(PROGRESS_MAX,msg.arg1,false);

            }




            notificationManager.notify(notificationid, notification.build());



//            handler.sendMessageDelayed(msg, 1000);
        }}

    public static class FileUtils {
        /**
         * By default File#delete fails for non-empty directories, it works like "rm".
         * We need something a little more brutual - this does the equivalent of "rm -r"
         * @param path Root File Path
         * @return true iff the file and all sub files/directories have been removed
         */
        public static boolean deleteRecursive(File path) throws FileNotFoundException{
            if (!path.exists()) throw new FileNotFoundException(path.getAbsolutePath());
            boolean ret = true;
            if (path.isDirectory()){
                for (File f : path.listFiles()){
                    ret = ret && deleteRecursive(f);
                }
            }
            return ret && path.delete();
        }
    }
    //insert in to Room DB
    @SuppressLint("StaticFieldLeak")
    public class AsyncTaskdeleteActivity extends AsyncTask<String,String,String> {
        String activitname;

        AsyncTaskdeleteActivity(  String activitname){
            this.activitname = activitname;
        }

        @Override
        protected String doInBackground(String... params) {

            //adding to database
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                    .activityTableDao()
                    .delete(activityname);


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //   Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
        }
    }
}