package com.gionee.mycustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Author: wsq
 * Date: 18-2-6
 * Email: wangshaoqiang@gionee.com
 * function:渐变色控件
 */

public class LinearGradientView extends View {
    private Paint mPaint;
    private float mStartX;
    private float mStartY;
    private float mEndX;
    private float mEndY;
    private int[] mColors;
    private LinearGradient mLinearGradient;
    private static final String TAG = "LinearGradientView";
    public LinearGradientView(Context context) {
        this(context, null, 0);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinearGradientView);
        mStartX = typedArray.getFloat(R.styleable.LinearGradientView_startX, 0);
        mStartY = typedArray.getFloat(R.styleable.LinearGradientView_startY, 0);
        mEndX = typedArray.getFloat(R.styleable.LinearGradientView_endX, 0);
        mEndY = typedArray.getFloat(R.styleable.LinearGradientView_endY, 0);
        int startColor = typedArray.getColor(R.styleable.LinearGradientView_startColor, Color.parseColor("#00000000"));
        int endColor = typedArray.getColor(R.styleable.LinearGradientView_endColor, Color.parseColor("#00000000"));
        mColors = new int[]{startColor, endColor};
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"onDraw");
        super.onDraw(canvas);
        if (null == mLinearGradient) {
            mLinearGradient = new LinearGradient(getMeasuredWidth() * mStartX,
                    getMeasuredHeight() * mStartY, getMeasuredWidth() * mEndX,
                    getMeasuredHeight() * mEndY, mColors, null, Shader.TileMode.CLAMP);
            mPaint.setShader(mLinearGradient);
        }
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
    }

   public void setShaderRange(float startX, float startY, float endX, float endY) {
       mStartX = startX;
       mStartY = startY;
       mEndX = endX;
       mEndY = endY;
       refresh();
   }

   public void setColors(int[] colors) {
        if (colors.length < 2) {
            throw new IllegalArgumentException("colors length must >= 2");
        }
        mColors = colors;
        refresh();
   }

    private void refresh(){
        if (null != mLinearGradient) {
            mLinearGradient = null;
        }
        invalidate();
    }
}
