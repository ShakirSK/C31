package main.master.c31.FacebookPageRequirement.pick3topicimages;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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


public class PickImagefbSTAFFFragment extends Fragment {


    GridView simpleGrid;
    private List<Uri> selectedUriList;
    TextView topText;
    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pick_imagefb_kid, container, false);
        simpleGrid = (GridView) view.findViewById(R.id.simpleGridView); // init GridView
        selectedUriList = new ArrayList<>();
        topText = (TextView)view.findViewById(R.id.topText);
        topText.setText("STAFF PICTURES");
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
                        .setSelectedUriList(selectedUriList)
                        .showMultiImage(uriList -> {
                            selectedUriList = uriList;
                            showUriList(uriList);
                        });
            }
        });
        return view;
    }

    private void showUriList(List<Uri> uriList) {

        ActivityPickImageFragmentAdapter customAdapter = new ActivityPickImageFragmentAdapter(getContext(), uriList);
        simpleGrid.setAdapter(customAdapter);

    }


}
