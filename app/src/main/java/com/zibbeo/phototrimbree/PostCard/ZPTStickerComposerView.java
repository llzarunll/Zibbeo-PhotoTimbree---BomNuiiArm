package com.zibbeo.phototrimbree.PostCard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.CreatePostcard.Massage.ZPTMessageComposerView;
import com.zibbeo.phototrimbree.PostCard.Sticker.StickerImageView;
import com.zibbeo.phototrimbree.R;
import com.zibbeo.phototrimbree.Database.databaseClass;
import com.zibbeo.phototrimbree.Database.Image;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZARUN on 28/5/2559.
 */
public class ZPTStickerComposerView extends BaseNavigationDrawer {

    View contentView;
    Button nextButton, previousButton;
    FrameLayout canvas;
    LinearLayout StickerBar;
    int stickerCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_sticker_composer_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("picture");

        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ImageView image = (ImageView) findViewById(R.id.imageView);

        image.setImageBitmap(bmp);
        init();
        getSticker();
    }

    @Override
    protected void onResume() {
        super.onResume();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*Convert Bitmap to Byte Array*/
                FrameLayout savedImage = (FrameLayout) findViewById(R.id.canvasView);
                savedImage.setDrawingCacheEnabled(true);
                savedImage.buildDrawingCache();
                Bitmap bmp = savedImage.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                savedImage.destroyDrawingCache();

                Toast.makeText(contentView.getContext(), "Complete", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(contentView.getContext(), ZPTMessageComposerView.class);
                startActivity(intent);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void init() {
        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);
        canvas = (FrameLayout) findViewById(R.id.canvasView);
        StickerBar = (LinearLayout) findViewById(R.id.StickerBar);
    }

    private void getSticker() {
        /*Get Sticker from Drawable*/
        final Drawable Stickers[] =
                {
                        getResources().getDrawable(R.drawable.cloud),
                        getResources().getDrawable(R.drawable.idea),
                        getResources().getDrawable(R.drawable.star),
                        getResources().getDrawable(R.drawable.camera),
                        getResources().getDrawable(R.drawable.photos),
                        getResources().getDrawable(R.drawable.alarm),
                        getResources().getDrawable(R.drawable.hourglass),
                        getResources().getDrawable(R.drawable.like),
                        getResources().getDrawable(R.drawable.noimage)
                };
        /*Add Stickers to Sticker Bar*/
        for (int i = 0; i < Stickers.length; i++) {
            ImageView ib_view = new ImageView(this);
            ib_view.setId(i);
            ib_view.setPadding(5, 10, 5, 10);

            ib_view.setImageDrawable(Stickers[i]);
            ib_view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(180, 180);
            ib_view.setLayoutParams(vp);
            ib_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stickerCount < 4) {
                        final StickerImageView iv_sticker = new StickerImageView(ZPTStickerComposerView.this);
                        iv_sticker.setImageDrawable(Stickers[v.getId()]);
                        iv_sticker.iv_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if ((stickerCount - 1) < 0)
                                    stickerCount = 0;
                                else
                                    stickerCount--;
                                canvas.removeView(iv_sticker);
                            }
                        });
                        canvas.addView(iv_sticker);
                        stickerCount++;
                    }
                    else {
                        Toast.makeText(contentView.getContext(), "Maximum of stickers is 4", Toast.LENGTH_LONG).show();
                    }
                }
            });
            StickerBar.addView(ib_view);
        }
    }
}
