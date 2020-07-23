package main.master.c31.testimage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.*;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.util.Log;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.annotations.SerializedName;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import main.master.c31.Database.DatabaseClient;
import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.LauncherMainActivity.LoginActivity;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import static android.content.Context.MODE_PRIVATE;

public class WorkManager13 extends Worker implements ProgressRequestBody.UploadCallbacks {
    String activitytitle,activityname, activitydescription,date;
    int notificationid;
    List<String> uriwithlogo;
    String psid,psname,pslogo;
    List<String> stringList;

    Bitmap bitmapwatermark;
    Bitmap bitmap;
    ProgressManager manager;
    private File drImageFile = null;

    public WorkManager13(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

         manager = new ProgressManager();
     /*   int i = 0;
        while(i<100){
            i++;
            try {
                Thread.sleep(1000);
                manager.updateProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
      // manager.updateProgress(0);
        String[] imagePath = getInputData().getStringArray("imagePath");
        activitytitle = getInputData().getString("sactivitytitle");
        activityname = getInputData().getString("sactivityname");
         activitydescription = getInputData().getString("sactivitydescription");
         date = getInputData().getString("datesubmit");
        notificationid = Integer.parseInt(getInputData().getString("notificationid"));

        stringList = new ArrayList<String>(Arrays.asList(imagePath));

        Log.d("stringList", stringList.toString()+"  "+notificationid);

        SharedPreferences sh
                = getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        psid= sh.getString("id", "");
        psname= sh.getString("name", "");
        pslogo= sh.getString("logo", "");


        String url = "http://creative31minds.com/creative-web/uploads/preschool_logo/"+psname.replaceAll("\\s", "")+"/"+pslogo;

           getBitmapFromURL(url);













        //Defining retrofit api service
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://local ip address/test/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

   /*     File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileupload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
*/


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


    private interface ApiService {
        @Multipart
        @POST("index.php")
        Call<PostResponse> postData(
                @Part MultipartBody.Part file,
                @Part("name") RequestBody name);
    }

    private class PostResponse{
        @SerializedName("success")
        private String success;

        public void setSuccess(String success){
            this.success = success;
        }
        public String getSuccess(){
            return success;
        }
    }



    public boolean zipFileAtPath(String sourcePath, String toLocation) {
        final int BUFFER = 2048;

        File sourceFile = new File(sourcePath);
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(toLocation);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            if (sourceFile.isDirectory()) {
                zipSubFolder(out, sourceFile, sourceFile.getParent().length());
            } else {
                byte data[] = new byte[BUFFER];
                FileInputStream fi = new FileInputStream(sourcePath);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(getLastPathComponent(sourcePath));
                entry.setTime(sourceFile.lastModified()); // to keep modification time after unzipping
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /*
     *
     * Zips a subfolder
     *
     */

    private void zipSubFolder(ZipOutputStream out, File folder,
                              int basePathLength) throws IOException {

        final int BUFFER = 2048;

        File[] fileList = folder.listFiles();
        BufferedInputStream origin = null;
        for (File file : fileList) {
            if (file.isDirectory()) {
                zipSubFolder(out, file, basePathLength);
            } else {
                byte data[] = new byte[BUFFER];
                String unmodifiedFilePath = file.getPath();
                String relativePath = unmodifiedFilePath
                        .substring(basePathLength);
                FileInputStream fi = new FileInputStream(unmodifiedFilePath);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(relativePath);
                entry.setTime(file.lastModified()); // to keep modification time after unzipping
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
        }
    }

    /*
     * gets the last path component
     *
     * Example: getLastPathComponent("downloads/example/fileToZip");
     * Result: "fileToZip"
     */
    public String getLastPathComponent(String filePath) {
        String[] segments = filePath.split("/");
        if (segments.length == 0)
            return "";
        String lastPathComponent = segments[segments.length - 1];
        return lastPathComponent;
    }




    public void getBitmapFromURL(String src) {

        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
             bitmapwatermark = BitmapFactory.decodeStream(input);
          //  manager.updateProgress(20);
        } catch (IOException e) {
            e.printStackTrace();

        }


        uriwithlogo = new ArrayList<>();

        for( int h =0;h<stringList.size();h++) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(stringList.get(h).toString()));

               addWatermark(bitmap, bitmapwatermark, (float) 0.10);

               if(stringList.size()<70){
                   manager.updateProgress(h);
               }

            } catch (IOException e) {
                e.printStackTrace();
            }

            if(h==stringList.size()-1)
            {

                manager.updateProgress(70);
                File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CreativeConnect/"+psname.replaceAll("\\s", "")+"/"+activityname);
                File pictureFileDir2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CreativeConnect/"+psname.replaceAll("\\s", ""));


                boolean dds =   zipFileAtPath(pictureFileDir.getAbsolutePath(),pictureFileDir2+"/"+activityname+".zip");
                manager.updateProgress(75);
                if(dds)
                {

                   manager.updateProgress(80);
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

                    String sis_video = "0";
                    RequestBody is_video =
                            RequestBody.create(
                                    okhttp3.MultipartBody.FORM, sis_video);


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

                            new AsyncTaskActivity(activitytitle,activityname,activitydescription,date).execute();
                            manager.updateProgress(404);
                            Result.failure();
                        }
                    });
                }
                else {
                    manager.updateProgress(404);
                }


            }
        }


    }



    public void addWatermark(Bitmap source, Bitmap watermark, float ratio) {
        Canvas canvas;
        Paint paint;
        Bitmap bmp;
        Matrix matrix;
        RectF r;

        int width, height;
        float scale;

        width = source.getWidth();
        height = source.getHeight();

        // Create the new bitmap
        bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);

        // Copy the original bitmap into the new one
        canvas = new Canvas(bmp);
        canvas.drawBitmap(source, 0, 0, paint);

        // Scale the watermark to be approximately to the ratio given of the source image height
        scale = (float) (((float) height * ratio) / (float) watermark.getHeight());

        // Create the matrix
        matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Determine the post-scaled size of the watermark
        r = new RectF(0, 0, watermark.getWidth(), watermark.getHeight());
        matrix.mapRect(r);

        // Move the watermark to the bottom right corner
        matrix.postTranslate(width - r.width(), height - r.height());

        // Draw the watermark
        canvas.drawBitmap(watermark, matrix, paint);


        getImageUri(getApplicationContext(), bmp);
    }

    public void getImageUri(Context inContext, Bitmap inImage) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CreativeConnect/"+psname.replaceAll("\\s", "")+"/"+activityname);
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if (!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");
        }

        String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
        File pictureFile = new File(filename);

   //     drImageFile = FileUtils.checkImageFileAndResize(pictureFile,getApplicationContext());

        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, oStream);

            Log.d("TAGbitmap3", pictureFile.getAbsolutePath());

            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
      //  manager.updateProgress(70);

        uriwithlogo.add(String.valueOf(pictureFile.getAbsolutePath()));

       // return Uri.parse(pictureFile.getAbsolutePath());
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


        public static File checkImageFileAndResize(File imgFile,Context context) {
            File drImagFile = null;
            if (FileUtils.calculateFileSizeInMB(imgFile) > 1) {
                if (FileUtils.resizeImageFile(imgFile, context) != null) {
                    if (FileUtils.resizeImageFile(imgFile, context).exists()) {
                        drImagFile = FileUtils.resizeImageFile(imgFile, context);

                    }
                }

            } else {
                drImagFile = imgFile;
            }
            return drImagFile;
        }

        public static double calculateFileSizeInMB(File file){
            double fileSizeInMB=0.0;
            try {
                if(file.exists()) {
                    double fileSizeInBytes = file.length();
                    // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
                    double fileSizeInKB = fileSizeInBytes / 1024;
                    // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                    fileSizeInMB = fileSizeInKB / 1024;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return fileSizeInMB;
        }

        public  static File resizeImageFile(File file, Context mContext){
            File resizedImage=null;
            File storageDir = Environment.getExternalStorageDirectory();
            Random random = new Random();
            int numbersw = random.nextInt(9999 - 1000) + 1000;
            String str = "VidAppDrImage"+String.valueOf(numbersw);
            try {
                resizedImage = new Resizer(mContext)
                        .setTargetLength(1024)
                        .setQuality(90)
                        .setOutputFormat("JPEG")
                        .setOutputFilename(str)
                        .setOutputDirPath(storageDir.getAbsolutePath())
                        .setSourceImage(file)
                        .getResizedFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resizedImage;
        }
    }
    //insert in to Room DB
    public class AsyncTaskActivity extends AsyncTask<String,String,String>{

        String activitname;
        String activitdescription;
        String activitdate;
        String activitytitle;

        AsyncTaskActivity(   String activitytitle, String activitname, String activitdescription, String activitdate){
            this.activitytitle = activitytitle;
            this.activitname = activitname;
            this.activitdescription = activitdescription;
            this.activitdate = activitdate;
        }
        @Override
        protected String doInBackground(String... params) {


            ActivityTable activityTable = new ActivityTable();
            activityTable.setActivitytitle(activitytitle);
            activityTable.setActivityname(activityname);
            activityTable.setDescription(activitdescription);
            activityTable.setActivitydate(activitdate);

            //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .activityTableDao()
                        .insert(activityTable);


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //   Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
        }
    }
}