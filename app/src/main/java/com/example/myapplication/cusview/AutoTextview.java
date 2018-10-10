package com.example.myapplication.cusview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import java.util.ArrayList;

public class AutoTextview extends TextView {
    private int mWidth,mHeight;
    private Paint mPaint;
    private Rect mTextBound;
    private ArrayList<String> mStrings=new ArrayList<>();
    private int delayTime=1000,animalTime=1000;
    private int currentposition=0;
    private float scrollY=0;
    private String TAG="AutoTextview";
    private Handler mHandler=new Handler();

    public AutoTextview(Context context) {
        super(context);
        Init();
    }

    public AutoTextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public AutoTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }

    private void Init(){
        mStrings.add("加载中...");
        mTextBound=getBounds();
    }


    public void InitContents(ArrayList<String> strings){
        if (strings!=null && strings.size()>0){
            this.mStrings=strings;
        }
        startAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        /**
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            Log.e("xxx", "EXACTLY");
            mWidth = specSize;
        } else
        {
            // 由字体决定的宽
            int desire = getPaddingLeft() + getPaddingRight() + mTextBound.width();
            mWidth=desire;
            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mWidth = Math.min(desire, specSize);
                Log.e("xxx", "AT_MOST");
            }
        }

        /***
         * 设置高度
         */

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mHeight = specSize;
        } else
        {
            int desire = (int) (getPaddingTop() + getPaddingBottom() +  mTextBound.height()*1.5);
            mHeight=desire;
            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(desire, specSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(0,-scrollY);
        canvas.drawText(mStrings.get(currentposition), getPaddingLeft(), mHeight - (mHeight-mTextBound.height())/2, mPaint);
        canvas.drawText(mStrings.get((currentposition+1)%mStrings.size()),  getPaddingLeft(), 2*mHeight - (mHeight-mTextBound.height())/2, mPaint);
        postInvalidate();
    }


    public void startAnimation(){
        ValueAnimator valueAnimator=new ValueAnimator();
        valueAnimator.setFloatValues(0,getMeasuredHeight());
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(animalTime);
        valueAnimator.setRepeatCount(0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scrollY= (float) animation.getAnimatedValue();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG,"onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                currentposition= (currentposition+1)%mStrings.size();
                scrollY=0;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startAnimation();
                    }
                },delayTime);
                Log.e(TAG,"onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();

    }


    public Rect getBounds(){
        Rect mTextBound;
        mTextBound = new Rect();
        mPaint = getPaint();
        mPaint.getTextBounds(mStrings.get(0),0,mStrings.get(0).length(), mTextBound);
        return mTextBound;
    }


}
