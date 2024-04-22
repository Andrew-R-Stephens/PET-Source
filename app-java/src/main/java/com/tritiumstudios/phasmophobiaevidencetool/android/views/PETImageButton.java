package com.tritiumstudios.phasmophobiaevidencetool.android.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import com.tritiumstudios.phasmophobiaevidencetool.R;

public class PETImageButton extends AppCompatImageButton {

    public PETImageButton(@NonNull Context context) {
        super(context);

        init(context, null);
    }

    public PETImageButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public PETImageButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public void init(@NonNull Context c, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {

        setDefaults();

        if (attrs != null) {
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.PETImageButton);


            setImageResource(
                    a.getResourceId(
                            R.styleable.PETImageButton_PETImageButtonBackground,
                            R.drawable.icon_button_designs));

            setImageLevel(
                    a.getInt(
                            R.styleable.PETImageButton_PETImageButtonType, 0));

            a.recycle();
        }


    }

    private void setDefaults() {
        setLayoutParams(new LinearLayout.LayoutParams(48, 48));
        setBackgroundColor(getResources().getColor(R.color.transparent));
        setScaleType(ScaleType.FIT_CENTER);

        setPaddingDefaults();
        setAdjustViewBounds(true);
    }

    private void setPaddingDefaults() {
        setPadding(
                getPaddingLeft() == 0 ? 8 : getPaddingLeft(),
                getPaddingTop() == 0 ? 8 : getPaddingTop(),
                getPaddingRight() == 0 ? 8 : getPaddingRight(),
                getPaddingBottom() == 0 ? 8 : getPaddingBottom());
    }

}
