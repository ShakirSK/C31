package main.master.c31.LauncherMainActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.AlphabeticIndex;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;

import com.squareup.picasso.Picasso;
import main.master.c31.Database.DatabaseClient;
import main.master.c31.Database.User;
import main.master.c31.Network.Datum;
import main.master.c31.R;
import main.master.c31.Session.SaveSharedPreference;

import static android.app.Activity.RESULT_OK;



public class more_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   ImageView profilepic;
   TextView preschoolname,preschoolname2,mobileno,email,address,website,ownername,fblink;
    Button logoutBT;
    private static int RESULT_LOAD_IMAGE = 1;
    public more_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment more_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static more_fragment newInstance(String param1, String param2) {
        more_fragment fragment = new more_fragment();
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
        View view =  inflater.inflate(R.layout.fragment_more, container, false);

        profilepic = (ImageView)view.findViewById(R.id.profilepic);
        preschoolname = (TextView)view.findViewById(R.id.preschoolname);
        preschoolname2 = (TextView)view.findViewById(R.id.preschoolname2);

        mobileno = (TextView)view.findViewById(R.id.mobilenumber);
        email = (TextView)view.findViewById(R.id.emailid);
        address = (TextView)view.findViewById(R.id.address);


        ownername = (TextView)view.findViewById(R.id.ownername);
        fblink = (TextView)view.findViewById(R.id.facebooklink);
        website = (TextView)view.findViewById(R.id.Websitelink);

        logoutBT = (Button)view.findViewById(R.id.logout);


        logoutBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        deleteTask();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "no", Toast.LENGTH_LONG).show();

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();

            }
        });

        GetData gt = new GetData();
        gt.execute();



        return view;
    }





    //retrive from Room DB
   public class GetData extends AsyncTask<Void, Void, List<User>> {

        @Override
        protected List<User> doInBackground(Void... voids) {
            //getting all data from room db in list
            List<User> taskList = DatabaseClient
                    .getInstance(getContext())
                    .getAppDatabase()
                    .userDao()
                    .getAll();
            return taskList;
        }

        @Override
        protected void onPostExecute(List<User> tasks) {
            super.onPostExecute(tasks);

            //List task data set to Record model  to show in recycleview
            for (int j = 0; j < tasks.size(); j++) {
                User funding = tasks.get(j);

                preschoolname.setText(funding.getPs_name()+" !");
                preschoolname2.setText(funding.getPs_name()+" !");

                ownername.setText(funding.getOwnername());
                fblink.setText(funding.getFacebooklink());
                website.setText(funding.getWebsite());

                mobileno.setText(funding.getPs_mobile());
                email.setText(funding.getPs_email());
                address.setText(funding.getCenter_address());


                String url = "http://creative31minds.com/creative-web/uploads/preschool_logo/"+funding.getPs_name().replaceAll("\\s", "")+"/"+funding.getPs_logo();
                Picasso.get().load(url).into(profilepic);
                String psa = funding.getPs_activities();
                String[] psachild = psa.split("\\s*,\\s*");

                String[] animal1 = psachild[0].split("\\[");
                String[] animal2 = psachild[1].split("]");
                Log.d( "onPostpsa ",animal1[1].replaceAll("^\"|\"$", "")+ "    "+animal2[0].replaceAll("^\"|\"$", ""));

            /*    AlphabeticIndex.Record record = new Record();
                //setting into model class for recycleview
                record.setMainImageURL(funding.getImg());
                record.setTitle(funding.getTitle());
                record.setShortDescription(funding.getShortDescription());

                recordslist.add(record);*/
            }


        }
    }


    private void deleteTask() {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getContext()).getAppDatabase()
                        .userDao()
                        .delete();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // Set LoggedIn status to false
                 SaveSharedPreference.setLoggedIn(getContext(), false);

                Toast.makeText(getContext(), "Successfully Logout", Toast.LENGTH_LONG).show();
                getActivity().finish();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

}