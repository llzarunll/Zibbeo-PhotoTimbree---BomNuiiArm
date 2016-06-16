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
    ImageButton farme1, farme2, farme3, farme4, farme5, farme6, farme7, farme8;
    ImageView ImgBlock1, ImgBlock2, ImgBlock3, ImgBlock4;
    FrameLayout FrameImg;
    SeekBar seekbar1;
    int FullHeight, FullWidth;
    public  int mIndex = 0;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    public Bitmap ImageSelect;

    //KITTI Add Control SeekbarOuter
    SeekBar sOuter, sInner;
    //LineClass mLineClass;
    MoveLineClass mMove;

    databaseClass mDatabaseClass;
    String getTpID, tpID, getImageTemplateID, imageTemplateID, stickerTemplateID;
    //Image
    String TemplateID, image_a, image_b, image_c, image_d,aid,bid,cid,did;



    /*marge_one_color, marge_two_color;
    Float marge_one_stroke, marge_two_stroke, top_value, bottom_value, right_value, left_value, center_x, center_y;
    Integer template;
    //Sticker
    String stickerTemplateID;
    //Image Model A
    String aid;
    Float aoffset_x, aoffset_x_original, aoffset_x_max, aoffset_x_min, aoffset_y, aoffset_y_original, aoffset_y_max;
    Float aoffset_y_min, ascale, ascale_original, ascale_max, ascale_min, arotate, arotate_original, arotate_max, arotate_min;
    Boolean aoffset_x_enable, aoffset_y_enable, ascale_enable, arotate_enable, afilter_enable;
    Integer afilter;
    byte[] aurl;
    //Image Model B
    String bid;
    Float boffset_x, boffset_x_original, boffset_x_max, boffset_x_min, boffset_y, boffset_y_original, boffset_y_max;
    Float boffset_y_min, bscale, bscale_original, bscale_max, bscale_min, brotate, brotate_original, brotate_max, brotate_min;
    Boolean boffset_x_enable, boffset_y_enable, bscale_enable, brotate_enable, bfilter_enable;
    Integer bfilter;
    byte[] burl;
    //Image Model C
    String cid;
    Float coffset_x, coffset_x_original, coffset_x_max, coffset_x_min, coffset_y, coffset_y_original, coffset_y_max;
    Float coffset_y_min, cscale, cscale_original, cscale_max, cscale_min, crotate, crotate_original, crotate_max, crotate_min;
    Boolean coffset_x_enable, coffset_y_enable, cscale_enable, crotate_enable, cfilter_enable;
    Integer cfilter;
    byte[] curl;
    //Image Model D
    String did;
    Float doffset_x, doffset_x_original, doffset_x_max, doffset_x_min, doffset_y, doffset_y_original, doffset_y_max;
    Float doffset_y_min, dscale, dscale_original, dscale_max, dscale_min, drotate, drotate_original, drotate_max, drotate_min;
    Boolean doffset_x_enable, doffset_y_enable, dscale_enable, drotate_enable, dfilter_enable;
    Integer dfilter;
    byte[] durl;*/
    /*Nuii*/
    //Image Template
    ArrayList imageTemplate;
    //Image Model A - D
    ArrayList imageA,imageB,imageC,imageD;

    /*Nuii*/
    ArrayList getTemplate;
    ArrayList getModel;

    /*** KITTI */
     DrawCanvas mDraw;

     Paint mPaint = new Paint();
     Paint mPaintInner = new Paint();
     Paint mPaint2 = new Paint();
     Paint mPaint3,mPaint4,mPaint5,mPaint6;
     Context mContext;
     ViewGroup mLayout;
     RelativeLayout mImageselect,mFrameLayout;
     ViewGroup.LayoutParams mLayoutParams;
     boolean touch_state = false;
     boolean mFirstTimeCheck = true;
    TouchImageView mImage;
     ArrayList<Point> mTopLeftArea,mTopRightArea,mBottomLeftArea,mBottomRightArea;
     Point mCenterPoint,mLeftPoint,mRightPoint,mTopPoint,mBottomPoint;
     float mStroke = 5f;
     float mStrokeInner = 6f;
     int mRadius = 30;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     **/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        contentView = inflater.inflate( R.layout.zpt_image_composer_view, null, false );
        mDrawerLayout.addView( contentView, 0 );

        mDraw = new DrawCanvas(this);
        init();

        //mFrameLayout.setVisibility(View.VISIBLE);
        //mImageselect.setVisibility(View.INVISIBLE);

        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                draw();
                return false;
            }
        });



        mDatabaseClass = new databaseClass( contentView.getContext() );

        /*Nuii*/
        //get data from previous page
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getImageTemplateID = bundle.getString( "imageID" );
        }
        if (getImageTemplateID != null) {
            imageTemplateID = mDatabaseClass.getImageComposer( getImageTemplateID ).getTemplate();
            stickerTemplateID = mDatabaseClass.getImageComposer( getImageTemplateID ).getSticker();
        }
        //Get Image Template
        /*imageTemplate = "123456789";*/
        if (imageTemplate != null) {
            // i = mDatabaseClass.getImageTemplate(imageTemplateID).getTemplate();
            GetImageTemplate(imageTemplateID);
        }
        //Get Sticker
        if (stickerTemplateID != null) {
            // imageTemplate = mDatabaseClass.getImageComposer(getImageTemplateID).getTemplate();
            // stickerTemplate = mDatabaseClass.getImageComposer(getImageTemplateID).getSticker();
        }
        //Get model of image A
        if (image_a != null) {
            GetImageModel( image_a );
        }

        //Get model of image B
        if (image_b != null) {
            GetImageModel( image_b );
        }

        //Get model of image C
        if (image_c != null) {
            GetImageModel( image_c );
        }

        //Get model of image D
        if (image_d != null) {
            GetImageModel( image_d );
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder( this ).addApi( AppIndex.API ).build();
    }

    //KITTI
    private class DrawCanvas extends View implements View.OnTouchListener{

        int tMaxLeft,tMaxRight,tMaxTop,tMaxBottom;
        float mDistanceCenter, mDistanceLeft, mDistanceRight, mDistanceTop, mDistanceBottom;
        Rect mTopLeftAreaRect,mTopRightAreaRect,mBottomLeftAreaRect,mBottomRightAreaRect;

        public Drawable myPic[] = {
                getResources().getDrawable(R.drawable.boston),
                getResources().getDrawable(R.drawable.carifornia),
                getResources().getDrawable(R.drawable.dubai),
                getResources().getDrawable(R.drawable.paris)
        };

        private DrawCanvas(Context mContext) {
            super(mContext);
            this.setOnTouchListener(this);

        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
//            Log.i("PARAM",""+mLayoutParams.height+" "+getHeight());





            if (mFirstTimeCheck) {
                tMaxLeft = mRadius;
                tMaxRight = getWidth() - mRadius;
                tMaxTop = mRadius;
                tMaxBottom = getHeight() - mRadius;

                mCenterPoint.set( getWidth() / 2, getHeight() / 2 );
                mLeftPoint.set( tMaxLeft, getHeight() / 2 );
                mRightPoint.set( tMaxRight, getHeight() / 2 );
                mTopPoint.set( getWidth() / 2, tMaxTop );
                mBottomPoint.set( getWidth() / 2, tMaxBottom );

                mTopLeftAreaRect = new Rect( tMaxLeft, tMaxTop, mCenterPoint.x, mCenterPoint.y );
                mTopRightAreaRect = new Rect( mTopPoint.x, mTopPoint.y, mRightPoint.x, mRightPoint.y );
                mBottomLeftAreaRect = new Rect( mLeftPoint.x, mLeftPoint.y, mBottomPoint.x, mBottomPoint.y );
                mBottomRightAreaRect = new Rect( mCenterPoint.x, mCenterPoint.y, tMaxRight, tMaxBottom );

                mFirstTimeCheck = false;
            }


            if (!touch_state) {
                int Point = 0;
                if ((int) (mPaint.getStrokeWidth() / 2) > Point) {
                    Point = (int) (mPaint.getStrokeWidth() / 2);
                } else {
                    Point = (int) (mPaintInner.getStrokeWidth() / 2);
                }
                mPaintInner.setStrokeWidth(mPaint.getStrokeWidth() + 5);

                Bitmap b = ((BitmapDrawable) myPic[0]).getBitmap();
                Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
                int w = getWidth(), h = getHeight();
                Point ImgA[] = {
                        new Point(mLeftPoint.x, mTopPoint.y),
                        new Point(mTopPoint.x, mTopPoint.y),
                        new Point(mCenterPoint.x, mCenterPoint.y),
                        new Point(mLeftPoint.x, mLeftPoint.y)
                };
                Bitmap roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgA);
                canvas.drawBitmap(roundBitmap, 0, 0, null);
                canvas.drawLine(mLeftPoint.x + Point, mTopPoint.y + Point, mTopPoint.x - Point, mTopPoint.y + Point, mPaintInner);
                canvas.drawLine(mTopPoint.x - Point, mTopPoint.y - Point, mCenterPoint.x - Point, mCenterPoint.y - Point, mPaintInner);
                canvas.drawLine(mCenterPoint.x - Point, mCenterPoint.y - Point, mLeftPoint.x + Point, mLeftPoint.y - Point, mPaintInner);
                canvas.drawLine(mLeftPoint.x + Point, mLeftPoint.y - Point, mLeftPoint.x + Point, mTopPoint.y + Point, mPaintInner);

                b = ((BitmapDrawable) myPic[1]).getBitmap();
                bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
                ImgA[0] = new Point(mTopPoint.x, mTopPoint.y);
                ImgA[1] = new Point(mRightPoint.x, mTopPoint.y);
                ImgA[2] = new Point(mRightPoint.x, mRightPoint.y);
                ImgA[3] = new Point(mCenterPoint.x, mCenterPoint.y);

                roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgA);
                canvas.drawBitmap(roundBitmap, 0, 0, null);
                canvas.drawLine(mTopPoint.x + Point, mTopPoint.y + Point, mRightPoint.x - Point, mTopPoint.y + Point, mPaintInner);
                canvas.drawLine(mRightPoint.x - Point, mTopPoint.y - Point, mRightPoint.x - Point, mRightPoint.y - Point, mPaintInner);
                canvas.drawLine(mRightPoint.x - Point, mRightPoint.y - Point, mCenterPoint.x + Point, mCenterPoint.y - Point, mPaintInner);
                canvas.drawLine(mCenterPoint.x + Point, mCenterPoint.y - Point, mTopPoint.x + Point, mTopPoint.y + Point, mPaintInner);

                b = ((BitmapDrawable) myPic[2]).getBitmap();
                bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
                ImgA[0] = new Point(mLeftPoint.x, mLeftPoint.y);
                ImgA[1] = new Point(mCenterPoint.x, mCenterPoint.y);
                ImgA[2] = new Point(mBottomPoint.x, mBottomPoint.y);
                ImgA[3] = new Point(mLeftPoint.x, mBottomPoint.y);
                roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgA);
                canvas.drawBitmap(roundBitmap, 0, 0, null);
                canvas.drawLine(mLeftPoint.x + Point, mLeftPoint.y + Point, mCenterPoint.x - Point, mCenterPoint.y + Point, mPaintInner);
                canvas.drawLine(mCenterPoint.x - Point, mCenterPoint.y - Point, mBottomPoint.x - Point, mBottomPoint.y - Point, mPaintInner);
                canvas.drawLine(mBottomPoint.x - Point, mBottomPoint.y - Point, mLeftPoint.x + Point, mBottomPoint.y - Point, mPaintInner);
                canvas.drawLine(mLeftPoint.x + Point, mBottomPoint.y - Point, mLeftPoint.x + Point, mLeftPoint.y + Point, mPaintInner);

                b = ((BitmapDrawable) myPic[3]).getBitmap();
                bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
                ImgA[0] = new Point(mCenterPoint.x, mCenterPoint.y);
                ImgA[1] = new Point(mRightPoint.x, mRightPoint.y);
                ImgA[2] = new Point(mRightPoint.x, mBottomPoint.y);
                ImgA[3] = new Point(mBottomPoint.x, mBottomPoint.y);

                roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgA);
                canvas.drawBitmap(roundBitmap, 0, 0, null);
                canvas.drawLine(mCenterPoint.x + Point, mCenterPoint.y + Point, mRightPoint.x - Point, mRightPoint.y + Point, mPaintInner);
                canvas.drawLine(mRightPoint.x - Point, mRightPoint.y - Point, mRightPoint.x - Point, mBottomPoint.y - Point, mPaintInner);
                canvas.drawLine(mRightPoint.x - Point, mBottomPoint.y - Point, mBottomPoint.x + Point, mBottomPoint.y - Point, mPaintInner);
                canvas.drawLine(mBottomPoint.x + Point, mBottomPoint.y - Point, mCenterPoint.x + Point, mCenterPoint.y + Point, mPaintInner);
            }

            //topleftarea
            canvas.drawRect( mTopLeftAreaRect, mPaint3 );
            //toprightarea
            canvas.drawRect( mTopRightAreaRect, mPaint4 );
            //bottomleftarea
            canvas.drawRect( mBottomLeftAreaRect, mPaint5 );
            //bottomrightarea
            canvas.drawRect( mBottomRightAreaRect, mPaint6 );
            //frame
            canvas.drawRect( tMaxLeft, tMaxTop, tMaxRight, tMaxBottom, mPaint );
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
            //Log.d("PARAM4", "" + tXPoint + " " + tYPoint +" D1 "+mDistanceCenter
            //        +" D2 "+mDistanceLeft+" D3 "+mDistanceRight+" D4 "+mDistanceTop+" D5 "+mDistanceBottom);

            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (mDistanceCenter <= mRadius){
                    mCenterPoint.set(tXPoint,tYPoint);
                    //Log.i("PARAM1",""+tXPoint+" "+tYPoint+" D1 "+mDistanceCenter);
                    invalidate();
//                draw();
                    touch_state = true;
                } else if (mDistanceLeft <= mRadius){
                    mLeftPoint.set(tMaxLeft,tYPoint);
                    //Log.i("PARAMLEFT",""+tXPoint+" "+tYPoint+" D2 "+mDistanceLeft);
                    invalidate();
//                draw();
                    touch_state = true;
                } else if (mDistanceRight <= mRadius){
                    mRightPoint.set(tMaxRight,tYPoint);
                    //Log.i("PARAMRIGHT",""+tXPoint+" "+tYPoint+" D3 "+mDistanceRight);
                    invalidate();
//                draw();
                    touch_state = true;
                } else if (mDistanceTop <= mRadius){
                    mTopPoint.set(tXPoint,tMaxTop);
                    //Log.i("PARAMTOP",""+tXPoint+" "+tYPoint+" D4 "+mDistanceTop);
                    invalidate();
//                draw();
                    touch_state = true;
                } else if (mDistanceBottom <= mRadius){
                    mBottomPoint.set(tXPoint,tMaxBottom);
                    //Log.i("PARAMBOTTOM",""+tXPoint+" "+tYPoint+" D5 "+mDistanceBottom);
                    invalidate();
                    touch_state = true;
                }
                else {
                    FindImage((int) motionEvent.getX(), (int) motionEvent.getY() );
                }
                setPointAreaArrayList();

            } else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE && touch_state) {
                if (mDistanceCenter <= mRadius){
                    mCenterPoint.set(tXPoint,tYPoint);
//                    Log.i("PARAM1",""+tXPoint+" "+tYPoint+" D1 "+mDistanceCenter);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceLeft <= mRadius){
                    mLeftPoint.set(tMaxLeft,tYPoint);
//                    Log.i("PARAMLEFT",""+tXPoint+" "+tYPoint+" D2 "+mDistanceLeft);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceRight <= mRadius){
                    mRightPoint.set(tMaxRight,tYPoint);
//                    Log.i("PARAMRIGHT",""+tXPoint+" "+tYPoint+" D3 "+mDistanceRight);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceTop <= mRadius){
                    mTopPoint.set(tXPoint,tMaxTop);
//                    Log.i("PARAMTOP",""+tXPoint+" "+tYPoint+" D4 "+mDistanceTop);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceBottom <= mRadius){
                    mBottomPoint.set(tXPoint,tMaxBottom);
//                    Log.i("PARAMBOTTOM",""+tXPoint+" "+tYPoint+" D5 "+mDistanceBottom);
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

        public Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius , Point point_draw[]) {
            Bitmap finalBitmap;
            if(bitmap.getWidth() != radius || bitmap.getHeight() != radius)
                finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
            else
                finalBitmap = bitmap;
            Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                    finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());

            Point point1_draw = new Point(mLeftPoint.x,mTopPoint.y);
            Point point2_draw = new Point(mTopPoint.x,mTopPoint.y);
            Point point3_draw = new Point(mCenterPoint.x,mCenterPoint.y);
            Point point4_draw = new Point(mLeftPoint.x,mLeftPoint.y);

            Path path = new Path();
            path.moveTo(point_draw[0].x,point_draw[0].y);
            path.lineTo(point_draw[1].x,point_draw[1].y);
            path.lineTo(point_draw[2].x,point_draw[2].y);
            path.lineTo(point_draw[3].x,point_draw[3].y);
            path.lineTo(point_draw[0].x,point_draw[0].y);
            path.close();
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.parseColor("#BAB399"));
            canvas.drawPath(path, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(finalBitmap, rect, rect, paint);
            return output;
        }

        private void FindImage(int x, int y) {

            if (x > mCenterPoint.x) {
                //Img B,D
                if (y > mCenterPoint.y) {
                    //ImgD
                    mIndex = 3;
                    selectImage();

                } else {
                    mIndex = 1;
                    //ImgB
                    selectImage();
                }
            } else {
                //Img A,C
                if (y > mCenterPoint.y) {
                    //ImgC
                    mIndex = 2;
                    selectImage();
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

    /*Nuii*/
    //Get Image Tamplate
    public ArrayList GetImageTemplate(String imageTemplate) {
        getTemplate = new ArrayList();
        getTemplate.add( mDatabaseClass.getImageTemplate( imageTemplate ).getImageTemplate_id() );
        getTemplate.add( 1, mDatabaseClass.getImageTemplate( imageTemplate ).getTemplate() );
        getTemplate.add( 2, mDatabaseClass.getImageTemplate( imageTemplate ).getImage_a() );
        getTemplate.add( 3, mDatabaseClass.getImageTemplate( imageTemplate ).getImage_b() );
        getTemplate.add( 4, mDatabaseClass.getImageTemplate( imageTemplate ).getImage_c() );
        getTemplate.add( 5, mDatabaseClass.getImageTemplate( imageTemplate ).getImage_d() );
        getTemplate.add( 6, mDatabaseClass.getImageTemplate( imageTemplate ).getMarge_one_color() );
        getTemplate.add( 7, mDatabaseClass.getImageTemplate( imageTemplate ).getMarge_two_color() );
        getTemplate.add( 8, mDatabaseClass.getImageTemplate( imageTemplate ).getMarge_one_stroke() );
        getTemplate.add( 9, mDatabaseClass.getImageTemplate( imageTemplate ).getMarge_two_stroke() );
        getTemplate.add( 10, mDatabaseClass.getImageTemplate( imageTemplate ).getTop_value() );
        getTemplate.add( 11, mDatabaseClass.getImageTemplate( imageTemplate ).getBottom_value() );
        getTemplate.add( 12, mDatabaseClass.getImageTemplate( imageTemplate ).getMarge_two_color() );
        getTemplate.add( 13, mDatabaseClass.getImageTemplate( imageTemplate ).getMarge_one_stroke() );
        getTemplate.add( 14, mDatabaseClass.getImageTemplate( imageTemplate ).getMarge_two_stroke() );
        getTemplate.add( 15, mDatabaseClass.getImageTemplate( imageTemplate ).getTop_value() );
        getTemplate.add( 16, mDatabaseClass.getImageTemplate( imageTemplate ).getBottom_value() );
        return (getTemplate);
    }

    /*Nuii*/
    //Get Image Model
    public ArrayList GetImageModel(String ImageID) {
        getModel = new ArrayList();
        getModel.add( 0, mDatabaseClass.getImage( ImageID ).getUrl() );
        getModel.add( 1, mDatabaseClass.getImage( ImageID ).getUrl() );
        getModel.add( 2, mDatabaseClass.getImage( ImageID ).getX() );
        getModel.add( 3, mDatabaseClass.getImage( ImageID ).getX_enable() );
        getModel.add( 4, mDatabaseClass.getImage( ImageID ).getX_original() );
        getModel.add( 5, mDatabaseClass.getImage( ImageID ).getX_max() );
        getModel.add( 6, mDatabaseClass.getImage( ImageID ).getX_min() );
        getModel.add( 7, mDatabaseClass.getImage( ImageID ).getY() );
        getModel.add( 8, mDatabaseClass.getImage( ImageID ).getY_enable() );
        getModel.add( 9, mDatabaseClass.getImage( ImageID ).getY_original() );
        getModel.add( 10, mDatabaseClass.getImage( ImageID ).getY_max() );
        getModel.add( 11, mDatabaseClass.getImage( ImageID ).getY_min() );
        getModel.add( 12, mDatabaseClass.getImage( ImageID ).getScale() );
        getModel.add( 13, mDatabaseClass.getImage( ImageID ).getScale_enable() );
        getModel.add( 14, mDatabaseClass.getImage( ImageID ).getScale_original() );
        getModel.add( 15, mDatabaseClass.getImage( ImageID ).getScale_max() );
        getModel.add( 16, mDatabaseClass.getImage( ImageID ).getScale_min() );
        getModel.add( 17, mDatabaseClass.getImage( ImageID ).getRotate() );
        getModel.add( 18, mDatabaseClass.getImage( ImageID ).getRotate_enable() );
        getModel.add( 19, mDatabaseClass.getImage( ImageID ).getRotate_original() );
        getModel.add( 20, mDatabaseClass.getImage( ImageID ).getRotate_max() );
        getModel.add( 21, mDatabaseClass.getImage( ImageID ).getRotate_min() );
        getModel.add( 22, mDatabaseClass.getImage( ImageID ).getFilter_enable() );
        getModel.add( 23, mDatabaseClass.getImage( ImageID ).getFilter() );
        return (getModel);

    }

    @Override
    protected void onResume() {
        super.onResume();



        sOuter.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float sizeF = new Float( progress );
                mPaint.setStrokeWidth( sizeF );
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
                float sizeF = new Float( progress );
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

        nextButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(contentView.getContext(), "Complete", Toast.LENGTH_LONG).show();
                /*Convert Bitmap to Byte Array*/
                /*
                RelativeLayout savedImage = (RelativeLayout) findViewById( R.id.FrameImageView );
                savedImage.setDrawingCacheEnabled( true );
                savedImage.buildDrawingCache();
                Bitmap bmp = savedImage.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress( Bitmap.CompressFormat.PNG, 100, stream );
                byte[] byteArray = stream.toByteArray();
                savedImage.destroyDrawingCache();
                Intent intent = new Intent( contentView.getContext(), ZPTStickerComposerView.class );
                intent.putExtra( "picture", byteArray );
                startActivity( intent );*/
                /*Nuii*/
                aid = image_a;
                bid = image_b;
                cid = image_c;
                did = image_d;

                //Add Values 1
                /*template = 1;
                marge_one_stroke = 1.00f;
                marge_one_color = "";
                marge_two_stroke = 1.00f;
                marge_two_color = "";
                top_value = 1.00f;
                bottom_value = 1.00f;
                right_value = 1.00f;
                left_value = 1.00f;
                center_x = 1.00f;
                center_y = 1.00f;*/


                /*Nuii : Call values from MoveLineClass*/
                imageTemplate = mMove.imageTemplate;


                /*Nuii*/
                //Insert Image Template;
                if (getImageTemplateID == null) {
                    getTpID = mDatabaseClass.createID();
                    image_a = mDatabaseClass.createID();
                    image_b = mDatabaseClass.createID();
                    image_c = mDatabaseClass.createID();
                    image_d = mDatabaseClass.createID();
                    /*while (mDatabaseClass.checkUniqueID(tID, "image")) {
                        tID = mDatabaseClass.createID();
                    }*/
                    //imageTemplate
                    ImageTemplate tImageTemplate = new ImageTemplate(getTpID, Integer.parseInt(imageTemplate.get(0).toString()), image_a, image_b, image_c, image_d
                            , Float.parseFloat(imageTemplate.get(1).toString()), imageTemplate.get(2).toString(), Float.parseFloat(imageTemplate.get(3).toString())
                            , imageTemplate.get(4).toString(), Float.parseFloat(imageTemplate.get(5).toString()), Float.parseFloat(imageTemplate.get(6).toString())
                            , Float.parseFloat(imageTemplate.get(7).toString()), Float.parseFloat(imageTemplate.get(8).toString())
                            , Float.parseFloat(imageTemplate.get(9).toString()), Float.parseFloat(imageTemplate.get(10).toString()));
                   /* ImageTemplate tImageTemplate = new ImageTemplate( getTpID, template, image_a, image_b, image_c, image_d
                            , marge_one_stroke, marge_one_color, marge_two_stroke, marge_two_color
                            , top_value, bottom_value, right_value, left_value, center_x, center_y );*/

                    mDatabaseClass.insertImageTemplate(tImageTemplate);

                } else {
                    getTpID = imageTemplateID;
                    ImageTemplate tImageTemplate = new ImageTemplate(Integer.parseInt(imageTemplate.get(0).toString()), image_a, image_b, image_c, image_d
                            , Float.parseFloat(imageTemplate.get(1).toString()), imageTemplate.get(2).toString(), Float.parseFloat(imageTemplate.get(3).toString())
                            , imageTemplate.get(4).toString(), Float.parseFloat(imageTemplate.get(5).toString()), Float.parseFloat(imageTemplate.get(6).toString())
                            , Float.parseFloat(imageTemplate.get(7).toString()), Float.parseFloat(imageTemplate.get(8).toString())
                            , Float.parseFloat(imageTemplate.get(9).toString()), Float.parseFloat(imageTemplate.get(10).toString()));
                    /*ImageTemplate tImageTemplate = new ImageTemplate( template, image_a, image_b, image_c, image_d
                            , marge_one_stroke, marge_one_color, marge_two_stroke, marge_two_color
                            , top_value, bottom_value, right_value, left_value, center_x, center_y );*/
                    mDatabaseClass.updateImageTemplate(tImageTemplate);
                }
                /*Nuii*/
                //Get value of image A
                /*aurl = null;
                aoffset_x = 0.00f;
                aoffset_x_enable = false;
                aoffset_x_original = 0.00f;
                aoffset_x_max = 0.00f;
                aoffset_x_min = 0.00f;
                aoffset_y = 0.00f;
                aoffset_y_enable = false;
                aoffset_y_original = 0.00f;
                aoffset_y_max = 0.00f;
                aoffset_y_min = 0.00f;
                ascale = 0.00f;
                ascale_enable = false;
                ascale_original = 0.00f;
                ascale_max = 0.00f;
                ascale_min = 0.00f;
                arotate = 0.00f;
                arotate_enable = false;
                arotate_original = 0.00f;
                arotate_max = 0.00f;
                arotate_min = 0.00f;
                afilter_enable = false;
                afilter = 1;*/
                 /*Nuii : Call values from MoveLineClass*/
                imageA = mMove.imageA;

                if (Integer.parseInt(imageTemplate.get(0).toString()) >= 1) {
                    if (aid == null) {
                        aid = mDatabaseClass.createID();

                       /* Image ImageA = new Image( aid, aurl, aoffset_x, aoffset_x_enable, aoffset_x_original, aoffset_x_max, aoffset_x_min
                                , aoffset_y, aoffset_y_enable, aoffset_y_original, aoffset_y_max, aoffset_y_min
                                , ascale, ascale_enable, ascale_original, ascale_max, ascale_min
                                , arotate, arotate_enable, arotate_original, arotate_max, arotate_min
                                , afilter_enable, afilter );*/

                        Image ImageA = new Image(aid, imageA.get(0).toString().getBytes(), Float.parseFloat(imageA.get(1).toString()), Boolean.parseBoolean(imageA.get(2).toString())
                                , Float.parseFloat(imageA.get(3).toString()), Float.parseFloat(imageA.get(4).toString()), Float.parseFloat(imageA.get(5).toString()), Float.parseFloat(imageA.get(6).toString()), Boolean.parseBoolean(imageA.get(7).toString())
                                , Float.parseFloat(imageA.get(8).toString()), Float.parseFloat(imageA.get(9).toString()), Float.parseFloat(imageA.get(10).toString()), Float.parseFloat(imageA.get(11).toString()), Boolean.parseBoolean(imageA.get(12).toString())
                                , Float.parseFloat(imageA.get(13).toString()), Float.parseFloat(imageA.get(14).toString()), Float.parseFloat(imageA.get(15).toString()), Float.parseFloat(imageA.get(16).toString()), Boolean.parseBoolean(imageA.get(17).toString())
                                , Float.parseFloat(imageA.get(18).toString()), Float.parseFloat(imageA.get(19).toString()), Float.parseFloat(imageA.get(20).toString()), Boolean.parseBoolean(imageA.get(21).toString()), Integer.parseInt(imageA.get(22).toString()));

                    /*    imageA.add(0,"url");//String : url of image (delete when object is delete)
                        imageA.add(1,"offset_x");//Float : offset X of image
                        imageA.add(2,"offset_x_enable");//Boolean : enable offset X of image
                        imageA.add(3,"offset_x_original");//Float : original offset X of image
                        imageA.add(4,"offset_x_max");//Float : maximum authorized offset X of image
                        imageA.add(5,"offset_x_min");//Float : minimum authorized offset X of image
                        imageA.add(6,"offset_y");//Float : offset Y of image
                        imageA.add(7,"offset_y_enable");//Boolean : enable offset X of image
                        imageA.add(8,"offset_y_original");//Float : original offset Y of image
                        imageA.add(9,"offset_y_max");//Float : maximum authorized offset Y of image
                        imageA.add(10,"offset_y_min");//Float : minimum authorized offset Y of image
                        imageA.add(11,"scale");//Float : scale of image
                        imageA.add(12,"scale_enable");//Float : Boolean : enable scale of image
                        imageA.add(13,"scale_original");//Float : original scale of image
                        imageA.add(14,"scale_max");//Float : maximum authorized scale of image
                        imageA.add(15,"scale_min");//Float : minimum authorized scale of image
                        imageA.add(16,"rotate");//Float : rotate of image
                        imageA.add(17,"rotate_enable");//Float : Boolean : enable rotation of image
                        imageA.add(18,"rotate_original");//Float : original rotation of image
                        imageA.add(19,"rotate_max");//Float : maximum authorized rotate of image
                        imageA.add(20,"rotate_min");//Float : minimum authorized rotate of image
                        imageA.add(21,"filter_enable");//Boolean : enable filter for this image
                        imageA.add(22,"filter");//Float : Integer : enum filter for this image
                        return (imageA);*/

                        mDatabaseClass.insertImage(ImageA);
                    } else {
                       /* Image ImageA = new Image( aurl, aoffset_x, aoffset_x_enable, aoffset_x_original, aoffset_x_max, aoffset_x_min
                                , aoffset_y, aoffset_y_enable, aoffset_y_original, aoffset_y_max, aoffset_y_min
                                , ascale, ascale_enable, ascale_original, ascale_max, ascale_min
                                , arotate, arotate_enable, arotate_original, arotate_max, arotate_min
                                , afilter_enable, afilter );*/
                        Image ImageA = new Image(imageA.get(0).toString().getBytes(), Float.parseFloat(imageA.get(1).toString()), Boolean.parseBoolean(imageA.get(2).toString())
                                , Float.parseFloat(imageA.get(3).toString()), Float.parseFloat(imageA.get(4).toString()), Float.parseFloat(imageA.get(5).toString()), Float.parseFloat(imageA.get(6).toString()), Boolean.parseBoolean(imageA.get(7).toString())
                                , Float.parseFloat(imageA.get(8).toString()), Float.parseFloat(imageA.get(9).toString()), Float.parseFloat(imageA.get(10).toString()), Float.parseFloat(imageA.get(11).toString()), Boolean.parseBoolean(imageA.get(12).toString())
                                , Float.parseFloat(imageA.get(13).toString()), Float.parseFloat(imageA.get(14).toString()), Float.parseFloat(imageA.get(15).toString()), Float.parseFloat(imageA.get(16).toString()), Boolean.parseBoolean(imageA.get(17).toString())
                                , Float.parseFloat(imageA.get(18).toString()), Float.parseFloat(imageA.get(19).toString()), Float.parseFloat(imageA.get(20).toString()), Boolean.parseBoolean(imageA.get(21).toString()), Integer.parseInt(imageA.get(22).toString()));
                        mDatabaseClass.updateImage(ImageA);
                    }
                }
                if (Integer.parseInt(imageTemplate.get(0).toString()) >= 2) {
                    /*Nuii*/
                    //Get value of image B
                    imageB = mMove.imageB;
                    if (bid == null) {
                        bid = mDatabaseClass.createID();

                        Image ImageB = new Image(bid, imageB.get(0).toString().getBytes(), Float.parseFloat(imageB.get(1).toString()), Boolean.parseBoolean(imageB.get(2).toString())
                                , Float.parseFloat(imageB.get(3).toString()), Float.parseFloat(imageB.get(4).toString()), Float.parseFloat(imageB.get(5).toString()), Float.parseFloat(imageB.get(6).toString()), Boolean.parseBoolean(imageB.get(7).toString())
                                , Float.parseFloat(imageB.get(8).toString()), Float.parseFloat(imageB.get(9).toString()), Float.parseFloat(imageB.get(10).toString()), Float.parseFloat(imageB.get(11).toString()), Boolean.parseBoolean(imageB.get(12).toString())
                                , Float.parseFloat(imageB.get(13).toString()), Float.parseFloat(imageB.get(14).toString()), Float.parseFloat(imageB.get(15).toString()), Float.parseFloat(imageB.get(16).toString()), Boolean.parseBoolean(imageB.get(17).toString())
                                , Float.parseFloat(imageB.get(18).toString()), Float.parseFloat(imageB.get(19).toString()), Float.parseFloat(imageB.get(20).toString()), Boolean.parseBoolean(imageB.get(21).toString()), Integer.parseInt(imageB.get(22).toString()));
                        mDatabaseClass.insertImage(ImageB);
                    } else {
                        Image ImageB = new Image(imageB.get(0).toString().getBytes(), Float.parseFloat(imageB.get(1).toString()), Boolean.parseBoolean(imageB.get(2).toString())
                                , Float.parseFloat(imageB.get(3).toString()), Float.parseFloat(imageB.get(4).toString()), Float.parseFloat(imageB.get(5).toString()), Float.parseFloat(imageB.get(6).toString()), Boolean.parseBoolean(imageB.get(7).toString())
                                , Float.parseFloat(imageB.get(8).toString()), Float.parseFloat(imageB.get(9).toString()), Float.parseFloat(imageB.get(10).toString()), Float.parseFloat(imageB.get(11).toString()), Boolean.parseBoolean(imageB.get(12).toString())
                                , Float.parseFloat(imageB.get(13).toString()), Float.parseFloat(imageB.get(14).toString()), Float.parseFloat(imageB.get(15).toString()), Float.parseFloat(imageB.get(16).toString()), Boolean.parseBoolean(imageB.get(17).toString())
                                , Float.parseFloat(imageB.get(18).toString()), Float.parseFloat(imageB.get(19).toString()), Float.parseFloat(imageB.get(20).toString()), Boolean.parseBoolean(imageB.get(21).toString()), Integer.parseInt(imageB.get(22).toString()));

                        mDatabaseClass.updateImage(ImageB);
                    }
                }
                if (Integer.parseInt(imageTemplate.get(0).toString()) >= 3) {
                    /*Nuii*/
                    //Get value of image C
                    imageC = mMove.imageC;
                    if (cid == null) {
                        cid = mDatabaseClass.createID();

                        Image ImageC = new Image(cid, imageC.get(0).toString().getBytes(), Float.parseFloat(imageC.get(1).toString()), Boolean.parseBoolean(imageC.get(2).toString())
                                , Float.parseFloat(imageC.get(3).toString()), Float.parseFloat(imageC.get(4).toString()), Float.parseFloat(imageC.get(5).toString()), Float.parseFloat(imageC.get(6).toString()), Boolean.parseBoolean(imageC.get(7).toString())
                                , Float.parseFloat(imageC.get(8).toString()), Float.parseFloat(imageC.get(9).toString()), Float.parseFloat(imageC.get(10).toString()), Float.parseFloat(imageC.get(11).toString()), Boolean.parseBoolean(imageC.get(12).toString())
                                , Float.parseFloat(imageC.get(13).toString()), Float.parseFloat(imageC.get(14).toString()), Float.parseFloat(imageC.get(15).toString()), Float.parseFloat(imageC.get(16).toString()), Boolean.parseBoolean(imageC.get(17).toString())
                                , Float.parseFloat(imageC.get(18).toString()), Float.parseFloat(imageC.get(19).toString()), Float.parseFloat(imageC.get(20).toString()), Boolean.parseBoolean(imageC.get(21).toString()), Integer.parseInt(imageC.get(22).toString()));
                        mDatabaseClass.insertImage(ImageC);
                    } else {
                        Image ImageC = new Image(imageC.get(0).toString().getBytes(), Float.parseFloat(imageC.get(1).toString()), Boolean.parseBoolean(imageC.get(2).toString())
                                , Float.parseFloat(imageC.get(3).toString()), Float.parseFloat(imageC.get(4).toString()), Float.parseFloat(imageC.get(5).toString()), Float.parseFloat(imageC.get(6).toString()), Boolean.parseBoolean(imageC.get(7).toString())
                                , Float.parseFloat(imageC.get(8).toString()), Float.parseFloat(imageC.get(9).toString()), Float.parseFloat(imageC.get(10).toString()), Float.parseFloat(imageC.get(11).toString()), Boolean.parseBoolean(imageC.get(12).toString())
                                , Float.parseFloat(imageC.get(13).toString()), Float.parseFloat(imageC.get(14).toString()), Float.parseFloat(imageC.get(15).toString()), Float.parseFloat(imageC.get(16).toString()), Boolean.parseBoolean(imageC.get(17).toString())
                                , Float.parseFloat(imageC.get(18).toString()), Float.parseFloat(imageC.get(19).toString()), Float.parseFloat(imageC.get(20).toString()), Boolean.parseBoolean(imageC.get(21).toString()), Integer.parseInt(imageC.get(22).toString()));

                        mDatabaseClass.updateImage(ImageC);
                    }
                }
                if (Integer.parseInt(imageTemplate.get(0).toString()) >= 4) {
                    /*Nuii*/
                    //Get value of image D
                    imageD = mMove.imageD;
                    if (did == null) {
                        did = mDatabaseClass.createID();

                        Image ImageD = new Image(did, imageD.get(0).toString().getBytes(), Float.parseFloat(imageD.get(1).toString()), Boolean.parseBoolean(imageD.get(2).toString())
                                , Float.parseFloat(imageD.get(3).toString()), Float.parseFloat(imageD.get(4).toString()), Float.parseFloat(imageD.get(5).toString()), Float.parseFloat(imageD.get(6).toString()), Boolean.parseBoolean(imageD.get(7).toString())
                                , Float.parseFloat(imageD.get(8).toString()), Float.parseFloat(imageD.get(9).toString()), Float.parseFloat(imageD.get(10).toString()), Float.parseFloat(imageD.get(11).toString()), Boolean.parseBoolean(imageD.get(12).toString())
                                , Float.parseFloat(imageD.get(13).toString()), Float.parseFloat(imageD.get(14).toString()), Float.parseFloat(imageD.get(15).toString()), Float.parseFloat(imageD.get(16).toString()), Boolean.parseBoolean(imageD.get(17).toString())
                                , Float.parseFloat(imageD.get(18).toString()), Float.parseFloat(imageD.get(19).toString()), Float.parseFloat(imageD.get(20).toString()), Boolean.parseBoolean(imageD.get(21).toString()), Integer.parseInt(imageD.get(22).toString()));
                        mDatabaseClass.insertImage(ImageD);
                    } else {
                        Image ImageD = new Image(imageD.get(0).toString().getBytes(), Float.parseFloat(imageD.get(1).toString()), Boolean.parseBoolean(imageD.get(2).toString())
                                , Float.parseFloat(imageD.get(3).toString()), Float.parseFloat(imageD.get(4).toString()), Float.parseFloat(imageD.get(5).toString()), Float.parseFloat(imageD.get(6).toString()), Boolean.parseBoolean(imageD.get(7).toString())
                                , Float.parseFloat(imageD.get(8).toString()), Float.parseFloat(imageD.get(9).toString()), Float.parseFloat(imageD.get(10).toString()), Float.parseFloat(imageD.get(11).toString()), Boolean.parseBoolean(imageD.get(12).toString())
                                , Float.parseFloat(imageD.get(13).toString()), Float.parseFloat(imageD.get(14).toString()), Float.parseFloat(imageD.get(15).toString()), Float.parseFloat(imageD.get(16).toString()), Boolean.parseBoolean(imageD.get(17).toString())
                                , Float.parseFloat(imageD.get(18).toString()), Float.parseFloat(imageD.get(19).toString()), Float.parseFloat(imageD.get(20).toString()), Boolean.parseBoolean(imageD.get(21).toString()), Integer.parseInt(imageD.get(22).toString()));

                        mDatabaseClass.updateImage(ImageD);
                    }
                }

                Intent intent = new Intent( contentView.getContext(), ZPTStickerComposerView.class );
                intent.putExtra( "imageTemplateID", getTpID );
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
        sOuter = (SeekBar) findViewById( R.id.seekBarOuter );
        sInner = (SeekBar) findViewById( R.id.seekBarInner );
        mImage = (TouchImageView) findViewById(R.id.mImage);
        farme1 = (ImageButton) findViewById( R.id.farme1 );
        farme2 = (ImageButton) findViewById( R.id.farme2 );
        farme3 = (ImageButton) findViewById( R.id.farme3 );
        farme4 = (ImageButton) findViewById( R.id.farme4 );
        farme5 = (ImageButton) findViewById( R.id.farme5 );
        farme6 = (ImageButton) findViewById( R.id.farme6 );
        farme7 = (ImageButton) findViewById( R.id.farme7 );
        farme8 = (ImageButton) findViewById( R.id.farme8 );

        mLayout = (RelativeLayout) findViewById( R.id.FrameImageView );
        mLayoutParams = mLayout.getLayoutParams();
//        mImageselect = (RelativeLayout) findViewById(R.id.Imageselect);
//        mFrameLayout = (RelativeLayout) findViewById(R.id.mFrameLayout);

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

        draw();

    }

    private void SetBlockImg(ImageView ObjImageView, int height, int width, int codeColor) {
        //Set Size Image
        ObjImageView.getLayoutParams().height = height;
        ObjImageView.getLayoutParams().width = width;
        ObjImageView.setBackgroundColor( codeColor );
        ObjImageView.setPadding( 50, 50, 50, 50 );
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder( ZPTImageComposerView.this );
        builder.setTitle( "Add Photo!" );
        builder.setItems( items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission( ZPTImageComposerView.this );

                if (items[item].equals( "Take Photo" )) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals( "Choose from Library" )) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals( "Cancel" )) {
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

        mImage.setImageBitmap( bm );
        mDraw.myPic[mIndex] = (Drawable)new BitmapDrawable(bm);
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


   /* @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction2 = Action.newAction(
        Action.TYPE_VIEW, // TODO: choose an action type.
        "ZPTImageComposerView Page", // TODO: Define a title for the content shown.
        // TODO: If you have web page content that matches this app activity's content,
        // make sure this auto-generated web page URL is correct.
        // Otherwise, set the URL to null.
        Uri.parse( "http://host/path" ),
        // TODO: Make sure this auto-generated app URL is correct.
        Uri.parse( "android-app://com.zibbeo.phototrimbree.PostCard/http/host/path" )
        );
        AppIndex.AppIndexApi.end( client2, viewAction2 );
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
        Action.TYPE_VIEW, // TODO: choose an action type.
        "ZPTImageComposerView Page", // TODO: Define a title for the content shown.
        // TODO: If you have web page content that matches this app activity's content,
        // make sure this auto-generated web page URL is correct.
        // Otherwise, set the URL to null.
        Uri.parse( "http://host/path" ),
        // TODO: Make sure this auto-generated app URL is correct.
        Uri.parse( "android-app://com.zibbeo.phototrimbree.PostCard/http/host/path" )
        );
        AppIndex.AppIndexApi.end( client, viewAction );
        client.disconnect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.disconnect();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        Action viewAction2 = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ZPTImageComposerView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse( "http://host/path" ),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse( "android-app://com.zibbeo.phototrimbree.PostCard/http/host/path" )
        );
        AppIndex.AppIndexApi.start( client2, viewAction2 );
    }*/
}
