package main.master.c31.LauncherMainActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.master.c31.Database.DatabaseClient;
import main.master.c31.Database.User;
import main.master.c31.R;

import static android.content.Context.MODE_PRIVATE;


public class home_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView schoolname;
    RecyclerView recyclerView;
    static ArrayList<String> arrayList;

    ArrayList text5,title;
    ArrayList activitydescription;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    public home_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static home_fragment newInstance(String param1, String param2) {
        home_fragment fragment = new home_fragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        GetData gt = new GetData();
        gt.execute();

        // Storing data into SharedPreferences
         sharedPreferences = getActivity().getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        // Creating an Editor object
        // to edit(write to the file)
         myEdit = sharedPreferences.edit();


        title = new ArrayList<>(Arrays.asList("Activity_Picture","Birthday","Events",
                "Facebook_Post","Facebook_Page","Artwork_Request"));

         text5 = new ArrayList<>(Arrays.asList("Upload Activity Pictures","Upload Birthdays","Add Event Details",
                "Facebook Post Request","Facebook Page Requirement","Artwork Request"));

         activitydescription = new ArrayList<>(Arrays.asList(
                "Upload activity pictures along with activity name and a short description.",
                "Upload birthday details of the child along with the child's name and date of birth.",
                "Add details of the events you plan to conduct along with name, date, time and venue for the event.",
                "Upload Facebook post to be uploaded on your page.\n",
                "Upload pictures as per the mentioned requirement in this section.",
                "Add details of your artwork requirement."));

        schoolname = (TextView)view.findViewById(R.id.schoolname);
      //  schoolname.setTextColor(Color.parseColor("#ec98b9"));
        recyclerView = (RecyclerView)view.findViewById(R.id.allcontest);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager horizontalLayoutManager  = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(horizontalLayoutManager);



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

            arrayList =  new ArrayList<>();
            //List task data set to Record model  to show in recycleview
            for (int j = 0; j < tasks.size(); j++) {
                User funding = tasks.get(j);

                // Storing the key and its value
                // as the data fetched from edittext
                myEdit.putString(
                        "id",
                        funding.getPreschool_id());
                myEdit.commit();

                myEdit.putString(
                        "name",
                        funding.getPs_name());
                myEdit.commit();

                myEdit.putString(
                        "logo",
                        funding.getPs_logo());
                myEdit.commit();

                schoolname.setText(funding.getPs_name()+" !");
                String psa = funding.getPs_activities();

                String[] psachild = psa.split("\\s*,\\s*");

                String[] animal1 = psachild[0].split("\\[");
                String[] animal2 = psachild[psachild.length-1].split("]");


                arrayList.add(animal1[1].replaceAll("^\"|\"$", ""));
                arrayList.add(animal2[0].replaceAll("^\"|\"$", ""));


                for (int i=0;i<psachild.length;i++){
                   if( psachild[i].contains("[")||psachild[i].contains("]"))
                   {
                       Log.d( "onPostpsahave ",psachild[i]);
                   }
                   else
                   {
                       arrayList.add(psachild[i].replaceAll("^\"|\"$", ""));
                       Log.d( "onPostpsanh",psachild[i]);
                   }
                }


                Log.d( "onPostarray",arrayList.toString());

                home_fragmentAdapter customAdapter = new home_fragmentAdapter(getActivity(), title,text5,activitydescription);
                recyclerView.setAdapter(customAdapter);

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
