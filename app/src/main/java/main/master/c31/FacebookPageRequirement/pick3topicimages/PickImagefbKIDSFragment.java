package main.master.c31.FacebookPageRequirement.pick3topicimages;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import main.master.c31.R;
import main.master.c31.UploadActivity.PickimageFrag.ActivityPickImageFragmentAdapter;


public class PickImagefbKIDSFragment extends Fragment {


    GridView simpleGrid;
    private List<Uri> selectedUriList;
    TextView topText;
    Button button;
    public static  List<Uri> fblisturi;


    public static PickImagefbKIDSFragment newInstance(int val) {
        PickImagefbKIDSFragment fragment = new PickImagefbKIDSFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pick_imagefb_kid, container, false);
        simpleGrid = (GridView) view.findViewById(R.id.simpleGridView); // init GridView
        topText = (TextView)view.findViewById(R.id.topText);
        topText.setText("FIVE PICTURES OF KIDS");
        selectedUriList = new ArrayList<>();

        String val = String.valueOf(getArguments().getInt("someInt", 0));
        Toast.makeText(getContext(),val,Toast.LENGTH_SHORT).show();

/*

       String strtext = String.valueOf(getArguments().getInt("edttext0"));

    */
/*    String result = container.getTag().toString();*//*

        Toast.makeText(getContext(),strtext,Toast.LENGTH_SHORT).show();

        String strtext1 = String.valueOf(getArguments().getInt("edttext1"));

        */
/*    String result = container.getTag().toString();*//*

        Toast.makeText(getContext(),strtext1,Toast.LENGTH_SHORT).show();

*/
        button = (Button)view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TedBottomPicker.with(getActivity())
                        //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                        .setPeekHeight(1600)
                        .showTitle(true)
                        .setCompleteButtonText("Done")
                        .setEmptySelectionText("Nothing Selected")
                        .setPreviewMaxCount(200)
                        .setGalleryTileBackgroundResId(R.color.statusbarcolor)
                        .setGalleryTile(R.drawable.ic_gallery)
                        .setSelectMaxCount(5)
                        .setSelectedUriList(selectedUriList)
                        .showMultiImage(uriList -> {
                            selectedUriList = uriList;
                            showUriList(uriList);
                        });
            }
        });


        return view;
    }
/*
    @SuppressLint("RestrictedApi")
    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);
        if (this.isVisible()) {
// we check that the fragment is becoming visible
          //  Log.d( "setUserVisibleHint: ",isFragmentVisible_  +" " +!_hasLoadedOnce );
            if (isFragmentVisible_ && selectedUriList.isEmpty()) {
               // choosemore.setVisibility(View.GONE);
                TedBottomPicker.with(getActivity())
                        //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                        .setPeekHeight(1600)
                        .showTitle(true)
                        .setCompleteButtonText("Done")
                        .setEmptySelectionText("Nothing Selected")
                        .setPreviewMaxCount(60)
                        .setSelectedUriList(selectedUriList)
                        .showMultiImage(uriList -> {
                            selectedUriList = uriList;
                            showUriList(uriList);
                        });

        //        _hasLoadedOnce = true;
            }
            else{
            //    choosemore.setVisibility(View.VISIBLE);
            }
        }

    }*/
    private void showUriList(List<Uri> uriList) {
        fblisturi = uriList;
        ActivityPickImageFragmentAdapter customAdapter = new ActivityPickImageFragmentAdapter(getContext(), uriList);
        simpleGrid.setAdapter(customAdapter);

    }


}
