package main.master.c31.FacebookpostRequest.pickfragment;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import main.master.c31.R;

public class FbRequestPickImageFragmentAdapter extends BaseAdapter {
    Context context;
    List<Uri> logos;
    LayoutInflater inflter;
    private RequestManager requestManager;

    public FbRequestPickImageFragmentAdapter(Context applicationContext, List<Uri> logos) {
        this.context = applicationContext;
        this.logos = logos;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return logos.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_gridview_pickfragment, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.icon); // get the reference of ImageView
        requestManager = Glide.with(context);
    //    icon.setImageResource(logos[i]); // set logo images
        requestManager
                .load(logos.get(i).toString())
                .apply(new RequestOptions().fitCenter())
                .into(icon);

        return view;
    }
}