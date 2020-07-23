package main.master.c31.FacebookpostRequest.byactivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import gun0912.tedbottompicker.TedBottomPicker;
import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import main.master.c31.UploadActivity.PickimageFrag.ActivityPickImageFragmentAdapter;
import main.master.c31.utils.ConnectionDetector;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FBpostPickImage extends AppCompatActivity {

    GridView simpleGrid;
    private List<Uri> selectedUriList;
    TextView selectphoto,submit;
    List<Uri> activitylisturi;
    String psid,sactivityname,sdescription,slink,schangeinmakein,checkboxselect_status,datesubmit;
    List<String> targetList;
    ProgressDialog mProgressDialog;
    int notificationid;
    Intent intent;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbpost_pick_image);

        SharedPreferences sh
                = getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        psid= sh.getString("id", "");


        userService = ApiUtils.getUserService();

        intent = getIntent();
        sactivityname = intent.getStringExtra("activityname");
        sdescription = intent.getStringExtra("activitydescription");
        slink = intent.getStringExtra("link");
        schangeinmakein = intent.getStringExtra("schangeinmakein");
        checkboxselect_status = intent.getStringExtra("checkboxselect_status");

        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        selectedUriList = new ArrayList<>();

        selectphoto = (TextView) findViewById(R.id.selectphoto);
        submit = (TextView) findViewById(R.id.submits);

        selectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking if internet available
                if (!ConnectionDetector.networkStatus(getApplicationContext())) {

                    //   Toast.makeText(getApplicationContext(),"tre",Toast.LENGTH_SHORT).show();
                    android.app.AlertDialog alertDialog = new AlertDialog.Builder(FBpostPickImage.this).create();
                    alertDialog.setTitle("No Internet Connection");
                    alertDialog.setMessage("Please check your internet connection  and try again");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();

                        }
                    });
                    alertDialog.show();
                    return;
                }


                    TedBottomPicker.with(FBpostPickImage.this)
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
                                showUriList(uriList);
                            });

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //checking if internet available
                if (!ConnectionDetector.networkStatus(getApplicationContext())) {

                    //   Toast.makeText(getApplicationContext(),"tre",Toast.LENGTH_SHORT).show();
                    android.app.AlertDialog alertDialog = new AlertDialog.Builder(FBpostPickImage.this).create();
                    alertDialog.setTitle("No Internet Connection");
                    alertDialog.setMessage("Please check your internet connection  and try again");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();

                        }
                    });
                    alertDialog.show();
                    return;
                }



                if(activitylisturi==null||activitylisturi.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Select Images",Toast.LENGTH_SHORT).show();
                }
                else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(FBpostPickImage.this);
                    builder.setTitle("Are you sure?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {




                            //     new AsyncGettingBitmapFromUrl().execute();

                            Submit_FBPostRequest();


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


            }
        });

    }

    public void Submit_FBPostRequest() {


        mProgressDialog = new ProgressDialog(FBpostPickImage.this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();



        Log.d( "urlp1: ", String.valueOf(selectedUriList));
        List<String> uri = new ArrayList<>();
        for(int i=0;i<selectedUriList.size();i++){
            String ere = removeUriFromPath(selectedUriList.get(i).toString());
            String urlString  = Uri.decode(ere);
            uri.add(urlString);
        }
        Log.d( "urlp2: ", String.valueOf(uri));

//        String ere = removeUriFromPath(Fbpost_requestimage.toString());
//        String urlString = Uri.decode(ere);
//        // String  urlString = ere.replaceAll(" ", "%20");
//        Log.d( "urlp2: ", String.valueOf(ere));

        //      String dfdnf = null;
//
//        try {
//            SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM");
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(inputFormat.parse(monthsubmit));
//            SimpleDateFormat outputFormat = new SimpleDateFormat("MM"); // 01-12
//
//            dfdnf = outputFormat.format(cal.getTime());
//        } catch (java.text.ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        String dateformsubmit = days+"-"+dfdnf+"-"+years;
//
//        Log.d( "urlp3: ", String.valueOf(dateformsubmit));
//
//

//        File imgFile = new File(urlString);
//        RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), imgFile);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imgFile.getName(), reqFile);
//


        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[uri.size()];

        for (int index = 0; index < uri.size(); index++) {
            Log.d("requestUploadSurvey" , index + "  " + uri.get(index));
            File file = new File( uri.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"),
                    file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("files[]",
                    file.getName(),
                    surveyBody);
        }

        Log.v("imageuploadarray", surveyImagesParts.toString()+"  "+uri.size());

        String spreschool_id = psid;
        RequestBody preschool_id =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, spreschool_id);

        String sfb_post_name = sactivityname ;
        RequestBody fb_post_name =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sfb_post_name);

        String sfb_description = sdescription ;
        RequestBody fb_description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sfb_description);

        String sfb_link = sdescription ;
        RequestBody fb_link =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sfb_link);

        String sischange = String.valueOf(checkboxselect_status);
        RequestBody ischange =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sischange);

        String screated_by = psid;
        RequestBody created_by =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, screated_by);
        String schanges = schangeinmakein;
        RequestBody changes =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, schanges);

        Log.v("datallparam", sfb_post_name+sischange+schanges);

        Call<ResponseBody> call = userService.FBPostRequest(preschool_id,
                fb_post_name,ischange,created_by,surveyImagesParts, changes,fb_link,fb_description);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                //  Log.v("Upload", "success");
                //Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "FBpost Request is successfully submited", Toast.LENGTH_SHORT).show();
                Intent splashLoginm = new Intent(getApplicationContext(), MainActivity.class);
                splashLoginm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(splashLoginm);
                finish();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("Upload error:", t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showUriList(List<Uri> uriList) {
        activitylisturi = new ArrayList<>() ;
        activitylisturi = uriList;
        ActivityPickImageFragmentAdapter customAdapter = new ActivityPickImageFragmentAdapter(getApplicationContext(), uriList);
        simpleGrid.setAdapter(customAdapter);

    }


    public static String removeUriFromPath(String uri)
    {
        return  uri.substring(7, uri.length());
    }


}
