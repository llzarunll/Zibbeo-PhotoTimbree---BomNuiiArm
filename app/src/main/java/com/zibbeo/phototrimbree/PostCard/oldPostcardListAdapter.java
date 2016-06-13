package com.zibbeo.phototrimbree.PostCard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zibbeo.phototrimbree.DecodeTask;
import com.zibbeo.phototrimbree.R;

/**
 * Created by samchaw on 5/26/16 AD.
 */
public class oldPostcardListAdapter extends BaseAdapter {

    Context mContext;
    int mImageID;
    String[] mNameArray;
    LayoutInflater layoutInflater;

    public oldPostcardListAdapter(Context context, int imageID, String[] nameArray) {
        mContext = context;
        mImageID = imageID;
        mNameArray = nameArray;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (mNameArray.length == 0)
            return 1;
        else
            return mNameArray.length;
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        ImageView frontImageView;
        TextView nameTextView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.old_postcard_row, viewGroup, false);
            frontImageView = (ImageView) view.findViewById(R.id.frontImageView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        } else {
            frontImageView = (ImageView) view.findViewById(R.id.frontImageView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            DecodeTask dt1 = (DecodeTask) frontImageView.getTag(R.id.frontImageView);
            if (dt1 != null)
                dt1.cancel(true);
        }
        if (mNameArray.length == 0)
            nameTextView.setText("No postcard!!");
        else
            nameTextView.setText(mNameArray[position]);
        frontImageView.setImageBitmap(null);
        DecodeTask dt2;
        if (mNameArray.length == 0)
            dt2 = new DecodeTask(mContext, frontImageView, R.drawable.noimage);
        else
            dt2 = new DecodeTask(mContext, frontImageView, mImageID);
        dt2.execute();
        frontImageView.setTag(R.id.frontImageView, dt2);

        return view;
    }
}
