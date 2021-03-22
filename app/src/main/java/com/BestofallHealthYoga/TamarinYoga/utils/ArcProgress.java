package com.BestofallHealthYoga.TamarinYoga.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.BestofallHealthYoga.TamarinYoga.R;

public class ArcProgress extends View {
    private static final String INSTANCE_ARC_ANGLE = "arc_angle";
    private static final String INSTANCE_BOTTOM_TEXT = "bottom_text";
    private static final String INSTANCE_BOTTOM_TEXT_SIZE = "bottom_text_size";
    private static final String INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_STROKE_WIDTH = "stroke_width";
    private static final String INSTANCE_SUFFIX = "suffix";
    private static final String INSTANCE_SUFFIX_TEXT_PADDING = "suffix_text_padding";
    private static final String INSTANCE_SUFFIX_TEXT_SIZE = "suffix_text_size";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color";
    private float arcAngle;
    private float arcBottomHeight;
    private String bottomText;
    private float bottomTextSize;
    final float defaultarcangle;
    private final float defaultbottomtextsize;
    final int defaultfinishedcolor;
     final int defaultmax;
    private final float defaultstrokewidth;
    private final float defaultsuffixpadding;
    private final String defaultsuffixtext;
    private final float defaultsuffixtextsize;
    private final int defaulttextcolor;
    private float defaulttextsize;
    private final int defaultunfinishedcolor;
    private int finishedStrokeColor;
    private int max;
    private final int minsize;
    private Paint paint;
    private int progress;
    private RectF rectF;
    private float strokeWidth;
    private String suffixText;
    private float suffixTextPadding;
    private float suffixTextSize;
    private int textColor;
    protected Paint textPaint;
    private float textSize;
    Typeface typeface;
    private int unfinishedStrokeColor;

    public ArcProgress(Context context) {
        this(context, (AttributeSet) null);
    }

    public ArcProgress(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ArcProgress(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.rectF = new RectF();
        this.progress = 0;
        this.suffixText = "٪";
        this.defaultfinishedcolor = -1;
        this.defaultunfinishedcolor = Color.rgb(72, 106, 176);
        this.defaulttextcolor = Color.rgb(66, 145, 241);
        this.defaultmax = 100;
        this.defaultarcangle = 288.0f;
        this.defaulttextsize = Utils.sp2px(getResources(), 18.0f);
        this.minsize = (int) Utils.dp2px(getResources(), 100.0f);
        this.defaulttextsize = Utils.sp2px(getResources(), 40.0f);
        this.defaultsuffixtextsize = Utils.sp2px(getResources(), 15.0f);
        this.defaultsuffixpadding = Utils.dp2px(getResources(), 4.0f);
        this.defaultsuffixtext = "٪";
        this.defaultbottomtextsize = Utils.sp2px(getResources(), 10.0f);
        this.defaultstrokewidth = Utils.dp2px(getResources(), 4.0f);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ArcProgress, i, 0);
        initByAttributes(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        initPainters();
    }


    public void initByAttributes(TypedArray typedArray) {
        this.finishedStrokeColor = typedArray.getColor(3, -1);
        this.unfinishedStrokeColor = typedArray.getColor(12, this.defaultunfinishedcolor);
        this.textColor = typedArray.getColor(10, this.defaulttextcolor);
        this.textSize = typedArray.getDimension(11, this.defaulttextsize);
        this.arcAngle = typedArray.getFloat(0, 288.0f);
        setMax(typedArray.getInt(4, 100));
        setProgress(typedArray.getInt(5, 0));
        this.strokeWidth = typedArray.getDimension(6, this.defaultstrokewidth);
        this.suffixTextSize = typedArray.getDimension(9, this.defaultsuffixtextsize);
        this.suffixText = TextUtils.isEmpty(typedArray.getString(7)) ? this.defaultsuffixtext : typedArray.getString(7);
        this.suffixTextPadding = typedArray.getDimension(8, this.defaultsuffixpadding);
        this.bottomTextSize = typedArray.getDimension(2, this.defaultbottomtextsize);
        this.bottomText = typedArray.getString(1);
    }


    public void initPainters() {
        this.textPaint = new TextPaint();
        this.textPaint.setColor(this.textColor);
        this.textPaint.setTextSize(this.textSize);
        this.textPaint.setAntiAlias(true);
        this.paint = new Paint();
        this.typeface = ResourcesCompat.getFont(getContext(), R.font.regular);
        this.textPaint.setTypeface(this.typeface);
        this.paint.setColor(this.defaultunfinishedcolor);
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(this.strokeWidth);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    public float getStrokeWidth() {
        return this.strokeWidth;
    }

    public void setStrokeWidth(float f) {
        this.strokeWidth = f;
        invalidate();
    }

    public float getSuffixTextSize() {
        return this.suffixTextSize;
    }

    public void setSuffixTextSize(float f) {
        this.suffixTextSize = f;
        invalidate();
    }

    public String getBottomText() {
        return this.bottomText;
    }

    public void setBottomText(String str) {
        this.bottomText = str;
        invalidate();
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int i) {
        this.progress = i;
        if (this.progress > getMax()) {
            this.progress %= getMax();
        }
        invalidate();
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int i) {
        if (i > 0) {
            this.max = i;
            invalidate();
        }
    }

    public float getBottomTextSize() {
        return this.bottomTextSize;
    }

    public void setBottomTextSize(float f) {
        this.bottomTextSize = f;
        invalidate();
    }

    public float getTextSize() {
        return this.textSize;
    }

    public void setTextSize(float f) {
        this.textSize = f;
        invalidate();
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int i) {
        this.textColor = i;
        invalidate();
    }

    public int getFinishedStrokeColor() {
        return this.finishedStrokeColor;
    }

    public void setFinishedStrokeColor(int i) {
        this.finishedStrokeColor = i;
        invalidate();
    }

    public int getUnfinishedStrokeColor() {
        return this.unfinishedStrokeColor;
    }

    public void setUnfinishedStrokeColor(int i) {
        this.unfinishedStrokeColor = i;
        invalidate();
    }

    public float getArcAngle() {
        return this.arcAngle;
    }

    public void setArcAngle(float f) {
        this.arcAngle = f;
        invalidate();
    }

    public String getSuffixText() {
        return this.suffixText;
    }

    public void setSuffixText(String str) {
        this.suffixText = str;
        invalidate();
    }

    public float getSuffixTextPadding() {
        return this.suffixTextPadding;
    }

    public void setSuffixTextPadding(float f) {
        this.suffixTextPadding = f;
        invalidate();
    }

    @Override
    public int getSuggestedMinimumHeight() {
        return this.minsize;
    }

    @Override
    public int getSuggestedMinimumWidth() {
        return this.minsize;
    }

    @Override
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(i, i2);
        int size = MeasureSpec.getSize(i);
        RectF rectF2 = this.rectF;
        float f = this.strokeWidth;
        float f2 = (float) size;
        rectF2.set(f / 2.0f, f / 2.0f, f2 - (f / 2.0f), ((float) MeasureSpec.getSize(i2)) - (this.strokeWidth / 2.0f));
        double d = (double) (((360.0f - this.arcAngle) / 2.0f) / 180.0f);
        Double.isNaN(d);
        this.arcBottomHeight = (f2 / 2.0f) * ((float) (1.0d - Math.cos(d * 3.141592653589793d)));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f = 270.0f - (this.arcAngle / 2.0f);
        float max2 = (((float) this.progress) / ((float) getMax())) * this.arcAngle;
        float f2 = this.progress == 0 ? 0.01f : f;
        this.paint.setColor(this.unfinishedStrokeColor);
        canvas.drawArc(this.rectF, f, this.arcAngle, false, this.paint);
        this.paint.setColor(this.finishedStrokeColor);
        canvas.drawArc(this.rectF, f2, max2, false, this.paint);
        String valueOf = String.valueOf(getProgress());
        if (!TextUtils.isEmpty(valueOf)) {
            this.textPaint.setColor(this.textColor);
            this.textPaint.setTextSize(this.textSize);
            float descent = this.textPaint.descent() + this.textPaint.ascent();
            float height = (((float) getHeight()) - descent) / 2.0f;
            canvas.drawText(valueOf, (((float) getWidth()) - this.textPaint.measureText(valueOf)) / 2.0f, height, this.textPaint);
            this.textPaint.setTextSize(this.suffixTextSize);
            canvas.drawText(this.suffixText, (((float) getWidth()) / 2.0f) + this.textPaint.measureText(valueOf) + this.suffixTextPadding, (height + descent) - (this.textPaint.descent() + this.textPaint.ascent()), this.textPaint);
        }
        if (this.arcBottomHeight == 0.0f) {
            double d = (double) (((360.0f - this.arcAngle) / 2.0f) / 180.0f);
            Double.isNaN(d);
            this.arcBottomHeight = (((float) getWidth()) / 2.0f) * ((float) (1.0d - Math.cos(d * 3.141592653589793d)));
        }
        if (!TextUtils.isEmpty(getBottomText())) {
            this.textPaint.setTextSize(this.bottomTextSize);
            canvas.drawText(getBottomText(), (((float) getWidth()) - this.textPaint.measureText(getBottomText())) / 2.0f, (((float) getHeight()) - this.arcBottomHeight) - ((this.textPaint.descent() + this.textPaint.ascent()) / 2.0f), this.textPaint);
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putFloat(INSTANCE_STROKE_WIDTH, getStrokeWidth());
        bundle.putFloat(INSTANCE_SUFFIX_TEXT_SIZE, getSuffixTextSize());
        bundle.putFloat(INSTANCE_SUFFIX_TEXT_PADDING, getSuffixTextPadding());
        bundle.putFloat(INSTANCE_BOTTOM_TEXT_SIZE, getBottomTextSize());
        bundle.putString(INSTANCE_BOTTOM_TEXT, getBottomText());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getTextSize());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat("progress", (float) getProgress());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, getFinishedStrokeColor());
        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR, getUnfinishedStrokeColor());
        bundle.putFloat(INSTANCE_ARC_ANGLE, getArcAngle());
        bundle.putString(INSTANCE_SUFFIX, getSuffixText());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.strokeWidth = bundle.getFloat(INSTANCE_STROKE_WIDTH);
            this.suffixTextSize = bundle.getFloat(INSTANCE_SUFFIX_TEXT_SIZE);
            this.suffixTextPadding = bundle.getFloat(INSTANCE_SUFFIX_TEXT_PADDING);
            this.bottomTextSize = bundle.getFloat(INSTANCE_BOTTOM_TEXT_SIZE);
            this.bottomText = bundle.getString(INSTANCE_BOTTOM_TEXT);
            this.textSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            this.textColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt("progress"));
            this.finishedStrokeColor = bundle.getInt(INSTANCE_FINISHED_STROKE_COLOR);
            this.unfinishedStrokeColor = bundle.getInt(INSTANCE_UNFINISHED_STROKE_COLOR);
            this.suffixText = bundle.getString(INSTANCE_SUFFIX);
            initPainters();
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }
}
