package com.zibbeo.phototrimbree.Cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.zibbeo.phototrimbree.DecodeTask;
import com.zibbeo.phototrimbree.R;

/**
 * Created by samchaw on 6/3/16 AD.
 */
public class readyPostcardListAdapter extends BaseAdapter {

    Context mContext;
    int mImageID;
    String[] mNameArray;
    LayoutInflater layoutInflater;
    Boolean[] ready;

    public readyPostcardListAdapter(Context context, int imageID, String[] nameArray) {
        mContext = context;
        mImageID = imageID;
        mNameArray = nameArray;
        ready = new Boolean[mNameArray.length];
        for (int i = 0; i < ready.length; i++) {
            ready[i] = false;
        }
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        final ListView listView = (ListView) viewGroup;

        if (view == null) {
            Log.i("ddd", view + " " + position);
            view = layoutInflater.inflate(R.layout.ready_postcard_row, viewGroup, false);
            viewHolder.frontImageView = (ImageView) view.findViewById(R.id.frontImageView);
            viewHolder.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            viewHolder.readySwitch = (Switch) view.findViewById(R.id.readySwitch);
            viewHolder.readySwitch.setChecked(ready[position]);
            view.setTag(viewHolder);
        } else {
//            viewHolder.frontImageView = (ImageView) view.findViewById(R.id.frontImageView);
//            viewHolder.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
//            viewHolder.readySwitch = (Switch) view.findViewById(R.id.readySwitch);
//            viewHolder.readySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    listView.setItemChecked(position, ready[position]);
//                }
//            });
            Log.i("aaa", view + " " + position);
            viewHolder = (ViewHolder) view.getTag();
//            viewHolder.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
//            viewHolder.readySwitch = (Switch) view.findViewById(R.id.readySwitch);
//            viewHolder.frontImageView = (ImageView) view.findViewById(R.id.frontImageView);
//            DecodeTask dt1 = (DecodeTask) viewHolder.frontImageView.getTag(R.id.frontImageView);
//            if (dt1 != null)
//                dt1.cancel(true);
        }
        if (mNameArray.length == 0)
            viewHolder.nameTextView.setText("No postcard!!");
        else
            viewHolder.nameTextView.setText(mNameArray[position]);
        viewHolder.frontImageView.setImageResource(mImageID);
//        viewHolder.frontImageView.setImageBitmap(null);
//        DecodeTask dt2;
//        if (mNameArray.length == 0)
//            dt2 = new DecodeTask(mContext, viewHolder.frontImageView, R.drawable.noimage);
//        else
//            dt2 = new DecodeTask(mContext, viewHolder.frontImageView, mImageID);
//        dt2.execute();
//        viewHolder.frontImageView.setTag(R.id.frontImageView, dt2);
        //restore switch state
        viewHolder.readySwitch.setChecked(ready[position]);

//        viewHolder.readySwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ready[position] = viewHolder.readySwitch.isChecked();
//            }
//        });
        return view;
    }

    public Boolean[] getReady() {
        return ready;
    }

    public Boolean getReadyWithPosition(int position) {
        return ready[position];
    }

    public void setReady(Boolean ready, int position) {
        this.ready[position] = ready;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public ImageView frontImageView;
        public TextView nameTextView;
        public Switch readySwitch;
    }
}
