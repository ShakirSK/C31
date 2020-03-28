package main.master.c31.UploadActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static main.master.c31.UploadActivity.PickimageFrag.ActivityPickImageFragment._hasLoadedOnce;
import static main.master.c31.UploadActivity.PickimageFrag.ActivityPickImageFragment.activitylisturi;

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
    String datesubmit;

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
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
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
                        if(activitylisturi==null)
                        {
                            Toast.makeText(getContext(),"Please Select Image",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Submit_Activity();
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
        Log.d( "urlp2: ", String.valueOf(uri));




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


        String spreschool_id = "19";
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

        String screated_by = "19";
        RequestBody created_by =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, screated_by);


        Call<ResponseBody> call = userService.Activity(preschool_id,
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








    }

    public static String removeUriFromPath(String uri)
    {
        return  uri.substring(7, uri.length());
    }


}
