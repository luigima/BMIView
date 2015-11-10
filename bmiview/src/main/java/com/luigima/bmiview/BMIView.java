package com.luigima.bmiview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class BMIView extends View {

    enum BodyCategory {
        VERY_SEVERELY_UNDERWEIGHT,
        SEVERELY_UNDERWEIGHT,
        UNDERWEIGHT,
        NORMAL,
        OVERWEIGHT,
        OBESE_CLASS_1,
        OBESE_CLASS_2,
        OBESE_CLASS_3
    }

    class Body {
        public BodyCategory bodyCategory;
        public int color;
        public String text;
        public float value_male, value_female;

        Body(BodyCategory bodyCategory, int color, String text, float value_male, float value_female) {
            this.bodyCategory = bodyCategory;
            this.color = color;
            this.text = text;
            this.value_male = value_male;
            this.value_female = value_female;
        }
    }

    private Boolean mShowText;
    private int mMin = 3, mMax = 42,
            mWidth = 600, mHeight = 125;
    private float mValue = 26.5f;
    private BodyCategory bodyCategory;
    private ArrayList<Body> bodyList = new ArrayList<Body>();

    // Graphics
    protected int colorNeutral = Color.parseColor("#212121"),
            colorNeutral2 = Color.parseColor("#727272");
    private int mFontSize = 10;
    private Paint mPaint;

    public BMIView(Context context) {
        super(context);
    }

    public BMIView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BMIView,
                0, 0);
        try {
            mShowText = a.getBoolean(R.styleable.BMIView_showTextMsg, true);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(colorNeutral2);

        // Scale the desired text size to match screen density
        mPaint.setTextSize(mFontSize * getResources().getDisplayMetrics().density);
        mPaint.setStrokeWidth(2f);
        setPadding(5, 5, 5, 5);

        bodyList.add(new Body(BodyCategory.VERY_SEVERELY_UNDERWEIGHT, Color.parseColor("#ff6f69"), getResources().getString(R.string.VERY_SEVERELY_UNDERWEIGHT), mMin, mMin));
        bodyList.add(new Body(BodyCategory.SEVERELY_UNDERWEIGHT, Color.parseColor("#ffcc5c"), getResources().getString(R.string.SEVERELY_UNDERWEIGHT), 16, 16));
        bodyList.add(new Body(BodyCategory.UNDERWEIGHT, Color.parseColor("#ffeead"), getResources().getString(R.string.UNDERWEIGHT), 17, 17));
        bodyList.add(new Body(BodyCategory.NORMAL, Color.parseColor("#88d8b0"), getResources().getString(R.string.NORMAL), 18.5f, 18.5f));
        bodyList.add(new Body(BodyCategory.OVERWEIGHT, Color.parseColor("#ffeead"), getResources().getString(R.string.OVERWEIGHT), 25, 25));
        bodyList.add(new Body(BodyCategory.OBESE_CLASS_1, Color.parseColor("#ffcc5c"), getResources().getString(R.string.OBESE_CLASS_1), 30, 30));
        bodyList.add(new Body(BodyCategory.OBESE_CLASS_2, Color.parseColor("#ff6f69"), getResources().getString(R.string.OBESE_CLASS_2), 35, 35));
        bodyList.add(new Body(BodyCategory.OBESE_CLASS_3, Color.parseColor("#ff6f69"), getResources().getString(R.string.OBESE_CLASS_3), 40, 40));

        bodyCategory = BodyCategory.NORMAL;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
            mWidth = width;
            Log.d("BMIView", "Mode: MeasureSpec.EXACTLY " + widthSize);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = MeasureSpec.getSize(widthMeasureSpec);
            mWidth = width;
            Log.d("BMIView", "Mode: MeasureSpec.AT_MOST");
        } else {
            //Be whatever you want
            width = mWidth;
            Log.d("BMIView", "Mode: else");
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(mHeight, heightSize);
        } else {
            //Be whatever you want
            height = mHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String stringValue = findBody(bodyCategory).text;
        float textWidth = mPaint.measureText(stringValue);
        float textHeight = mFontSize * getResources().getDisplayMetrics().density;
        Paint.FontMetrics fm = mPaint.getFontMetrics();


        int topOfBar = getPaddingTop() + 25;
        int botOfBar = getPaddingTop() + 75;
        int leftSideOfBar = 0;//(int) (mWidth*0.10f);
        int rightSideOfBar = mWidth;//(int) (mWidth*0.90f);
        float midOfBar = rightSideOfBar * 0.5f;

        float valueOfBar = rightSideOfBar;
        if (mValue <= mMax) {
            // - mMin moves the value to the left. The min value should be at the left border
            valueOfBar = rightSideOfBar * ((mValue - mMin) / (float) (mMax - mMin));
        }

        Paint.Style oldStyle = mPaint.getStyle();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // Draws the categories
        for (int i = 0; i < bodyList.size() - 1; i++) {
            mPaint.setColor(bodyList.get(i).color);
            float bodyValue1 = rightSideOfBar * ((bodyList.get(i).value_male - mMin) / (float) (mMax - mMin));
            float bodyValue2 = rightSideOfBar * ((bodyList.get(i + 1).value_male - mMin) / (float) (mMax - mMin));
            canvas.drawRect(bodyValue1, topOfBar, bodyValue2, botOfBar, mPaint);
        }
        //Draws the last category
        mPaint.setColor(bodyList.get(bodyList.size() - 1).color);
        float bodyValueOfBar = rightSideOfBar * ((bodyList.get(bodyList.size() - 1).value_male - mMin) / (float) (mMax - mMin));
        canvas.drawRect(bodyValueOfBar, topOfBar, rightSideOfBar, botOfBar, mPaint);

        mPaint.setShader(null);
        mPaint.setColor(colorNeutral2);

        // Draws the marker
        canvas.drawLine(valueOfBar, topOfBar, valueOfBar, botOfBar, mPaint);

        //Draws the arrows
        Path path = new Path();
        path.moveTo(valueOfBar, botOfBar);
        path.lineTo(valueOfBar - 12, botOfBar + 12);
        path.lineTo(valueOfBar + 12, botOfBar + 12);
        path.lineTo(valueOfBar, botOfBar);
        path.moveTo(valueOfBar, topOfBar);
        path.lineTo(valueOfBar - 12, topOfBar - 12);
        path.lineTo(valueOfBar + 12, topOfBar - 12);
        path.lineTo(valueOfBar, topOfBar);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, mPaint);
        float botOfArrow = botOfBar + 20;


        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);

        //Draws the text
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaint.setColor(colorNeutral);
        //scanvas.drawText(stringValue, leftSideOfBar, topOfBar, mPaint);
        mPaint.setStyle(oldStyle);

    }

    private void calculateBodyCategory(float mValue) {
        for (Body b : bodyList) {
            if (b.value_male <= mValue) {
                bodyCategory = b.bodyCategory;

            }
        }
    }

    public boolean isShowText() {
        return mShowText;
    }

    public float getValue() {
        return mValue;
    }

    public void setBodyCategory(BodyCategory bodyCategory) {
        this.bodyCategory = bodyCategory;
    }

    public String getBodyDescription() {
        return findBody(bodyCategory).text;
    }

    public void setValue(float mValue) {
        if (mValue < mMin) {
            this.mValue = mMin;
        } else {
            this.mValue = mValue;
        }


        calculateBodyCategory(this.mValue);
        invalidate();
    }

    private Body findBody(BodyCategory bodyCategory) {
        for (Body b : bodyList) {
            if (b.bodyCategory == bodyCategory) {
                return b;
            }
        }
        return bodyList.get(0);
    }

    public void setShowText(boolean showText) {
        mShowText = showText;
        invalidate();
        requestLayout();
    }

}