package main.master.c31.FacebookPageRequirement.change_in_activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import gun0912.tedbottompicker.TedBottomPicker;
import main.master.c31.LauncherMainActivity.HOME.MainActivity;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import main.master.c31.UploadActivity.PickimageFrag.ActivityPickImageFragmentAdapter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static main.master.c31.FacebookPageRequirement.FacebookPageRequirementMain.arrayList;

public class FBRequirementMenuActivity extends AppCompatActivity {

    GridView simpleGrid;
    private List<Uri> selectedUriList;
    TextView topText;
    TextView selectphoto,submit;
    List<Uri> fblisturi;
    UserService userService;
    String psid;
    int keyrequirement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbrequirement_menu);

        SharedPreferences sh
                = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        psid= sh.getString("id", "");

        userService = ApiUtils.getUserService();

        topText = (TextView)findViewById(R.id.topText);
        Intent intent = getIntent();
        topText.setText(intent.getStringExtra("YourKeyHere"));

        //Toast.makeText(getApplicationContext(),intent.getStringExtra("YourKeyHere"),Toast.LENGTH_SHORT).show();

        for (Map.Entry<Integer, String> entry : arrayList.entrySet()) {
            if (entry.getValue().equals(intent.getStringExtra("YourKeyHere"))) {
                String ddf = String.valueOf(entry.getKey());
               // Toast.makeText(getApplicationContext(),ddf+entry.getValue(),Toast.LENGTH_SHORT).show();
                keyrequirement=entry.getKey();

            }
        }

        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        selectedUriList = new ArrayList<>();

        selectphoto = (TextView) findViewById(R.id.selectphoto);
        submit = (TextView) findViewById(R.id.submits);

        selectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TedBottomPicker.with(FBRequirementMenuActivity.this)
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

                AlertDialog.Builder builder = new AlertDialog.Builder(FBRequirementMenuActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(fblisturi.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"Please Select Image",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Submit_FBRequirement();
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

    }

    private void showUriList(List<Uri> uriList) {
        fblisturi = new ArrayList<>() ;
        fblisturi = uriList;
        ActivityPickImageFragmentAdapter customAdapter = new ActivityPickImageFragmentAdapter(getApplicationContext(), uriList);
        simpleGrid.setAdapter(customAdapter);

    }

    public void Submit_FBRequirement() {

        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        Log.d( "urlp1: ", String.valueOf(fblisturi));
        List<String> uri = new ArrayList<>();
        for(int i=0;i<fblisturi.size();i++){
            String ere = removeUriFromPath(fblisturi.get(i).toString());
            String urlString  = Uri.decode(ere);
            uri.add(urlString);
        }
        Log.d( "urlp2: ", String.valueOf(uri));




        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[uri.size()];

        for (int index = 0; index < uri.size(); index++) {
            Log.d("requestUploadSurvey" , index + "  " + uri.get(index));
            File file = new File( uri.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"),
                    file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("files"+keyrequirement+"[]",
                    file.getName(),
                    surveyBody);
        }

        Log.v("imageuploadarray", surveyImagesParts.toString()+"  "+uri.size());


        String spreschool_id = psid;
        RequestBody preschool_id =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, spreschool_id);


        String screated_by = psid;
        RequestBody modified_by =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, screated_by);


        Call<ResponseBody> call = userService.FBRequirement(preschool_id,
                modified_by,surveyImagesParts);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
              //  Log.v("Upload", response.toString());
             //   Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                Toast.makeText(FBRequirementMenuActivity.this, "Successfully Submited", Toast.LENGTH_SHORT).show();
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

    public static String removeUriFromPath(String uri)
    {
        return  uri.substring(7, uri.length());
    }

}
