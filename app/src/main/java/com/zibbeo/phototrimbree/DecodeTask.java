package com.zibbeo.phototrimbree;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by samchaw on 5/26/16 AD.
 */
public class DecodeTask extends AsyncTask<String, Void, Bitmap> {

    Context mContext;
    ImageView mImageView;
    int resId;

    public DecodeTask(Context context, ImageView iv, int res_id) {
        mContext = context;
        mImageView = iv;
        resId = res_id;
    }

    protected Bitmap doInBackground(String... params) {
        return decodeBitmapFromResource(resId, 100, 80);
    }

    protected void onPostExecute(Bitmap result) {
        mImageView.setImageBitmap(result);
    }

    private Bitmap decodeBitmapFromResource(int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mContext.getResources(), resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), resId, options);
        return bmp;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if(height > reqHeight || width > reqWidth) {
            if(width > height)
                inSampleSize = Math.round((float) height / (float) reqHeight);
            else
                inSampleSize = Math.round((float) width / (float) reqWidth);
        }
        return inSampleSize;
    }

}
