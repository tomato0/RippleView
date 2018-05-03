package com.gionee.mycustomview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Author: wsq
 * Date: 18-2-7
 * Email: wangshaoqiang@gionee.com
 * function: sin曲线，水波浪
 * y = ASin(ωx + φ) + k
 */

public class RippleView extends View {

    private static final double ONE_CYCLE = 2 * Math.PI;
    private static final float PAINT_STROKE_WIDTH = 1;
    private static final long TIME_UNIT_SECONDS = 1000;
    private static final int DEFAULT_FLOW_SECONDS = 4;
    private static final int DEFAULT_RIPPLE_HEIGHT = 20;
    private static final int DEFAULT_RIPPLE_AMOUNT = 1;
    private Paint mPaint;
    private float mMove;
    private double mY;
    private Path mPath;
    /*涟漪的高度*/
    private int mRippleHeight;
    /*水平面高度*/
    private int mLevelHeight;
    /*涟漪的个数*/
    private int mRippleAmount;
    /*x = 0 到 x = getWidth 移动时间*/
    private int mFlowSeconds;
    /*水波偏移 0~2之间*/
    private float mRippleOffset;
    /*水波颜色*/
    private int mRippleColor;
    private ValueAnimator mAnim;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView);
        mRippleHeight = typedArray.getDimensionPixelSize(R.styleable.RippleView_ripple_height,
                DEFAULT_RIPPLE_HEIGHT);
        mLevelHeight = typedArray.getDimensionPixelSize(R.styleable.RippleView_level_height, 0);
        mRippleAmount = typedArray.getInt(R.styleable.RippleView_ripple_amount, DEFAULT_RIPPLE_AMOUNT);
        mRippleColor = typedArray.getColor(R.styleable.RippleView_ripple_color, Color.YELLOW);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(PAINT_STROKE_WIDTH);
        mPaint.setColor(mRippleColor);
        mPath = new Path();
        mFlowSeconds = DEFAULT_FLOW_SECONDS;
    }

    public void setRippleHeight(int rippleHeight) {
        mRippleHeight = rippleHeight;
    }

    public void setLevelHeight(int levelHeight) {
        mLevelHeight = levelHeight;
    }

    public void setRippleAmount(int rippleAmount) {
        mRippleAmount = rippleAmount;
    }

    public void setFlowSeconds(int flowSeconds) {
        mFlowSeconds = flowSeconds;
    }

    public void setRippleOffset(float rippleOffset) {
        mRippleOffset = rippleOffset;
    }

    public void setRippleColor(int rippleColor) {
        mRippleColor = rippleColor;
        mPaint.setColor(mRippleColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0, getHeight());
        for (float x = 0; x <= getWidth(); x++) {
            mY = mRippleHeight * Math.sin(mRippleAmount * ONE_CYCLE * x / getWidth() -
                    mMove) + mLevelHeight;
            mPath.lineTo(x, (float) (getHeight() - mY));
        }
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    public void startFlowAnimator() {
        ValueAnimator mAnim = new ValueAnimator();
        mAnim.setFloatValues(mMove + (float) (Math.PI * mRippleOffset),
                (float) (mRippleAmount * ONE_CYCLE + Math.PI * mRippleOffset));
        mAnim.setInterpolator(new LinearInterpolator());
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMove = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnim.setDuration(mFlowSeconds * TIME_UNIT_SECONDS);
        mAnim.setRepeatCount(ValueAnimator.INFINITE);
        mAnim.start();
    }
}
