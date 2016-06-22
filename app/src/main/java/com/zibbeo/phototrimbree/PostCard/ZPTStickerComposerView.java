package com.zibbeo.phototrimbree.PostCard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.CreatePostcard.Massage.ZPTMessageComposerView;
import com.zibbeo.phototrimbree.Database.ImageComposer;
import com.zibbeo.phototrimbree.Database.ImageTemplate;
import com.zibbeo.phototrimbree.Database.StickerTemplate;
import com.zibbeo.phototrimbree.PostCard.Sticker.StickerImageView;
import com.zibbeo.phototrimbree.PostCard.Sticker.StickerView;
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
    ArrayList<stickerListItem> stickerItems = new ArrayList<stickerListItem>();
    String Name;
    ImageView selectStickerImage;

    /*Nuii*/
    databaseClass mDatabaseClass;
    String  stickerTemplateID1, stickerTemplateID2, stickerTemplateID3, stickerTemplateID4 ,getImageTemplateID ,imageTemplateID;
    //ArrayList getST1,getST2,getST3,getST4, stickerValue1,stickerValue2,stickerValue3,stickerValue4,sticker1,sticker2,sticker3,sticker4;
    byte[] sticker1,sticker2,sticker3,sticker4;
    float sticker1_x,sticker1_y,sticker2_x,sticker2_y,sticker3_x,sticker3_y,sticker4_x,sticker4_y;
    String getStID1,getStID2,getStID3,getStID4;

    public static class stickerListItem {
        public static String stickerIndex;
        public Bitmap stickerImage;
        public float rotateX;
        public float rotateY;
        public float scaleX;
        public float scaleY;
        public float moveX;
        public float moveY;

        public stickerListItem(String stickerIndex, Bitmap stickerImage, float rotateX, float rotateY, float scaleX, float scaleY, float moveX, float moveY) {
            this.stickerIndex = stickerIndex;
            this.stickerImage = stickerImage;
            this.rotateX = rotateX;
            this.rotateY = rotateY;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            this.moveX = moveX;
            this.moveY = moveY;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_sticker_composer_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        /*Show Image from Image Composer*/
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ImageView image = (ImageView) findViewById(R.id.imageView);
        bmp = scaleDown(bmp,image.getMaxWidth(),true);
        image.setImageBitmap(bmp);

        init();
        getSticker();

        /*Nuii*/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            stickerTemplateID1 = bundle.getString( "stickerTemplateID1" );
            stickerTemplateID2 = bundle.getString( "stickerTemplateID2" );
            stickerTemplateID3 = bundle.getString( "stickerTemplateID3" );
            stickerTemplateID4 = bundle.getString( "stickerTemplateID4" );
            imageTemplateID = bundle.getString( "imageTemplateID" );
            getImageTemplateID = bundle.getString( "getImageTemplateID" );
        }

        //Get sticker 1
        if (stickerTemplateID1 != null) {
            GetSticker1( stickerTemplateID1 );
        }

        //Get sticker 2
        if (stickerTemplateID2 != null) {
            GetSticker2( stickerTemplateID2 );
        }

        //Get sticker 3
        if (stickerTemplateID3 != null) {
            GetSticker3( stickerTemplateID3 );
        }

        //Get sticker 4
        if (stickerTemplateID4 != null) {
            GetSticker4( stickerTemplateID4 );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Convert Bitmap to Byte Array*//*
                FrameLayout savedImage = (FrameLayout) findViewById(R.id.canvasView);
                savedImage.setDrawingCacheEnabled(true);
                savedImage.buildDrawingCache();
                Bitmap bmp = savedImage.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                savedImage.destroyDrawingCache();*/

                /*Nuii*/
                //region Insert Sticlker image
                if(stickerCount>=1) {
                    setSticker1();
                    if (stickerTemplateID1 == null) {
                        getStID1 = mDatabaseClass.createID();
                    }
                    StickerTemplate tStickerTemplate = new StickerTemplate(getStID1,sticker1,sticker1_x,sticker1_y);
                    mDatabaseClass.insertStickerTemplate(tStickerTemplate);
                }
                else {
                    getStID1 = stickerTemplateID1;
                    StickerTemplate tStickerTemplate = new StickerTemplate(getStID1,sticker1,sticker1_x,sticker1_y);
                    mDatabaseClass.updateStickerTemplate(tStickerTemplate);
                }

                if(stickerCount>=2) {
                    setSticker2();
                    if (stickerTemplateID2 == null) {
                        getStID2 = mDatabaseClass.createID();
                    }
                    StickerTemplate tStickerTemplate = new StickerTemplate(getStID2,sticker2,sticker2_x,sticker2_y);
                    mDatabaseClass.updateStickerTemplate(tStickerTemplate);
                }
                else {
                    getStID2 = stickerTemplateID2;
                    StickerTemplate tStickerTemplate = new StickerTemplate(getStID2,sticker2,sticker2_x,sticker2_y);
                    mDatabaseClass.updateStickerTemplate(tStickerTemplate);
                }

                if(stickerCount>=3) {
                    setSticker3();
                    if (stickerTemplateID3 == null) {
                        getStID3 = mDatabaseClass.createID();
                    }

                    StickerTemplate tStickerTemplate = new StickerTemplate(getStID3,sticker3,sticker3_x,sticker3_y);
                    mDatabaseClass.updateStickerTemplate(tStickerTemplate);
                }
                else {
                    getStID3 = stickerTemplateID3;
                    StickerTemplate tStickerTemplate = new StickerTemplate(getStID3,sticker3,sticker3_x,sticker3_y);
                    mDatabaseClass.updateStickerTemplate(tStickerTemplate);
                }

                if(stickerCount==4) {
                    setSticker4();
                    if (stickerTemplateID4 == null) {
                        getStID4 = mDatabaseClass.createID();
                    }
                    StickerTemplate tStickerTemplate = new StickerTemplate(getStID4,sticker4,sticker4_x,sticker4_y);
                    mDatabaseClass.updateStickerTemplate(tStickerTemplate);
                }
                else {
                    getStID4 = stickerTemplateID4;
                    StickerTemplate tStickerTemplate = new StickerTemplate(getStID4,sticker4,sticker4_x,sticker4_y);
                    mDatabaseClass.updateStickerTemplate(tStickerTemplate);
                }

                //insert image composer
                if(getImageTemplateID==null)
                {
                    getImageTemplateID =  mDatabaseClass.createID();
                    ImageComposer tImageComposer = new ImageComposer(getImageTemplateID,imageTemplateID,stickerTemplateID1,stickerTemplateID2,stickerTemplateID3,stickerTemplateID4);
                    mDatabaseClass.insertImageComposer(tImageComposer);
                }else {
                    ImageComposer tImageComposer = new ImageComposer(getImageTemplateID,imageTemplateID,stickerTemplateID1,stickerTemplateID2,stickerTemplateID3,stickerTemplateID4);
                    mDatabaseClass.updateImageComposer(tImageComposer);
                }
                //endregion


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
            /*getResources().getDrawable(R.drawable.alarm),
            getResources().getDrawable(R.drawable.hourglass),
            getResources().getDrawable(R.drawable.like),*/
            getResources().getDrawable(R.drawable.noimage)
        };

        /*Add Stickers to Sticker Bar*/
        for (int i = 0; i < Stickers.length; i++) {
            final ImageView ib_view = new ImageView(this);
            ib_view.setId(i);
            ib_view.setPadding(5, 10, 5, 10);

            ib_view.setImageDrawable(Stickers[i]);
            ib_view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(180, 180);
            ib_view.setLayoutParams(vp);
            ib_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stickerItems.size() < 4) {
                        final StickerImageView iv_sticker = new StickerImageView(ZPTStickerComposerView.this);
                        iv_sticker.setImageDrawable(Stickers[v.getId()]);
                        iv_sticker.iv_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                stickerItems.remove(stickerItems.get(Integer.valueOf(Name)-1));
                                canvas.removeView(iv_sticker);
                            }
                        });

                        canvas.addView(iv_sticker);
                        iv_sticker.owner_id = String.valueOf(stickerCount);

                        Bitmap StickerImage = v.getDrawingCache();
                        stickerItems.add(new stickerListItem(iv_sticker.owner_id, StickerImage, -1, -1, -1, -1, -1, -1));
                        stickerCount++;
                        /*iv_sticker.setControlItemsHidden(true);*/
                        iv_sticker.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent event) {
                                if (view.getTag().equals("DraggableViewGroup")) {
                                    switch (event.getAction()) {
                                        case MotionEvent.ACTION_DOWN:
                                            iv_sticker.move_orgX = event.getRawX();
                                            iv_sticker.move_orgY = event.getRawY();
                                            break;
                                        case MotionEvent.ACTION_MOVE:
                                            float offsetX = event.getRawX() - iv_sticker.move_orgX;
                                            float offsetY = event.getRawY() - iv_sticker.move_orgY;
                                            iv_sticker.setX(iv_sticker.getX() + offsetX);
                                            iv_sticker.setY(iv_sticker.getY() + offsetY);
                                            iv_sticker.move_orgX = event.getRawX();
                                            iv_sticker.move_orgY = event.getRawY();
                                            break;
                                        case MotionEvent.ACTION_UP:
                                            for(stickerListItem SelectItem : stickerItems){
                                                if (stickerListItem.stickerIndex.equals (iv_sticker.owner_id)){
                                                    SelectItem.rotateX = iv_sticker.getRotationX();
                                                    SelectItem.rotateY = iv_sticker.getRotationY();
                                                    SelectItem.scaleX = iv_sticker.getScaleX();
                                                    SelectItem.scaleY = iv_sticker.getScaleY();
                                                    SelectItem.moveX = iv_sticker.getX();
                                                    SelectItem.moveY = iv_sticker.getY();
                                                    break;
                                                }
                                            }
                                            break;
                                        /*case MotionEvent.:
                                            iv_sticker.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                public void onFocusChange(View view, boolean hasFocus) {
                                                    iv_sticker.setControlItemsHidden(hasFocus);
                                                }
                                            });
                                            break;*/
                                    }
                                } else if (view.getTag().equals("iv_scale")) {
                                    switch (event.getAction()) {
                                        case MotionEvent.ACTION_DOWN:
                                            iv_sticker.this_orgX = iv_sticker.getX();
                                            iv_sticker.this_orgY = iv_sticker.getY();

                                            iv_sticker.scale_orgX = event.getRawX();
                                            iv_sticker.scale_orgY = event.getRawY();
                                            iv_sticker.scale_orgWidth = iv_sticker.getLayoutParams().width;
                                            iv_sticker.scale_orgHeight = iv_sticker.getLayoutParams().height;

                                            iv_sticker.rotate_orgX = event.getRawX();
                                            iv_sticker.rotate_orgY = event.getRawY();

                                            iv_sticker.centerX = iv_sticker.getX() + ((View) iv_sticker.getParent()).getX() +
                                                    (float) iv_sticker.getWidth() / 2;

                                            int result = 0;
                                            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                                            if (resourceId > 0) {
                                                result = getResources().getDimensionPixelSize(resourceId);
                                            }
                                            double statusBarHeight = result;
                                            iv_sticker.centerY = iv_sticker.getY() +
                                                    ((View) iv_sticker.getParent()).getY() +
                                                    statusBarHeight +
                                                    (float) iv_sticker.getHeight() / 2;

                                            break;
                                        case MotionEvent.ACTION_MOVE:
                                            iv_sticker.rotate_newX = event.getRawX();
                                            iv_sticker.rotate_newY = event.getRawY();

                                            double angle_diff = Math.abs(
                                                    Math.atan2(event.getRawY() - iv_sticker.scale_orgY, event.getRawX() - iv_sticker.scale_orgX)
                                                            - Math.atan2(iv_sticker.scale_orgY - iv_sticker.centerY, iv_sticker.scale_orgX - iv_sticker.centerX)) * 180 / Math.PI;
                                            double length1 = iv_sticker.getLength(iv_sticker.centerX, iv_sticker.centerY, iv_sticker.scale_orgX, iv_sticker.scale_orgY);
                                            double length2 = iv_sticker.getLength(iv_sticker.centerX, iv_sticker.centerY, event.getRawX(), event.getRawY());

                                            int size = iv_sticker.convertDpToPixel(iv_sticker.SELF_SIZE_DP, iv_sticker.getContext());
                                            if (length2 > length1
                                                    && (angle_diff < 25 || Math.abs(angle_diff - 180) < 25)
                                                    ) {
                                                //scale up
                                                double offsetX = Math.abs(event.getRawX() - iv_sticker.scale_orgX);
                                                double offsetY = Math.abs(event.getRawY() - iv_sticker.scale_orgY);
                                                double offset = Math.max(offsetX, offsetY);
                                                offset = Math.round(offset);
                                                iv_sticker.getLayoutParams().width += offset;
                                                iv_sticker.getLayoutParams().height += offset;
                                                iv_sticker.onScaling(true);
                                            } else if (length2 < length1
                                                    && (angle_diff < 25 || Math.abs(angle_diff - 180) < 25)
                                                    && iv_sticker.getLayoutParams().width > size / 2
                                                    && iv_sticker.getLayoutParams().height > size / 2) {
                                                //scale down
                                                double offsetX = Math.abs(event.getRawX() - iv_sticker.scale_orgX);
                                                double offsetY = Math.abs(event.getRawY() - iv_sticker.scale_orgY);
                                                double offset = Math.max(offsetX, offsetY);
                                                offset = Math.round(offset);
                                                iv_sticker.getLayoutParams().width -= offset;
                                                iv_sticker.getLayoutParams().height -= offset;
                                                iv_sticker.onScaling(false);
                                            }

                                            //rotate
                                            double angle = Math.atan2(event.getRawY() - iv_sticker.centerY, event.getRawX() - iv_sticker.centerX) * 180 / Math.PI;
                                            //setRotation((float) angle - 45);
                                            iv_sticker.setRotation((float) angle - 45);
                                            iv_sticker.onRotating();
                                            iv_sticker.rotate_orgX = iv_sticker.rotate_newX;
                                            iv_sticker.rotate_orgY = iv_sticker.rotate_newY;
                                            iv_sticker.scale_orgX = event.getRawX();
                                            iv_sticker.scale_orgY = event.getRawY();
                                            iv_sticker.postInvalidate();
                                            iv_sticker.requestLayout();
                                            break;
                                        case MotionEvent.ACTION_UP:
                                            for(stickerListItem SelectItem : stickerItems){
                                                if (stickerListItem.stickerIndex.equals (iv_sticker.owner_id)){
                                                    SelectItem.rotateX = iv_sticker.getRotationX();
                                                    SelectItem.rotateY = iv_sticker.getRotationY();
                                                    SelectItem.scaleX = iv_sticker.getScaleX();
                                                    SelectItem.scaleY = iv_sticker.getScaleY();
                                                    SelectItem.moveX = iv_sticker.getX();
                                                    SelectItem.moveY = iv_sticker.getY();
                                                    break;
                                                }
                                            }
                                            break;
                                    }
                                }
                                return true;
                            }
                        });
                    } else {
                        Toast.makeText(contentView.getContext(), "Maximum of stickers is 4", Toast.LENGTH_LONG).show();
                    }
                }
            });
            StickerBar.addView(ib_view);
        }
    }

    private void loadSticker (byte[] sticker, float x, float y)
    {
        Bitmap bmp = BitmapFactory.decodeByteArray(sticker, 0, sticker.length);
        Drawable d = new BitmapDrawable(getResources(), bmp);
        final StickerImageView iv_sticker = new StickerImageView(ZPTStickerComposerView.this);
        iv_sticker.setImageDrawable(d);
        iv_sticker.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stickerItems.remove(stickerItems.get(Integer.valueOf(Name)-1));
                canvas.removeView(iv_sticker);
            }
        });
        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(100, 100);
        params1.leftMargin = Math.round(x);
        params1.topMargin = Math.round(y);
        stickerItems.add(new stickerListItem(String.valueOf(stickerItems.size()), bmp, -1, -1, -1, -1, Math.round(x), Math.round(y)));
        canvas.addView(iv_sticker, params1);
    }

    //region Get Sticker
    /*Nuii*/
    //Get Sticker 1
    public void GetSticker1(String stickerTemplateID1) {
        sticker1 = mDatabaseClass.getStickerTemplate(stickerTemplateID1).getSticker();
        sticker1_x = mDatabaseClass.getStickerTemplate(stickerTemplateID1).getX();
        sticker1_y = mDatabaseClass.getStickerTemplate(stickerTemplateID1).getY();

        loadSticker(sticker1, sticker1_x, sticker1_y);
    }

    //Get Sticker 2
    public void GetSticker2(String stickerTemplateID2) {
        sticker2 = mDatabaseClass.getStickerTemplate(stickerTemplateID2).getSticker();
        sticker2_x = mDatabaseClass.getStickerTemplate(stickerTemplateID2).getX();
        sticker2_y = mDatabaseClass.getStickerTemplate(stickerTemplateID2).getY();

        loadSticker(sticker2, sticker2_x, sticker2_y);
    }

    //Get Sticker 3
    public void GetSticker3(String stickerTemplateID3) {
        sticker3 = mDatabaseClass.getStickerTemplate(stickerTemplateID3).getSticker();
        sticker3_x = mDatabaseClass.getStickerTemplate(stickerTemplateID3).getX();
        sticker3_y = mDatabaseClass.getStickerTemplate(stickerTemplateID3).getY();

        loadSticker(sticker3, sticker3_x, sticker3_y);
    }

    //Get Sticker 4
    public void GetSticker4(String stickerTemplateID4) {
        sticker4 = mDatabaseClass.getStickerTemplate(stickerTemplateID4).getSticker();
        sticker4_x = mDatabaseClass.getStickerTemplate(stickerTemplateID4).getX();
        sticker4_y = mDatabaseClass.getStickerTemplate(stickerTemplateID4).getY();

        loadSticker(sticker4, sticker4_x, sticker4_y);
    }

    private byte[] BitmapToByte(Bitmap bmp)    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress( Bitmap.CompressFormat.PNG, 100, stream );
        return stream.toByteArray();
    }

    //Set Sticker 1
    public void setSticker1() {
        sticker1 = BitmapToByte(stickerItems.get(1).stickerImage);//byte[] : sticker
        sticker1_x = stickerItems.get(1).moveX; //Float : x
        sticker1_y = stickerItems.get(1).moveY; //Float : y
    }

    //Set Sticker 2
    public void setSticker2() {
        sticker2 = BitmapToByte(stickerItems.get(2).stickerImage);//byte[] : sticker
        sticker2_x = stickerItems.get(2).moveX;//Float : x
        sticker2_y = stickerItems.get(2).moveY;//Float : y
    }

    public void setSticker3() {
        sticker3 = BitmapToByte(stickerItems.get(3).stickerImage);//byte[] : sticker
        sticker3_x = stickerItems.get(3).moveX;//Float : x
        sticker3_y = stickerItems.get(3).moveY;//Float : y
    }

    public void setSticker4() {
        sticker4 = BitmapToByte(stickerItems.get(4).stickerImage);//byte[] : sticker
        sticker4_x = stickerItems.get(4).moveX;//Float : x
        sticker4_y = stickerItems.get(4).moveY;//Float : y
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }
    //endregion

}
