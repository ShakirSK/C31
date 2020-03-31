package main.master.c31.UploadActivity.PickimageFrag;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import main.master.c31.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActivityPickImageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActivityPickImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityPickImageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Uri> selectedUriList;
    public static boolean _hasLoadedOnce= false; // your boolean field

    GridView simpleGrid;
     FloatingActionButton choosemore;
     public static  List<Uri> activitylisturi;
    public ActivityPickImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FbRequestPickImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityPickImageFragment newInstance(String param1, String param2) {
        ActivityPickImageFragment fragment = new ActivityPickImageFragment();
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
        View view = inflater.inflate(R.layout.fragment_activity_pick_image, container, false);
        simpleGrid = (GridView) view.findViewById(R.id.simpleGridView); // init GridView
        choosemore = (FloatingActionButton) view.findViewById(R.id.choosemore);
        selectedUriList = new ArrayList<>();
        choosemore.setOnClickListener(new View.OnClickListener() {
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

       /* BSImagePicker pickerDialog = new BSImagePicker.Builder
                ("main.master.c31.fileprovider")
                .setMaximumDisplayingImages(Integer.MAX_VALUE)
                .isMultiSelect()
                .setMinimumMultiSelectCount(3)
                .setMaximumMultiSelectCount(6)
                .build();
*/
   /*     BSImagePicker pickerDialog = new BSImagePicker.Builder
                ("main.master.c31.fileprovider")
                .isMultiSelect() //Set this if you want to use multi selection mode.
                .setMinimumMultiSelectCount(3) //Default: 1.
                .setMaximumMultiSelectCount(6) //Default: Integer.MAX_VALUE (i.e. User can select as many images as he/she wants)
                .setMultiSelectBarBgColor(android.R.color.white) //Default: #FFFFFF. You can also set it to a translucent color.
                .setMultiSelectTextColor(R.color.primary_text) //Default: #212121(Dark grey). This is the message in the multi-select bottom bar.
                .setMultiSelectDoneTextColor(R.color.colorAccent) //Default: #388e3c(Green). This is the color of the "Done" TextView.
                .setOverSelectTextColor(R.color.error_text) //Default: #b71c1c. This is the color of the message shown when user tries to select more than maximum select count.
                .disableOverSelectionMessage() //You can also decide not to show this over select message.
                .build();*/
      /*  BSImagePicker pickerDialog = new BSImagePicker.Builder("main.master.c31.fileprovider")
                .build();*/
       // pickerDialog.show(getSupportFragmentManager(), "picker");
     //   pickerDialog.show(getChildFragmentManager(), "picker");
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

   /* private void showUriList(List<Uri> uriList) {
        // Remove all views before
        // adding the new ones.
     //   mSelectedImagesContainer.removeAllViews();

      //  iv_image.setVisibility(View.GONE);
    //    mSelectedImagesContainer.setVisibility(View.VISIBLE);

        int widthPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int heightPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());


        for (Uri uri : uriList) {

            View imageHolder = LayoutInflater.from(getContext()).inflate(R.layout.image_item, null);
            ImageView thumbnail = imageHolder.findViewById(R.id.media_image);

            requestManager
                    .load(uri.toString())
                    .apply(new RequestOptions().fitCenter())
                    .into(thumbnail);

            mSelectedImagesContainer.addView(imageHolder);

            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(widthPixel, heightPixel));

        }

    }*/

    @SuppressLint("RestrictedApi")
    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);
        if (this.isVisible()) {
// we check that the fragment is becoming visible
            Log.d( "setUserVisibleHint: ",isFragmentVisible_  +" " +!_hasLoadedOnce );
            if (isFragmentVisible_ && selectedUriList.isEmpty()) {
                choosemore.setVisibility(View.GONE);

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
                _hasLoadedOnce = true;
            }
            else{
                choosemore.setVisibility(View.VISIBLE);
            }
        }

    }
    private void showUriList(List<Uri> uriList) {


        activitylisturi = new ArrayList<>() ;
        activitylisturi = uriList;
        Log.d( "urlp1: ", String.valueOf(activitylisturi));
        ActivityPickImageFragmentAdapter customAdapter = new ActivityPickImageFragmentAdapter(getContext(), uriList);
        simpleGrid.setAdapter(customAdapter);

    }


    /*
      .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
@Override
public void onImagesSelected(List<Uri> uriList) {
        // here is selected image uri list
        }*/
}
