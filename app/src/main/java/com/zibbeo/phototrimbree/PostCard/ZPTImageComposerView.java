package com.zibbeo.phototrimbree.PostCard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.zibbeo.phototrimbree.Database.Image;
import com.zibbeo.phototrimbree.Database.ImageTemplate;
import com.zibbeo.phototrimbree.Database.databaseClass;
import com.zibbeo.phototrimbree.PostCard.Utility;
import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.CreatePostcard.Massage.ZPTMessageComposerView;
import com.zibbeo.phototrimbree.CreatePostcard.Sign.ZPTSignComposerView;
import com.zibbeo.phototrimbree.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ZARUN on 28/5/2559.
 */
public class ZPTImageComposerView extends BaseNavigationDrawer {
    View contentView;
    Button nextButton, previousButton;
    ImageButton farme1, farme2, farme3, farme4, farme5, farme6;
    RelativeLayout mFrameLayout;
    public  int mIndex = 0;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    //KITTI Add Control SeekbarOuter
    SeekBar sOuter, sInner;
    databaseClass mDatabaseClass;
    /*** KITTI */
    DrawCanvas mDraw;

    Paint mPaint = new Paint();
    Paint mPaintInner = new Paint();
    Paint mPaint2 = new Paint();
    Paint mPaint3,mPaint4,mPaint5,mPaint6;
    ViewGroup mLayout;
    ViewGroup.LayoutParams mLayoutParams;
    boolean touch_state = false;
    boolean next_state = false;
    boolean mFirstTimeCheck = true;
    RotateZoomImageView mImage;
    ArrayList<Point> mTopLeftArea,mTopRightArea,mBottomLeftArea,mBottomRightArea;
    Point mCenterPoint,mLeftPoint,mRightPoint,mTopPoint,mBottomPoint;
    float mStroke = 5f;
    float mStrokeInner = 6f;
    float mStrokeSeekbar = 0f;
    float vMatrix[] = new float[4];
    int mRadius = 30;
    boolean sCenter = true;
    boolean sTop = true;
    boolean sBottom = true;
    boolean sLeft = true;
    boolean sRight = true;
    Bitmap mPicOriginal[] = new Bitmap[4];
    ImageButton mBtnColor1,mBtnColor2;
    private Paint mColorPaint;
    private LruCache<String, Bitmap> mMemoryCache;





    /*Nuii*/
    String stickerTemplateID1, stickerTemplateID2, stickerTemplateID3, stickerTemplateID4 ;
    String getTpID, getImageTemplateID,imageTemplateID;

    //Image
    String TemplateID, image_a, image_b, image_c, image_d,aid,bid,cid,did;
    String marge_one_color, marge_two_color;
    float marge_one_stroke, marge_two_stroke, top_value, bottom_value, right_value, left_value, center_x, center_y;
    int template;

    //Image Model A
    //String aid;
    float aoffset_x, aoffset_x_original, aoffset_x_max, aoffset_x_min, aoffset_y, aoffset_y_original, aoffset_y_max;
    float aoffset_y_min;
    float ascale;
    float ascale_original;
    float ascale_max;
    float ascale_min;
    float arotate;
    float arotate_original;
    float arotate_max;
    float arotate_min;
    Boolean aoffset_x_enable, aoffset_y_enable, ascale_enable, arotate_enable, afilter_enable;
    int afilter;
    byte[] aurl;

    //Image Model B
    float boffset_x, boffset_x_original, boffset_x_max, boffset_x_min, boffset_y, boffset_y_original, boffset_y_max;
    float boffset_y_min, bscale, bscale_original, bscale_max, bscale_min, brotate, brotate_original, brotate_max, brotate_min;
    Boolean boffset_x_enable, boffset_y_enable, bscale_enable, brotate_enable, bfilter_enable;
    int bfilter;
    byte[] burl;

    //Image Model C
    float coffset_x, coffset_x_original, coffset_x_max, coffset_x_min, coffset_y, coffset_y_original, coffset_y_max;
    float coffset_y_min, cscale, cscale_original, cscale_max, cscale_min, crotate, crotate_original, crotate_max, crotate_min;
    Boolean coffset_x_enable, coffset_y_enable, cscale_enable, crotate_enable, cfilter_enable;
    int cfilter;
    byte[] curl;

    //Image Model D
    float doffset_x, doffset_x_original, doffset_x_max, doffset_x_min, doffset_y, doffset_y_original, doffset_y_max;
    float doffset_y_min, dscale, dscale_original, dscale_max, dscale_min, drotate, drotate_original, drotate_max, drotate_min;
    Boolean doffset_x_enable, doffset_y_enable, dscale_enable, drotate_enable, dfilter_enable;
    int dfilter;
    byte[] durl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        contentView = inflater.inflate( R.layout.zpt_image_composer_view, null, false );
        mDrawerLayout.addView( contentView, 0 );
        mPaint = new Paint();

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

        // on button click
        //new ColorPickerDialog(this, this, mColorPaint.getColor()).show();
        mDraw = new DrawCanvas(this);

        init();
        if(mDraw.mMatrix[0] == null){
            mDraw.mMatrix[0] = new Matrix();
        }
        if(mDraw.mMatrix[1] == null){
            mDraw.mMatrix[1] = new Matrix();
        }
        if(mDraw.mMatrix[2] == null){
            mDraw.mMatrix[2] = new Matrix();
        }
        if(mDraw.mMatrix[3] == null){
            mDraw.mMatrix[3] = new Matrix();
        }

        mDatabaseClass = new databaseClass( contentView.getContext() );

        //region Get value
        /*Nuii*/
        //get data from previous page
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getImageTemplateID = bundle.getString( "imageID" );
        }
        if (getImageTemplateID != null) {
            imageTemplateID = mDatabaseClass.getImageComposer(getImageTemplateID).getTemplate();
            stickerTemplateID1 = mDatabaseClass.getImageComposer(getImageTemplateID).getSticker1();
            stickerTemplateID2 = mDatabaseClass.getImageComposer(getImageTemplateID).getSticker2();
            stickerTemplateID3 = mDatabaseClass.getImageComposer(getImageTemplateID).getSticker3();
            stickerTemplateID4 = mDatabaseClass.getImageComposer(getImageTemplateID).getSticker4();
        }

        mTopPoint.x = (int)top_value;
        mBottomPoint.x = (int)top_value;
        mRightPoint.y = (int)top_value;
        mLeftPoint.y = (int)top_value;
        mCenterPoint = new Point((int)center_x,(int)center_y);
        mStrokeSeekbar = 0f;
        mPaint.setStrokeWidth(marge_one_stroke);
        mDraw.sFarme = 0;//template;
        mDraw.setPoint();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;


        if(aurl != null) {
            Bitmap mBitmap = BitmapFactory.decodeByteArray(aurl, 0, aurl.length, opts);
            mDraw.myPic[0] = mBitmap;
            mDraw.mMatrix[0].setTranslate(aoffset_x, aoffset_y);
            mDraw.mMatrix[0].postRotate(arotate, mBitmap.getWidth()/2, mBitmap.getHeight()/2);
            mDraw.mMatrix[0].postScale(ascale, ascale, mBitmap.getWidth()/2, mBitmap.getHeight()/2);
        }
        if(burl != null) {
            Bitmap mBitmap = BitmapFactory.decodeByteArray(burl, 0, burl.length, opts);
            mDraw.myPic[1] = mBitmap;
            mDraw.mMatrix[1].setTranslate(boffset_x, boffset_y);
            mDraw.mMatrix[1].postRotate(brotate, mBitmap.getWidth()/2, mBitmap.getHeight()/2);
            mDraw.mMatrix[1].postScale(bscale, bscale, mBitmap.getWidth()/2, mBitmap.getHeight()/2);
        }

        if(curl != null) {
            Bitmap mBitmap = BitmapFactory.decodeByteArray(curl, 0, curl.length, opts);
            mDraw.myPic[2] = mBitmap;
            mDraw.mMatrix[2].setTranslate(coffset_x, coffset_y);
            mDraw.mMatrix[2].postRotate(crotate, mBitmap.getWidth()/2, mBitmap.getHeight()/2);
            mDraw.mMatrix[2].postScale(cscale, cscale, mBitmap.getWidth()/2, mBitmap.getHeight()/2);
        }

        if(durl != null) {
            Bitmap mBitmap = BitmapFactory.decodeByteArray(durl, 0, durl.length, opts);
            mDraw.myPic[3] = mBitmap;
            mDraw.mMatrix[3].setTranslate(doffset_x, doffset_y);
            mDraw.mMatrix[3].postRotate(drotate, mBitmap.getWidth()/2, mBitmap.getHeight()/2);
            mDraw.mMatrix[3].postScale(dscale, dscale, mBitmap.getWidth()/2, mBitmap.getHeight()/2);
        }


        mFrameLayout.setVisibility(View.VISIBLE);
        mFrameLayout.setEnabled(false);

       
        //Get Image Template
        /*imageTemplate = "123456789";*/
        if (imageTemplateID != null) {
            // i = mDatabaseClass.getImageTemplate(imageTemplateID).getTemplate();
            setImageTemplate(imageTemplateID);
        }

        //Get model of image A
        if (image_a != null) {
            setImageModelA( image_a );
        }

        //Get model of image B
        if (image_b != null) {
            setImageModelB( image_b );
        }

        //Get model of image C
        if (image_c != null) {
            setImageModelC( image_c );
        }

        //Get model of image D
        if (image_d != null) {
            setImageModelD( image_d );
        }
        //endregion

        //KITTI
        mDraw.setPoint();
        draw();
        mImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    mDraw.mMatrix[mIndex] = mImage.getMatrix();
                    draw();
                }
                return false;
            }
        });

        farme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDraw.sFarme = 0;
                mDraw.setPoint();
                draw();
            }
        });

        farme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDraw.sFarme = 1;
                mDraw.setPoint();
                draw();
            }
        });

        farme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDraw.sFarme = 2;
                mDraw.setPoint();
                draw();
            }
        });


        farme4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDraw.sFarme = 3;
                mDraw.setPoint();
                draw();
            }
        });

        farme5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDraw.sFarme = 4;
                mDraw.setPoint();
                draw();
            }
        });

        farme6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDraw.sFarme = 5;
                mDraw.setPoint();
                draw();
            }
        });

        mBtnColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HSVColorPickerDialog cpd = new HSVColorPickerDialog( ZPTImageComposerView.this,  0xFF4488CC, new HSVColorPickerDialog.OnColorSelectedListener() {
                    @Override
                    public void colorSelected(Integer color) {
                        // Do something with the selected color
                       mPaint.setColor(color);
                       mBtnColor1.setBackgroundColor(color);
                    }
                });
                cpd.setTitle( "Pick a color" );
                cpd.show();
            }
        });

        mBtnColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HSVColorPickerDialog cpd = new HSVColorPickerDialog( ZPTImageComposerView.this,  0xFF4488CC, new HSVColorPickerDialog.OnColorSelectedListener() {
                    @Override
                    public void colorSelected(Integer color) {
                        // Do something with the selected color
                        mPaintInner.setColor(color);
                        mBtnColor2.setBackgroundColor(color);
                    }
                });
                cpd.setTitle( "Pick a color" );
                cpd.show();
            }
        });


    }

    //KITTI
    private class DrawCanvas extends View implements View.OnTouchListener{

        int tMaxLeft,tMaxRight,tMaxTop,tMaxBottom;
        int tmpMaxLeft,tmpMaxRight,tmpMaxTop,tmpMaxBottom;
        float mDistanceCenter, mDistanceLeft, mDistanceRight, mDistanceTop, mDistanceBottom;
        Rect mTopLeftAreaRect,mTopRightAreaRect,mBottomLeftAreaRect,mBottomRightAreaRect;

        int mPointX[] = {0,0,0,0};
        int mPointY[] = {0,0,0,0};
        public float MaxWidtf[] = new float[4];
        public Bitmap myPic[] =  new Bitmap[4];
                /*{
                BitmapFactory.decodeResource(getResources(),R.drawable.boston),
                BitmapFactory.decodeResource(getResources(),R.drawable.carifornia),
                BitmapFactory.decodeResource(getResources(),R.drawable.dubai),
                BitmapFactory.decodeResource(getResources(),R.drawable.paris)
        };*/
        public Matrix[] mMatrix = new Matrix[4];
        public int sFarme = 1;
        private DrawCanvas(Context mContext) {
            super(mContext);
            this.setOnTouchListener(this);

        }
        public void setPoint(){
            switch (sFarme){
                case 0:{
                    mCenterPoint.set(tmpMaxRight,tmpMaxBottom);
                    mLeftPoint.set(tmpMaxTop,tmpMaxBottom);
                    mRightPoint.set(tmpMaxRight, tmpMaxTop);
                    mTopPoint.set(tmpMaxRight, tmpMaxTop);
                    sCenter = false;
                    sLeft = false;
                    sRight = false;
                    sTop = false;
                    sBottom = false;
                }break;
                case 1: {
                    mCenterPoint.set(tmpMaxRight, getHeight() / 2);
                    mLeftPoint.set(tmpMaxLeft, getHeight() / 2);
                    mRightPoint.set(tmpMaxRight, getHeight() / 2);
                    mTopPoint.set(tmpMaxRight, tmpMaxTop);
                    mBottomPoint.set(tmpMaxRight, tmpMaxBottom);
                    sCenter = true;
                    sLeft = true;
                    sRight = false;
                    sTop = false;
                    sBottom = false;
                }break;
                case 2:{
                    mCenterPoint.set(getWidth()/2, tmpMaxBottom);
                    mLeftPoint.set(tmpMaxLeft, tmpMaxBottom);
                    mRightPoint.set(tmpMaxRight, tmpMaxBottom);
                    mTopPoint.set(getWidth()/2, tmpMaxTop);
                    mBottomPoint.set(getWidth()/2, tmpMaxBottom);
                    sCenter = true;
                    sLeft = false;
                    sRight = false;
                    sTop = true;
                    sBottom = false;
                }break;
                case 3:{
                    mCenterPoint.set(getWidth()/2, getHeight()/2);
                    mTopPoint.set(getWidth()/2, tmpMaxTop);
                    mRightPoint.set(tmpMaxRight, getHeight() / 2);
                    mBottomPoint.set(getWidth()/2, tmpMaxBottom);
                    sCenter = true;
                    sLeft = false;
                    sRight = true;
                    sTop = true;
                    sBottom = true;
                }break;
                case 4:{
                    mCenterPoint.set(getWidth()/2, getHeight()/2);
                    mLeftPoint.set(tmpMaxLeft,getHeight()/2);
                    mRightPoint.set(tmpMaxRight, getHeight() / 2);
                    mBottomPoint.set(getWidth()/2, tmpMaxBottom);
                    sCenter = true;
                    sLeft = true;
                    sRight = true;
                    sTop = false;
                    sBottom = true;
                }break;
                case 5:{
                    mCenterPoint.set(getWidth()/2, getHeight()/2);
                    mTopPoint.set(getWidth()/2, tmpMaxTop);
                    mLeftPoint.set(tmpMaxLeft,getHeight()/2);
                    mRightPoint.set(tmpMaxRight, getHeight() / 2);
                    mBottomPoint.set(getWidth()/2, tmpMaxBottom);
                    sCenter = true;
                    sLeft = true;
                    sRight = true;
                    sTop = true;
                    sBottom = true;
                }break;
                default: {
                    mCenterPoint.set(getWidth() / 2, getHeight() / 2);
                    mLeftPoint.set(tMaxLeft, getHeight() / 2);
                    mRightPoint.set(tMaxRight, getHeight() / 2);
                    mTopPoint.set(getWidth() / 2, tMaxTop);
                    mBottomPoint.set(getWidth() / 2, tMaxBottom);
                }
            }
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            System.gc();

            if (mFirstTimeCheck) {

                tMaxLeft = 0 + mRadius;
                tMaxRight = getWidth() - mRadius;
                tMaxTop = 0 + mRadius;
                tMaxBottom = getHeight() - mRadius;

                tmpMaxLeft = 0 + mRadius;
                tmpMaxRight = getWidth() - mRadius;
                tmpMaxTop = 0 + mRadius;
                tmpMaxBottom = getHeight() - mRadius;

                mCenterPoint.set(getWidth() / 2, getHeight() / 2);
                mLeftPoint.set(tMaxLeft, getHeight() / 2);
                mRightPoint.set(tMaxRight, getHeight() / 2);
                mTopPoint.set(getWidth() / 2, tMaxTop);
                mBottomPoint.set(getWidth() / 2, tMaxBottom);

                mTopLeftAreaRect = new Rect( tMaxLeft, tMaxTop, mCenterPoint.x, mCenterPoint.y );
                mTopRightAreaRect = new Rect( mTopPoint.x, mTopPoint.y, mRightPoint.x, mRightPoint.y );
                mBottomLeftAreaRect = new Rect( mLeftPoint.x, mLeftPoint.y, mBottomPoint.x, mBottomPoint.y );
                mBottomRightAreaRect = new Rect( mCenterPoint.x, mCenterPoint.y, tMaxRight, tMaxBottom );
                setPoint();
                mFirstTimeCheck = false;
            }


            if (!touch_state && !next_state) {
                int mPoint = 0;
                Path mPath;
                if ((int) (mPaint.getStrokeWidth() / 2) > mPoint) {
                    mPoint = (int) (mPaint.getStrokeWidth() / 2);
                } else {
                    mPoint = (int) (mPaintInner.getStrokeWidth() / 2);
                }

                Bitmap bitmap;
                float Test[] = new float[4];
                Test = getValueMatrix(mMatrix[0]);
                mPointX[0] =  - ((int) Test[0]);
                mPointY[0] =  - ((int) Test[1]);


                Bitmap image;
                if (getBitmapFromMemCache("Temp") == null) {
                    image = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                    addBitmapToMemoryCache("Temp",image);
                }

                if(myPic[0] == null) {
                    bitmap = getBitmapFromMemCache("Temp");
                } else {
                    bitmap = myPic[0];
                }
                bitmap = Bitmap.createBitmap(bitmap,0, 0,bitmap.getWidth(),bitmap.getHeight(), mMatrix[0] ,false);

                if(sFarme == 3) {
                    Point ImgA[] = {
                            new Point(mLeftPoint.x+ mPointX[0], mTopPoint.y+ mPointY[0]),
                            new Point(mTopPoint.x+ mPointX[0], mTopPoint.y+ mPointY[0]),
                            new Point(mCenterPoint.x+ mPointX[0], mCenterPoint.y+ mPointY[0]),
                            new Point(mBottomPoint.x+ mPointX[0], mBottomPoint.y+ mPointY[0]),
                            new Point(mLeftPoint.x+ mPointX[0], mBottomPoint.y+ mPointY[0]),
                            new Point(mLeftPoint.x+ mPointX[0], mLeftPoint.y+ mPointY[0])
                    };
                    bitmap = getRoundedCroppedBitmap(bitmap, ImgA);

                    ImgA[0] = new Point(mLeftPoint.x + mPoint, mTopPoint.y + mPoint);
                    ImgA[1] = new Point(mTopPoint.x - mPoint, mTopPoint.y + mPoint);
                    ImgA[2] = new Point(mCenterPoint.x - mPoint, mCenterPoint.y - mPoint);
                    ImgA[3] = new Point(mBottomPoint.x - mPoint, mBottomPoint.y - mPoint);
                    ImgA[4] = new Point(mLeftPoint.x + mPoint, mBottomPoint.y - mPoint);
                    ImgA[5] = new Point(mLeftPoint.x + mPoint, mLeftPoint.y + mPoint);
                    mPath = new Path();
                    for(int i = 0; i <= ImgA.length ;i++)
                    {
                        if(i == 0)
                        {
                            mPath.moveTo(ImgA[i].x ,ImgA[i].y);
                        }
                        else if(i == ImgA.length)
                        {
                            mPath.lineTo(ImgA[0].x,ImgA[0].y);
                        }
                        else
                        {
                            mPath.lineTo(ImgA[i].x,ImgA[i].y);
                        }
                    }
                }else if(sFarme == 4)
                {
                    Point ImgA[] = {
                            new Point(mLeftPoint.x+ mPointX[0], mTopPoint.y+ mPointY[0]),
                            new Point(mTopPoint.x+ mPointX[0], mTopPoint.y+ mPointY[0]),
                            new Point(mRightPoint.x+ mPointX[0], mTopPoint.y+ mPointY[0]),
                            new Point(mRightPoint.x+ mPointX[0], mRightPoint.y+ mPointY[0]),
                            new Point(mCenterPoint.x+ mPointX[0], mCenterPoint.y+ mPointY[0]),
                            new Point(mLeftPoint.x+ mPointX[0], mLeftPoint.y+ mPointY[0])
                    };
                    bitmap = getRoundedCroppedBitmap(bitmap, ImgA);
                        ImgA[0] = new Point(mLeftPoint.x + mPoint, mTopPoint.y + mPoint);
                        ImgA[1] = new Point(mTopPoint.x - mPoint, mTopPoint.y + mPoint);
                        ImgA[2] = new Point(mRightPoint.x - mPoint, mTopPoint.y + mPoint);
                        ImgA[3] = new Point(mRightPoint.x - mPoint, mRightPoint.y - mPoint);
                        ImgA[4] = new Point(mCenterPoint.x - mPoint , mCenterPoint.y - mPoint);
                        ImgA[5] = new Point(mLeftPoint.x + mPoint, mLeftPoint.y - mPoint);
                    mPath = new Path();
                    for(int i = 0; i <= ImgA.length ;i++)
                    {
                        if(i == 0)
                        {
                            mPath.moveTo(ImgA[i].x ,ImgA[i].y);
                        }
                        else if(i == ImgA.length)
                        {
                            mPath.lineTo(ImgA[0].x,ImgA[0].y);
                        }
                        else
                        {
                            mPath.lineTo(ImgA[i].x,ImgA[i].y);
                        }
                    }
                }
                else {

                    Point ImgA[] = {
                            new Point(mLeftPoint.x + mPointX[0], mTopPoint.y + mPointY[0]),
                            new Point(mTopPoint.x + mPointX[0] , mTopPoint.y + mPointY[0]),
                            new Point(mCenterPoint.x +mPointX[0] , mCenterPoint.y+ mPointY[0]),
                            new Point(mLeftPoint.x+mPointX[0] , mLeftPoint.y+mPointY[0])
                    };

                    bitmap = getRoundedCroppedBitmap(bitmap, ImgA);

                    ImgA[0] = new Point(mLeftPoint.x + mPoint, mTopPoint.y + mPoint);
                    ImgA[1] = new Point(mTopPoint.x - mPoint, mTopPoint.y + mPoint);
                    ImgA[2] = new Point(mCenterPoint.x - mPoint, mCenterPoint.y - mPoint);
                    ImgA[3] = new Point(mLeftPoint.x + mPoint, mLeftPoint.y - mPoint);


                    mPath = new Path();
                    for(int i = 0; i <= ImgA.length ;i++)
                    {
                        if(i == 0)
                        {
                            mPath.moveTo(ImgA[i].x,ImgA[i].y);
                        }
                        else if(i == ImgA.length)
                        {
                            mPath.lineTo(ImgA[0].x,ImgA[0].y);
                        }
                        else
                        {
                            mPath.lineTo(ImgA[i].x,ImgA[i].y);
                        }
                    }

                }
                canvas.drawBitmap(bitmap, -mPointX[0],-mPointY[0],null);
                canvas.drawPath(mPath, mPaintInner);
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }

                Test = getValueMatrix(mMatrix[1]);
                mPointX[1] =   ((int) Test[0]);
                mPointY[1] =  - ((int) Test[1]);

                if(myPic[1] == null) {
                    bitmap = getBitmapFromMemCache("Temp");
                } else {
                    bitmap = myPic[1];
                }
                bitmap = Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth(),bitmap.getHeight(), mMatrix[1] ,false);
                if(sFarme == 4) {
                    Point ImgB[] = {
                            new Point(mRightPoint.x - mPointX[1], mRightPoint.y + mPointY[1]),
                            new Point(mRightPoint.x - mPointX[1], mRightPoint.y + mPointY[1]),
                            new Point(mRightPoint.x - mPointX[1], mRightPoint.y + mPointY[1]),
                            new Point(mRightPoint.x - mPointX[1], mRightPoint.y + mPointY[1])
                    };
                    bitmap = getRoundedCroppedBitmap(bitmap, ImgB);

                    ImgB[0] = new Point(mRightPoint.x, mRightPoint.y);
                    ImgB[1] = new Point(mRightPoint.x, mRightPoint.y);
                    ImgB[2] = new Point(mRightPoint.x, mRightPoint.y);
                    ImgB[3] = new Point(mRightPoint.x, mRightPoint.y);
                    mPath = new Path();
//                    for(int i = 0; i <= ImgB.length ;i++)
//                    {
//                        if(i == 0)
//                        {
//                            mPath.moveTo(ImgB[i].x  ,ImgB[i].y);
//                        }
//                        else if(i == ImgB.length)
//                        {
//                            mPath.lineTo(ImgB[0].x,ImgB[0].y);
//                        }
//                        else
//                        {
//                            mPath.lineTo(ImgB[i].x,ImgB[i].y);
//                        }
//                    }
                }else {
                    Point ImgB[] = {
                            new Point(mTopPoint.x - mPointX[1], mTopPoint.y + mPointY[1]),
                            new Point(mRightPoint.x - mPointX[1], mTopPoint.y + mPointY[1]),
                            new Point(mRightPoint.x - mPointX[1], mRightPoint.y + mPointY[1]),
                            new Point(mCenterPoint.x - mPointX[1], mCenterPoint.y + mPointY[1])
                    };
                    bitmap = getRoundedCroppedBitmap(bitmap, ImgB);

                    ImgB[0] = new Point(mTopPoint.x + mPoint, mTopPoint.y + mPoint);
                    ImgB[1] = new Point(mRightPoint.x - mPoint, mTopPoint.y + mPoint);
                    ImgB[2] = new Point(mRightPoint.x - mPoint, mRightPoint.y - mPoint);
                    ImgB[3] = new Point(mCenterPoint.x + mPoint, mCenterPoint.y - mPoint);

                    mPath = new Path();
                    for(int i = 0; i <= ImgB.length ;i++)
                    {
                        if(i == 0)
                        {
                            mPath.moveTo(ImgB[i].x  ,ImgB[i].y);
                        }
                        else if(i == ImgB.length)
                        {
                            mPath.lineTo(ImgB[0].x,ImgB[0].y);
                        }
                        else
                        {
                            mPath.lineTo(ImgB[i].x,ImgB[i].y);
                        }
                    }
                }
                canvas.drawBitmap(bitmap, + mPointX[1], - mPointY[1], null);
                canvas.drawPath(mPath,mPaintInner);
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }

                MaxWidtf[2] = getWidth();
                Test = getValueMatrix(mMatrix[2]);
                mPointX[2] =  - ((int) Test[0]);
                mPointY[2] =   ((int) Test[1]);

                if(myPic[2] == null) {
                    bitmap = getBitmapFromMemCache("Temp");
                } else {
                    bitmap = myPic[2];
                }
                bitmap = Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth(),bitmap.getHeight(), mMatrix[2] ,false);

                if(sFarme == 3) {
                    Point ImgC[] = {
                            new Point(mBottomPoint.x, mBottomPoint.y),
                            new Point(mBottomPoint.x, mBottomPoint.y),
                            new Point(mBottomPoint.x, mBottomPoint.y),
                            new Point(mBottomPoint.x, mBottomPoint.y)
                    };
                    bitmap = getRoundedCroppedBitmap(bitmap, ImgC);


                    mPath = new Path();
//                    for(int i = 0; i <= ImgC.length ;i++)
//                    {
//                        if(i == 0)
//                        {
//                            mPath.moveTo(ImgC[i].x  ,ImgC[i].y);
//                        }
//                        else if(i == ImgC.length)
//                        {
//                            mPath.lineTo(ImgC[0].x,ImgC[0].y);
//                        }
//                        else
//                        {
//                            mPath.lineTo(ImgC[i].x,ImgC[i].y);
//                        }
//                    }
                }else {
                    Point ImgC[] = {
                            new Point(mLeftPoint.x + mPointX[2], mLeftPoint.y - mPointY[2]),
                            new Point(mCenterPoint.x + mPointX[2], mCenterPoint.y - mPointY[2]),
                            new Point(mBottomPoint.x + mPointX[2], mBottomPoint.y - mPointY[2]),
                            new Point(mLeftPoint.x + mPointX[2], mBottomPoint.y - mPointY[2]),
                    };
                    bitmap = getRoundedCroppedBitmap(bitmap, ImgC);

                    ImgC[0] = new Point(mLeftPoint.x + mPoint, mLeftPoint.y + mPoint);
                    ImgC[1] = new Point(mCenterPoint.x - mPoint, mCenterPoint.y + mPoint);
                    ImgC[2] = new Point(mBottomPoint.x - mPoint, mBottomPoint.y - mPoint);
                    ImgC[3] = new Point(mLeftPoint.x + mPoint, mBottomPoint.y - mPoint);
                    mPath = new Path();
                    for(int i = 0; i <= ImgC.length ;i++)
                    {
                        if(i == 0)
                        {
                            mPath.moveTo(ImgC[i].x  ,ImgC[i].y);
                        }
                        else if(i == ImgC.length)
                        {
                            mPath.lineTo(ImgC[0].x,ImgC[0].y);
                        }
                        else
                        {
                            mPath.lineTo(ImgC[i].x,ImgC[i].y);
                        }
                    }
                }
                canvas.drawBitmap(bitmap, - mPointX[2], + mPointY[2], null);
                canvas.drawPath(mPath,mPaintInner);

                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }

                MaxWidtf[3] = getWidth();
                if(myPic[3] == null) {
                    bitmap = getBitmapFromMemCache("Temp");
                } else {
                    bitmap = myPic[3];
                }

                mPointX[3] =  ((int) Test[0]);
                mPointY[3] =  ((int) Test[1]);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth(),bitmap.getHeight(), mMatrix[3] ,false);
                Point ImgD[] = {
                    new Point(mCenterPoint.x - mPointX[3], mCenterPoint.y - mPointY[3]),
                    new Point(mRightPoint.x - mPointX[3], mRightPoint.y - mPointY[3]),
                    new Point(mRightPoint.x - mPointX[3], mBottomPoint.y - mPointY[3]),
                    new Point(mBottomPoint.x - mPointX[3], mBottomPoint.y - mPointY[3])
                };
                bitmap = getRoundedCroppedBitmap(bitmap, ImgD);

                ImgD[0] = new Point(mCenterPoint.x + mPoint, mCenterPoint.y + mPoint);
                ImgD[1] = new Point(mRightPoint.x - mPoint, mRightPoint.y + mPoint);
                ImgD[2] = new Point(mRightPoint.x - mPoint, mBottomPoint.y - mPoint);
                ImgD[3] = new Point(mBottomPoint.x + mPoint, mBottomPoint.y - mPoint);

                mPath = new Path();
                for(int i = 0; i <= ImgD.length ;i++)
                {
                    if(i == 0)
                    {
                        mPath.moveTo(ImgD[i].x  ,ImgD[i].y);
                    }
                    else if(i == ImgD.length)
                    {
                        mPath.lineTo(ImgD[0].x,ImgD[0].y);
                    }
                    else
                    {
                        mPath.lineTo(ImgD[i].x,ImgD[i].y);
                    }
                }
                canvas.drawBitmap(bitmap, mPointX[3], mPointY[3], null);
                canvas.drawPath(mPath,mPaintInner);

                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }

            }

            canvas.drawRect( tMaxLeft, tMaxTop, tMaxRight, tMaxBottom, mPaint );

            switch (sFarme){
                case 0:{

                }break;
                case 1:{
                    canvas.drawLine( mLeftPoint.x, mLeftPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //left
                    canvas.drawCircle( mLeftPoint.x, mLeftPoint.y, mRadius, mPaint2 );
                    //right
                    canvas.drawCircle( mCenterPoint.x, mCenterPoint.y, mRadius, mPaint2 );
                }
                break;
                case 2:{
                    canvas.drawLine( mTopPoint.x, mTopPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //top
                    canvas.drawCircle( mTopPoint.x, mTopPoint.y, mRadius, mPaint2 );
                    //bottom
                    canvas.drawCircle( mCenterPoint.x, mCenterPoint.y, mRadius, mPaint2 );
                }
                break;
                case 3:{
                    //top
                    canvas.drawLine( mTopPoint.x, mTopPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //right
                    canvas.drawLine( mRightPoint.x, mRightPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //bottom
                    canvas.drawLine( mBottomPoint.x, mBottomPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //center
                    canvas.drawCircle( mCenterPoint.x, mCenterPoint.y, mRadius, mPaint2 );
                    //right
                    canvas.drawCircle( mRightPoint.x, mRightPoint.y, mRadius, mPaint2 );
                    //top
                    canvas.drawCircle( mTopPoint.x, mTopPoint.y, mRadius, mPaint2 );
                    //bottom
                    canvas.drawCircle( mBottomPoint.x, mBottomPoint.y, mRadius, mPaint2 );
                }break;
                case 4:{
                    //left
                    canvas.drawLine( mLeftPoint.x, mLeftPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //right
                    canvas.drawLine( mRightPoint.x, mRightPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //bottom
                    canvas.drawLine( mBottomPoint.x, mBottomPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //center
                    canvas.drawCircle( mCenterPoint.x, mCenterPoint.y, mRadius, mPaint2 );
                    //left
                    canvas.drawCircle( mLeftPoint.x, mLeftPoint.y, mRadius, mPaint2 );
                    //right
                    canvas.drawCircle( mRightPoint.x, mRightPoint.y, mRadius, mPaint2 );
                    //bottom
                    canvas.drawCircle( mBottomPoint.x, mBottomPoint.y, mRadius, mPaint2 );
                }break;
                default:{
                    //top
                    canvas.drawLine( mTopPoint.x, mTopPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //left
                    canvas.drawLine( mLeftPoint.x, mLeftPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //right
                    canvas.drawLine( mRightPoint.x, mRightPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //bottom
                    canvas.drawLine( mBottomPoint.x, mBottomPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //center
                    canvas.drawCircle( mCenterPoint.x, mCenterPoint.y, mRadius, mPaint2 );
                    //left
                    canvas.drawCircle( mLeftPoint.x, mLeftPoint.y, mRadius, mPaint2 );
                    //right
                    canvas.drawCircle( mRightPoint.x, mRightPoint.y, mRadius, mPaint2 );
                    //top
                    canvas.drawCircle( mTopPoint.x, mTopPoint.y, mRadius, mPaint2 );
                    //bottom
                    canvas.drawCircle( mBottomPoint.x, mBottomPoint.y, mRadius, mPaint2 );
                }
            }
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int tXPoint,tYPoint;
            tXPoint = (int)motionEvent.getX();
            tYPoint = (int)motionEvent.getY();

            if (tXPoint > tMaxRight)
                tXPoint = tMaxRight;
            else if (tXPoint < tMaxLeft)
                tXPoint = tMaxLeft;
            if (tYPoint > tMaxBottom)
                tYPoint = tMaxBottom;
            else if (tYPoint < tMaxTop)
                tYPoint = tMaxTop;

            mDistanceCenter = (float) Math.sqrt(Math.pow(mCenterPoint.x-tXPoint, 2) + Math.pow(mCenterPoint.y-tYPoint, 2));
            mDistanceLeft = (float) Math.sqrt(Math.pow(mLeftPoint.x-tXPoint, 2) + Math.pow(mLeftPoint.y-tYPoint, 2));
            mDistanceRight = (float) Math.sqrt(Math.pow(mRightPoint.x-tXPoint, 2) + Math.pow(mRightPoint.y-tYPoint, 2));
            mDistanceTop = (float) Math.sqrt(Math.pow(mTopPoint.x-tXPoint, 2) + Math.pow(mTopPoint.y-tYPoint, 2));
            mDistanceBottom = (float) Math.sqrt(Math.pow(mBottomPoint.x-tXPoint, 2) + Math.pow(mBottomPoint.y-tYPoint, 2));

            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (mDistanceCenter <= mRadius && sCenter){
                    mCenterPoint.set(tXPoint,tYPoint);
                    mFrameLayout.setVisibility(View.VISIBLE);
                    mFrameLayout.setEnabled(false);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceLeft <= mRadius && sLeft){
                    mLeftPoint.set(tMaxLeft,tYPoint);
                    mFrameLayout.setVisibility(View.VISIBLE);
                    mFrameLayout.setEnabled(false);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceRight <= mRadius && sRight){
                    mRightPoint.set(tMaxRight,tYPoint);
                    mFrameLayout.setVisibility(View.VISIBLE);
                    mFrameLayout.setEnabled(false);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceTop <= mRadius && sTop){
                    mTopPoint.set(tXPoint,tMaxTop);
                    mFrameLayout.setVisibility(View.VISIBLE);
                    mFrameLayout.setEnabled(false);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceBottom <= mRadius && sBottom){
                    mBottomPoint.set(tXPoint,tMaxBottom);
                    mFrameLayout.setVisibility(View.VISIBLE);
                    mFrameLayout.setEnabled(false);
                    invalidate();
                    touch_state = true;
                }
                else {
                    FindImage((int) motionEvent.getX(), (int) motionEvent.getY() );
                }
                setPointAreaArrayList();

            } else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE && touch_state) {
                if (mDistanceCenter <= mRadius && sCenter){

                    switch (sFarme){
                        case 1: {
                            mCenterPoint.set(tMaxRight, tYPoint);
                        }break;
                        case 2:{
                            mCenterPoint.set(tXPoint,tMaxBottom);
                        }break;
                        default:{
                            mCenterPoint.set(tXPoint,tYPoint);
                        }
                    }
                    invalidate();
                    touch_state = true;
                } else if (mDistanceLeft <= mRadius && sLeft){
                    mLeftPoint.set(tMaxLeft,tYPoint);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceRight <= mRadius && sRight){
                    mRightPoint.set(tMaxRight,tYPoint);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceTop <= mRadius && sTop){
                    mTopPoint.set(tXPoint,tMaxTop);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceBottom <= mRadius && sBottom){
                    mBottomPoint.set(tXPoint,tMaxBottom);
                    invalidate();
                    touch_state = true;
                }
                setPointAreaArrayList();

            } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                touch_state = false;
            }
            return true;
        }

        private void setPointAreaArrayList(){

            int tNewXTL,tNewYTL,tNewXTR,tNewYTR,tNewXBL,tNewYBL,tNewXBR,tNewYBR;

            if (mCenterPoint.x > mTopPoint.x) tNewXTL = mCenterPoint.x;
            else tNewXTL = mTopPoint.x;

            if (mCenterPoint.y > mLeftPoint.y) tNewYTL = mCenterPoint.y;
            else tNewYTL = mLeftPoint.y;

            if (mCenterPoint.x > mTopPoint.x) tNewXTR = mTopPoint.x;
            else tNewXTR = mCenterPoint.x;

            if (mCenterPoint.y > mRightPoint.y) tNewYTR = mCenterPoint.y;
            else tNewYTR = mRightPoint.y;

            if (mCenterPoint.x > mBottomPoint.x) tNewXBL = mCenterPoint.x;
            else tNewXBL = mBottomPoint.x;

            if (mCenterPoint.y > mLeftPoint.y) tNewYBL = mLeftPoint.y;
            else tNewYBL = mCenterPoint.y;

            if (mCenterPoint.x > mBottomPoint.x) tNewXBR = mBottomPoint.x;
            else tNewXBR = mCenterPoint.x;

            if (mCenterPoint.y > mRightPoint.y) tNewYBR = mRightPoint.y;
            else tNewYBR = mCenterPoint.y;

            mTopLeftAreaRect.set(tMaxLeft,tMaxTop,tNewXTL,tNewYTL);
            mTopRightAreaRect.set(tNewXTR,mTopPoint.y,mRightPoint.x,tNewYTR);
            mBottomLeftAreaRect.set(mLeftPoint.x,tNewYBL,tNewXBL,mBottomPoint.y);
            mBottomRightAreaRect.set(tNewXBR,tNewYBR,tMaxRight,tMaxBottom);
        }

        public Bitmap getRoundedCroppedBitmap(Bitmap bitmap, Point point_draw[]) {

            Bitmap finalBitmap;
            finalBitmap = bitmap;

            Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                    finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);



            Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());


            Path path = new Path();
            for(int i = 0; i <= point_draw.length ;i++)
            {
                if(i == 0)
                {
                    path.moveTo(point_draw[i].x,point_draw[i].y);
                }
                else if(i == point_draw.length)
                {
                    path.lineTo(point_draw[0].x,point_draw[0].y);
                }
                else
                {
                    path.lineTo(point_draw[i].x,point_draw[i].y);
                }
            }

            path.close();
            canvas.drawPath(path, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(finalBitmap, rect, rect, paint);

            return output;
        }

        private void FindImage(int x, int y) {
            mImage.setImageBitmap(null);
            mFrameLayout.setVisibility(INVISIBLE);
            mFrameLayout.setEnabled(true);
            if (x > mCenterPoint.x) {
                //Img B,D
                if (y > mCenterPoint.y) {
                    if(sFarme == 2){
                        mIndex = 1;
                        //ImgB
                        selectImage();
                    }else {
                        //ImgD
                        mIndex = 3;
                        selectImage();
                    }
                } else {
                    mIndex = 1;
                    //ImgB
                    selectImage();
                }
            } else {
                //Img A,C
                if (y > mCenterPoint.y) {
                    if(sFarme == 2){
                        //ImgA
                        mIndex = 0;
                        selectImage();
                    }else if(sFarme == 3){
                        //ImgA
                        mIndex = 0;
                        selectImage();
                    }else {
                        //ImgC
                        mIndex = 2;
                        selectImage();
                    }
                } else {
                    //ImgA
                    mIndex = 0;
                    selectImage();
                }
            }
        }
    }

    public void draw() {
        try {
            mLayout.removeView(mDraw);
        } catch (Exception e) { }
        mLayout.addView(mDraw);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sOuter.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float sizeF = new Float( progress );
                mPaint.setStrokeWidth( sizeF );
                mPaintInner.setStrokeWidth( sizeF );
                draw();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        } );
        sInner.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float sizeF = new Float(progress) / 5;
                float sizeO = mPaint.getStrokeWidth();
                float sizeI = mPaintInner.getStrokeWidth();
                if(mStrokeSeekbar < progress) {
                    sizeI = sizeI - sizeF;
                    sizeO = sizeO + sizeF;
                } else {
                    sizeI = sizeI + sizeF;
                    sizeO = sizeO - sizeF;
                }
                mPaint.setStrokeWidth(sizeO);
                mPaintInner.setStrokeWidth(sizeI);

                draw();
                mStrokeSeekbar = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        } );

        nextButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadius = 0;
                draw();

                FrameLayout savedImage = (FrameLayout) findViewById( R.id.FrameImageView );
                savedImage.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
                savedImage.setDrawingCacheEnabled( false );
                savedImage.buildDrawingCache();
                next_state = true;
                Bitmap bmp = savedImage.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress( Bitmap.CompressFormat.PNG, 100, stream );
                byte[] byteArray = stream.toByteArray();
                savedImage.destroyDrawingCache();

                //region save data
                /*Nuii*/
                getImageTemplate();
                getImageA();
                if ( image_a == null) {
                    aid = mDatabaseClass.createID();

                    Image ImageA = new Image(aid, aurl, aoffset_x, aoffset_x_enable, aoffset_x_original, aoffset_x_max, aoffset_x_min
                            , aoffset_y, aoffset_y_enable, aoffset_y_original, aoffset_y_max, aoffset_y_min
                            , ascale, ascale_enable, ascale_original, ascale_max, ascale_min
                            , arotate, arotate_enable, arotate_original, arotate_max, arotate_min
                            , afilter_enable, afilter);
                    mDatabaseClass.insertImage(ImageA);
                }else {
                    aid = image_a;
                    Image ImageA = new Image(aid, aurl, aoffset_x, aoffset_x_enable, aoffset_x_original, aoffset_x_max, aoffset_x_min
                            , aoffset_y, aoffset_y_enable, aoffset_y_original, aoffset_y_max, aoffset_y_min
                            , ascale, ascale_enable, ascale_original, ascale_max, ascale_min
                            , arotate, arotate_enable, arotate_original, arotate_max, arotate_min
                            , afilter_enable, afilter);
                    mDatabaseClass.updateImage(ImageA);
                }
                if ( template >= 2) {
                    getImageB();
                    if ( image_b == null) {
                        bid = mDatabaseClass.createID();

                        Image ImageB = new Image( bid, burl, boffset_x, boffset_x_enable, boffset_x_original, boffset_x_max, boffset_x_min
                                , boffset_y, boffset_y_enable, boffset_y_original, boffset_y_max, boffset_y_min
                                , bscale, bscale_enable, bscale_original, bscale_max, bscale_min
                                , brotate, brotate_enable, brotate_original, brotate_max, brotate_min
                                , bfilter_enable, bfilter);
                        mDatabaseClass.insertImage(ImageB);
                    }else {
                        bid = image_b;
                        Image ImageB = new Image( burl, boffset_x, boffset_x_enable, boffset_x_original, boffset_x_max, boffset_x_min
                                , boffset_y, boffset_y_enable, boffset_y_original, boffset_y_max, boffset_y_min
                                , bscale, bscale_enable, bscale_original, bscale_max, bscale_min
                                , brotate, brotate_enable, brotate_original, brotate_max, brotate_min
                                , bfilter_enable, bfilter);
                        mDatabaseClass.updateImage(ImageB);
                    }
                }
                if ( template >= 3) {
                    getImageC();
                    if ( image_c == null) {
                        cid = mDatabaseClass.createID();

                        Image ImageC = new Image( cid, curl, coffset_x, coffset_x_enable, coffset_x_original, coffset_x_max, coffset_x_min
                                , coffset_y, coffset_y_enable, coffset_y_original, coffset_y_max, coffset_y_min
                                , cscale, cscale_enable, cscale_original, cscale_max, cscale_min
                                , crotate, crotate_enable, crotate_original, crotate_max, crotate_min
                                , cfilter_enable, cfilter);
                        mDatabaseClass.insertImage(ImageC);
                    }else {
                        cid = image_c;
                        Image ImageC = new Image( curl, coffset_x, coffset_x_enable, coffset_x_original, coffset_x_max, coffset_x_min
                                , coffset_y, coffset_y_enable, coffset_y_original, coffset_y_max, coffset_y_min
                                , cscale, cscale_enable, cscale_original, cscale_max, cscale_min
                                , crotate, crotate_enable, crotate_original, crotate_max, crotate_min
                                , cfilter_enable, cfilter);
                        mDatabaseClass.updateImage(ImageC);
                    }
                }
                if ( template == 4) {
                    getImageD();
                    if ( image_d == null) {
                        did = mDatabaseClass.createID();

                        Image ImageD = new Image( did, durl, doffset_x, doffset_x_enable, doffset_x_original, doffset_x_max, doffset_x_min
                                , doffset_y, doffset_y_enable, doffset_y_original, doffset_y_max, doffset_y_min
                                , dscale, dscale_enable, dscale_original, dscale_max, dscale_min
                                , drotate, drotate_enable, drotate_original, drotate_max, drotate_min
                                , dfilter_enable, dfilter);
                        mDatabaseClass.insertImage(ImageD);
                    }else {
                        did = image_d;
                        Image ImageD = new Image( durl, doffset_x, doffset_x_enable, doffset_x_original, doffset_x_max, doffset_x_min
                                , doffset_y, doffset_y_enable, doffset_y_original, doffset_y_max, doffset_y_min
                                , dscale, dscale_enable, dscale_original, dscale_max, dscale_min
                                , drotate, drotate_enable, drotate_original, drotate_max, drotate_min
                                , dfilter_enable, dfilter);
                        mDatabaseClass.updateImage(ImageD);
                    }
                }

                if (imageTemplateID == null) {
                    getTpID = mDatabaseClass.createID();
                    ImageTemplate tImageTemplate = new ImageTemplate(getTpID, template, aid, bid, cid, did
                            , marge_one_stroke, marge_one_color, marge_two_stroke, marge_two_color
                            , top_value, bottom_value, right_value, left_value, center_x, center_y);
                    mDatabaseClass.insertImageTemplate(tImageTemplate);
                } else {
                    getTpID = imageTemplateID;
                    ImageTemplate tImageTemplate = new ImageTemplate(getTpID,template, aid, bid, cid, did
                            , marge_one_stroke, marge_one_color, marge_two_stroke, marge_two_color
                            , top_value, bottom_value, right_value, left_value, center_x, center_y);
                    mDatabaseClass.updateImageTemplate(tImageTemplate);
                }
                //endregion

                Intent intent = new Intent( contentView.getContext(), ZPTStickerComposerView.class );
                intent.putExtra( "picture", byteArray );
                /*Nuii*/
                intent.putExtra( "imageTemplateID", getTpID );
                intent.putExtra( "getImageTemplateID", getImageTemplateID );
                intent.putExtra( "stickerTemplateID1", stickerTemplateID1 );
                intent.putExtra( "stickerTemplateID2", stickerTemplateID2 );
                intent.putExtra( "stickerTemplateID3", stickerTemplateID3 );
                intent.putExtra( "stickerTemplateID4", stickerTemplateID4 );
                startActivity( intent );
            }

        } );

        previousButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        } );

    }

    private void init() {
        nextButton = (Button) findViewById( R.id.nextButton );
        previousButton = (Button) findViewById( R.id.previousButton );
        //KITTI : Link Control
        mBtnColor1 = (ImageButton) findViewById(R.id.imageView2);
        mBtnColor1.setBackgroundColor(mPaint.getColor());
        mBtnColor2 = (ImageButton) findViewById(R.id.imageView3);
        mBtnColor2.setBackgroundColor(mPaintInner.getColor());;
        sOuter = (SeekBar) findViewById( R.id.seekBarOuter );
        sInner = (SeekBar) findViewById( R.id.seekBarInner );
        mImage = (RotateZoomImageView) findViewById(R.id.mImage);
        farme1 = (ImageButton) findViewById( R.id.farme1 );
        farme2 = (ImageButton) findViewById( R.id.farme2 );
        farme3 = (ImageButton) findViewById( R.id.farme3 );
        farme4 = (ImageButton) findViewById( R.id.farme4 );
        farme5 = (ImageButton) findViewById( R.id.farme5 );
        farme6 = (ImageButton) findViewById( R.id.farme6 );

        mLayout = (FrameLayout) findViewById( R.id.FrameImageView );
        mLayoutParams = mLayout.getLayoutParams();
        mFrameLayout = (RelativeLayout) findViewById(R.id.mFrameLayout);
        mCenterPoint = new Point(mLayoutParams.width/2,mLayoutParams.height/2);
        mLeftPoint = new Point();
        mRightPoint = new Point();
        mTopPoint = new Point();
        mBottomPoint = new Point();
        mTopLeftArea = new ArrayList<>();
        mTopRightArea = new ArrayList<>();
        mBottomLeftArea = new ArrayList<>();
        mBottomRightArea = new ArrayList<>();

        mPaint = new Paint() {
            {
                setColor(Color.BLACK);
                setStrokeWidth(mStroke);
                setStyle(Paint.Style.STROKE);
                setStrokeJoin(Paint.Join.ROUND);
            }
        };


        mPaintInner = new Paint() {
            {
                setColor(Color.WHITE);
                setStrokeWidth(mStrokeInner);
                setStyle(Paint.Style.STROKE);
                setStrokeJoin(Paint.Join.ROUND);
            }
        };
        mPaint3 = new Paint() {
            {
                setStyle(Style.FILL);
                setAntiAlias(true);
                setAlpha(0);
            }
        };
        mPaint4 = new Paint() {
            {
                setStyle(Style.FILL);
                setAntiAlias(true);
                setAlpha(0);
            }
        };
        mPaint5 = new Paint() {
            {
                setStyle(Style.FILL);
                setAntiAlias(true);
                setAlpha(0);
            }
        };
        mPaint6 = new Paint() {
            {
                setStyle(Style.FILL);
                setAntiAlias(true);
                setAlpha(0);
            }
        };
//
//        draw();
    }

    private void selectImage() {
        final CharSequence[] items = {"Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder( ZPTImageComposerView.this );
        builder.setTitle( "Add Photo!" );
        builder.setItems( items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission( ZPTImageComposerView.this );

                /*if (items[item].equals( "Take Photo" )) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else */
                if (items[item].equals( "Choose from Library" )) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals( "Cancel" )) {
                    mImage.setImageBitmap(mDraw.myPic[mIndex]);
                    mImage.setMatrix(mDraw.mMatrix[mIndex]);
                    dialog.dismiss();
                }
            }
        } );
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType( "image/*" );
        intent.setAction( Intent.ACTION_GET_CONTENT );//
        startActivityForResult( Intent.createChooser( intent, "Select File" ), SELECT_FILE );
    }

    private void cameraIntent() {
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        startActivityForResult( intent, REQUEST_CAMERA );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult( data );
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult( data );
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get( "data" );
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress( Bitmap.CompressFormat.JPEG, 90, bytes );

        File destination = new File( Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg" );

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream( destination );
            fo.write( bytes.toByteArray() );
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mImage.setImageBitmap( thumbnail );
    }

    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap( getApplicationContext().getContentResolver(), data.getData() );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bm = scaleDown(bm,mDraw.getWidth(),true);// mDraw.getWidth();
        mImage.setImageBitmap( bm );
        mDraw.myPic[mIndex] = bm;
        draw();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals( "Take Photo" ))
                        cameraIntent();
                    else if (userChoosenTask.equals( "Choose from Library" ))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    //region Nuii : set value function
    /*Nuii*/
    /*Nuii*/
    //set Image Tamplate
    public void getImageTemplate() {
        template = mDraw.sFarme;//1;//Integer : จำนวนเทมเพลต

        marge_one_stroke = mPaint.getStrokeWidth();//Float : marge one stroke in purcent
        marge_one_color =  String.valueOf(mPaint.getColor());//String : color in hex
        marge_two_stroke = mPaintInner.getStrokeWidth();//Float : marge two stroke in purcent
        marge_two_color = String.valueOf(mPaintInner.getColor());
        top_value = mTopPoint.x; //0.00f;//Float : position of value top for the line
        bottom_value = mBottomPoint.x;//Float : position of value bottom for the line
        right_value = mRightPoint.y;//Float : position of value right for the line
        left_value = mLeftPoint.y;//Float : position of value left for the line
        center_x = (float) mCenterPoint.x;//Float : position of value x for the line
        center_y = (float) mCenterPoint.y;//Float : position of value y for the line
    }

    //Image Model A
    public void getImageA() {
//        float[] vImgA = new float[9];
//        mDraw.mMatrix[0].getValues(vImgA);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (mDraw.myPic[0] == null){
            mPicOriginal[0] = getBitmapFromMemCache("Temp");
        }else {
            mPicOriginal[0] = mDraw.myPic[0];
        }

        mPicOriginal[0].compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        aurl = byteArray ;//byte[] : image (delete when object is delete)
        vMatrix = new float[4];
        vMatrix = getValueMatrix(mDraw.mMatrix[0]);
        aoffset_x = vMatrix[0]; ;//Float : offset X of image
        aoffset_x_enable = true;//Boolean : enable offset X of image
        aoffset_x_original = 0.00f ;//Float : original offset X of image
        aoffset_x_max = 0.00f ;//Float : maximum authorized offset X of image
        aoffset_x_min = 0.00f ;//Float : minimum authorized offset X of image
        aoffset_y = vMatrix[1]; //Float : offset Y of image
        aoffset_y_enable = true;//Boolean : enable offset X of image
        aoffset_y_original = 0.00f ;//Float : original offset Y of image
        aoffset_y_max = 0.00f ;//Float : maximum authorized offset Y of image
        aoffset_y_min = 0.00f ;//Float : minimum authorized offset Y of image
        ascale = vMatrix[2] ;//Float : scale of image
        ascale_enable = true;//Float : Boolean : enable scale of image
        ascale_original = 0.00f; //Float : original scale of image
        ascale_max = 0.00f ;//Float : maximum authorized scale of image
        ascale_min = 0.00f ;//Float : minimum authorized scale of image
        arotate = vMatrix[3];//Float : rotate of image
        arotate_enable = true; //Float : Boolean : enable rotation of image
        arotate_original = 0.00f ;//Float : original rotation of image
        arotate_max = 0.00f ;//Float : maximum authorized rotate of image
        arotate_min = 0.00f ;//Float : minimum authorized rotate of image
        afilter_enable = true;//Boolean : enable filter for this image
        afilter = 1 ;//Float : Integer : enum filter for this image

    }
    //Image Model B
    public void getImageB() {
//        float[] vImgA = new float[9];
//        mDraw.mMatrix[1].getValues(vImgA);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (mDraw.myPic[1] == null){
            mPicOriginal[1] = getBitmapFromMemCache("Temp");
        }else {
            mPicOriginal[1] = mDraw.myPic[1];
        }
        mPicOriginal[1].compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        burl = byteArray;//"Image".getBytes() ;//byte[] : image (delete when object is delete)
        vMatrix = new float[4];
        vMatrix = getValueMatrix(mDraw.mMatrix[1]);
        boffset_x = vMatrix[0] ;//Float : offset X of image
        boffset_x_enable = true;//Boolean : enable offset X of image
        boffset_x_original = 0.00f ;//Float : original offset X of image
        boffset_x_max = 0.00f ;//Float : maximum authorized offset X of image
        boffset_x_min = 0.00f ;//Float : minimum authorized offset X of image
        boffset_y = vMatrix[1] ;//Float : offset Y of image
        boffset_y_enable = true;//Boolean : enable offset X of image
        boffset_y_original = 0.00f ;//Float : original offset Y of image
        boffset_y_max = 0.00f ;//Float : maximum authorized offset Y of image
        boffset_y_min = 0.00f ;//Float : minimum authorized offset Y of image
        bscale = vMatrix[2];//Float : scale of image
        bscale_enable = true;//Float : Boolean : enable scale of image
        bscale_original = 0.00f ;//Float : original scale of image
        bscale_max = 0.00f ;//Float : maximum authorized scale of image
        bscale_min = 0.00f ;//Float : minimum authorized scale of image
        brotate = vMatrix[3] ;//Float : rotate of image
        brotate_enable = true;//Float : Boolean : enable rotation of image
        brotate_original = 0.00f ;//Float : original rotation of image
        brotate_max = 0.00f ;//Float : maximum authorized rotate of image
        brotate_min = 0.00f ;//Float : minimum authorized rotate of image
        bfilter_enable = true;//Boolean : enable filter for this image
        bfilter = 1 ;//Float : Integer : enum filter for this image
    }
    //Image Model C
    public void getImageC() {
//        float[] vImgA = new float[9];
//        mDraw.mMatrix[2].getValues(vImgA);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (mDraw.myPic[2] == null){
            mPicOriginal[2] = getBitmapFromMemCache("Temp");
        }else {
            mPicOriginal[2] = mDraw.myPic[2];
        }
        mPicOriginal[2].compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        vMatrix = new float[4];
        vMatrix = getValueMatrix(mDraw.mMatrix[2]);
        curl = byteArray;"Image".getBytes() ;//byte[] : image (delete when object is delete)
        coffset_x = vMatrix[0] ;//Float : offset X of image
        coffset_x_enable = true;//Boolean : enable offset X of image
        coffset_x_original = 0.00f ;//Float : original offset X of image
        coffset_x_max = 0.00f ;//Float : maximum authorized offset X of image
        coffset_x_min = 0.00f ;//Float : minimum authorized offset X of image
        coffset_y = vMatrix[1] ;//Float : offset Y of image
        coffset_y_enable = true;//Boolean : enable offset X of image
        coffset_y_original = 0.00f ;//Float : original offset Y of image
        coffset_y_max = 0.00f ;//Float : maximum authorized offset Y of image
        coffset_y_min = 0.00f ;//Float : minimum authorized offset Y of image
        cscale = vMatrix[2];//Float : scale of image
        cscale_enable = true;//Float : Boolean : enable scale of image
        cscale_original = 0.00f ;//Float : original scale of image
        cscale_max = 0.00f ;//Float : maximum authorized scale of image
        cscale_min = 0.00f ;//Float : minimum authorized scale of image
        crotate = vMatrix[3] ;//Float : rotate of image
        crotate_enable = true;//Float : Boolean : enable rotation of image
        crotate_original = 0.00f ;//Float : original rotation of image
        crotate_max = 0.00f ;//Float : maximum authorized rotate of image
        crotate_min = 0.00f ;//Float : minimum authorized rotate of image
        cfilter_enable = true;//Boolean : enable filter for this image
        cfilter = 1 ;//Float : Integer : enum filter for this image
    }
    //Image Model D
    public void getImageD() {
//        float[] vImgA = new float[9];
//        mDraw.mMatrix[3].getValues(vImgA);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (mDraw.myPic[3] == null){
            mPicOriginal[3] = getBitmapFromMemCache("Temp");
        }else {
            mPicOriginal[3] = mDraw.myPic[3];
        }
        mPicOriginal[3].compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        curl = byteArray; //"Image".getBytes() ;//byte[] : image (delete when object is delete)
        vMatrix = new float[4];
        vMatrix = getValueMatrix(mDraw.mMatrix[3]);
        doffset_x = vMatrix[0] ;//Float : offset X of image
        doffset_x_enable = true;//Boolean : enable offset X of image
        doffset_x_original = 0.00f ;//Float : original offset X of image
        doffset_x_max = 0.00f ;//Float : maximum authorized offset X of image
        doffset_x_min = 0.00f ;//Float : minimum authorized offset X of image
        doffset_y = vMatrix[1] ;//Float : offset Y of image
        doffset_y_enable = true;//Boolean : enable offset X of image
        doffset_y_original = 0.00f ;//Float : original offset Y of image
        doffset_y_max = 0.00f ;//Float : maximum authorized offset Y of image
        doffset_y_min = 0.00f ;//Float : minimum authorized offset Y of image
        dscale = vMatrix[2];//Float : scale of image
        dscale_enable = true;//Float : Boolean : enable scale of image
        dscale_original = 0.00f ;//Float : original scale of image
        dscale_max = 0.00f ;//Float : maximum authorized scale of image
        dscale_min = 0.00f ;//Float : minimum authorized scale of image
        drotate = vMatrix[3] ;//Float : rotate of image
        drotate_enable = true;//Float : Boolean : enable rotation of image
        drotate_original = 0.00f ;//Float : original rotation of image
        drotate_max = 0.00f ;//Float : maximum authorized rotate of image
        drotate_min = 0.00f ;//Float : minimum authorized rotate of image
        dfilter_enable = true;//Boolean : enable filter for this image
        dfilter = 1 ;//Float : Integer : enum filter for this image
    }

    /*Nuii*/
    //Set point of Image Template
    public void setImageTemplate(String imageTemplate) {
        template = mDatabaseClass.getImageTemplate(imageTemplate).getTemplate();
        image_a = mDatabaseClass.getImageTemplate(imageTemplate).getImage_a();
        image_b = mDatabaseClass.getImageTemplate(imageTemplate).getImage_b();
        image_c = mDatabaseClass.getImageTemplate(imageTemplate).getImage_c();
        image_d = mDatabaseClass.getImageTemplate(imageTemplate).getImage_d();
        marge_one_stroke = mDatabaseClass.getImageTemplate(imageTemplate).getMarge_one_stroke();
        marge_one_color = mDatabaseClass.getImageTemplate(imageTemplate).getMarge_one_color();
        marge_two_stroke = mDatabaseClass.getImageTemplate(imageTemplate).getMarge_two_stroke();
        marge_two_color = mDatabaseClass.getImageTemplate(imageTemplate).getMarge_two_color();
        top_value = mDatabaseClass.getImageTemplate(imageTemplate).getTop_value();
        bottom_value = mDatabaseClass.getImageTemplate(imageTemplate).getBottom_value();
        right_value = mDatabaseClass.getImageTemplate(imageTemplate).getRight_value();
        left_value = mDatabaseClass.getImageTemplate(imageTemplate).getLeft_value();
        center_x = mDatabaseClass.getImageTemplate(imageTemplate).getCenter_x();
        center_y = mDatabaseClass.getImageTemplate(imageTemplate).getCenter_y();

    }

    /*Nuii*/
    //Set point of Image Model A
    public void setImageModelA(String ImageID) {
        aurl=mDatabaseClass.getImage(ImageID).getUrl();
        aoffset_x = mDatabaseClass.getImage(ImageID).getX();
        aoffset_x_enable = mDatabaseClass.getImage(ImageID).getX_enable();
        aoffset_x_original = mDatabaseClass.getImage(ImageID).getX_original();
        aoffset_x_max = mDatabaseClass.getImage(ImageID).getX_max();
        aoffset_x_min = mDatabaseClass.getImage(ImageID).getX_min();
        aoffset_y = mDatabaseClass.getImage(ImageID).getY();
        aoffset_y_enable = mDatabaseClass.getImage(ImageID).getY_enable();
        aoffset_y_original = mDatabaseClass.getImage(ImageID).getY_original();
        aoffset_y_max = mDatabaseClass.getImage(ImageID).getY_max();
        aoffset_y_min =  mDatabaseClass.getImage(ImageID).getY_min();
        ascale = mDatabaseClass.getImage(ImageID).getScale();
        ascale_enable = mDatabaseClass.getImage(ImageID).getScale_enable();
        ascale_original = mDatabaseClass.getImage(ImageID).getScale_original();
        ascale_max = mDatabaseClass.getImage(ImageID).getScale_max();
        ascale_min = mDatabaseClass.getImage(ImageID).getScale_min();
        arotate = mDatabaseClass.getImage(ImageID).getRotate();
        arotate_enable = mDatabaseClass.getImage(ImageID).getRotate_enable();
        arotate_original = mDatabaseClass.getImage(ImageID).getRotate_original();
        arotate_max = mDatabaseClass.getImage(ImageID).getRotate_max();
        arotate_min = mDatabaseClass.getImage(ImageID).getRotate_min();
        afilter_enable = mDatabaseClass.getImage(ImageID).getFilter_enable();
        afilter = mDatabaseClass.getImage(ImageID).getFilter();
    }

    //Set point of Image Model B
    public void setImageModelB(String ImageID) {
        burl=mDatabaseClass.getImage(ImageID).getUrl();
        boffset_x = mDatabaseClass.getImage(ImageID).getX();
        boffset_x_enable = mDatabaseClass.getImage(ImageID).getX_enable();
        boffset_x_original = mDatabaseClass.getImage(ImageID).getX_original();
        boffset_x_max = mDatabaseClass.getImage(ImageID).getX_max();
        boffset_x_min = mDatabaseClass.getImage(ImageID).getX_min();
        boffset_y = mDatabaseClass.getImage(ImageID).getY();
        boffset_y_enable = mDatabaseClass.getImage(ImageID).getY_enable();
        boffset_y_original = mDatabaseClass.getImage(ImageID).getY_original();
        boffset_y_max = mDatabaseClass.getImage(ImageID).getY_max();
        boffset_y_min =  mDatabaseClass.getImage(ImageID).getY_min();
        bscale = mDatabaseClass.getImage(ImageID).getScale();
        bscale_enable = mDatabaseClass.getImage(ImageID).getScale_enable();
        bscale_original = mDatabaseClass.getImage(ImageID).getScale_original();
        bscale_max = mDatabaseClass.getImage(ImageID).getScale_max();
        bscale_min = mDatabaseClass.getImage(ImageID).getScale_min();
        brotate = mDatabaseClass.getImage(ImageID).getRotate();
        brotate_enable = mDatabaseClass.getImage(ImageID).getRotate_enable();
        brotate_original = mDatabaseClass.getImage(ImageID).getRotate_original();
        brotate_max = mDatabaseClass.getImage(ImageID).getRotate_max();
        brotate_min = mDatabaseClass.getImage(ImageID).getRotate_min();
        bfilter_enable = mDatabaseClass.getImage(ImageID).getFilter_enable();
        bfilter = mDatabaseClass.getImage(ImageID).getFilter();
    }

    //Set point of Image Model C
    public void setImageModelC(String ImageID) {
        curl=mDatabaseClass.getImage(ImageID).getUrl();
        coffset_x = mDatabaseClass.getImage(ImageID).getX();
        coffset_x_enable = mDatabaseClass.getImage(ImageID).getX_enable();
        coffset_x_original = mDatabaseClass.getImage(ImageID).getX_original();
        coffset_x_max = mDatabaseClass.getImage(ImageID).getX_max();
        coffset_x_min = mDatabaseClass.getImage(ImageID).getX_min();
        coffset_y = mDatabaseClass.getImage(ImageID).getY();
        coffset_y_enable = mDatabaseClass.getImage(ImageID).getY_enable();
        coffset_y_original = mDatabaseClass.getImage(ImageID).getY_original();
        coffset_y_max = mDatabaseClass.getImage(ImageID).getY_max();
        coffset_y_min =  mDatabaseClass.getImage(ImageID).getY_min();
        cscale = mDatabaseClass.getImage(ImageID).getScale();
        cscale_enable = mDatabaseClass.getImage(ImageID).getScale_enable();
        cscale_original = mDatabaseClass.getImage(ImageID).getScale_original();
        cscale_max = mDatabaseClass.getImage(ImageID).getScale_max();
        cscale_min = mDatabaseClass.getImage(ImageID).getScale_min();
        crotate = mDatabaseClass.getImage(ImageID).getRotate();
        crotate_enable = mDatabaseClass.getImage(ImageID).getRotate_enable();
        crotate_original = mDatabaseClass.getImage(ImageID).getRotate_original();
        crotate_max = mDatabaseClass.getImage(ImageID).getRotate_max();
        crotate_min = mDatabaseClass.getImage(ImageID).getRotate_min();
        cfilter_enable = mDatabaseClass.getImage(ImageID).getFilter_enable();
        cfilter = mDatabaseClass.getImage(ImageID).getFilter();
    }

    //Set point of Image Model D
    public void setImageModelD(String ImageID) {
        durl=mDatabaseClass.getImage(ImageID).getUrl();
        doffset_x = mDatabaseClass.getImage(ImageID).getX();
        doffset_x_enable = mDatabaseClass.getImage(ImageID).getX_enable();
        doffset_x_original = mDatabaseClass.getImage(ImageID).getX_original();
        doffset_x_max = mDatabaseClass.getImage(ImageID).getX_max();
        doffset_x_min = mDatabaseClass.getImage(ImageID).getX_min();
        doffset_y = mDatabaseClass.getImage(ImageID).getY();
        doffset_y_enable = mDatabaseClass.getImage(ImageID).getY_enable();
        doffset_y_original = mDatabaseClass.getImage(ImageID).getY_original();
        doffset_y_max = mDatabaseClass.getImage(ImageID).getY_max();
        doffset_y_min =  mDatabaseClass.getImage(ImageID).getY_min();
        dscale = mDatabaseClass.getImage(ImageID).getScale();
        dscale_enable = mDatabaseClass.getImage(ImageID).getScale_enable();
        dscale_original = mDatabaseClass.getImage(ImageID).getScale_original();
        dscale_max = mDatabaseClass.getImage(ImageID).getScale_max();
        dscale_min = mDatabaseClass.getImage(ImageID).getScale_min();
        drotate = mDatabaseClass.getImage(ImageID).getRotate();
        drotate_enable = mDatabaseClass.getImage(ImageID).getRotate_enable();
        drotate_original = mDatabaseClass.getImage(ImageID).getRotate_original();
        drotate_max = mDatabaseClass.getImage(ImageID).getRotate_max();
        drotate_min = mDatabaseClass.getImage(ImageID).getRotate_min();
        dfilter_enable = mDatabaseClass.getImage(ImageID).getFilter_enable();
        dfilter = mDatabaseClass.getImage(ImageID).getFilter();
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min((float) maxImageSize / realImage.getWidth(), (float) maxImageSize / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public float[] getValueMatrix(Matrix mMatrix)
    {
        float fValue[] = new float[4];
        float[] v = new float[9];
        mMatrix.getValues(v);
        // translation is simple
        float tX = v[Matrix.MTRANS_X];
        float tY = v[Matrix.MTRANS_Y];

        // calculate real scale
        float scaleX = v[Matrix.MSCALE_X];
        float skewY = v[Matrix.MSKEW_Y];
        float realScale = (float) Math.sqrt(scaleX * scaleX + skewY * skewY);

        // calculate the degree of rotation
        float skewX = v[Matrix.MSKEW_X];
        float realAngle = Math.round(Math.atan2(skewX, scaleX) * (180 / Math.PI));
        fValue[0] = tX;
        fValue[1] = tY;
        fValue[2] = realScale;
        fValue[3] = realAngle;
        return  fValue;
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    //endregion : function

}
