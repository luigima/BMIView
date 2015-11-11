package com.luigima.bmiview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class BMIView extends View {

    private final static int VERY_SEVERELY_UNDERWEIGHT = 1;
    private final static int SEVERELY_UNDERWEIGHT = 2;
    private final static int UNDERWEIGHT = 3;
    private final static int NORMAL = 4;
    private final static int OVERWEIGHT = 5;
    private final static int OBESE_CLASS_1 = 6;
    private final static int OBESE_CLASS_2 = 7;
    private final static int OBESE_CLASS_3 = 8;

    private boolean showText = true;
    private int mMin = 3, mMax = 42,
            mWidth = 600, mHeight = 125;
    protected int colorNeutral = Color.parseColor("#212121"),
            colorNeutral2 = Color.parseColor("#727272");
    private int mFontSize = 10;
    private Paint mPaint;
    private int currentBodyCategory;
    private ArrayList<BodyCategory> bodyCategoryList;
    private float bmiValue = 0f;
    private float weight = 0;
    private float height = 0;

    // 0 = men, 1 = women
    private int gender = 0;

    public BMIView(Context context) {
        super(context);
        //initPainting();
        initBodyCategories();
    }

    public BMIView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Apply all attributes
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BMIView,
                0, 0);
        try {
            showText = a.getBoolean(R.styleable.BMIView_showBmiText, true);
        } finally {
            a.recycle();
        }
        initPainting();
        initBodyCategories();
    }

    private void initPainting() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(colorNeutral2);

        // Scale the desired text size to match screen density
        mPaint.setTextSize(mFontSize * getResources().getDisplayMetrics().density);
        mPaint.setStrokeWidth(2f);
        setPadding(5, 5, 5, 5);
    }

    private void initBodyCategories() {
        bodyCategoryList = new ArrayList<>();

        bodyCategoryList.add(new BodyCategory(VERY_SEVERELY_UNDERWEIGHT, Color.parseColor("#ff6f69"), getResources().getString(R.string.VERY_SEVERELY_UNDERWEIGHT), mMin, mMin));
        bodyCategoryList.add(new BodyCategory(SEVERELY_UNDERWEIGHT, Color.parseColor("#ffcc5c"), getResources().getString(R.string.SEVERELY_UNDERWEIGHT), 16, 16));
        bodyCategoryList.add(new BodyCategory(UNDERWEIGHT, Color.parseColor("#ffeead"), getResources().getString(R.string.UNDERWEIGHT), 17, 17));
        bodyCategoryList.add(new BodyCategory(NORMAL, Color.parseColor("#88d8b0"), getResources().getString(R.string.NORMAL), 18.5f, 18.5f));
        bodyCategoryList.add(new BodyCategory(OVERWEIGHT, Color.parseColor("#ffeead"), getResources().getString(R.string.OVERWEIGHT), 25, 25));
        bodyCategoryList.add(new BodyCategory(OBESE_CLASS_1, Color.parseColor("#ffcc5c"), getResources().getString(R.string.OBESE_CLASS_1), 30, 30));
        bodyCategoryList.add(new BodyCategory(OBESE_CLASS_2, Color.parseColor("#ff6f69"), getResources().getString(R.string.OBESE_CLASS_2), 35, 35));
        bodyCategoryList.add(new BodyCategory(OBESE_CLASS_3, Color.parseColor("#ff6f69"), getResources().getString(R.string.OBESE_CLASS_3), 40, 40));

        currentBodyCategory = NORMAL;
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
            width = widthSize;
            mWidth = width;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = MeasureSpec.getSize(widthMeasureSpec);
            mWidth = width;
        } else {
            width = mWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(mHeight, heightSize);
        } else {
            height = mHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String stringValue = getBodyCategory(currentBodyCategory).text;
        Paint.FontMetrics fm = mPaint.getFontMetrics();


        int topOfBar = getPaddingTop() + 25;
        int botOfBar = getPaddingTop() + 75;
        int rightSideOfBar = mWidth;

        float valueOfBar = rightSideOfBar;
        if (bmiValue <= mMax) {
            // - mMin moves the value to the left. The min value should be at the left border
            valueOfBar = rightSideOfBar * ((bmiValue - mMin) / (float) (mMax - mMin));
        }

        Paint.Style oldStyle = mPaint.getStyle();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // Draws the categories
        for (int i = 0; i < bodyCategoryList.size() - 1; i++) {
            mPaint.setColor(bodyCategoryList.get(i).color);
            float bodyValue1 = rightSideOfBar * ((bodyCategoryList.get(i).getLimit(gender) - mMin) / (float) (mMax - mMin));
            float bodyValue2 = rightSideOfBar * ((bodyCategoryList.get(i + 1).getLimit(gender) - mMin) / (float) (mMax - mMin));
            canvas.drawRect(bodyValue1, topOfBar, bodyValue2, botOfBar, mPaint);
        }
        //Draws the last category
        mPaint.setColor(bodyCategoryList.get(bodyCategoryList.size() - 1).color);
        float bodyValueOfBar = rightSideOfBar * ((bodyCategoryList.get(bodyCategoryList.size() - 1).getLimit(gender) - mMin) / (float) (mMax - mMin));
        canvas.drawRect(bodyValueOfBar, topOfBar, rightSideOfBar, botOfBar, mPaint);

        mPaint.setShader(null);
        mPaint.setColor(colorNeutral2);

        // Draws the marker
        canvas.drawLine(valueOfBar, topOfBar, valueOfBar, botOfBar, mPaint);

        //Draws the arrows
        //TODO remove allocation
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


        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);

        //Draws the text
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaint.setColor(colorNeutral);
        //scanvas.drawText(stringValue, leftSideOfBar, topOfBar, mPaint);
        mPaint.setStyle(oldStyle);

    }

    private int calculateBodyCategory(float mValue) {
        int category = 1;
        for (BodyCategory b : bodyCategoryList) {
            if (b.getLimit(gender) <= mValue) {
                category = b.bodyCategory;
            }
        }
        return category;
    }

    @Override
    public void invalidate() {
        currentBodyCategory = calculateBodyCategory(this.bmiValue);
        if (height == 0f) {
            bmiValue = 0;
        } else {
            bmiValue = weight / (height * height);
        }

        if (bmiValue < mMin) {
            this.bmiValue = mMin;
        }

        super.invalidate();
    }

    public boolean isShowText() {
        return showText;
    }

    public String getBodyDescription() {
        return getBodyCategory(currentBodyCategory).text;
    }


    public BMIView setHeight(float height) {
        this.height = height;
        invalidate();
        return this;
    }

    public BMIView setWeight(float weight) {
        this.weight = weight;
        invalidate();
        return this;
    }

    public float getWeight() {
        return weight;
    }

    public float getBmiValue() {
        return bmiValue;
    }

    /**
     * Returns the current gender
     * 0 = men, 1 = women
     *
     * @return bmi category limit
     */
    public int getGender() {
        return gender;
    }

    /**
     * Sets the gender
     *
     * @param gender 0 = men, 1 = women
     */
    public BMIView setGender(int gender) {
        this.gender = gender;
        invalidate();
        return this;
    }

    private BodyCategory getBodyCategory(int bodyCategory) {
        for (BodyCategory b : bodyCategoryList) {
            if (b.bodyCategory == bodyCategory) {
                return b;
            }
        }
        return bodyCategoryList.get(0);
    }

    public BMIView setShowText(boolean showText) {
        this.showText = showText;
        invalidate();
        requestLayout();

        return this;
    }

}