package main.master.c31.LauncherMainActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.AlphabeticIndex;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;

import main.master.c31.Database.DatabaseClient;
import main.master.c31.Database.User;
import main.master.c31.Network.Datum;
import main.master.c31.R;

import static android.app.Activity.RESULT_OK;



public class more_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   ImageView imageclick;
   TextView preschoolname;
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

        imageclick = (ImageView)view.findViewById(R.id.imageclick);
        preschoolname = (TextView)view.findViewById(R.id.preschoolname);


        GetData gt = new GetData();
        gt.execute();


      /*  imageclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });*/
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage;
            selectedImage = data.getData();

            String[] filePathColumn = { MediaStore.Images.Media.DATA };


            Bitmap bitmapImage = null;
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                imageclick.setImageBitmap(bitmapImage);
            } catch (IOException e) {
                e.printStackTrace();
            }




        }


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

                preschoolname.setText(funding.getPs_name());
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

}