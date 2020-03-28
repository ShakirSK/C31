package main.master.c31.FacebookpostRequest;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import main.master.c31.LauncherMainActivity.MainActivity;
import main.master.c31.Network.ApiUtils;
import main.master.c31.Network.UserService;
import main.master.c31.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static main.master.c31.FacebookpostRequest.pickfragment.FbRequestPickImageFragment.Fbpost_requestimage;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacebookInfoFragment extends Fragment {

    LinearLayout one,two;
    CheckBox cbone,cbtwo;
    EditText changeinmakein;
    UserService userService;
    TextView save;
    EditText activityname;
    String sactivityname,schangeinmakein;
    int checkboxselect_status;
    public FacebookInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_facebook_info, container, false);

        userService = ApiUtils.getUserService();

        one = (LinearLayout)view.findViewById(R.id.one);
        two = (LinearLayout)view.findViewById(R.id.two);

        cbone = (CheckBox)view.findViewById(R.id.checkbox1);
        cbtwo = (CheckBox)view.findViewById(R.id.checkbox2);
        changeinmakein = (EditText)view.findViewById(R.id.changeinmakein);
        activityname = (EditText) view.findViewById(R.id.activityname);



        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cbtwo.setChecked(false);
                cbone.setChecked(true);
                changeinmakein.setVisibility(View.VISIBLE);
                checkboxselect_status=1;


            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cbtwo.setChecked(true);
                cbone.setChecked(false);
                changeinmakein.setVisibility(View.GONE);
                checkboxselect_status=0;
            }
        });

        save = (TextView)view.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sactivityname = activityname.getText().toString().trim();

                if (sactivityname.isEmpty()) {
                    activityname.setError("Name required");
                    activityname.requestFocus();
                    return;
                }

                if(checkboxselect_status==1)
                {
                    schangeinmakein = changeinmakein.getText().toString().trim();

                    if (schangeinmakein.isEmpty()) {
                        changeinmakein.setError("Changes required");
                        changeinmakein.requestFocus();
                        return;
                    }
                }
                else if(checkboxselect_status==0)
                 {
                     schangeinmakein="notanychanges";
                }



                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(Fbpost_requestimage==null)
                        {
                            Toast.makeText(getContext(),"Please Select Image",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Submit_FBPostRequest();
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
        return view ;
    }

    public void Submit_FBPostRequest() {


        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        Log.d( "urlp1: ", String.valueOf(Fbpost_requestimage));


        String ere = removeUriFromPath(Fbpost_requestimage.toString());
        String urlString = Uri.decode(ere);
        // String  urlString = ere.replaceAll(" ", "%20");
        Log.d( "urlp2: ", String.valueOf(ere));

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

        File imgFile = new File(urlString);
        RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), imgFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imgFile.getName(), reqFile);



        String spreschool_id = "19";
        RequestBody preschool_id =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, spreschool_id);

        String sfb_post_name = sactivityname ;
        RequestBody fb_post_name =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sfb_post_name);

        String sischange = String.valueOf(checkboxselect_status);
        RequestBody ischange =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, sischange);

        String screated_by = "19";
        RequestBody created_by =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, screated_by);
        String schanges = schangeinmakein;
        RequestBody changes =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, schanges);

        Log.v("datallparam", sfb_post_name+sischange+schanges);

        Call<ResponseBody> call = userService.FBPostRequest(preschool_id,
                fb_post_name,ischange,created_by,body, changes);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
              //  Log.v("Upload", "success");
                //Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "FBpost Request is successfully submited", Toast.LENGTH_SHORT).show();
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
    }


    public static String removeUriFromPath(String uri)
    {
        return  uri.substring(7, uri.length());
    }



}
