package main.master.c31.testimage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import gun0912.tedbottompicker.TedBottomPicker;
import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class testimageActvity extends AppCompatActivity {
    RequestManager requestManager;
        ProgressDialog pd;
        String birthdayimage="";
        String psid,psname,pslogo;
    List<Uri> selectedUriList;
     ImageView imageView;

    List<String> uriwithlogo;
    List<String> targetList;
    ProgressDialog mProgressDialog;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_testimage_actvity);

            SharedPreferences sh
                    = this.getSharedPreferences("MySharedPref", MODE_PRIVATE);
            psid= sh.getString("id", "");
            psname= sh.getString("name", "");
            pslogo= sh.getString("logo", "");


            imageView = (ImageView)findViewById(R.id.imageView5);
            TedBottomPicker.with(testimageActvity.this)
                    //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                    .setPeekHeight(1600)
                    .showTitle(true)
                    .setCompleteButtonText("Done")
                    .setEmptySelectionText("Nothing Selected")
                    .setPreviewMaxCount(200)
                    .setGalleryTileBackgroundResId(R.color.statusbarcolor)
                    .setGalleryTile(R.drawable.ic_gallery)
                    .setSelectedUriList(selectedUriList)
                    .showMultiImage(uriList -> {
                        selectedUriList = uriList;
//                        birthdayimage = String.valueOf(selectedUriList.get(0));
//                        requestManager = Glide.with(getApplicationContext());
//                        //    icon.setImageResource(logos[i]); // set logo images
//                        requestManager
//                                .load(selectedUriList.get(0))
//                                .apply(new RequestOptions().fitCenter())
//                                .into(imageView);


                 /*       new Handler().postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        ConstraintLayout savingLayout = (ConstraintLayout) findViewById(R.id.idForSaving);
                                        File file = saveBitMap(testimageActvity.this, savingLayout);
                                        if (file != null) {
                                            pd.cancel();
                                            Log.i("TAG", "Drawing saved to the gallery!");
                                        } else {
                                            pd.cancel();
                                            Log.i("TAG", "Oops! Image could not be saved.");
                                        }

                                    }
                                }, 1000);
*/

                    });

            pd = new ProgressDialog(testimageActvity.this);
        }

        public void SaveClick(View view) {


//            pd.setMessage("saving your image");
//            pd.show();
//            ConstraintLayout savingLayout = (ConstraintLayout) findViewById(R.id.idForSaving);
//            File file = saveBitMap(testimageActvity.this, savingLayout);
//            if (file != null) {
//                pd.cancel();
//                Log.i("TAG", "Drawing saved to the gallery!");
//            } else {
//                pd.cancel();
//                Log.i("TAG", "Oops! Image could not be saved.");
//            }


            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

       //     new AsyncGettingBitmapFromUrl().execute();

            startWork();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "We will Notify you when  Activity textworkmanager is uploaded", Toast.LENGTH_SHORT).show();

                    Intent splashLoginm = new Intent(getApplicationContext(), MainActivity.class);
                    splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(splashLoginm);

                }
            }, 50);

        }

        private File saveBitMap(Context context, View drawView) {
            File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CreativeConnect");
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

            for( int h =0;h<selectedUriList.size();h++) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(selectedUriList.get(h).toString()));

                                  /*  bitmap2 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),
                                            Uri.parse(url));*/



                    Bitmap bitmapout = addWatermark(bitmap, bitmap2, (float) 0.10);

                    Uri ur = getImageUri(getApplicationContext(), bitmapout);

                    if (ur != null) {

                        uriwithlogo.add(String.valueOf(ur));
                        Log.i("TAGinfo   ", uriwithlogo.toString());

                       /* if(uriwithlogo.size()>5){
                            uriwithlogo.add(String.valueOf(ur));
                            Log.i("TAGinfo   ", uriwithlogo.toString());
                        }
                        */
                         /*    File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CreativeConnect");
                        File pictureFileDir2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PhotoLab");
                        String[] s = new String[1];
                        s[0] = pictureFileDir.getAbsolutePath();
*/
                        //   zipFileAtPath(pictureFileDir.getAbsolutePath(),pictureFileDir2+"/CreativeConnect.zip");
                        Log.i("TAG", "Drawing saved to the gallery!");
                    } else {

                        Log.i("TAG", "Oops! Image could not be saved.");
                    }


                    // zip(pictureFileDir,"CreativeConnect");

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

                if(h==selectedUriList.size()-1)
                {

               /*     Intent mIntent = new Intent(getContext(), FileUploadService.class);
                    mIntent.putStringArrayListExtra("mFilePath", (ArrayList<String>) uriwithlogo);
                    FileUploadService.enqueueWork(getContext(), mIntent);*/
                  //  Submit_Activity();

                  //  startWork();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "We will Notify you when  Activity textworkmanager is uploaded", Toast.LENGTH_SHORT).show();

                            Intent splashLoginm = new Intent(getApplicationContext(), MainActivity.class);
                            splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(splashLoginm);

                        }
                    }, 50);

                }
            }
        }
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            //deleting previoss images first while loading new images
            File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CreativeConnect/ActivityImages");
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
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CreativeConnect/ActivityImages");
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startWork(){
        targetList = new ArrayList<String>();
        selectedUriList.forEach(uri -> targetList.add(uri.toString()));
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkManager13.class)
                .setInputData(createInputData(targetList))
                .setInitialDelay(2, TimeUnit.SECONDS).build();
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
    }

    private Data createInputData(List<String> imagePath){
        Data data = new Data.Builder()
                .putStringArray("imagePath", imagePath.toArray(new String[imagePath.size()]))
                .putString("nameofA","textworkmanager")
                .build();
        return data;
    }
}
