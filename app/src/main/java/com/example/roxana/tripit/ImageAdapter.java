package com.example.roxana.tripit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roxana on 5/11/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
        for (int i : mThumbIds_intial)
            mThumbIds.add(i);
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Object getItem(int position) {
        return mThumbIds.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        // if it's not recycled, initialize some attributes
        if (convertView == null) {
            imageView = new ImageView(mContext);
            // Center crop image
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        // Set images into ImageView
        imageView.setImageResource(mThumbIds.get(position));
        return imageView;
    }

    // References to images in res > drawable
    private Integer[] mThumbIds_intial = { R.drawable.paris,  R.drawable.london,
            R.drawable.atena,  R.drawable.sidney, R.drawable.milano,R.drawable.ny,R.drawable.dubai,R.drawable.rome};

    public List<Integer> mThumbIds = new ArrayList<Integer>();

    public void addCity(int drawableId) {
        mThumbIds.add(drawableId);
    }
}
