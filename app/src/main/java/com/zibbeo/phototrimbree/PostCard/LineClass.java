package com.zibbeo.phototrimbree.PostCard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by samchaw on 5/3/16 AD.
 */
public class LineClass extends View {

    Paint mPaint = new Paint();
    Context mContext;
    ViewGroup mLayout;
    ViewGroup.LayoutParams mLayoutParams;

    float mX = 0,mY = 0;

    public LineClass(Context context,ViewGroup sViewGroup) {
        super(context);

        mContext = context;
        mLayout = sViewGroup;
        mLayoutParams = mLayout.getLayoutParams();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);


    }

    public void drawLine(MotionEvent arg1) {

        if(arg1.getAction() == MotionEvent.ACTION_DOWN) {
            mX = arg1.getX();
            mY = arg1.getY();
//            draw();

        } else if(arg1.getAction() == MotionEvent.ACTION_MOVE) {

            mX = arg1.getX();
            mY = arg1.getY();
//            draw();

        } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
//            mLayout.removeView(draw);

        }
    }

//    private void draw() {
//        try {
//            mLayout.removeView(draw);
//        } catch (Exception e) { }
//        mLayout.addView(draw);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i("PARAM",""+mLayoutParams.height+" "+getHeight());

        canvas.drawLine(10, 10, getWidth()-20, getHeight()-10, mPaint);
        canvas.drawPoint(mX,mY,mPaint);
//        canvas.drawLine(20, 0, 0, 20, mPaint);
    }
}
