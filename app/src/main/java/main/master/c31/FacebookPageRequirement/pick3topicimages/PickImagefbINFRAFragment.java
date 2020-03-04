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


public class PickImagefbINFRAFragment extends Fragment {


    GridView simpleGrid;
    private List<Uri> selectedUriList;
    TextView topText;
    TextView selectphoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pick_imagefb_infra, container, false);
        simpleGrid = (GridView) view.findViewById(R.id.simpleGridView); // init GridView
        selectedUriList = new ArrayList<>();
        topText = (TextView)view.findViewById(R.id.topText);
        topText.setText("INFRASTRUCTURE PICTURES");

        selectphoto = (TextView) view.findViewById(R.id.selectphoto);

        selectphoto.setOnClickListener(new View.OnClickListener() {
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

/*    @SuppressLint("RestrictedApi")
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

        ActivityPickImageFragmentAdapter customAdapter = new ActivityPickImageFragmentAdapter(getContext(), uriList);
        simpleGrid.setAdapter(customAdapter);

    }


}
