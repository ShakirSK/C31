package main.master.c31.testimage;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import gun0912.tedbottompicker.TedBottomPicker;
import main.master.c31.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class testimageActvity extends AppCompatActivity {
    RequestManager requestManager;
        ProgressDialog pd;
        String birthdayimage="";
    List<Uri> selectedUriList;
     ImageView imageView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_testimage_actvity);

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
                        birthdayimage = String.valueOf(selectedUriList.get(0));
                        requestManager = Glide.with(getApplicationContext());
                        //    icon.setImageResource(logos[i]); // set logo images
                        requestManager
                                .load(birthdayimage)
                                .apply(new RequestOptions().fitCenter())
                                .into(imageView);


                    });

            pd = new ProgressDialog(testimageActvity.this);
        }

        public void SaveClick(View view) {
            pd.setMessage("saving your image");
            pd.show();
            RelativeLayout savingLayout = (RelativeLayout) findViewById(R.id.idForSaving);
            File file = saveBitMap(testimageActvity.this, savingLayout);
            if (file != null) {
                pd.cancel();
                Log.i("TAG", "Drawing saved to the gallery!");
            } else {
                pd.cancel();
                Log.i("TAG", "Oops! Image could not be saved.");
            }
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
            try {
                pictureFile.createNewFile();
                FileOutputStream oStream = new FileOutputStream(pictureFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
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
    }
