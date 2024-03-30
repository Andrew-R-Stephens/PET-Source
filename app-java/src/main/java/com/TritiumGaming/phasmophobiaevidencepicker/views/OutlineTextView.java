package com.TritiumGaming.phasmophobiaevidencepicker.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class OutlineTextView extends AppCompatTextView {

    private final float defaultOutlineWidth = 0f;
    private boolean isDrawing = false;
    private int outlineColor = 0;
    private float outlineWidth = 0f;

    public OutlineTextView(@NonNull Context context) {
        super(context);

        init(context, null);
    }

    public OutlineTextView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public OutlineTextView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public void init(@NonNull Context c, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.OutlineTextView);
            outlineColor = a.getColor(R.styleable.OutlineTextView_outlineColor, getCurrentTextColor());
            outlineWidth = a.getDimension(R.styleable.OutlineTextView_outlineWidth, defaultOutlineWidth);
            a.recycle();
        } else {
            outlineColor = getCurrentTextColor();
            outlineWidth = defaultOutlineWidth;
        }

        setOutlineWidth(TypedValue.COMPLEX_UNIT_PX, outlineWidth);
        setOutlineColor(outlineColor);
    }

    public void setOutlineColor(int color) {
        outlineColor = color;
    }

    public void setOutlineWidth(int unit, float width) {
        outlineWidth = TypedValue.applyDimension(
                unit, width, getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void invalidate() {
        // prevent onDraw.setTextColor force redraw
        if (isDrawing) return;

        super.invalidate();
    }
    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = getPaint();
        if(paint == null) {
            return;
        }

        isDrawing = true;

        paint.setStyle(Paint.Style.FILL);
        super.onDraw(canvas);

        int currentTextColor = getCurrentTextColor();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(outlineWidth);
        setTextColor(outlineColor);
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        setTextColor(currentTextColor);
        super.onDraw(canvas);

        isDrawing = false;
    }

}
