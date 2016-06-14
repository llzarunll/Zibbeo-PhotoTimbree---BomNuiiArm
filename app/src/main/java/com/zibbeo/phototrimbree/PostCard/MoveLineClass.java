package com.zibbeo.phototrimbree.PostCard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.zibbeo.phototrimbree.R;

import java.util.ArrayList;

/**
 * Created by samchaw on 5/3/16 AD.
 */
public class MoveLineClass {

    Paint mPaint = new Paint();
    Paint mPaintInner = new Paint();
    Paint mPaint2 = new Paint();
    Paint mPaint3,mPaint4,mPaint5,mPaint6;
    Context mContext;
    ViewGroup mLayout;
    ViewGroup.LayoutParams mLayoutParams;
    DrawCanvas mDraw;
    boolean touch_state = false;
    boolean mFirstTimeCheck = true;
    boolean touchexit=false;

    ArrayList<Point> mTopLeftArea,mTopRightArea,mBottomLeftArea,mBottomRightArea;

    Point mCenterPoint,mLeftPoint,mRightPoint,mTopPoint,mBottomPoint;

    float mStroke = 1f;
    float mStrokeInner = 6f;
    int mRadius = 30;
    
    public MoveLineClass(Context context,ViewGroup sViewGroup) {

        mContext = context;
        mLayout = sViewGroup;
        mLayoutParams = mLayout.getLayoutParams();
        mDraw = new DrawCanvas(mContext);

        mCenterPoint = new Point(497,mLayoutParams.height/2);
        mLeftPoint = new Point();
        mRightPoint = new Point();
        mTopPoint = new Point();
        mBottomPoint = new Point();
        mTopLeftArea = new ArrayList<>();
        mTopRightArea = new ArrayList<>();
        mBottomLeftArea = new ArrayList<>();
        mBottomRightArea = new ArrayList<>();

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(mStroke);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
//        mPaint2.setStrokeWidth(10f);
//        mPaint2.setColor(Color.BLACK);
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

    public void draw() {
        try {
            mLayout.removeView(mDraw);
        } catch (Exception e) { }
        mLayout.addView(mDraw);
    }

    private class DrawCanvas extends View implements View.OnTouchListener{

        int tMaxLeft,tMaxRight,tMaxTop,tMaxBottom;
        float mDistanceCenter, mDistanceLeft, mDistanceRight, mDistanceTop, mDistanceBottom;
        Rect mTopLeftAreaRect,mTopRightAreaRect,mBottomLeftAreaRect,mBottomRightAreaRect;

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

                mCenterPoint.set(getWidth() / 2, getHeight() / 2);
                mLeftPoint.set(tMaxLeft, getHeight() / 2);
                mRightPoint.set(tMaxRight, getHeight() / 2);
                mTopPoint.set(getWidth() / 2, tMaxTop);
                mBottomPoint.set(getWidth() / 2, tMaxBottom);

                mTopLeftAreaRect = new Rect(tMaxLeft, tMaxTop, mCenterPoint.x, mCenterPoint.y);
                mTopRightAreaRect = new Rect(mTopPoint.x, mTopPoint.y, mRightPoint.x, mRightPoint.y);
                mBottomLeftAreaRect = new Rect(mLeftPoint.x, mLeftPoint.y, mBottomPoint.x, mBottomPoint.y);
                mBottomRightAreaRect = new Rect(mCenterPoint.x, mCenterPoint.y, tMaxRight, tMaxBottom);

                mFirstTimeCheck = false;
            }


            if(!touch_state)
            {
                int Point = 5;
                if((int)(mPaint.getStrokeWidth()/2) > Point) {
                    Point = (int) (mPaint.getStrokeWidth() / 2);
                }else {
                    Point = (int) (mPaintInner.getStrokeWidth()/ 2);
                }
            Drawable myPic[] = {
                    getResources().getDrawable(R.drawable.boston),
                    getResources().getDrawable(R.drawable.carifornia),
                    getResources().getDrawable(R.drawable.dubai),
                    getResources().getDrawable(R.drawable.paris),
            };
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
            canvas.drawLine(mLeftPoint.x + Point,mTopPoint.y + Point,mTopPoint.x - Point, mTopPoint.y + Point,mPaintInner);
            canvas.drawLine(mTopPoint.x - Point,mTopPoint.y - Point,mCenterPoint.x - Point, mCenterPoint.y - Point,mPaintInner);
            canvas.drawLine(mCenterPoint.x - Point,mCenterPoint.y - Point,mLeftPoint.x + Point, mLeftPoint.y - Point,mPaintInner);
            canvas.drawLine(mLeftPoint.x + Point,mLeftPoint.y - Point,mLeftPoint.x + Point, mTopPoint.y + Point,mPaintInner);

            b = ((BitmapDrawable) myPic[1]).getBitmap();
            bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
            ImgA[0] = new Point(mTopPoint.x, mTopPoint.y);
            ImgA[1] = new Point(mRightPoint.x, mTopPoint.y);
            ImgA[2] = new Point(mRightPoint.x, mRightPoint.y);
            ImgA[3] = new Point(mCenterPoint.x, mCenterPoint.y);

            roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgA);
            canvas.drawBitmap(roundBitmap, 0, 0, null);
            canvas.drawLine(mTopPoint.x + Point,mTopPoint.y + Point,mRightPoint.x - Point, mTopPoint.y + Point,mPaintInner);
            canvas.drawLine(mRightPoint.x - Point,mTopPoint.y - Point,mRightPoint.x - Point, mRightPoint.y - Point,mPaintInner);
            canvas.drawLine(mRightPoint.x - Point,mRightPoint.y - Point,mCenterPoint.x + Point, mCenterPoint.y - Point,mPaintInner);
            canvas.drawLine(mCenterPoint.x + Point,mCenterPoint.y - Point,mTopPoint.x + Point, mTopPoint.y + Point,mPaintInner);

            b = ((BitmapDrawable) myPic[2]).getBitmap();
            bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
            ImgA[0] = new Point(mLeftPoint.x, mLeftPoint.y);
            ImgA[1] = new Point(mCenterPoint.x, mCenterPoint.y);
            ImgA[2] = new Point(mBottomPoint.x, mBottomPoint.y);
            ImgA[3] = new Point(mLeftPoint.x, mBottomPoint.y);
            roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgA);
            canvas.drawBitmap(roundBitmap, 0, 0, null);
            canvas.drawLine(mLeftPoint.x + Point,mLeftPoint.y + Point,mCenterPoint.x - Point, mCenterPoint.y + Point,mPaintInner);
            canvas.drawLine(mCenterPoint.x - Point,mCenterPoint.y - Point,mBottomPoint.x - Point, mBottomPoint.y - Point,mPaintInner);
            canvas.drawLine(mBottomPoint.x - Point,mBottomPoint.y - Point,mLeftPoint.x + Point, mBottomPoint.y - Point,mPaintInner);
            canvas.drawLine(mLeftPoint.x + Point,mBottomPoint.y - Point,mLeftPoint.x + Point, mLeftPoint.y + Point,mPaintInner);

            b = ((BitmapDrawable) myPic[3]).getBitmap();
            bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
            ImgA[0] = new Point(mCenterPoint.x, mCenterPoint.y);
            ImgA[1] = new Point(mRightPoint.x, mRightPoint.y);
            ImgA[2] = new Point(mRightPoint.x, mBottomPoint.y);
            ImgA[3] = new Point(mBottomPoint.x, mBottomPoint.y);

            roundBitmap = getRoundedCroppedBitmap(bitmap, w, ImgA);
            canvas.drawLine(mCenterPoint.x + Point,mCenterPoint.y + Point,mRightPoint.x - Point, mRightPoint.y + Point,mPaintInner);
            canvas.drawLine(mRightPoint.x - Point,mRightPoint.y - Point,mRightPoint.x - Point, mBottomPoint.y - Point,mPaintInner);
            canvas.drawLine(mRightPoint.x - Point,mBottomPoint.y - Point,mBottomPoint.x + Point, mBottomPoint.y - Point,mPaintInner);
            canvas.drawLine(mBottomPoint.x + Point,mBottomPoint.y - Point,mCenterPoint.x + Point, mCenterPoint.y + Point,mPaintInner);
            canvas.drawBitmap(roundBitmap, 0, 0, null);
            //InnerBroder

        }

            //topleftarea
            canvas.drawRect(mTopLeftAreaRect, mPaint3);
            //toprightarea
            canvas.drawRect(mTopRightAreaRect, mPaint4);
            //bottomleftarea
            canvas.drawRect(mBottomLeftAreaRect, mPaint5);
            //bottomrightarea
            canvas.drawRect(mBottomRightAreaRect, mPaint6);
            //frame
            canvas.drawRect(tMaxLeft, tMaxTop, tMaxRight, tMaxBottom, mPaint);
            //top
            canvas.drawLine(mTopPoint.x, mTopPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint);
            //left
            canvas.drawLine(mLeftPoint.x, mLeftPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint);
            //right
            canvas.drawLine(mRightPoint.x, mRightPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint);
            //bottom
            canvas.drawLine(mBottomPoint.x, mBottomPoint.y, mCenterPoint.x, mCenterPoint.y, mPaint);
            //center
            canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mRadius, mPaint2);
            //left
            canvas.drawCircle(mLeftPoint.x, mLeftPoint.y, mRadius, mPaint2);
            //right
            canvas.drawCircle(mRightPoint.x, mRightPoint.y, mRadius, mPaint2);
            //top
            canvas.drawCircle(mTopPoint.x, mTopPoint.y, mRadius, mPaint2);
            //bottom
            canvas.drawCircle(mBottomPoint.x, mBottomPoint.y, mRadius, mPaint2);




        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int tXPoint,tYPoint;
            tXPoint = (int)motionEvent.getX();
            tYPoint = (int)motionEvent.getY();
            touchexit=false;
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
            Log.d("PARAM4", "" + tXPoint + " " + tYPoint +" D1 "+mDistanceCenter
                    +" D2 "+mDistanceLeft+" D3 "+mDistanceRight+" D4 "+mDistanceTop+" D5 "+mDistanceBottom);

            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (mDistanceCenter <= mRadius){
                    mCenterPoint.set(tXPoint,tYPoint);
                    Log.i("PARAM1",""+tXPoint+" "+tYPoint+" D1 "+mDistanceCenter);
                    invalidate();
//                draw();
                    touch_state = true;
                } else if (mDistanceLeft <= mRadius){
                    mLeftPoint.set(tMaxLeft,tYPoint);
                    Log.i("PARAMLEFT",""+tXPoint+" "+tYPoint+" D2 "+mDistanceLeft);
                    invalidate();
//                draw();
                    touch_state = true;
                } else if (mDistanceRight <= mRadius){
                    mRightPoint.set(tMaxRight,tYPoint);
                    Log.i("PARAMRIGHT",""+tXPoint+" "+tYPoint+" D3 "+mDistanceRight);
                    invalidate();
//                draw();
                    touch_state = true;
                } else if (mDistanceTop <= mRadius){
                    mTopPoint.set(tXPoint,tMaxTop);
                    Log.i("PARAMTOP",""+tXPoint+" "+tYPoint+" D4 "+mDistanceTop);
                    invalidate();
//                draw();
                    touch_state = true;
                } else if (mDistanceBottom <= mRadius){
                    mBottomPoint.set(tXPoint,tMaxBottom);
                    Log.i("PARAMBOTTOM",""+tXPoint+" "+tYPoint+" D5 "+mDistanceBottom);
                    invalidate();
//                draw();
                    touch_state = true;
                }
                setPointAreaArrayList();

            } else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE && touch_state) {
                if (mDistanceCenter <= mRadius){
                    mCenterPoint.set(tXPoint,tYPoint);
                    Log.i("PARAM1",""+tXPoint+" "+tYPoint+" D1 "+mDistanceCenter);
                    invalidate();
//                draw();
                    touch_state = true;
                } else if (mDistanceLeft <= mRadius){
                    mLeftPoint.set(tMaxLeft,tYPoint);
                    Log.i("PARAMLEFT",""+tXPoint+" "+tYPoint+" D2 "+mDistanceLeft);
                    invalidate();
//                draw();
                    touch_state = true;
                } else if (mDistanceRight <= mRadius){
                    mRightPoint.set(tMaxRight,tYPoint);
                    Log.i("PARAMRIGHT",""+tXPoint+" "+tYPoint+" D3 "+mDistanceRight);
                    invalidate();
//                draw();
                    touch_state = true;
                } else if (mDistanceTop <= mRadius){
                    mTopPoint.set(tXPoint,tMaxTop);
                    Log.i("PARAMTOP",""+tXPoint+" "+tYPoint+" D4 "+mDistanceTop);
                    invalidate();
//                draw();
                    touch_state = true;
                } else if (mDistanceBottom <= mRadius){
                    mBottomPoint.set(tXPoint,tMaxBottom);
                    Log.i("PARAMBOTTOM",""+tXPoint+" "+tYPoint+" D5 "+mDistanceBottom);
                    invalidate();
//                draw();
                    touch_state = true;
                }
                setPointAreaArrayList();

            } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
//            mLayout.removeView(mDraw);
                touch_state = false;
                touchexit = true;
            }
            else if(motionEvent.getAction()==MotionEvent.ACTION_HOVER_MOVE)
            {
                touchexit = true;
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
    }
}
