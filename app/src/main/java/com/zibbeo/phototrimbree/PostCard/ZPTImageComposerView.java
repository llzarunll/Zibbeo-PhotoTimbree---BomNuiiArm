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
    boolean mFirstTimeCheck = true;
    RotateZoomImageView mImage;
    ArrayList<Point> mTopLeftArea,mTopRightArea,mBottomLeftArea,mBottomRightArea;
    Point mCenterPoint,mLeftPoint,mRightPoint,mTopPoint,mBottomPoint;
    float mStroke = 5f;
    float mStrokeInner = 6f;
    int mRadius = 30;
    boolean sCenter = true;
    boolean sTop = true;
    boolean sBottom = true;
    boolean sLeft = true;
    boolean sRight = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        contentView = inflater.inflate( R.layout.zpt_image_composer_view, null, false );
        mDrawerLayout.addView( contentView, 0 );

        mDraw = new DrawCanvas(this);
        init();

        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                draw();
                return false;
            }
        });

        mFrameLayout.setVisibility(View.VISIBLE);

        mDatabaseClass = new databaseClass( contentView.getContext() );
        //KITTI
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
                mDraw.sFarme = "0";
                mDraw.setPoint();
                sCenter = false;
                sLeft = false;
                sRight = false;
                sTop = false;
                sBottom = false;
                draw();
            }
        });

        farme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDraw.sFarme = "1";
                mDraw.setPoint();
                sCenter = true;
                sLeft = true;
                sRight = false;
                sTop = false;
                sBottom = false;
                draw();
            }
        });

        farme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDraw.sFarme = "2";
                mDraw.setPoint();
                sCenter = true;
                sLeft = false;
                sRight = false;
                sTop = true;
                sBottom = false;
                draw();
            }
        });


        farme4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDraw.sFarme = "3";
                mDraw.setPoint();
                sCenter = true;
                sLeft = false;
                sRight = true;
                sTop = true;
                sBottom = true;
                draw();
            }
        });

        farme5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDraw.sFarme = "4";
                mDraw.setPoint();
                sCenter = true;
                sLeft = true;
                sRight = true;
                sTop = false;
                sBottom = true;
                draw();
            }
        });

        farme6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDraw.sFarme = "5";
                mDraw.setPoint();
                sCenter = true;
                sLeft = true;
                sRight = true;
                sTop = true;
                sBottom = true;
                draw();
            }
        });

    }

    //KITTI
    private class DrawCanvas extends View implements View.OnTouchListener{

        int tMaxLeft,tMaxRight,tMaxTop,tMaxBottom;
        int tmpMaxLeft,tmpMaxRight,tmpMaxTop,tmpMaxBottom;
        float mDistanceCenter, mDistanceLeft, mDistanceRight, mDistanceTop, mDistanceBottom;
        Rect mTopLeftAreaRect,mTopRightAreaRect,mBottomLeftAreaRect,mBottomRightAreaRect;

        public Drawable myPic[] =  {
           this.getResources().getDrawable(R.drawable.boston),
           this.getResources().getDrawable(R.drawable.carifornia),
           this.getResources().getDrawable(R.drawable.dubai),
                this.getResources().getDrawable(R.drawable.paris)
        };
        public Matrix[] mMatrix = new Matrix[4];
        public String sFarme = "99";
        private DrawCanvas(Context mContext) {
            super(mContext);
            this.setOnTouchListener(this);

        }
        public void setPoint(){
            switch (sFarme){
                case "0":
                {
                    mCenterPoint.set(tmpMaxRight,tmpMaxBottom);
                    mLeftPoint.set(tmpMaxTop,tmpMaxBottom);
                    mRightPoint.set(tmpMaxRight, tmpMaxTop);
                    mTopPoint.set(tmpMaxRight, tmpMaxTop);
                }break;
                case "1": {
                    mCenterPoint.set(tmpMaxRight, getHeight() / 2);
                    mLeftPoint.set(tmpMaxLeft, getHeight() / 2);
                    mRightPoint.set(tmpMaxRight, getHeight() / 2);
                    mTopPoint.set(tmpMaxRight, tmpMaxTop);
                    mBottomPoint.set(tmpMaxRight, tmpMaxBottom);
                }break;
                case "2":{
                    mCenterPoint.set(getWidth()/2, tmpMaxBottom);
                    mLeftPoint.set(tmpMaxLeft, tmpMaxBottom);
                    mRightPoint.set(tmpMaxRight, tmpMaxBottom);
                    mTopPoint.set(getWidth()/2, tmpMaxTop);
                    mBottomPoint.set(getWidth()/2, tmpMaxBottom);
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

                mFirstTimeCheck = false;
            }


            if (!touch_state) {
                int Point = 0;
                if ((int) (mPaint.getStrokeWidth() / 2) > Point) {
                    Point = (int) (mPaint.getStrokeWidth() / 2);
                } else {
                    Point = (int) (mPaintInner.getStrokeWidth() / 2);
                }
                mPaintInner.setStrokeWidth(0);
                Bitmap b = ((BitmapDrawable) myPic[0]).getBitmap();
                //Bitmap b = ((BitmapDrawable) myPic[0]).getBitmap();
                Bitmap bitmap = Bitmap.createBitmap(b, 0, 0,
                        b.getWidth(), b.getHeight(), mMatrix[0], true);

//                        b.copy(Bitmap.Config.ARGB_8888, true);
                int w = getWidth(), h = getHeight();
                Bitmap roundBitmap;
                if(sFarme == "3") {
                    Point ImgA[] = {
                            new Point(mLeftPoint.x, mTopPoint.y),
                            new Point(mTopPoint.x, mTopPoint.y),
                            new Point(mCenterPoint.x, mCenterPoint.y),
                            new Point(mBottomPoint.x, mBottomPoint.y),
                            new Point(mLeftPoint.x, mBottomPoint.y),
                            new Point(mLeftPoint.x, mLeftPoint.y)
                    };
                    roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgA);
                }else if(sFarme == "4")
                {
                    Point ImgA[] = {
                            new Point(mLeftPoint.x, mTopPoint.y),
                            new Point(mTopPoint.x, mTopPoint.y),
                            new Point(mRightPoint.x, mTopPoint.y),
                            new Point(mRightPoint.x, mRightPoint.y),
                            new Point(mCenterPoint.x, mCenterPoint.y),
                            new Point(mLeftPoint.x, mLeftPoint.y)
                    };
                    roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgA);
                }
                else {
                    Point ImgA[] = {
                            new Point(mLeftPoint.x, mTopPoint.y),
                            new Point(mTopPoint.x, mTopPoint.y),
                            new Point(mCenterPoint.x, mCenterPoint.y),
                            new Point(mLeftPoint.x, mLeftPoint.y)
                    };
                    roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgA);
                }
                canvas.drawBitmap(roundBitmap, 0, 0, null);
//                canvas.drawLine(mLeftPoint.x + Point, mTopPoint.y + Point, mTopPoint.x - Point, mTopPoint.y + Point, mPaintInner);
//                canvas.drawLine(mTopPoint.x - Point, mTopPoint.y - Point, mCenterPoint.x - Point, mCenterPoint.y - Point, mPaintInner);
//                canvas.drawLine(mCenterPoint.x - Point, mCenterPoint.y - Point, mLeftPoint.x + Point, mLeftPoint.y - Point, mPaintInner);
//                canvas.drawLine(mLeftPoint.x + Point, mLeftPoint.y - Point, mLeftPoint.x + Point, mTopPoint.y + Point, mPaintInner);

                b = ((BitmapDrawable) myPic[1]).getBitmap();
                bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
                if(sFarme == "4") {
                    Point ImgB[] = {
                            new Point(mRightPoint.x, mRightPoint.y),
                            new Point(mRightPoint.x, mRightPoint.y),
                            new Point(mRightPoint.x, mRightPoint.y),
                            new Point(mRightPoint.x, mRightPoint.y)
                    };
                    roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgB);
                }else {
                    Point ImgB[] = {
                            new Point(mTopPoint.x, mTopPoint.y),
                            new Point(mRightPoint.x, mTopPoint.y),
                            new Point(mRightPoint.x, mRightPoint.y),
                            new Point(mCenterPoint.x, mCenterPoint.y)
                    };
                    roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgB);
                }

                canvas.drawBitmap(roundBitmap, 0, 0, null);
//                canvas.drawLine(mTopPoint.x + Point, mTopPoint.y + Point, mRightPoint.x - Point, mTopPoint.y + Point, mPaintInner);
//                canvas.drawLine(mRightPoint.x - Point, mTopPoint.y - Point, mRightPoint.x - Point, mRightPoint.y - Point, mPaintInner);
//                canvas.drawLine(mRightPoint.x - Point, mRightPoint.y - Point, mCenterPoint.x + Point, mCenterPoint.y - Point, mPaintInner);
//                canvas.drawLine(mCenterPoint.x + Point, mCenterPoint.y - Point, mTopPoint.x + Point, mTopPoint.y + Point, mPaintInner);

                b = ((BitmapDrawable) myPic[2]).getBitmap();
                bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
                if(sFarme == "3") {
                    Point ImgC[] = {
                            new Point(mBottomPoint.x, mBottomPoint.y),
                            new Point(mBottomPoint.x, mBottomPoint.y),
                            new Point(mBottomPoint.x, mBottomPoint.y),
                            new Point(mBottomPoint.x, mBottomPoint.y)
                    };
                    roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgC);
                }else {
                    Point ImgC[] = {
                            new Point(mLeftPoint.x, mLeftPoint.y),
                            new Point(mCenterPoint.x, mCenterPoint.y),
                            new Point(mBottomPoint.x, mBottomPoint.y),
                            new Point(mLeftPoint.x, mBottomPoint.y),
                    };
                    roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgC);
                }

                canvas.drawBitmap(roundBitmap, 0, 0, null);
//                canvas.drawLine(mLeftPoint.x + Point, mLeftPoint.y + Point, mCenterPoint.x - Point, mCenterPoint.y + Point, mPaintInner);
//                canvas.drawLine(mCenterPoint.x - Point, mCenterPoint.y - Point, mBottomPoint.x - Point, mBottomPoint.y - Point, mPaintInner);
//                canvas.drawLine(mBottomPoint.x - Point, mBottomPoint.y - Point, mLeftPoint.x + Point, mBottomPoint.y - Point, mPaintInner);
//                canvas.drawLine(mLeftPoint.x + Point, mBottomPoint.y - Point, mLeftPoint.x + Point, mLeftPoint.y + Point, mPaintInner);

                b = ((BitmapDrawable) myPic[3]).getBitmap();
                bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
                Point ImgD[] = {
                    new Point(mCenterPoint.x, mCenterPoint.y),
                    new Point(mRightPoint.x, mRightPoint.y),
                    new Point(mRightPoint.x, mBottomPoint.y),
                    new Point(mBottomPoint.x, mBottomPoint.y)
                };


                roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgD);
                canvas.drawBitmap(roundBitmap, 0, 0, null);
//                canvas.drawLine(mCenterPoint.x + Point, mCenterPoint.y + Point, mRightPoint.x - Point, mRightPoint.y + Point, mPaintInner);
//                canvas.drawLine(mRightPoint.x - Point, mRightPoint.y - Point, mRightPoint.x - Point, mBottomPoint.y - Point, mPaintInner);
//                canvas.drawLine(mRightPoint.x - Point, mBottomPoint.y - Point, mBottomPoint.x + Point, mBottomPoint.y - Point, mPaintInner);
//                canvas.drawLine(mBottomPoint.x + Point, mBottomPoint.y - Point, mCenterPoint.x + Point, mCenterPoint.y + Point, mPaintInner);
            }

            canvas.drawRect( tMaxLeft, tMaxTop, tMaxRight, tMaxBottom, mPaint );

            switch (sFarme){
                case "0":{

                }break;
                case "1":{
                    canvas.drawLine( mLeftPoint.x, mLeftPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //left
                    canvas.drawCircle( mLeftPoint.x, mLeftPoint.y, mRadius, mPaint2 );
                    //right
                    canvas.drawCircle( mCenterPoint.x, mCenterPoint.y, mRadius, mPaint2 );
                }
                break;
                case "2":{
                    canvas.drawLine( mTopPoint.x, mTopPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint );
                    //top
                    canvas.drawCircle( mTopPoint.x, mTopPoint.y, mRadius, mPaint2 );
                    //bottom
                    canvas.drawCircle( mCenterPoint.x, mCenterPoint.y, mRadius, mPaint2 );
                }
                break;
                case "3":{
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
                case "4":{
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
                    invalidate();
                    touch_state = true;
                } else if (mDistanceLeft <= mRadius && sLeft){
                    mLeftPoint.set(tMaxLeft,tYPoint);
                    mFrameLayout.setVisibility(View.VISIBLE);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceRight <= mRadius && sRight){
                    mRightPoint.set(tMaxRight,tYPoint);
                    mFrameLayout.setVisibility(View.VISIBLE);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceTop <= mRadius && sTop){
                    mTopPoint.set(tXPoint,tMaxTop);
                    mFrameLayout.setVisibility(View.VISIBLE);
                    invalidate();
                    touch_state = true;
                } else if (mDistanceBottom <= mRadius && sBottom){
                    mBottomPoint.set(tXPoint,tMaxBottom);
                    mFrameLayout.setVisibility(View.VISIBLE);
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
                        case "1": {
                            mCenterPoint.set(tMaxRight, tYPoint);
                        }break;
                        case "2":{
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
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.parseColor("#BAB399"));
            canvas.drawPath(path, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(finalBitmap, rect, rect, paint);
            return output;
        }

        private void FindImage(int x, int y) {

            mFrameLayout.setVisibility(INVISIBLE);
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
                mRadius = 0;
                draw();
                FrameLayout savedImage = (FrameLayout) findViewById( R.id.FrameImageView );
                savedImage.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
                savedImage.setDrawingCacheEnabled( true );
                savedImage.buildDrawingCache();
                Bitmap bmp = savedImage.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress( Bitmap.CompressFormat.PNG, 100, stream );
                byte[] byteArray = stream.toByteArray();
                savedImage.destroyDrawingCache();
                Intent intent = new Intent( contentView.getContext(), ZPTStickerComposerView.class );
                intent.putExtra( "picture", byteArray );
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

        draw();

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
        mDraw.myPic[mIndex] = new BitmapDrawable(bm);
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
}
