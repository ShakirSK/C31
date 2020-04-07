package main.master.c31.UploadActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static android.content.Context.MODE_PRIVATE;
import static main.master.c31.UploadActivity.PickimageFrag.ActivityPickImageFragment._hasLoadedOnce;
import static main.master.c31.UploadActivity.PickimageFrag.ActivityPickImageFragment.activitylisturi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import main.master.c31.networknotificationtest.FileUploadService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActivityInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActivityInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView date,save;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    EditText activityname,activitydescription;
    String sactivityname,sactivitydescription;
    UserService userService;
    String datesubmit,psid,psname,pslogo;
    ConstraintLayout savingLayout;
    private RequestManager requestManager;
    ImageView icon;
    int h;
    public static List<String> uriwithlogo;
    ProgressDialog mProgressDialog;
    private static final int BUFFER = 80000;
    List<String> filesListInDir = new ArrayList<String>();

    public ActivityInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityInfoFragment newInstance(String param1, String param2) {
        ActivityInfoFragment fragment = new ActivityInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity_info, container, false);

        savingLayout = (ConstraintLayout) view.findViewById(R.id.idForSaving);

         icon = (ImageView) view.findViewById(R.id.icon); // get the reference of ImageView

        SharedPreferences sh
                = this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        psid= sh.getString("id", "");
        psname= sh.getString("name", "");
        pslogo= sh.getString("logo", "");

        _hasLoadedOnce=false;
        userService = ApiUtils.getUserService();

        activityname = (EditText) view.findViewById(R.id.activityname);
        activitydescription = (EditText) view.findViewById(R.id.activitydescription);

        date = (TextView)view.findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                 datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);

                                datesubmit = day + "-" + (month + 1) + "-" + year;
                            }
                        }, year, month, dayOfMonth);
             //   datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });




        save = (TextView)view.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sactivityname = activityname.getText().toString().trim();

                sactivitydescription = activitydescription.getText().toString().trim();


                if (sactivityname.isEmpty()) {
                    activityname.setError("Name required");
                    activityname.requestFocus();
                    return;
                }


                if (sactivitydescription.isEmpty()) {
                    activitydescription.setError("Description required");
                    activitydescription.requestFocus();
                    return;
                }



                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(activitylisturi.isEmpty())
                        {
                            Toast.makeText(getContext(),"Please Select Image",Toast.LENGTH_SHORT).show();
                        }
                        else{



                            mProgressDialog = new ProgressDialog(getActivity());
                            mProgressDialog.setIndeterminate(true);
                            mProgressDialog.setMessage("Loading...");
                            mProgressDialog.setCanceledOnTouchOutside(false);
                            mProgressDialog.show();

                            new AsyncGettingBitmapFromUrl().execute();


                            // Submit_Activity();
/*
                            savingLayout.setVisibility(View.VISIBLE);



                            final Handler handler = new Handler();

                                requestManager = Glide.with(getContext());

                            for(  h =0;h<activitylisturi.size();h++) {


                                       handler.postDelayed(new Runnable() {
                                    public void run() {

                                        for(  h =0;h<activitylisturi.size();h++) {
                                            Log.i("TAGinfo   ",h+"");
                                            requestManager
                                                    .load(activitylisturi.get(h).toString())
                                                    .apply(new RequestOptions().fitCenter())
                                                    .into(icon);

                                            }
                                    }
                                }, 500);


                                Runnable runnable = new Runnable()
                                {
                                    @Override
                                    public void run() {


                                        File file = saveBitMap(getContext(), savingLayout);
                                        if (file != null) {

                                            Log.i("TAG", "Drawing saved to the gallery!");
                                        } else {

                                            Log.i("TAG", "Oops! Image could not be saved.");
                                        }

                                    }};
                                handler.postDelayed(runnable,500);
                            }*/

                          /*      handler.postDelayed(new Runnable() {
                                    public void run() {

                                        for(  h =0;h<activitylisturi.size();h++) {
                                            Log.i("TAGinfo   ",h+"");
                                            requestManager
                                                    .load(activitylisturi.get(h).toString())
                                                    .apply(new RequestOptions().fitCenter())
                                                    .into(icon);


                                                File file = saveBitMap(getContext(), savingLayout);
                                                if (file != null) {

                                                    Log.i("TAG", "Drawing saved to the gallery!");
                                                } else {

                                                    Log.i("TAG", "Oops! Image could not be saved.");
                                                }

                                              *//*  if(h==activitylisturi.size()-1){
                                                    handler.removeCallbacksAndMessages(null);

                                                }*//*
                                            }
                                        handler.postDelayed(this, 500);

                                    }
                                }, 500);*/


                           /*     new Handler().postDelayed(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                File file = saveBitMap(getContext(), savingLayout);
                                                if (file != null) {

                                                    Log.i("TAG", "Drawing saved to the gallery!");
                                                } else {

                                                    Log.i("TAG", "Oops! Image could not be saved.");
                                                }
                                            }
                                        }, 1000);
*/




                        }

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //   Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_LONG).show();

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();


            }
        });



        return view;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void Submit_Activity() {
/*
        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        Log.d( "urlp1: ", String.valueOf(activitylisturi));
        List<String> uri = new ArrayList<>();
        for(int i=0;i<activitylisturi.size();i++){
            String ere = removeUriFromPath(activitylisturi.get(i).toString());
            String urlString  = Uri.decode(ere);
            uri.add(urlString);
        }
        Log.d( "urlp2: ", String.valueOf(uri));*/




        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[uriwithlogo.size()];

        for (int index = 0; index < uriwithlogo.size(); index++) {
            Log.d("requestUploadSurvey" , index + "  " + uriwithlogo.get(index));
            File file = new File( uriwithlogo.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"),
                    file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("files[]",
                    file.getName(),
                    surveyBody);
        }

        Log.v("imageuploadarray", surveyImagesParts.toString()+"  "+uriwithlogo.size());


        String spreschool_id = psid;
        RequestBody preschool_id =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, spreschool_id);

        String sac_name = sactivityname ;
        RequestBody activity_name =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sac_name);

        String sdesc_name = sactivitydescription ;
        RequestBody description_name =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sdesc_name);

        String sdob = datesubmit;
        RequestBody dob =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sdob);

        String screated_by = psid;
        RequestBody created_by =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, screated_by);


  /*      Call<ResponseBody> call = userService.Activity(preschool_id,
                activity_name,dob,description_name, created_by,surveyImagesParts);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.v("Upload", response.toString());
                //Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Activity is successfully submited", Toast.LENGTH_SHORT).show();
                Intent splashLoginm = new Intent(getContext(), MainActivity.class);
                splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(splashLoginm);
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("Upload error:", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

*/






    }

    public static String removeUriFromPath(String uri)
    {
        return  uri.substring(7, uri.length());
    }




    private File saveBitMap(Context context, View drawView) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Logicchip");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if (!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap = getBitmapFromView(drawView);
        Log.d("TAGbitmap", pictureFile.getAbsolutePath());
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            Log.d("TAGbitmap2", bitmap.toString());

            Log.d("TAGbitmap3", pictureFile.getAbsolutePath());

            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery(context, pictureFile.getAbsolutePath());
        return pictureFile;
    }

    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }


    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue scanning gallery.");
        }
    }


    public static Bitmap addWatermark(Bitmap source, Bitmap watermark, float ratio) {
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

        return bmp;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Logicchip");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if (!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
        File pictureFile = new File(filename);

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

       /* ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
   */     return Uri.parse(pictureFile.getAbsolutePath());
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            //deleting previoss images first while loading new images
            File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Logicchip");
            if (pictureFileDir.isDirectory())
            {
                String[] children = pictureFileDir.list();
                for (int i = 0; i < children.length; i++)
                {
                    new File(pictureFileDir, children[i]).delete();
                }
            }

            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private class AsyncGettingBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {


            Bitmap bitmap = null;
            String url = "http://creative31minds.com/creative-web/uploads/preschool_logo/"+psname.replaceAll("\\s", "")+"/"+pslogo;

            bitmap=   getBitmapFromURL(url);


            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap2) {

            Log.d("bitmap" , bitmap2.toString());


            Bitmap bitmap = null;

            uriwithlogo = new ArrayList<>();

            for(  h =0;h<activitylisturi.size();h++) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(activitylisturi.get(h).toString()));

                                  /*  bitmap2 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),
                                            Uri.parse(url));*/



                     Bitmap bitmapout = addWatermark(bitmap, bitmap2, (float) 0.10);

                      Uri ur = getImageUri(getContext(), bitmapout);

                    if (ur != null) {
                        uriwithlogo.add(String.valueOf(ur));
                    /*    File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Logicchip");
                        File pictureFileDir2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PhotoLab");
                        String[] s = new String[1];
                        s[0] = pictureFileDir.getAbsolutePath();
*/
                      //   zipFileAtPath(pictureFileDir.getAbsolutePath(),pictureFileDir2+"/Logicchip.zip");
                        Log.i("TAG", "Drawing saved to the gallery!");
                    } else {

                        Log.i("TAG", "Oops! Image could not be saved.");
                    }


                   // zip(pictureFileDir,"Logicchip");

                   /*   String finaluri = getRealPathFromUri(getContext(), ur);



                    if (finaluri != null) {
                        uriwithlogo.add(finaluri);
                        Log.i("TAG", "Drawing saved to the gallery!");
                    } else {

                        Log.i("TAG", "Oops! Image could not be saved.");
                    }*/
                                 Log.i("TAGinfo   ", bitmap + "   " + bitmapout + "  " + ur + "   " );
              //      Log.i("TAGinfo   ", uriwithlogo.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(h==activitylisturi.size()-1)
                {

                    File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Logicchip");
                   File pictureFileDir2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PhotoLab");

                   boolean dds =   zipFileAtPath(pictureFileDir.getAbsolutePath(),pictureFileDir2+"/Logicchip.zip");
                    if(dds)
                    {
                        mProgressDialog.dismiss();
                    }


               /*   Intent mIntent = new Intent(getContext(), FileUploadService.class);
                    mIntent.putStringArrayListExtra("mFilePath", (ArrayList<String>) uriwithlogo);
                    FileUploadService.enqueueWork(getContext(), mIntent);*/
                   //Submit_Activity();
                }
            }
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



}
